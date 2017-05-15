package com.easyway.face.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.easyway.face.domain.LogData;
import com.easyway.face.service.LogDataService;
import com.easyway.face.util.ExcelUtil;

@Controller
@RequestMapping("/ewface")
public class LogDataController {
	@Autowired
	private LogDataService service;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = {"/menu",""})
	public  String menu(Map<String, Object> model) {
		return "menu";
	}
	
	@RequestMapping(value = {"/list",""})
	public  String list(Map<String, Object> model,@RequestParam(required=false) String startTime,@RequestParam(required=false) String endTime,
			@RequestParam(required=false) String minScore,@RequestParam(required=false) String maxScore) {
		
		Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("startTime", startTime);
		
		
		paramMap.put("endTime", endTime);
		paramMap.put("minScore",minScore);
		paramMap.put("maxScore",maxScore);
		
	    List<LogData>    list  =  service.FindLogDataList(paramMap);
		model.put("dataList", list);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("minScore",minScore);
		model.put("maxScore",maxScore);
		return "list";
	}

	@RequestMapping(value = {"/list2",""},method= RequestMethod.POST)
	public  String list2(Map<String, Object> model) {
		
	    List<LogData>    list  =  service.FindLogDataList(new HashMap<String,String>());
		model.put("dataList", list);
		return "list_2";
	}
	
	@RequestMapping(value="/echarts",method= RequestMethod.POST)
	public String echarts(Map<String,Object> model,@RequestParam(value="enginee",defaultValue="A") String enginee,@RequestParam(value="threshold",defaultValue="60") String threshold){
		
		model.putAll(service.getNumberByScore(enginee,threshold));
		model.put("threshold",threshold);
		model.put("enginee",enginee);
		
		return "echarts" ;
	}
	
	
	@RequestMapping("/getPhoto/{photo}/{id}")
	public void getPhoto(HttpServletResponse response,@PathVariable(value="id") Integer id,@PathVariable(value="photo") String whichPhoto) throws Exception{
		
		LogData  entity = service.getIdPhoto(id);
		byte[] b = null;
		
		if("idPhoto".equals(whichPhoto)){
			b = entity.getIdPhoto();
		}
		
		if("facePhoto".equals(whichPhoto)){
			b = entity.getFacePhoto();
		}
		
		OutputStream os = null;
		response.setContentType("image/jped");
		 try{
			 os = response.getOutputStream();  
				os.write(b);  
		 } catch (IOException e) {
			 e.printStackTrace();
		 }  finally {
			 if(os != null)
			 os.close();
		 } 
		
	}
	
	
	
	@RequestMapping (value="/exportExcel",method= RequestMethod.POST)
	public void  exportExcelH(HttpServletResponse response) throws Exception{
	
		List<Object>    list  =  service.FindDataList(new HashMap<String,String>());
		JSONArray ja = new JSONArray(list);
		
		 Map<String,String> headMap = new LinkedHashMap<String,String>();
        headMap.put("id","ID");
        headMap.put("idName","姓名");
        headMap.put("idSex","身份证性别");
        
        headMap.put("idNo","身份证号");
        headMap.put("tktTrain","车票车次");
        headMap.put("tktTime","车票乘车时间");
        headMap.put("time","核验时间");
        headMap.put("isPass","是否通过");
        headMap.put("threshold","比对阀值");
        headMap.put("score","比对分值");
        headMap.put("enginee","人脸系统");
        headMap.put("idPhoto","身份证照片");
        headMap.put("facePhoto","人脸照片");
        String title = "人脸识别数据";
       
        System.out.println("正在导出xlsx....");
        ExcelUtil.downloadExcelFile(title,headMap,ja,response);
        System.out.println("导出xlsx完成");
	
	/*	   List<LogData>    list  =  service.FindLogDataList(new HashMap<String,String>());
		 	FileOutputStream fileOut = null;  
	        try {  
	              
	            // 创建一个工作薄  
	            HSSFWorkbook wb = new HSSFWorkbook();  
	            //创建一个sheet  
	            HSSFSheet sheet = wb.createSheet("out put excel");  
	  
	            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();  
	            *//** 
	             * 该构造函数有8个参数 
	             * 前四个参数是控制图片在单元格的位置，分别是图片距离单元格left，top，right，bottom的像素距离 
	             * 后四个参数，前连个表示图片左上角所在的cellNum和 rowNum，后天个参数对应的表示图片右下角所在的cellNum和 rowNum， 
	             * excel中的cellNum和rowNum的index都是从0开始的 
	             *  
	             *//*  
	            //图片一导出到单元格B2中  
	            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,  
	                    (short) 1, 1, (short) 2, 2);  
	            //图片二导出到单元格C3到E5中，且图片的left和top距离边框50  
	            HSSFClientAnchor anchor1 = new HSSFClientAnchor(50, 50, 0, 0,  
	                    (short) 2, 2, (short) 5, 5);  
	  
	            // 插入图片  
	            patriarch.createPicture(anchor, wb.addPicture(list.get(0).getIdPhoto(), HSSFWorkbook.PICTURE_TYPE_JPEG));  
	            patriarch.createPicture(anchor1, wb.addPicture(list.get(0).getIdPhoto(), HSSFWorkbook.PICTURE_TYPE_JPEG));  
	  
	            fileOut = new FileOutputStream("E:/output_Excel.xls");  
	            // 写入excel文件  
	            wb.write(fileOut); 
	            wb.close();
	        } catch (IOException io) {  
	            io.printStackTrace();  
	            System.out.println("io erorr : " + io.getMessage());  
	        } finally {  
	            if (fileOut != null) {  
	                try {  
	                    fileOut.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	    }  */
		
	}
}
	
	
	
	
	
