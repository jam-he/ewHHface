package com.easyway.face.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.easyway.face.domain.LogData;

public interface LogDataDao extends PagingAndSortingRepository<LogData, String>,JpaSpecificationExecutor<LogData>{
	

	LogData findById(Integer id);

	List<LogData> findByTime(Map<String, Object> model);
	
	//时间范围 和 比对分值范围查询
	List<LogData> findByTimeBetweenAndScoreBetween(String startTime,
			String endTime, float minScore, float maxScore);
	
	@Query("select count(*) from LogData where enginee = ?1 and (score between ?2 and ?3) ")
	Long findCountByScoreAndEnginee(String enginee,float f, float g);
	
	@Query("select count(*) from LogData where enginee = ?1 and score < ?2")
	Long findCountByScoreLessThanThreshold(String enginee,float f);
	@Query("select count(*) from LogData where enginee = ?1 and score  >= ?2")
	Long findCountByScoreMoreThanThreshold(String enginee,float f);

	List<Object> findByTimeBetween(String startTime, String endTime);

}
