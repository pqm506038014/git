package com.peng.amr.dao;

import java.util.List;

import com.peng.amr.vo.Action;

public interface IActionDAO extends IDAO<Integer, Action> {
	/**
	 * 根据权限组编号查询权限信息
	 * @param gid 权限组编号
	 * @return
	 * @throws Exception
	 */
	public List<Action> findAllByGroups(Integer gid) throws Exception;
	/**
	 * 根据部门和权限编号查询指定的权限数据
	 * @param did 部门的编号
	 * @param actid 权限的编号
	 * @return
	 * @throws Exception
	 */
	public Action findByIdAndDept(Integer did,Integer actid) throws Exception;
}
