package com.electems.rmc.controller;

import java.util.HashMap;
import java.util.Map;

import com.electems.rmc.dto.UserDataViewDTO;

public class AbstractController {
	protected static  Map<String, UserDataViewDTO> USER_DATA = new HashMap<String, UserDataViewDTO>();
	protected static  Map<String, UserDataViewDTO> USER_SpecificDATA = new HashMap<String, UserDataViewDTO>();

}
