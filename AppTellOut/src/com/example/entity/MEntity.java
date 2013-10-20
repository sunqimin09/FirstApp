package com.example.entity;

public class MEntity {
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMy_type() {
		return my_type;
	}

	public void setMy_type(String my_type) {
		this.my_type = my_type;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	private long id;
	
	private String name;
	/**我所属的公司，行业*/
	private String my_type;
	
	private int score;
	
	private int peopleNum;
	
}
