package com.electems.rmc.service;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.electems.rmc.model.AppConstant;
import com.electems.rmc.repository.AppConstantRepository;

@Service("appConstantService")
public class AppConstantService {
	
	@Inject
	AppConstantRepository appConstantRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<AppConstant> findClaimYears(){
		return appConstantRepository.findByCode("claimYear");
	}
	
	public List<AppConstant> getCutOffPerc(){
		return appConstantRepository.findByType("cutoffYear");
	}
	
	public List<AppConstant> getAllAppConstants(){
		String hql ="";
		hql = "select c from AppConstant c order by description";
		Query query = entityManager.createQuery(hql);
		return query.getResultList();
	}
	
	public AppConstant saveAppconstant(AppConstant appConstant){
		if (appConstant.getId() == null) {
			appConstant = entityManager.merge(appConstant);
		}
		appConstant = appConstantRepository.saveAndFlush(appConstant);
		return appConstant;
	}
	
	
	public List<AppConstant> getFileUploadPath(){
		return appConstantRepository.findByType("fileUploadPath");
	}
	
	public List<AppConstant> getEmail(){
		return appConstantRepository.findByType("email");
	}
	
	public List<AppConstant> getDeviceTypeList(){
		return appConstantRepository.findByType("DEVICE");
	}
	
	public List<AppConstant> getSensorsTypeList(){
		return appConstantRepository.findByType("SENSORS");
	}
	
	public List<AppConstant> getInstallRateTypeList(){
		return appConstantRepository.findByType("INSTALL_RATE");
	}
	
	public List<AppConstant> getTaxRate(){
		return appConstantRepository.findByType("TAX_RATE_PERC");
	}
	
	public List<AppConstant> getPaymentParams(){
		return appConstantRepository.findByType("PAYMENT_PARAMS");
	}
}
