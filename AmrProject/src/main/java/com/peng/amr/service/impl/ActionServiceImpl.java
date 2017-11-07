package com.peng.amr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.peng.amr.dao.IActionDAO;
import com.peng.amr.service.IActionService;
import com.peng.amr.service.abs.AbstractService;
import com.peng.amr.vo.Action;
@Service
public class ActionServiceImpl extends AbstractService implements IActionService {
	@Resource
	private IActionDAO actionDAO;
	@Override
	public List<Action> list(Integer eid, Integer gid) throws Exception {
		if(super.checkAuth(eid, 6)) {
			return this.actionDAO.findAllByGroups(gid);
		}
		return null;
	}

}
