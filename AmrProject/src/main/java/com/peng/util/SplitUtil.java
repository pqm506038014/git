package com.peng.util;
import com.peng.amr.action.abs.AbstractAction;
public class SplitUtil {
	private int cp = 1;  //默认的当前页
	private int ls = 3;  //指定每页显示的数据量
	private String col;  //字段
	private String kw;   //关键字
	private AbstractAction action; //因为要调用AbstractAction的.getDefaultColumn()，取得默认的字段
	@Override
	public String toString() {
		return "SplitUtil [cp=" + cp + ", ls=" + ls + ", col=" + col + ", kw=" + kw + ", action=" + action + "]";
	}
	public SplitUtil(AbstractAction action) {// 取得公共的Action
		this.action = action;
	} 
	public void setCp(String cp) {
		try {
			this.cp = Integer.parseInt(cp);//如果出现了异常则按照默认的值处理
		} catch (Exception e) {
		}
	}

	public void setLs(String ls) {
		try {
			this.ls = Integer.parseInt(ls);//如果出现了异常则按照默认的值处理
		} catch (Exception e) {
		}
	}

	public void setCol(String col) {
		if (col == null || "".equals(col)) {   //如果分页中没有指定模糊查询的字段则使用默认的
			//这里Action的父类的一个方法，是一个抽象方法，该抽象方法是子类实现的
			//子类就可以根据自己的需要定义出默认的模糊查询字段
			this.col = this.action.getDefaultColumn();
		} else {
			 this.col = col;
		}
	}
	
	public void setKw(String kw) {//如果传递的kw是空字符串则设置为null
		if ("".equals(kw)) {
			this.kw = null;
		} else {
			this.kw = kw;
		}
	}

	public int getCurrentPage() {
		return cp;
	}

	public int getLineSize() {
		return ls;
	}

	public String getColumn() {
		return col;
	}

	public String getKeyword() {
		return kw;
	}
    /**
	public void setAttribute(HttpServletRequest request, Object allRecorders,
			String url, String paramA, Object valueA, String paramB,
			Object valueB) {
			request.setAttribute("allRecorders", allRecorders);
			request.setAttribute("url", this.action.getMsg(url));
			request.setAttribute("paramName", paramA);
			request.setAttribute("paramValue", valueA);
			request.setAttribute("paramNameB", paramB);
			request.setAttribute("paramValueB", valueB);
	}
	*/
}
