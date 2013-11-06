package com.example.entity;

/**
 * 代表以下几种:
 * 1.地区
 * 2.行业???
 * 3.公司
 * @author sunqm
 *
 */
public class TypeEntity extends BaseEntity{

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAvergageScore() {
		return avergageScore;
	}

	public void setAvergageScore(int avergageScore) {
		this.avergageScore = avergageScore;
	}

	public int getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(int userNumber) {
		this.userNumber = userNumber;
	}

	private int id = 0;
	
	/**名字*/
	private String name = null;
	
	/**平均得分*/
	private int avergageScore = 0;
	
	/***/
	private int userNumber = 0;
	
}
