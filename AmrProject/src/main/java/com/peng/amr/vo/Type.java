package com.peng.amr.vo;

import java.io.Serializable;
import java.util.List;

public class Type implements Serializable {
	private Integer tid;//以及类别编号
	private String title;//名称
	private List<Subtype> subtypes;//当前类别下的所有子类别
	private List<Details> details;//每个类别对应的清单详情信息
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Subtype> getSubtypes() {
		return subtypes;
	}
	public void setSubtypes(List<Subtype> subtypes) {
		this.subtypes = subtypes;
	}
	public List<Details> getDetails() {
		return details;
	}
	public void setDetails(List<Details> details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "Type [tid=" + tid + ", title=" + title + ", subtypes=" + subtypes + ", details=" + details + "]";
	}
	public Type(Integer tid, String title, List<Subtype> subtypes, List<Details> details) {
		super();
		this.tid = tid;
		this.title = title;
		this.subtypes = subtypes;
		this.details = details;
	}
	public Type() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
