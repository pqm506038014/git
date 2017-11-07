package com.peng.amr.dao;

import java.util.List;

import com.peng.amr.vo.Subtype;

public interface ISubtypeDAO extends IDAO<String, Subtype> {
	/**
	 * 实现根据父类别编号查询子类别信息
	 * @param tid	父类别编号
	 * @return
	 * @throws Exception
	 */
	public List<Subtype> findAllByType(Integer tid) throws Exception;
}
