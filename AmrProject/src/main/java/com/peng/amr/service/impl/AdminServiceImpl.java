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
		if(rvo != null) {//���������
			vo.setName(rvo.getName()); //���������浽vo������
			vo.setPhone(rvo.getPhone()); //���绰���浽vo������
			vo.setPhoto(rvo.getPhoto());
			vo.setAflag(rvo.getAflag()); //����������
			vo.setDept(rvo.getDept()); //������ǲ�����Ϣ
			vo.setLevel(rvo.getLevel()); //�����Ա����
			//���ݲ��ŵı�Ų�ѯ��Ӧ��Ȩ������Ϣ
			List<Groups> allGups = this.groupsDAO.findAllByDept(vo.getDept().getDid());
			Iterator<Groups> iter = allGups.iterator();
			while(iter.hasNext()) {
				Groups g = iter.next();
				//����Ȩ����ı��ȡ�ö�Ӧ��Ȩ������Ϣ
				g.setAllActions(this.actionDAO.findAllByGroups(g.getGid()));
			}
			//ÿһ����Ա�����Լ��Ĳ��ţ�������Ȩ���飬��ʱ��Ϊ����������Ȩ������Ϣ����ЩȨ������Ϣ�����Լ���Ȩ����Ϣ
			vo.getDept().setAllGroups(allGups);
			return true;
		}
		return false;
	}
	@Override
	public Map<String, Object> addPre() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//������level��dao���findAll()����
		map.put("allLevels", this.levelDAO.findAll());
		return map;
	}
	@Override
	public boolean checkEid(int eid) throws Exception {
		return this.empDAO.findById(eid) == null;
	}
	@Override
	public boolean add(Emp vo, Integer eid) throws Exception {
		//���ø���ķ�������Ȩ�޵���֤
		if(!super.checkAuth(eid, 2)) {
			return false;
		}
		//�ж�����Ĺ�Ա����Ƿ��Ѿ������ˣ��ڿͻ��˵ı���Զ����֤��һ�Σ��������ٴ���֤��ԭ���Ƿ�ֹ����ܿ���ֱ�ӷ�������
		if(this.empDAO.findById(vo.getEid()) != null) {
			return false;
		}
		//�ж������н���Ƿ��ڸõȼ�н�ʷ�Χ�ڣ��ڿͻ��˵ı���Զ����֤��һ�Σ��������ٴ���֤��ԭ���Ƿ�ֹ����ܿ���ֱ�ӷ�������
		Level level = this.levelDAO.findById(vo.getLevel().getLid());
		if(vo.getSalary() > level.getHisal() || vo.getSalary() < level.getLosal()) {
			return false;
		}
		vo.setHeid(eid);//���ô����˵ı��
		vo.setAflag(2);//������Ϊ2����ʾ����ͨ����Ա���������1��ʾ�ǳ�������Ա
		vo.setDept(new Dept());//ָ����Ա���ڲ���
		vo.getDept().setDid(1);//ָ����Ա���ڲ���Ϊ1�Ų���
		return this.empDAO.doCreate(vo)>0;
	}
	@Override
	public Map<String, Object> list(int eid, String column, String keyWord, int currentPage, int lineSize)
			throws Exception {
		if(!super.checkAuth(eid, 3)) {//Ȩ�޵���֤
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
