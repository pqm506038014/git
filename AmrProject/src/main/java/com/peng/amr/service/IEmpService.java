package com.peng.amr.service;

import java.util.Map;

import com.peng.amr.vo.Emp;

public interface IEmpService {
	/**
	 * 增加雇员之前的数据准备查询
	 * <li> 查询到雇员级别的信息和雇员的部门信息之后返回</li>
	 * @return 保存了雇员级别信息的数据
	 * @throws Exception
	 */
	public Map<String, Object> addPre() throws Exception;
	/**
	 * 验证雇员的编号信息
	 * @param eid	修改后的雇员编号
	 * @return
	 * @throws Exception
	 */
	public boolean checkEid(int eid) throws Exception;
	/**
	 * 实现雇员的增加，调用的是数据层的doCreate()方法
	 * @param vo 包含了增加的数据的vo对象
	 * @param eid	操作人的编号
	 * @return
	 * @throws Exception
	 */
	public boolean add(Emp vo,Integer eid) throws Exception;
	/**
	 * 模糊分页查询雇员的信息的列表
	 * @param eid	操作人的编号，用于权限验证
	 * @param column	模糊查询的字段
	 * @param keyWord	模糊查询的关键字
	 * @param currentPage	当前页
	 * @param lineSize	每页显示的数据量
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> list(Integer eid,String column,String keyWord,Integer currentPage,Integer lineSize) throws Exception;
	/**
	 * 实现数据的回显查询
	 * @param eid	需要被修改的雇员编号
	 * @return	返回的数据：<br>
	 * <li>key=allLevels,value=ILevelDAO.findAll()</li>
	 * <li>key=allDepts,value=IDeptDAO.findAllBySflag()</li>
	 * <li>key=emp,value=IEmpDAO.findById()</li>
	 * @throws Exception
	 */
	public Map<String, Object> editPre(Integer eid) throws Exception;
	/**
	 * 实现雇员信息的编辑操作
	 * @param vo	包含了编辑信息的vo对象
	 * @param eid	操作人的编号
	 * @return
	 * @throws Exception
	 */
	public boolean edit(Emp vo,Integer eid) throws Exception;
}
