package com.peng.amr.vo;

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("serial")
public class Page implements Serializable {
	private Integer cp;//表示当前页
	private Integer allPages; //表示总的页数，在业务层通过总数据量和每页显示的数据量计算出来的
	private int[] pages;//保存页面的数字
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
	 * 判断当前页是否是第一页
	 * @return 如果当前页大于1则返回true，此时在页面要生成有效的超链接，否则返回false,生成导航条
	 */
	public boolean isFirst() {
		return this.cp>1;
	}
	/**
	 * 判断当前页的前面是否还有两页以上
	 * @return 如果有则出现“...”，否则不出现
	 */
	public boolean isHasPre() {
		return this.cp>2;
	}
	/**
	 * 判断当前页是否是最后一页
	 * @return	如果是最后一夜返回false，否则返回 true
	 */
	public boolean isLast() {
		return this.cp<this.allPages;
	}
	/**
	 * 判断当前页后面是否还有两页以上
	 * @return	如果有则返回true，否则返回false
	 */
	public boolean isHasNext() {
		return this.cp<this.allPages-2;
	}
	public int[] getPages() {
		int start = this.cp-2;//表示数组中的第一个数字
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

