package com.peng.amr.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.peng.amr.dao.IDetailsDAO;
import com.peng.amr.dao.ISubtypeDAO;
import com.peng.amr.dao.ITypeDAO;
import com.peng.amr.service.IDetailsService;
import com.peng.amr.service.abs.AbstractService;
import com.peng.amr.vo.Details;
import com.peng.amr.vo.Emp;
@Service
public class DetailsServiceImpl extends AbstractService implements IDetailsService {
	@Resource
	private ITypeDAO typeDAO;
	@Resource
	private IDetailsDAO detailsDAO;
	@Resource
	private ISubtypeDAO subtypeDAO;
	@Override
	public Map<String, Object> addPre(Integer eid) throws Exception {
		if(!super.checkAuth(eid, 25)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allTypes", this.typeDAO.findAll());
		return map;
	}

	@Override
	public boolean add(Details vo, Integer eid) throws Exception {
		if(!super.checkAuth(eid, 25)) {
			return false;
		}
		vo.setEmp(new Emp());
		vo.getEmp().setEid(eid);
		vo.setAmount(1);
		return this.detailsDAO.doCreate(vo)>0;
	}

	@Override
	public List<Details> listDetails(Integer eid) throws Exception {
		if(!super.checkAuth(eid, 25)) {
			return null;
		}
		return this.detailsDAO.findAllDetails(eid);
	}

	@Override
	public Map<String, Object> rm(Integer eid, List<Integer> ids) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		if(!super.checkAuth(eid, 25)) {
			flag = false;
		}
		if(ids.size() == 0) {
			flag = false;
		}
		if(flag) {
			//��ѯ��Ҫɾ���������嵥��Ϣ
			List<Details> allDetails = this.detailsDAO.findByPhoto(ids);
			Iterator<Details> iterD = allDetails.iterator();
			while(iterD.hasNext()) {
				Details details = iterD.next();
				//ÿ���嵥������ӵ��˵ı�ţ���������֤���ڲ��������Ƿ����ӵ�����ͬһ����ţ������������ɾ�������������Ϊfalse
				if(!details.getEmp().getEid().equals(eid)) {
					flag = false;
					break;
				}
			}
			//������е��嵥���ǵ�ǰ�û���ӵ������ɾ��
			if(flag) {
				//ʵ���嵥������ɾ��
				flag = this.detailsDAO.doRemoveBatch(ids)>0;
				if(flag) {
					//���ɾ���ɹ����򽫲�ѯ�����嵥��Ϣ���浽map�����м������أ�Ŀ�����ڿ��Ʋ�ʵ����Ƭ��ɾ��
					map.put("allDetails", allDetails);
				}
			}
		}
		//����ɾ�����(true��false)
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map<String, Object> editAmount(Integer eid, Map<Integer, Integer> dinfo, List<Integer> ids)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		if(!super.checkAuth(eid, 25)) {
			flag = false;
		}
		if(flag) {
			//����map���ϣ�ȡ�ñ�ź�����
			Set<Map.Entry<Integer, Integer>> set = dinfo.entrySet();
			Iterator<Map.Entry<Integer, Integer>> iter = set.iterator();
			while(iter.hasNext()) {
				Map.Entry<Integer, Integer> me = iter.next();
				Details vo = new Details();
				vo.setDid(me.getKey());	// ȡ����Ӧ�ı�ŵ�vo������
				vo.setAmount(me.getValue());//ȡ����Ӧ��������vo������
				vo.setEmp(new Emp());
				//������ǵ�ǰ�����˵ı�ţ�Ŀ������sql�������֤���ڲ������˺���ӵ����Ƿ���ͬһ����
				vo.getEmp().setEid(eid);
				if(this.detailsDAO.doUpdateAmount(vo) == 0) {//����ĳЩ���⵼���޸�ʧ�ܣ��򽫱�Ǹ�Ϊfalse
					flag = false;
				}
			}
			//���µĲ�����ɾ������Ϊ0���嵥
			if(flag) {
				if(ids.size() > 0) {
					//�Ȳ�ѯҪɾ�����嵥��Ϣ
					List<Details> allDetails = this.detailsDAO.findByPhoto(ids);
					Iterator<Details > iterD = allDetails.iterator();
					while(iterD.hasNext()) {
						Details details = iterD.next();
						//���������е��嵥�����ڵ�ǰ�Ĳ�����
						if(!details.getEmp().getEid().equals(eid)) {
							flag = false;
							break;
						}
					}
					if(flag) {
						//ɾ������Ϊ0���嵥
						flag = this.detailsDAO.doRemoveBatch(ids)>0;
						if(flag) {
							//��ɾ�����嵥����Ϣ���浽map�����з��ظ����Ʋ�ʵ�ֶ�Ӧ����Ƭɾ��
							map.put("allDetails", allDetails);
						}
					}
				}
			}
		}
		//�������մ���ı��
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map<String, Object> editPre(Integer did, Integer eid) throws Exception {
		if(!super.checkAuth(eid, 25)) {
			return null;
		}
		Details details = this.detailsDAO.findByIdForPrebuy(did, eid);
		if(details == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allTypes", this.typeDAO.findAll());
		map.put("allSubtypes", this.subtypeDAO.findAllByType(details.getType().getTid()));
		map.put("details", details);
		return map;
	}

	@Override
	public boolean edit(Integer eid, Details vo) throws Exception {
		if(!super.checkAuth(eid, 25)) {
			return false;
		}
		return this.detailsDAO.doUpdatePrebuy(vo)>0;
	}

}
