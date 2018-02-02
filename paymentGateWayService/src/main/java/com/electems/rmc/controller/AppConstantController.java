package com.electems.rmc.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.electems.rmc.model.AppConstant;
import com.electems.rmc.service.AppConstantService;

@Controller
@RequestMapping("/rest/appConstant")
public class AppConstantController {
	
	@Inject
	AppConstantService appConstantService;

	@RequestMapping(value = "/claimYears", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppConstant>> getClaimYears() throws Exception {
		List<AppConstant> list = appConstantService.findClaimYears();
		return new ResponseEntity<List<AppConstant>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/cutOffPerc", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppConstant>> getCutOffPerc() throws Exception {
		List<AppConstant> list = appConstantService.getCutOffPerc();
		return new ResponseEntity<List<AppConstant>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppConstant>> getAllAppConstants() throws Exception {
		List<AppConstant> list = appConstantService.getAllAppConstants();
		return new ResponseEntity<List<AppConstant>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppConstant> saveLocation(@RequestBody AppConstant appConstant) throws Exception {
		appConstant = appConstantService.saveAppconstant(appConstant);
		return new ResponseEntity<AppConstant>(appConstant, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getDeviceTypeList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppConstant>> getDeviceTypeList() throws Exception {
		List<AppConstant> list = appConstantService.getDeviceTypeList();
		return new ResponseEntity<List<AppConstant>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getSensorsTypeList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppConstant>> getSensorsTypeList() throws Exception {
		List<AppConstant> list = appConstantService.getSensorsTypeList();
		return new ResponseEntity<List<AppConstant>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getInstallRateTypeList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppConstant>> getInstallRateTypeList() throws Exception {
		List<AppConstant> list = appConstantService.getInstallRateTypeList();
		return new ResponseEntity<List<AppConstant>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getTaxRate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppConstant>> getTaxRate() throws Exception {
		List<AppConstant> appConstants = appConstantService.getTaxRate();
		return new ResponseEntity<List<AppConstant>>(appConstants, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getPaymentParams", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppConstant>> getPaymentParams() throws Exception {
		List<AppConstant> appConstants = appConstantService.getPaymentParams();
		return new ResponseEntity<List<AppConstant>>(appConstants, HttpStatus.OK);
	}
}
