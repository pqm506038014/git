package com.peng.amr.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.peng.amr.dao.IDeptDAO;
import com.peng.amr.service.IDeptService;
import com.peng.amr.service.abs.AbstractService;
import com.peng.amr.vo.Dept;
@Service
public class DeptServiceImpl extends AbstractService implements IDeptService {
	@Resource
	private IDeptDAO deptDAO;
	@Override
	public List<Dept> list(Integer eid) throws Exception {
		if(!super.checkAuth(eid, 4)) {
			return null;
		}
		return this.deptDAO.findAll();
	}
	@Override
	public boolean edit(Integer eid, Dept vo) throws Exception {
		if(super.checkAuth(eid, 7)) {//验证操作人是否拥有7号权限
			return this.deptDAO.doUpdate(vo)>0;
		}
		return false;
	}

}
