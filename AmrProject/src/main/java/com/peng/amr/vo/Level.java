package com.peng.amr.vo;

import java.io.Serializable;

public class Level implements Serializable {
	private Integer lid;
	private String title;
	private Double losal;
	private Double hisal;
	public Integer getLid() {
		return lid;
	}
	public void setLid(Integer lid) {
		this.lid = lid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getLosal() {
		return losal;
	}
	public void setLosal(Double losal) {
		this.losal = losal;
	}
	public Double getHisal() {
		return hisal;
	}
	public void setHisal(Double hisal) {
		this.hisal = hisal;
	}
	public Level() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Level(Integer lid, String title, Double losal, Double hisal) {
		super();
		this.lid = lid;
		this.title = title;
		this.losal = losal;
		this.hisal = hisal;
	}
	@Override
	public String toString() {
		return "Level [lid=" + lid + ", title=" + title + ", losal=" + losal + ", hisal=" + hisal + "]";
	}
	
}
