package com.peng.amr.service.abs;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.peng.amr.dao.IActionDAO;
import com.peng.amr.dao.IEmpDAO;
import com.peng.amr.vo.Emp;

@Service
public class AbstractService {
	@Resource
	private IEmpDAO empDAO;
	@Resource
	private IActionDAO actionDAO;
	public boolean checkAuth(int eid,int ... actid) throws Exception {
		//查询出当前的雇员信息
		Emp emp = this.empDAO.findById(eid);
		//如果是超级管理员或者普通管理员则直接放行，表示有权限，根据你的需要
		if(emp.getAflag().equals(1) || emp.getAflag().equals(2)) {
			return true;
		}
		//如果不是管理员，则需要判断是否有权限
		for(int x:actid) {
			if(this.actionDAO.findByIdAndDept(emp.getDept().getDid(), x) == null) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 将查询条件保存到map集合中
	 * @param column	模糊查询的字段
	 * @param keyWord	模糊查询关键字
	 * @param currentPage	当前页
	 * @param lineSize	每页显示的数量
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> listParamMap(String column,String keyWord,Integer currentPage,Integer lineSize) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("column", column);
		if(keyWord != null) {
			map.put("keyWord", "%"+keyWord+"%");
		}
		map.put("start", (currentPage-1)*lineSize);
		map.put("lineSize", lineSize);
		return map;
	}
	/**
	 * 将统计数据量的查询条件保存到map集合中
	 * @param column	模糊查询的字段
	 * @param keyWord	模糊查询关键字
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> countParamMap(String column,String keyWord) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("column", column);
		if(keyWord != null) {
			map.put("keyWord", "%"+keyWord+"%");
		}
		return map;
	}
}
