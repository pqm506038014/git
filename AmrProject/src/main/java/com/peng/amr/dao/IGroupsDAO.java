package com.peng.amr.dao;

import java.util.List;

import com.peng.amr.vo.Groups;

public interface IGroupsDAO extends IDAO<Integer, Groups> {
	/**
	 * 根据部门的编号查询权限组的信息
	 * @param did 部门的编号
	 * @return
	 * @throws Exception
	 */
	public List<Groups> findAllByDept(Integer did) throws Exception;
}
