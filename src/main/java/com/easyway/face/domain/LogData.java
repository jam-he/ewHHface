package com.easyway.face.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="log")
public class LogData {
	
	@Id
	private Integer id;
	@Column
	private String idName; 	 			//身份证姓名
	@Column
	private String idSex; 	    			//身份证性别
	@Column
	private String idNo;	   				//身份证号
	@Column
	private byte[] idPhoto;     				//身份证相片
	@Column
	private String tktTrain ; 			//车票车次
	@Column
	private String tktTime; 				//车票乘车时间
	@Column
	private String time;					//核验时间
	@Column
	private Integer isPass;				//是否通过
	@Column
	private byte[] facePhoto;			//人脸相片
	@Column
	private float threshold;				//比对阀值
	@Column
	private float score;					//比对分值
	@Column
	private String enginee;				//人脸系统
	
	
	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	public String getIdSex() {
		return idSex;
	}
	public void setIdSex(String idSex) {
		this.idSex = idSex;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	@Lob
	@Basic(fetch=FetchType.LAZY) //访问这个属性的时候才会加载进内存
	public byte[] getIdPhoto() {
		return idPhoto;
	}
	public void setIdPhoto(byte[] idPhoto) {
		this.idPhoto = idPhoto;
	}
	@Lob
	@Basic(fetch=FetchType.LAZY) //访问这个属性的时候才会加载进内存
	public byte[] getFacePhoto() {
		return facePhoto;
	}
	public void setFacePhoto(byte[] facePhoto) {
		this.facePhoto = facePhoto;
	}
	public String getTktTrain() {
		return tktTrain;
	}
	public void setTktTrain(String tktTrain) {
		this.tktTrain = tktTrain;
	}
	public String getTktTime() {
		return tktTime;
	}
	public void setTktTime(String tktTime) {
		this.tktTime = tktTime;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getIsPass() {
		return isPass;
	}
	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}

	public float getThreshold() {
		return threshold;
	}
	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getEnginee() {
		return enginee;
	}
	public void setEnginee(String enginee) {
		this.enginee = enginee;
	}
	
	
}
