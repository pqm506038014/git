package com.peng.amr.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Purchase implements Serializable {
	private Integer pid;
	private String title;
	private Double total;
	private Integer status;
	private Date pubdate;
	private String note;
	private Emp emp;
	private Integer meid;
	private List<Details> allDetails;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getPubdate() {
		return pubdate;
	}
	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Emp getEmp() {
		return emp;
	}
	public void setEmp(Emp emp) {
		this.emp = emp;
	}
	public Integer getMeid() {
		return meid;
	}
	public void setMeid(Integer meid) {
		this.meid = meid;
	}
	public List<Details> getAllDetails() {
		return allDetails;
	}
	public void setAllDetails(List<Details> allDetails) {
		this.allDetails = allDetails;
	}
	@Override
	public String toString() {
		return "Purchase [pid=" + pid + ", title=" + title + ", total=" + total + ", status=" + status + ", pubdate="
				+ pubdate + ", note=" + note + ", emp=" + emp + ", meid=" + meid + ", allDetails=" + allDetails + "]";
	}
	
}
