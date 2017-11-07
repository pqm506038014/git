package com.peng.amr.service;

import java.util.Map;

import com.peng.amr.vo.Purchase;

public interface IPurchaseService {
	/**
	 * 实现清单的申请提交，调用的方法是：<br>
	 * <li>IDetailsDAO.doUpdateByPurchase()</li>
	 * <li>IPurchaseDAO.doCreate()</li>
	 * @param eid	操作人的编号
	 * @param vo	保存了申请人信息的vo对象
	 * @return
	 * @throws Exception
	 */
	public boolean add(Integer eid,Purchase vo) throws Exception;
	/**
	 * 实现申请单的查询，调用的是数据层的findAllByEmp()和getAllCountByEmp()方法
	 * @param eid	当前用户的编号
	 * @param currentpage	当前页
	 * @param lineSize	每页显示的数量
	 * @return	返回的数据：<br>
	 * <li>申请单的信息：key=allPurchases,value=IPurchaseDAO.findAllByEmp()</li>
	 * <li>分页的信息：key=pager,value=new Page(currentPage,allPages)</li>
	 * @throws Exception
	 */
	public Map<String, Object> listByEmp(Integer eid,Integer currentPage,Integer lineSize) throws Exception;
	/**
	 * 实现根据申请单编号和用户的编号查询数据，调用的是数据层的findByIdAndEmp()方法
	 * @param eid	操作人的编号
	 * @param pid	申请单的编号
	 * @return	如果有数据返回申请单的详细信息，没有返回null
	 * @throws Exception
	 */
	public Purchase getByEmp(Integer eid,Integer pid) throws Exception;
	/**
	 * 为财务部门查询清单信息，调用数据层的findAllSimpleSplit()和getAllCountSimple()方法
	 * @param eid	当前用户的编号（用于权限验证）
	 * @param currentPage	当前页
	 * @param lineSize		每页显示的数据量
	 * @return	返回的数据：<br>
	 * <li>申请单的信息：key=allPurchases,value=IPurchaseDAO.findAllSimpleSplit()</li>
	 * <li>分页的信息：key=pager,value=new Page(currentPage,allPages)</li>
	 * @throws Exception
	 */
	public Map<String, Object> listSimple(Integer eid,Integer currentPage,Integer lineSize) throws Exception;
	/**
	 * 根据申请单的比那好，进行详细信息的查询，调用的是数据层的findById()方法
	 * @param eid	当前用户的编号
	 * @param pid	申请单的编号
	 * @return
	 * @throws Exception
	 */
	public Purchase show(Integer eid,Integer pid) throws Exception;
}
