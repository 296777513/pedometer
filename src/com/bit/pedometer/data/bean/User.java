package com.bit.pedometer.data.bean;

import java.io.Serializable;
import java.sql.Blob;

import cn.bmob.v3.BmobObject;

public class User extends BmobObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String sex;
	private int weight;
	private int sensitivity;
	private int step_length;
	private int groupId;
	private byte[] picture;
	private int today_step;

	public int getToday_step() {
		return today_step;
	}

	public void setToday_step(int today_step) {
		this.today_step = today_step;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}

	public int getStep_length() {
		return step_length;
	}

	public void setStep_length(int step_length) {
		this.step_length = step_length;
	}

}
