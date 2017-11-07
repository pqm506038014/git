package com.peng.amr.service;

import java.util.List;

import com.peng.amr.vo.Dept;

public interface IDeptService {
	/**
	 * 查询所有的部门信息，调用的是dao层的findall()方法
	 * @param eid	当前雇员的编号，用于权限验证
	 * @return
	 * @throws Exception
	 */
	public List<Dept> list(Integer eid) throws Exception;
	/**
	 * 实现部门信息的修改
	 * @param eid	操作人的编号，实现权限的验证
	 * @param vo	要修改的数据
	 * @return
	 * @throws Exception
	 */
	public boolean edit(Integer eid,Dept vo) throws Exception; 
}
