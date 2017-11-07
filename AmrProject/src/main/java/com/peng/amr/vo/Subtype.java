package com.peng.amr.vo;

import java.io.Serializable;
import java.util.List;

public class Subtype implements Serializable {
	private Integer stid;//类别的编号
	private String title;//名称
	private Type type;	//所属的父类别
	private List<Details> allDetails;//有多个详情信息
	public Integer getStid() {
		return stid;
	}
	public void setStid(Integer stid) {
		this.stid = stid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public List<Details> getAllDetails() {
		return allDetails;
	}
	public void setAllDetails(List<Details> allDetails) {
		this.allDetails = allDetails;
	}
	@Override
	public String toString() {
		return "Subtype [stid=" + stid + ", title=" + title + ", type=" + type + ", allDetails=" + allDetails + "]";
	}
	public Subtype(Integer stid, String title, Type type, List<Details> allDetails) {
		super();
		this.stid = stid;
		this.title = title;
		this.type = type;
		this.allDetails = allDetails;
	}
	public Subtype() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
