package com.peng.amr.service;

import java.util.List;

import com.peng.amr.vo.Groups;

public interface IGroupsService {
	/**
	 * 根据部门编号查询权限组信息，调用的是IGroupsDAO的findAllByDept()方法
	 * @param eid	操作人的编号
	 * @param did	部门编号
	 * @return
	 * @throws Exception
	 */
	public List<Groups> findAllByDept(Integer eid,Integer did) throws Exception;
}
