package com.peng.amr.vo;

import java.io.Serializable;

public class Details implements Serializable {
	private Integer did;	//详情的编号
	private Type type;		//所属的大范围的类别
	private Subtype subtype;//所属的子类别
	private Emp emp;		//增加该详情的雇员信息
	private String title;	//名称
	private Double price;	//价格
	private Integer amount;	//数量
	private String photo;	//照片
	private Integer rflag;	//特殊标记，后面要进行归还的标记
	private Res res;		//表示办公用品，作用就是取得或者是修改rid的值
	public Integer getDid() {
		return did;
	}
	public void setDid(Integer did) {
		this.did = did;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public Subtype getSubtype() {
		return subtype;
	}
	public void setSubtype(Subtype subtype) {
		this.subtype = subtype;
	}
	public Emp getEmp() {
		return emp;
	}
	public void setEmp(Emp emp) {
		this.emp = emp;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Integer getRflag() {
		return rflag;
	}
	public void setRflag(Integer rflag) {
		this.rflag = rflag;
	}
	public Res getRes() {
		return res;
	}
	public void setRes(Res res) {
		this.res = res;
	}
	@Override
	public String toString() {
		return "Details [did=" + did + ", type=" + type + ", subtype=" + subtype + ", emp=" + emp + ", title=" + title
				+ ", price=" + price + ", amount=" + amount + ", photo=" + photo + ", rflag=" + rflag + ", res=" + res
				+ "]";
	}
	public Details(Integer did, Type type, Subtype subtype, Emp emp, String title, Double price, Integer amount,
			String photo, Integer rflag, Res res) {
		super();
		this.did = did;
		this.type = type;
		this.subtype = subtype;
		this.emp = emp;
		this.title = title;
		this.price = price;
		this.amount = amount;
		this.photo = photo;
		this.rflag = rflag;
		this.res = res;
	}
	public Details() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
