package com.peng.amr.service;

import java.util.Map;

import com.peng.amr.vo.Emp;

public interface IAdminService {
	/**
	 * 实现登陆操作，调用的是IEmpDAO接口中的findLogin()方法
	 * @param vo 保存了编号和密码的vo类
	 * @return
	 * @throws Exception
	 */
	public boolean login(Emp vo) throws Exception;
	/**
	 * 增加管理员之前的数据准备查询
	 * <li> 查询到雇员级别的信息之后返回</li>
	 * @return 保存了雇员级别信息的数据
	 * @throws Exception
	 */
	public Map<String, Object> addPre() throws Exception;
	/**
	 * 验证雇员的编号信息
	 * @param eid
	 * @return
	 * @throws Exception
	 */
	public boolean checkEid(int eid) throws Exception;
	/**
	 * 实现雇员（管理员）的增加，调用的是数据层的doCreate()方法
	 * @param vo 包含了增加的数据的vo对象
	 * @param eid	操作人的编号
	 * @return
	 * @throws Exception
	 */
	public boolean add(Emp vo,Integer eid) throws Exception;
	/**
	 * 实现管理员的模糊分页查询，调用的方法：
	 * <li>dao层的findAllAdmin()方法，取得管理员的数据</li>
	 * <li>dao层的getAllAdminCount()方法，取得查询到的数据量</li>
	 * @param eid
	 * @param column
	 * @param keyWord
	 * @param currentPage
	 * @param lineSize
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> list(int eid,String column,String keyWord,int currentPage,int lineSize) throws Exception;
}
