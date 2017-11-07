package com.peng.amr.dao;

import java.util.List;
import java.util.Map;

import com.peng.amr.vo.Emp;

public interface IEmpDAO extends IDAO<Integer, Emp> {
	/**
	 * ��½֮��Ҫ��ȡ���û�����ʵ�����Լ��û�����Ƭ
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Emp findLogin(Emp vo) throws Exception;
	/**
	 * ʵ��ģ����ҳ��ѯ����Ա
	 * @param paramMap	�����˲�ѯ������map����
	 * @return
	 * @throws Exception
	 */
	public List<Emp> findAllAdmin(Map<String, Object> paramMap) throws Exception;
	/**
	 * ͳ�Ʋ�ѯ���Ĺ���Ա��������
	 * @param paramMap	�����˲�ѯ������map����
	 * @return
	 * @throws Exception
	 */
	public Integer getAllAdminCount(Map<String, Object> paramMap) throws Exception;
	/**
	 * ʵ��ģ����ҳ��ѯ��Ա
	 * @param paramMap	�����˲�ѯ������map����
	 * @return
	 * @throws Exception
	 */
	public List<Emp> findAllEmp(Map<String, Object> paramMap) throws Exception;
	/**
	 * ͳ�Ʋ�ѯ���Ĺ�Ա��������
	 * @param paramMap	�����˲�ѯ������map����
	 * @return
	 * @throws Exception
	 */
	public Integer getAllEmpCount(Map<String, Object> paramMap) throws Exception;
}
