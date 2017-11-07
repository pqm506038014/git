package com.peng.amr.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.peng.amr.dao.IDeptDAO;
import com.peng.amr.dao.IEmpDAO;
import com.peng.amr.dao.ILevelDAO;
import com.peng.amr.service.IEmpService;
import com.peng.amr.service.abs.AbstractService;
import com.peng.amr.vo.Emp;
import com.peng.amr.vo.Level;
import com.peng.amr.vo.Page;
@Service
public class EmpServiceImpl extends AbstractService implements IEmpService {
	@Resource
	private IEmpDAO empDAO;
	@Resource
	private IDeptDAO deptDAO;
	@Resource
	private ILevelDAO levelDAO;
	@Override
	public Map<String, Object> addPre() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//回显雇员的级别信息
		map.put("allLevels", this.levelDAO.findAll());
		//回显雇员的部门信息，这里是普通部门
		map.put("allDepts", this.deptDAO.findAllBySflag(0));
		return map;
	}

	@Override
	public boolean checkEid(int eid) throws Exception {
		return this.empDAO.findById(eid) == null;
	}

	@Override
	public boolean add(Emp vo, Integer eid) throws Exception {
		if(!super.checkAuth(eid, 12)) {//需要12号权限
			return false;
		}
		//验证雇员的编号是否已经存在，防止表单外提交
		if(this.empDAO.findById(vo.getEid()) != null) {
			return false;
		}
		//如果增加的雇员的编号是管理部门的编号，则直接返回false，防止表单外提交
		if(vo.getDept().getDid().equals(1)) {
			return false;
		}
		//验证雇员的薪资级别，防止表单外提交
		Level level = this.levelDAO.findById(vo.getLevel().getLid());
		if(vo.getSalary() > level.getHisal() || vo.getSalary() < level.getLosal()) {
			return false;
		}
		vo.setHeid(eid);//设置操作人的编号
		vo.setAflag(0);//设置雇员的特殊标记，标记为0是普通员工，如果为1则为超级管理员，为2则为普通管理员
		return this.empDAO.doCreate(vo)>0;
	}

	@Override
	public Map<String, Object> list(Integer eid, String column, String keyWord, Integer currentPage, Integer lineSize)
			throws Exception {
		if(!super.checkAuth(eid, 13)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allEmps", this.empDAO.findAllEmp(super.listParamMap(column, keyWord, currentPage, lineSize)));
		Integer count = this.empDAO.getAllEmpCount(super.countParamMap(column, keyWord));
		Integer allPages = count/lineSize+(count%lineSize==0?0:1);
		Page page = new Page(currentPage, allPages);
		page.setCount(count);
		map.put("pager", page);
		return map;
	}

	@Override
	public Map<String, Object> editPre(Integer eid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//查询雇员级别信息
		map.put("allLevels", this.levelDAO.findAll());
		//部门信息
		map.put("allDepts", this.deptDAO.findAllBySflag(0));
		//雇员的基本信息
		map.put("emp", this.empDAO.findById(eid));
		return map;
	}
	
	@Override
	public boolean edit(Emp vo, Integer eid) throws Exception {
		if(!super.checkAuth(eid, 15)) {//需要有15号权限
			return false;
		}
		//不能修改为管理部门
		if(vo.getDept().getDid().equals(1)) {
			return false;
		}
		//修改的薪资必须在当前的薪资级别范围内
		Level level = this.levelDAO.findById(vo.getLevel().getLid());
		if(vo.getSalary() > level.getHisal() || vo.getSalary() < level.getLosal()) {
			return false;
		}
		vo.setAflag(0);//将特殊标记设置为0
		return this.empDAO.doUpdate(vo)>0;
	}

}
