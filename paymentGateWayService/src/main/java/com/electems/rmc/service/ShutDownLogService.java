package com.electems.rmc.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
 
@Service("shutDownLogService")
public class ShutDownLogService {
	
	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * Following Function is used to get the Notification Record based on rule type:2 to send notification 
	 */
	public List<Object[]> fetchNotificationRecordsForRuleTypeTwo(Integer period){
		
		String hql ="select sh.outputBit, sh.deviceId, dir.ruleData, dir.groupId from ShutDownLog sh, DeviceItemRule dir  where sh.createdDate > :date and sh.deviceId = dir.deviceId " +
				" and dir.ruleType = 2 and dir.outputBit = sh.outputBit ";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -200);
		Date date = cal.getTime();
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("date", date);
		
		try {
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	
	/*
	 * Following Function returns record of device shut down in X minutes in order to be notified.
	 */
	public Integer numberOFTimeOutputShutDown(Integer period, Integer deviceId, String outputBit){
		
		String hql ="select sum(sh.isNotify) from ShutDownLog sh where sh.createdDate > :date and sh.deviceId = :deviceId " +
				" and sh.outputBit = :outputBit ";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -200);
		Date date = cal.getTime();
		
		long timezoneAlteredTime = date.getTime() + TimeZone.getTimeZone("Asia/Calcutta").getRawOffset()-
				TimeZone.getTimeZone("EST").getRawOffset();
	    Calendar cSchedStartCal1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
	    cSchedStartCal1.setTimeInMillis(timezoneAlteredTime);
	    date = cSchedStartCal1.getTime();
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("date", date);
		query.setParameter("deviceId", deviceId);
		query.setParameter("outputBit", outputBit);
		
		List result = query.getResultList();
		Long numberOFTimeOutputDown = (Long) result.get(0);
		try {
			//return (Integer) query.getSingleResult();
			return Integer.parseInt(String.valueOf(numberOFTimeOutputDown));
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public Integer updateShutDownLog(Integer period, Integer deviceId, String outputBit){
		
		String hql ="update ShutDownLog set isNotify = 0 where createdDate > :date and deviceId = :deviceId " +
				" and outputBit = :outputBit ";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -200);
		Date date = cal.getTime();
		
		long timezoneAlteredTime = date.getTime() + TimeZone.getTimeZone("Asia/Calcutta").getRawOffset()-
				TimeZone.getTimeZone("EST").getRawOffset();
	    Calendar cSchedStartCal1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
	    cSchedStartCal1.setTimeInMillis(timezoneAlteredTime);
	    date = cSchedStartCal1.getTime();
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("date", date);
		query.setParameter("deviceId", deviceId);
		query.setParameter("outputBit", outputBit);
		
		int result = query.executeUpdate();
		
		try {			
			return result;
		} catch (Exception e) {
			return null;
		}
	}
	
}
