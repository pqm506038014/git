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
		//��ѯ����ǰ�Ĺ�Ա��Ϣ
		Emp emp = this.empDAO.findById(eid);
		//����ǳ�������Ա������ͨ����Ա��ֱ�ӷ��У���ʾ��Ȩ�ޣ����������Ҫ
		if(emp.getAflag().equals(1) || emp.getAflag().equals(2)) {
			return true;
		}
		//������ǹ���Ա������Ҫ�ж��Ƿ���Ȩ��
		for(int x:actid) {
			if(this.actionDAO.findByIdAndDept(emp.getDept().getDid(), x) == null) {
				return false;
			}
		}
		return true;
	}
	/**
	 * ����ѯ�������浽map������
	 * @param column	ģ����ѯ���ֶ�
	 * @param keyWord	ģ����ѯ�ؼ���
	 * @param currentPage	��ǰҳ
	 * @param lineSize	ÿҳ��ʾ������
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
	 * ��ͳ���������Ĳ�ѯ�������浽map������
	 * @param column	ģ����ѯ���ֶ�
	 * @param keyWord	ģ����ѯ�ؼ���
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
