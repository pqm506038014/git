package com.peng.util;
import com.peng.amr.action.abs.AbstractAction;
public class SplitUtil {
	private int cp = 1;  //Ĭ�ϵĵ�ǰҳ
	private int ls = 3;  //ָ��ÿҳ��ʾ��������
	private String col;  //�ֶ�
	private String kw;   //�ؼ���
	private AbstractAction action; //��ΪҪ����AbstractAction��.getDefaultColumn()��ȡ��Ĭ�ϵ��ֶ�
	@Override
	public String toString() {
		return "SplitUtil [cp=" + cp + ", ls=" + ls + ", col=" + col + ", kw=" + kw + ", action=" + action + "]";
	}
	public SplitUtil(AbstractAction action) {// ȡ�ù�����Action
		this.action = action;
	} 
	public void setCp(String cp) {
		try {
			this.cp = Integer.parseInt(cp);//����������쳣����Ĭ�ϵ�ֵ����
		} catch (Exception e) {
		}
	}

	public void setLs(String ls) {
		try {
			this.ls = Integer.parseInt(ls);//����������쳣����Ĭ�ϵ�ֵ����
		} catch (Exception e) {
		}
	}

	public void setCol(String col) {
		if (col == null || "".equals(col)) {   //�����ҳ��û��ָ��ģ����ѯ���ֶ���ʹ��Ĭ�ϵ�
			//����Action�ĸ����һ����������һ�����󷽷����ó��󷽷�������ʵ�ֵ�
			//����Ϳ��Ը����Լ�����Ҫ�����Ĭ�ϵ�ģ����ѯ�ֶ�
			this.col = this.action.getDefaultColumn();
		} else {
			 this.col = col;
		}
	}
	
	public void setKw(String kw) {//������ݵ�kw�ǿ��ַ���������Ϊnull
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
