package com.electems.rmc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "T_ORDER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Order extends AbstractAuditingEntity implements Serializable {
private static final long serialVersionUID = -7722544975069356302L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "order_status")
	private String status;		
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "customer_email")
	private String customerEmail;
	
	@Column(name = "customer_firstaddress")
	private String customerFirstAddress;
    
	@Column(name = "customer_secondaddress")
	private String customerSecondtAddress;
	
	@Column(name = "customer_contactNumber")
	private Integer contactNumber;
	
    @Size(max = 250)
    @Column(name = "order_num", length = 14)
    private String orderNumber;
    
    @Column(name = "purchased_date")
	private Date purchasedDate;
    
    @Column(name = "transaction_code")
	private Integer transactionCode;
    
    @Column(name = "transaction_Ids")
   	private String transactionIds;
    
    @Column(name = "amount")
	private Double amount;
    
    @Column(name = "taxable_amount")
	private Integer taxableAmount;
    
    @Column(name = "mail_status")
	private String mailStatus;    
   
   /* @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "LineItem_ID")
	private List<LineItem> lineItem = new ArrayList<LineItem>();*/
    
    @JsonManagedReference("lineitem")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order", orphanRemoval = true)
	private List<LineItem> lineItem = new ArrayList<LineItem>();
    

	public List<LineItem> getLineItem() {
		return lineItem;
	}

	public void setLineItem(List<LineItem> lineItem) {
		this.lineItem = lineItem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerFirstAddress() {
		return customerFirstAddress;
	}

	public void setCustomerFirstAddress(String customerFirstAddress) {
		this.customerFirstAddress = customerFirstAddress;
	}

	public String getCustomerSecondtAddress() {
		return customerSecondtAddress;
	}

	public void setCustomerSecondtAddress(String customerSecondtAddress) {
		this.customerSecondtAddress = customerSecondtAddress;
	}

	public Integer getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Integer contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(Date purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public Integer getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(Integer transactionCode) {
		this.transactionCode = transactionCode;
	}	

	public String getTransactionIds() {
		return transactionIds;
	}

	public void setTransactionIds(String transactionIds) {
		this.transactionIds = transactionIds;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getTaxableAmount() {
		return taxableAmount;
	}

	public void setTaxableAmount(Integer taxableAmount) {
		this.taxableAmount = taxableAmount;
	}

	public String getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

}
