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
		//���Թ�Ա�ļ�����Ϣ
		map.put("allLevels", this.levelDAO.findAll());
		//���Թ�Ա�Ĳ�����Ϣ����������ͨ����
		map.put("allDepts", this.deptDAO.findAllBySflag(0));
		return map;
	}

	@Override
	public boolean checkEid(int eid) throws Exception {
		return this.empDAO.findById(eid) == null;
	}

	@Override
	public boolean add(Emp vo, Integer eid) throws Exception {
		if(!super.checkAuth(eid, 12)) {//��Ҫ12��Ȩ��
			return false;
		}
		//��֤��Ա�ı���Ƿ��Ѿ����ڣ���ֹ�����ύ
		if(this.empDAO.findById(vo.getEid()) != null) {
			return false;
		}
		//������ӵĹ�Ա�ı���ǹ����ŵı�ţ���ֱ�ӷ���false����ֹ�����ύ
		if(vo.getDept().getDid().equals(1)) {
			return false;
		}
		//��֤��Ա��н�ʼ��𣬷�ֹ�����ύ
		Level level = this.levelDAO.findById(vo.getLevel().getLid());
		if(vo.getSalary() > level.getHisal() || vo.getSalary() < level.getLosal()) {
			return false;
		}
		vo.setHeid(eid);//���ò����˵ı��
		vo.setAflag(0);//���ù�Ա�������ǣ����Ϊ0����ͨԱ�������Ϊ1��Ϊ��������Ա��Ϊ2��Ϊ��ͨ����Ա
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
		//��ѯ��Ա������Ϣ
		map.put("allLevels", this.levelDAO.findAll());
		//������Ϣ
		map.put("allDepts", this.deptDAO.findAllBySflag(0));
		//��Ա�Ļ�����Ϣ
		map.put("emp", this.empDAO.findById(eid));
		return map;
	}
	
	@Override
	public boolean edit(Emp vo, Integer eid) throws Exception {
		if(!super.checkAuth(eid, 15)) {//��Ҫ��15��Ȩ��
			return false;
		}
		//�����޸�Ϊ������
		if(vo.getDept().getDid().equals(1)) {
			return false;
		}
		//�޸ĵ�н�ʱ����ڵ�ǰ��н�ʼ���Χ��
		Level level = this.levelDAO.findById(vo.getLevel().getLid());
		if(vo.getSalary() > level.getHisal() || vo.getSalary() < level.getLosal()) {
			return false;
		}
		vo.setAflag(0);//������������Ϊ0
		return this.empDAO.doUpdate(vo)>0;
	}

}
