package com.sun.entity;

/**
 * 用户表
 * 
 * @author sunqm
 * 
 */
public class User extends DbEntity {

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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getSelf_assessment() {
		return self_assessment;
	}

	public void setSelf_assessment(String self_assessment) {
		this.self_assessment = self_assessment;
	}

	public int getPay() {
		return pay;
	}

	public void setPay(int pay) {
		this.pay = pay;
	}

	public float getPay_percentage() {
		return pay_percentage;
	}

	public void setPay_percentage(float pay_percentage) {
		this.pay_percentage = pay_percentage;
	}

	public int getEnvironment() {
		return environment;
	}

	public void setEnvironment(int environment) {
		this.environment = environment;
	}

	public float getEnvironment_percentage() {
		return environment_percentage;
	}

	public void setEnvironment_percentage(float environment_percentage) {
		this.environment_percentage = environment_percentage;
	}

	public int getWelfare() {
		return welfare;
	}

	public void setWelfare(int welfare) {
		this.welfare = welfare;
	}

	public float getWelfare_percentage() {
		return welfare_percentage;
	}

	public void setWelfare_percentage(float welfare_percentage) {
		this.welfare_percentage = welfare_percentage;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	/** 编号 */
	private long id = 0;
	/** 用户名 */
	private String name = null;
	/** 用户密码 */
	private String pwd = null;
	/** 用户邮箱 */
	private String email = null;
	/** 图片地址 */
	private String image_url = null;
	/** 自我评价 */
	private String self_assessment = null;
	/** 薪资 */
	private int pay = 0;
	/** 薪资比例 */
	private float pay_percentage = 0;
	/** 环境压力 */
	private int environment = 0;
	/** 环境压力比例 */
	private float environment_percentage = 0;
	/** 福利 */
	private int welfare = 0;
	/** 福利比例 */
	private float welfare_percentage = 0;
	/** 创建时间 */
	private String create_time = null;

}
