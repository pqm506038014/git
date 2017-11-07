package com.peng.amr.dao;

import java.util.List;
import java.util.Map;

import com.peng.amr.vo.Purchase;

public interface IPurchaseDAO extends IDAO<Integer, Purchase> {
	/**
	 * 实现查询当前用户的所有申请单信息
	 * @param paramMap	保存了查询条件的map集合
	 * @return
	 * @throws Exception
	 */
	public List<Purchase> findAllByEmp(Map<String, Object> paramMap) throws Exception;
	/**
	 * 所有申请记录的个数统计
	 * @param eid	当前用户的编号
	 * @return
	 * @throws Exception
	 */
	public int getAllCountByEmp(Integer eid) throws Exception;
	/**
	 * 根据申请单的编号查询信息
	 * @param pid	申请单的编号
	 * @param eid	雇员的编号
	 * @return
	 * @throws Exception
	 */
	public Purchase findByIdAndEmp(Integer pid,Integer eid) throws Exception;
	/**
	 * 为财务部门分页查询所有的申请单信息
	 * @param start	表示从指定的行开始查询
	 * @param lineSize	每页显示的数据量
	 * @return
	 * @throws Exception
	 */
	public List<Purchase> findAllSimpleSplit(Integer start,Integer lineSize) throws Exception;
	/**
	 * 为财务部门统计查询到的数据量
	 * @return
	 * @throws Exception
	 */
	public int getAllCountSimple() throws Exception;
}
