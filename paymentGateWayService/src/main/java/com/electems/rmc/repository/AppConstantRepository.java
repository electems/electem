package com.electems.rmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.electems.rmc.model.AppConstant;

public interface AppConstantRepository extends JpaRepository<AppConstant, Long>{
	
	@Query("select a from AppConstant a where a.type = :type")
	List<AppConstant> findByType(@Param("type")String code);
	
	@Query("select a from AppConstant a where a.code = :code")
	List<AppConstant> findByCode(@Param("code")String code);

}
