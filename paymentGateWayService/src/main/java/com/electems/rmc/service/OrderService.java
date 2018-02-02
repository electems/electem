package com.electems.rmc.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.electems.rmc.model.AppConstant;
import com.electems.rmc.model.LineItem;
import com.electems.rmc.model.Order;
import com.electems.rmc.repository.OrderRepository;

@Component("orderService")
public class OrderService {
	
	@Inject
	OrderRepository orderRepository;
	
	@Inject
    private SpringTemplateEngine templateEngine;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	AppConstantService appConstantService;
	
	public Order saveOrder(Order order){
		if (order.getId() == null) {
			order = entityManager.merge(order);
		}
		order = orderRepository.saveAndFlush(order);
		return order;
	}

	public String generateOrderNumber() {
		Calendar cal = Calendar.getInstance();
		Integer year = cal.get(Calendar.YEAR) - 2000;
		Integer month = cal.get(Calendar.MONTH) + 1;
		Integer date = cal.get(Calendar.DATE);
		DecimalFormat twoDigits = new DecimalFormat("00");

		String prefix = "EM-CO"+year.toString() + twoDigits.format(month) + twoDigits.format(date) + "-";
		String prefixMatch = prefix + "%";
		Query q = entityManager.createQuery("select count(o) from Order o where o.orderNumber like :x");
		q.setParameter("x", prefixMatch);
		Long count = (Long) q.getSingleResult();
		if (count != null) {
			count++;
			DecimalFormat formatter = new DecimalFormat("0000");
			prefix += formatter.format(count);

			return prefix;
		}
		return null;
	}
	
	/*
	 * Sending Mail to the customer based on status
	 */
	public List<Order> fetchByMailStatus(String mailStatus){
		
		String hql ="select sh from Order sh  where sh.mailStatus = :mailStatus ";
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("mailStatus", mailStatus);
		
		try {
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	
	public Object fetchByTranstionID(String id){
		
		String hql ="select sh from Order sh  where sh.transactionIds = :id ";
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("id", id);
		
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public String generatePDF(String transactionIds){
		
		Order order = (Order)fetchByTranstionID(transactionIds);
		
		Integer deviceQuantity = null;
		Integer sensorQuantity = null;
		Integer taxPerce = null;
		List<AppConstant> installRate = appConstantService.getInstallRateTypeList();
		for (AppConstant appConstant : installRate) {
			if (appConstant.getCode().equalsIgnoreCase("DEVICE_QTY")) {
				deviceQuantity = Integer.valueOf(appConstant.getValue());
			}
			if (appConstant.getCode().equalsIgnoreCase("SENSORS_QTY")) {
				sensorQuantity = Integer.valueOf(appConstant.getValue());
			}
			
		}
		
		List<AppConstant> appConstants = appConstantService.getTaxRate();
		for (AppConstant appConstant : appConstants) {
			if (appConstant.getCode().equalsIgnoreCase("TAX_RATE")) {
				taxPerce = Integer.valueOf(appConstant.getValue());
			}
		}
		
		if(order != null){
			long total =0l;
			long installationCost =0l;
			long taxRate = 0l;
			long grandTotal = 0l;
			for (LineItem lineItem : order.getLineItem()) {
				long result =lineItem.getItemQuantity() * lineItem.getItemUnitRate();
				String[] tempStringArray = lineItem.getItemName().split(":");
				if("DEVICE".equalsIgnoreCase(tempStringArray[0])){
					long installCost = lineItem.getItemQuantity() * deviceQuantity;
					installationCost = installationCost + installCost;
				} else if("SENSORS".equalsIgnoreCase(tempStringArray[0])){
					long installCost = lineItem.getItemQuantity() * sensorQuantity;
					installationCost = installationCost + installCost;
				}

				total = total + result;
				 
			}
			taxRate = (((total + installationCost) * taxPerce)/100);
			grandTotal = total + installationCost + taxRate;
			
			String totalAmountInWords = convert((int)grandTotal);
			
			String pattern = "MM/dd/yyyy hh:mm";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			String orderDate = simpleDateFormat.format(order.getCreatedDate());
			
			Locale locale = Locale.forLanguageTag("en");
	        Context context = new Context(locale);
	        context.setVariable("orderId", order.getTransactionIds());
	        context.setVariable("orderDate", orderDate);
	        context.setVariable("analogData", order.getLineItem());
	        context.setVariable("name", order.getCustomerName());
	        context.setVariable("address", order.getCustomerFirstAddress());
	        context.setVariable("contactNum", order.getContactNumber());
	        context.setVariable("installCost", "₹ "+installationCost);
	        context.setVariable("totalSum", "₹ "+total);
	        context.setVariable("taxes", "₹ "+taxRate);
	        context.setVariable("grandTotal", "₹ "+grandTotal);
	        context.setVariable("totalAmountInWords", totalAmountInWords);
	        String content = templateEngine.process("orderSummary", context);
	        
	        File newHtmlFile = new File("C:/project/orderSummary.html");
	        String filepath = null;
			try {
				FileUtils.writeStringToFile(newHtmlFile, content);
				filepath = executeShellCommand();
			} catch (IOException e) {
				
			}
			return filepath;
		}
		return "";
	}
	
	
	private String executeShellCommand() {
		String command = null;
			
		command="wkhtmltopdf C:/project/orderSummary.html --javascript-delay 5000 "
				+ "C:/project/orderSummary.pdf";
		
		 try {
			 
			 Runtime rt = Runtime.getRuntime();
			 Process processObj = rt.exec(command);
			 InputStream stdin = processObj.getErrorStream();
			 InputStreamReader isr = new InputStreamReader(stdin);
			 BufferedReader br = new BufferedReader(isr);
			 String myoutput = "";
			 while ((myoutput=br.readLine()) != null) {
				 myoutput = myoutput+"\n";
			 }
			 
		 }catch(Exception e){
			 System.out.println(e.getMessage());
		 }
		return "C:/project/orderSummary.pdf";
	}
	
	
	String string;
	String st1[] = { "", "one", "two", "three", "four", "five", "six", "seven",
			"eight", "nine", };
	String st2[] = { "hundred", "thousand", "lakh", "crore" };
	String st3[] = { "ten", "eleven", "twelve", "thirteen", "fourteen",
			"fifteen", "sixteen", "seventeen", "eighteen", "ninteen", };
	String st4[] = { "twenty", "thirty", "fourty", "fifty", "sixty", "seventy",
			"eighty", "ninty" };

	public String convert(int number) {
		int n = 1;
		int word;
		string = "";
		while (number != 0) {
			switch (n) {
			case 1:
				word = number % 100;
				pass(word);
				if (number > 100 && number % 100 != 0) {
					show("and ");
				}
				number /= 100;
				break;

			case 2:
				word = number % 10;
				if (word != 0) {
					show(" ");
					show(st2[0]);
					show(" ");
					pass(word);
				}
				number /= 10;
				break;

			case 3:
				word = number % 100;
				if (word != 0) {
					show(" ");
					show(st2[1]);
					show(" ");
					pass(word);
				}
				number /= 100;
				break;

			case 4:
				word = number % 100;
				if (word != 0) {
					show(" ");
					show(st2[2]);
					show(" ");
					pass(word);
				}
				number /= 100;
				break;

			case 5:
				word = number % 100;
				if (word != 0) {
					show(" ");
					show(st2[3]);
					show(" ");
					pass(word);
				}
				number /= 100;
				break;

			}
			n++;
		}
		return string;
	}

	public void pass(int number) {
		int word, q;
		if (number < 10) {
			show(st1[number]);
		}
		if (number > 9 && number < 20) {
			show(st3[number - 10]);
		}
		if (number > 19) {
			word = number % 10;
			if (word == 0) {
				q = number / 10;
				show(st4[q - 2]);
			} else {
				q = number / 10;
				show(st1[word]);
				show(" ");
				show(st4[q - 2]);
			}
		}
	}

	public void show(String s) {
		String st;
		st = string;
		string = s;
		string += st;
	}

}
