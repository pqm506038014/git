package com.peng.amr.service;

import java.util.List;

import com.peng.amr.vo.Action;

public interface IActionService {
	/**
	 * 根据权限组编号查询权限信息
	 * @param eid	操作人的编号
	 * @param gid	权限组编号
	 * @return
	 * @throws Exception
	 */
	public List<Action> list(Integer eid,Integer gid) throws Exception;
}
