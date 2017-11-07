package com.peng.amr.service;

import java.util.Map;

import com.peng.amr.vo.Emp;

public interface IEmpService {
	/**
	 * ���ӹ�Ա֮ǰ������׼����ѯ
	 * <li> ��ѯ����Ա�������Ϣ�͹�Ա�Ĳ�����Ϣ֮�󷵻�</li>
	 * @return �����˹�Ա������Ϣ������
	 * @throws Exception
	 */
	public Map<String, Object> addPre() throws Exception;
	/**
	 * ��֤��Ա�ı����Ϣ
	 * @param eid	�޸ĺ�Ĺ�Ա���
	 * @return
	 * @throws Exception
	 */
	public boolean checkEid(int eid) throws Exception;
	/**
	 * ʵ�ֹ�Ա�����ӣ����õ������ݲ��doCreate()����
	 * @param vo ���������ӵ����ݵ�vo����
	 * @param eid	�����˵ı��
	 * @return
	 * @throws Exception
	 */
	public boolean add(Emp vo,Integer eid) throws Exception;
	/**
	 * ģ����ҳ��ѯ��Ա����Ϣ���б�
	 * @param eid	�����˵ı�ţ�����Ȩ����֤
	 * @param column	ģ����ѯ���ֶ�
	 * @param keyWord	ģ����ѯ�Ĺؼ���
	 * @param currentPage	��ǰҳ
	 * @param lineSize	ÿҳ��ʾ��������
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> list(Integer eid,String column,String keyWord,Integer currentPage,Integer lineSize) throws Exception;
	/**
	 * ʵ�����ݵĻ��Բ�ѯ
	 * @param eid	��Ҫ���޸ĵĹ�Ա���
	 * @return	���ص����ݣ�<br>
	 * <li>key=allLevels,value=ILevelDAO.findAll()</li>
	 * <li>key=allDepts,value=IDeptDAO.findAllBySflag()</li>
	 * <li>key=emp,value=IEmpDAO.findById()</li>
	 * @throws Exception
	 */
	public Map<String, Object> editPre(Integer eid) throws Exception;
	/**
	 * ʵ�ֹ�Ա��Ϣ�ı༭����
	 * @param vo	�����˱༭��Ϣ��vo����
	 * @param eid	�����˵ı��
	 * @return
	 * @throws Exception
	 */
	public boolean edit(Emp vo,Integer eid) throws Exception;
}
