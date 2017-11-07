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
			//查询出要删除的所有清单信息
			List<Details> allDetails = this.detailsDAO.findByPhoto(ids);
			Iterator<Details> iterD = allDetails.iterator();
			while(iterD.hasNext()) {
				Details details = iterD.next();
				//每个清单都有添加的人的编号，这里是验证现在操作的人是否和添加的人是同一个编号，如果不是则不能删除，将标记设置为false
				if(!details.getEmp().getEid().equals(eid)) {
					flag = false;
					break;
				}
			}
			//如果所有的清单都是当前用户添加的则可以删除
			if(flag) {
				//实现清单的批量删除
				flag = this.detailsDAO.doRemoveBatch(ids)>0;
				if(flag) {
					//如果删除成功，则将查询到的清单信息保存到map集合中继续返回，目的是在控制层实现照片的删除
					map.put("allDetails", allDetails);
				}
			}
		}
		//保存删除标记(true、false)
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
			//遍历map集合，取得编号和数量
			Set<Map.Entry<Integer, Integer>> set = dinfo.entrySet();
			Iterator<Map.Entry<Integer, Integer>> iter = set.iterator();
			while(iter.hasNext()) {
				Map.Entry<Integer, Integer> me = iter.next();
				Details vo = new Details();
				vo.setDid(me.getKey());	// 取出对应的编号到vo对象中
				vo.setAmount(me.getValue());//取出对应的数量到vo对象中
				vo.setEmp(new Emp());
				//保存的是当前操作人的编号，目的是在sql语句中验证现在操作的人和添加的人是否是同一个人
				vo.getEmp().setEid(eid);
				if(this.detailsDAO.doUpdateAmount(vo) == 0) {//出现某些问题导致修改失败，则将标记改为false
					flag = false;
				}
			}
			//以下的操作是删除数量为0的清单
			if(flag) {
				if(ids.size() > 0) {
					//先查询要删除的清单信息
					List<Details> allDetails = this.detailsDAO.findByPhoto(ids);
					Iterator<Details > iterD = allDetails.iterator();
					while(iterD.hasNext()) {
						Details details = iterD.next();
						//必须是所有的清单都属于当前的操作者
						if(!details.getEmp().getEid().equals(eid)) {
							flag = false;
							break;
						}
					}
					if(flag) {
						//删除数量为0的清单
						flag = this.detailsDAO.doRemoveBatch(ids)>0;
						if(flag) {
							//将删除的清单的信息保存到map集合中返回给控制层实现对应的照片删除
							map.put("allDetails", allDetails);
						}
					}
				}
			}
		}
		//保存最终处理的标记
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
