package com.electems.rmc.controller;

public class IOTQuery {
	
	public static String AVG_ANALOG_INPUT_DATA_TYPE1 =" select avg(s.a0) as a0, avg(s.a1) as a1,  avg(s.a2) as a2,  avg(s.a3) as a3,  avg(s.a4) as a4,  avg(s.a5) as a5,"
			+ "  avg(s.a6) as a6, avg(s.a7) as a7,  avg(s.a8) as a8,  avg(s.a9) as a9,  avg(s.a10) as a10,  avg(s.a11) as a11,  avg(s.a12) as a12,  avg(s.a13) as a13,  "
			+ " avg(s.a14) as a14,  avg(s.a15) as a15 from t_device_data s where s.device_id = DEVICE_ID and s.d0 = 0 and s.d1 = 0 and s.d2 = 0 and s.d3 = 0 and "
			+ " created_on > now() - interval '12 hour' limit 10";
	
	public static String AVG_ANALOG_INPUT_DATA_TYPE2 =" select avg(s.a0) as a0, avg(s.a1) as a1, avg(s.a2) as a2,  avg(s.a3) as a3,  avg(s.a4)"
			+ " as a4,  avg(s.a5) as a5,  avg(s.a6) as a6,  avg(s.a7) as a7 from t_device_data s where s.device_id = DEVICE_ID and s.d0 = 0 and"
			+ " s.d1 = 0 and s.d2 = 0 and s.d3 = 0 and created_on > now() - interval '12 hour' limit 10 ";

}
