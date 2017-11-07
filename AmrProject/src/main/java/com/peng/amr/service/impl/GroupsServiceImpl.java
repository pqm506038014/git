package com.peng.amr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.peng.amr.dao.IGroupsDAO;
import com.peng.amr.service.IGroupsService;
import com.peng.amr.service.abs.AbstractService;
import com.peng.amr.vo.Groups;
@Service
public class GroupsServiceImpl extends AbstractService implements IGroupsService {
	@Resource
	private IGroupsDAO groupsDAO;
	@Override
	public List<Groups> findAllByDept(Integer eid, Integer did) throws Exception {
		if(super.checkAuth(eid, 6)) {
			return this.groupsDAO.findAllByDept(did);
		}
		return null;
	}

}
