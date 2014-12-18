package com.example.pedometer.model;

public class Group {

	private int ID;
	private int total_number;
	private int member_number;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getAverage_number() {
		return total_number;
	}

	public void setAverage_number(int total_number) {
		this.total_number = total_number;
	}

	public int getMember_number() {
		return member_number;
	}

	public void setMember_number(int member_number) {
		this.member_number = member_number;
	}

}
