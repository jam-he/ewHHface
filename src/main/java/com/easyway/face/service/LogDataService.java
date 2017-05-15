package com.easyway.face.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyway.face.dao.LogDataDao;
import com.easyway.face.domain.LogData;

@Service
public class LogDataService {
	@Autowired
	private LogDataDao dao;

	public List<LogData> FindAllLogData() {
		
		List<LogData> list = (List<LogData>)dao.findAll();
		return list;
	}

	public LogData getIdPhoto(Integer id) {
		
		return dao.findById(id);
		
	}

	public List<LogData> FindLogDataList(Map<String, String> map) {
		
		String startTime = map.get("startTime");
		String endTime =map.get("endTime");
		
		float minScore = 0;
		float maxScore = 100;
		
		
		if(map.get("startTime")==null || "".equals(map.get("startTime"))){
			startTime =	 "1970-01-01 00:00:00";
		}
		if(map.get("endTime")==null || "".equals(map.get("endTime"))){
			endTime = "2999-01-01 00:00:00";
		}
		if(map.get("minScore")!=null && !"".equals((String)map.get("minScore"))){
			minScore =Float.valueOf(map.get("minScore"));
		}
		if(map.get("maxScore")!=null && !"".equals((String)map.get("maxScore"))){
			maxScore =	Float.valueOf(map.get("maxScore"));
		}
		
		List<LogData> list = (List<LogData>) dao.findByTimeBetweenAndScoreBetween(startTime,endTime,minScore,maxScore);
		return list;
		
	}
	
public List<Object> FindDataList(Map<String, String> map) {
		
		String startTime = map.get("startTime");
		String endTime =map.get("endTime");
		
/*		float minScore = 0;
		float maxScore = 100;
		*/
		
		if(map.get("startTime")==null || "".equals(map.get("startTime"))){
			startTime =	 "1970-01-01 00:00:00";
		}
		if(map.get("endTime")==null || "".equals(map.get("endTime"))){
			endTime = "2999-01-01 00:00:00";
		}
/*		if(map.get("minScore")!=null && !"".equals((String)map.get("minScore"))){
			minScore =Float.valueOf(map.get("minScore"));
		}
		if(map.get("maxScore")!=null && !"".equals((String)map.get("maxScore"))){
			maxScore =	Float.valueOf(map.get("maxScore"));
		}*/
		
		List<Object> list = dao.findByTimeBetween(startTime,endTime);
		
		return list;
		
	}

	public Map<String,Object> getNumberByScore(String enginee,String threshold) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		Long number = null;
		for(int i=0;i<10;i++){
			number   = dao.findCountByScoreAndEnginee(enginee,(float)i*10,(float)((i+1)*10-1));
			map.put("group"+i, number);
		}
		
		number = dao.findCountByScoreLessThanThreshold(enginee,Float.parseFloat(threshold));
		map.put("group10",number );
		number = dao.findCountByScoreMoreThanThreshold(enginee,Float.parseFloat(threshold));
		map.put("group11",number );
		
		return map;
	}
	
}
