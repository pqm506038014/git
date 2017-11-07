package com.peng.amr.vo;

import java.io.Serializable;
import java.util.List;

public class Groups implements Serializable {
	private Integer gid;
	private String title;
	private String type;
	private List<Dept> allDepts;
	private List<Action> allActions;
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Dept> getAllDepts() {
		return allDepts;
	}
	public void setAllDepts(List<Dept> allDepts) {
		this.allDepts = allDepts;
	}
	public List<Action> getAllActions() {
		return allActions;
	}
	public void setAllActions(List<Action> allActions) {
		this.allActions = allActions;
	}
	public Groups(Integer gid, String title, String type, List<Dept> allDepts, List<Action> allActions) {
		super();
		this.gid = gid;
		this.title = title;
		this.type = type;
		this.allDepts = allDepts;
		this.allActions = allActions;
	}
	public Groups() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Groups [gid=" + gid + ", title=" + title + ", type=" + type + ", allDepts=" + allDepts + ", allActions="
				+ allActions + "]";
	}
	
}
