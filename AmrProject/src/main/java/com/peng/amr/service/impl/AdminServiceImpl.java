package com.peng.amr.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.peng.amr.dao.IActionDAO;
import com.peng.amr.dao.IEmpDAO;
import com.peng.amr.dao.IGroupsDAO;
import com.peng.amr.dao.ILevelDAO;
import com.peng.amr.service.IAdminService;
import com.peng.amr.service.abs.AbstractService;
import com.peng.amr.vo.Dept;
import com.peng.amr.vo.Emp;
import com.peng.amr.vo.Groups;
import com.peng.amr.vo.Level;
import com.peng.amr.vo.Page;
@Service
public class AdminServiceImpl extends AbstractService implements IAdminService {
	@Resource
	private IGroupsDAO groupsDAO;
	@Resource
	private IActionDAO actionDAO;
	@Resource
	private IEmpDAO empDAO;
	@Resource
	private ILevelDAO levelDAO;
	@Override
	public boolean login(Emp vo) throws Exception {
		Emp rvo = this.empDAO.findLogin(vo);
		if(rvo != null) {//如果有数据
			vo.setName(rvo.getName()); //将姓名保存到vo对象中
			vo.setPhone(rvo.getPhone()); //将电话保存到vo对象中
			vo.setPhoto(rvo.getPhoto());
			vo.setAflag(rvo.getAflag()); //保存特殊标记
			vo.setDept(rvo.getDept()); //保存的是部门信息
			vo.setLevel(rvo.getLevel()); //保存雇员级别
			//根据部门的编号查询对应的权限组信息
			List<Groups> allGups = this.groupsDAO.findAllByDept(vo.getDept().getDid());
			Iterator<Groups> iter = allGups.iterator();
			while(iter.hasNext()) {
				Groups g = iter.next();
				//根据权限组的编号取得对应的权限组信息
				g.setAllActions(this.actionDAO.findAllByGroups(g.getGid()));
			}
			//每一个雇员都有自己的部门，部门有权限组，此时是为部门设置了权限组信息，这些权限组信息都有自己的权限信息
			vo.getDept().setAllGroups(allGups);
			return true;
		}
		return false;
	}
	@Override
	public Map<String, Object> addPre() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//调用了level的dao层的findAll()方法
		map.put("allLevels", this.levelDAO.findAll());
		return map;
	}
	@Override
	public boolean checkEid(int eid) throws Exception {
		return this.empDAO.findById(eid) == null;
	}
	@Override
	public boolean add(Emp vo, Integer eid) throws Exception {
		//调用父类的方法进行权限的验证
		if(!super.checkAuth(eid, 2)) {
			return false;
		}
		//判断输入的雇员编号是否已经存在了，在客户端的表单中远程验证过一次，在这里再次验证的原因是防止恶意避开表单直接放松请求
		if(this.empDAO.findById(vo.getEid()) != null) {
			return false;
		}
		//判断输入的薪资是否在该等级薪资范围内，在客户端的表单中远程验证过一次，在这里再次验证的原因是防止恶意避开表单直接放松请求
		Level level = this.levelDAO.findById(vo.getLevel().getLid());
		if(vo.getSalary() > level.getHisal() || vo.getSalary() < level.getLosal()) {
			return false;
		}
		vo.setHeid(eid);//设置处理人的编号
		vo.setAflag(2);//特殊标记为2（表示是普通管理员），如果是1表示是超级管理员
		vo.setDept(new Dept());//指定雇员所在部门
		vo.getDept().setDid(1);//指定雇员所在部门为1号部门
		return this.empDAO.doCreate(vo)>0;
	}
	@Override
	public Map<String, Object> list(int eid, String column, String keyWord, int currentPage, int lineSize)
			throws Exception {
		if(!super.checkAuth(eid, 3)) {//权限的验证
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allEmps", this.empDAO.findAllAdmin(super.listParamMap(column, keyWord, currentPage, lineSize)));
		Integer count = this.empDAO.getAllAdminCount(super.countParamMap(column, keyWord));
		Integer allPages = count/lineSize+(count%lineSize==0?0:1);
		Page page = new Page(currentPage,allPages);
		page.setCount(count);
		map.put("pager", page);
		return map;
	}

}
