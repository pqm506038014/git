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
		vo.setEmp(new Emp());//创建当前操作人对象（申请人）
		vo.getEmp().setEid(eid);//设置申请人编号
		vo.setPubdate(new Date());//设置日期
		vo.setStatus(0);	//设置申请状态，此时是0，表示等待申请
		vo.setTotal(MathUtil.round(sum, 2));//四舍五入，保留两位小数
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
		//保存查询的条件，在sql语句中获取
		paramMap.put("eid", eid);
		paramMap.put("start", (currentPage-1)*lineSize);
		paramMap.put("lineSize", lineSize);
		//保存所有的申请单信息
		map.put("allPurchases", this.purchaseDAO.findAllByEmp(paramMap));
		//统计数据量
		Integer count = this.purchaseDAO.getAllCountByEmp(eid);
		//计算总页数
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
		//查询出申请单信息
		Purchase vo = this.purchaseDAO.findByIdAndEmp(pid, eid);
		if(vo != null) {//如果有数据
			//此时还需要将组成该申请单的待购清单的信息保存到申请单的属性中
			vo.setAllDetails(this.detailsDAO.findAllByPurchase(pid));
			//还需要查询申请人的信息
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
		//保存所有的申请单信息
		map.put("allPurchases", this.purchaseDAO.findAllSimpleSplit((currentPage-1)*lineSize, lineSize));
		//统计数据量
		Integer count = this.purchaseDAO.getAllCountByEmp(eid);
		//计算总页数
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
		//查询出申请单信息
		Purchase vo = this.purchaseDAO.findById(pid);
		if(vo != null) {//如果有数据
			//此时还需要将组成该申请单的待购清单的信息保存到申请单的属性中
			vo.setAllDetails(this.detailsDAO.findAllByPurchase(pid));
			//还需要查询申请人的信息
			vo.setEmp(this.empDAO.findById(vo.getEmp().getEid()));
		}
		return vo;
	}

}
