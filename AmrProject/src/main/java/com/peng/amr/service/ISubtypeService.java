package com.peng.amr.service;

import java.util.List;

import com.peng.amr.vo.Subtype;

public interface ISubtypeService {
	/**
	 * 商品的分类列表，即子类别列表
	 * @param tid	父类别编号
	 * @return
	 * @throws Exception
	 */
	public List<Subtype> list(Integer tid) throws Exception;
}
