package com.peng.amr.vo;

import java.io.Serializable;
import java.util.Date;

public class Res implements Serializable {
	private Integer rid;
	private Type type;
	private Subtype subtype;
	private String title;
	private Double price;
	private Date indate;
	private String photo;
	private Integer rflag;
	private Integer amount;
	public Integer getRid() {
		return rid;
	}
	public void setRid(Integer rid) {
		this.rid = rid;
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
	public Date getIndate() {
		return indate;
	}
	public void setIndate(Date indate) {
		this.indate = indate;
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
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "Res [rid=" + rid + ", type=" + type + ", subtype=" + subtype + ", title=" + title + ", price=" + price
				+ ", indate=" + indate + ", photo=" + photo + ", rflag=" + rflag + ", amount=" + amount + "]";
	}
	
}
