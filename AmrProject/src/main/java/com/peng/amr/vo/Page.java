package com.peng.amr.vo;

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("serial")
public class Page implements Serializable {
	private Integer cp;//��ʾ��ǰҳ
	private Integer allPages; //��ʾ�ܵ�ҳ������ҵ���ͨ������������ÿҳ��ʾ�����������������
	private int[] pages;//����ҳ�������
	private Integer count;
	
	public Page(Integer cp, Integer allPages) {
		this.cp = cp;
		this.allPages = allPages;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getCp() {
		return cp;
	}
	public void setCp(Integer cp) {
		this.cp = cp;
	}
	public Integer getAllPages() {
		return allPages;
	}
	public void setAllPages(Integer allPages) {
		this.allPages = allPages;
	}
	
	public Page(Integer cp, Integer allPages,Integer count) {
		this.cp = cp;
		this.allPages = allPages;
		this.count=count;
	}
	public Page() {
	}
	
	public void setPages(int[] pages) {
		this.pages = pages;
	}
	
	@Override
	public String toString() {
		return "Pages [cp=" + cp + ", allPages=" + allPages + ", pages=" + Arrays.toString(getPages()) + ", count=" + count
				+ "]";
	}
	/**
	 * �жϵ�ǰҳ�Ƿ��ǵ�һҳ
	 * @return �����ǰҳ����1�򷵻�true����ʱ��ҳ��Ҫ������Ч�ĳ����ӣ����򷵻�false,���ɵ�����
	 */
	public boolean isFirst() {
		return this.cp>1;
	}
	/**
	 * �жϵ�ǰҳ��ǰ���Ƿ�����ҳ����
	 * @return ���������֡�...�������򲻳���
	 */
	public boolean isHasPre() {
		return this.cp>2;
	}
	/**
	 * �жϵ�ǰҳ�Ƿ������һҳ
	 * @return	��������һҹ����false�����򷵻� true
	 */
	public boolean isLast() {
		return this.cp<this.allPages;
	}
	/**
	 * �жϵ�ǰҳ�����Ƿ�����ҳ����
	 * @return	������򷵻�true�����򷵻�false
	 */
	public boolean isHasNext() {
		return this.cp<this.allPages-2;
	}
	public int[] getPages() {
		int start = this.cp-2;//��ʾ�����еĵ�һ������
		int end = this.cp+2;
		if(start<1) {
			start = 1;
		}
		if(end >this.allPages) {
			end = this.allPages;
		}
		int result = end-start+1;
		this.pages = new int[result];
		for(int i = 0;i<result;i++) {
			this.pages[i] = start++;
		}
		return pages;
	}
}

