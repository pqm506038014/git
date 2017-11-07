package com.peng.amr.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.peng.amr.dao.IDetailsDAO;
import com.peng.amr.dao.IEmpDAO;
import com.peng.amr.dao.IPurchaseDAO;
import com.peng.amr.service.IPurchaseService;
import com.peng.amr.service.abs.AbstractService;
import com.peng.amr.vo.Details;
import com.peng.amr.vo.Emp;
import com.peng.amr.vo.Page;
import com.peng.amr.vo.Purchase;
import com.peng.util.MathUtil;
@Service
public class PurchaseServiceImpl extends AbstractService implements IPurchaseService {
	@Resource
	private IDetailsDAO detailsDAO;
	@Resource
	private IPurchaseDAO purchaseDAO;
	@Resource
	private IEmpDAO empDAO;
	@Override
	public boolean add(Integer eid, Purchase vo) throws Exception {
		if(!super.checkAuth(eid, 30)) {
			return false;
		}
		List<Details> allDetails = this.detailsDAO.findAllDetails(eid);
		if(allDetails.size() == 0) {
			return false;
		}
		double sum = 0.0;
		Iterator<Details> iter = allDetails.iterator();
		while(iter.hasNext()) {
			Details details = iter.next();
			sum += details.getPrice()*details.getAmount();
		}
		if(sum < 0.0) {
			return false;
		}
		vo.setEmp(new Emp());//������ǰ�����˶��������ˣ�
		vo.getEmp().setEid(eid);//���������˱��
		vo.setPubdate(new Date());//��������
		vo.setStatus(0);	//��������״̬����ʱ��0����ʾ�ȴ�����
		vo.setTotal(MathUtil.round(sum, 2));//�������룬������λС��
		if(this.purchaseDAO.doCreate(vo)>0) {
			return this.detailsDAO.doUpdateByPurchase(vo.getPid(), eid)>0;
		}
		return false;
	}
	@Override
	public Map<String, Object> listByEmp(Integer eid, Integer currentPage, Integer lineSize) throws Exception {
		if(!super.checkAuth(eid, 27)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//�����ѯ����������sql����л�ȡ
		paramMap.put("eid", eid);
		paramMap.put("start", (currentPage-1)*lineSize);
		paramMap.put("lineSize", lineSize);
		//�������е����뵥��Ϣ
		map.put("allPurchases", this.purchaseDAO.findAllByEmp(paramMap));
		//ͳ��������
		Integer count = this.purchaseDAO.getAllCountByEmp(eid);
		//������ҳ��
		Integer allPages = count/lineSize+(count%lineSize==0?0:1);
		Page page = new Page(currentPage,allPages);
		page.setCount(count);
		map.put("pager", page);
		return map;
	}
	@Override
	public Purchase getByEmp(Integer eid, Integer pid) throws Exception {
		if(!super.checkAuth(eid, 27)) {
			return null;
		}
		//��ѯ�����뵥��Ϣ
		Purchase vo = this.purchaseDAO.findByIdAndEmp(pid, eid);
		if(vo != null) {//���������
			//��ʱ����Ҫ����ɸ����뵥�Ĵ����嵥����Ϣ���浽���뵥��������
			vo.setAllDetails(this.detailsDAO.findAllByPurchase(pid));
			//����Ҫ��ѯ�����˵���Ϣ
			vo.setEmp(this.empDAO.findById(vo.getEmp().getEid()));
		}
		return vo;
	}
	@Override
	public Map<String, Object> listSimple(Integer eid, Integer currentPage, Integer lineSize) throws Exception {
		if(!super.checkAuth(eid, 41)) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		//�������е����뵥��Ϣ
		map.put("allPurchases", this.purchaseDAO.findAllSimpleSplit((currentPage-1)*lineSize, lineSize));
		//ͳ��������
		Integer count = this.purchaseDAO.getAllCountByEmp(eid);
		//������ҳ��
		Integer allPages = count/lineSize+(count%lineSize==0?0:1);
		Page page = new Page(currentPage,allPages);
		page.setCount(count);
		map.put("pager", page);
		return map;
	}
	@Override
	public Purchase show(Integer eid, Integer pid) throws Exception {
		if(!super.checkAuth(eid, 41)) {
			return null;
		}
		//��ѯ�����뵥��Ϣ
		Purchase vo = this.purchaseDAO.findById(pid);
		if(vo != null) {//���������
			//��ʱ����Ҫ����ɸ����뵥�Ĵ����嵥����Ϣ���浽���뵥��������
			vo.setAllDetails(this.detailsDAO.findAllByPurchase(pid));
			//����Ҫ��ѯ�����˵���Ϣ
			vo.setEmp(this.empDAO.findById(vo.getEmp().getEid()));
		}
		return vo;
	}

}
