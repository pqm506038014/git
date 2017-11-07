package com.peng.amr.service;

import java.util.Map;

import com.peng.amr.vo.Emp;

public interface IAdminService {
	/**
	 * ʵ�ֵ�½���������õ���IEmpDAO�ӿ��е�findLogin()����
	 * @param vo �����˱�ź������vo��
	 * @return
	 * @throws Exception
	 */
	public boolean login(Emp vo) throws Exception;
	/**
	 * ���ӹ���Ա֮ǰ������׼����ѯ
	 * <li> ��ѯ����Ա�������Ϣ֮�󷵻�</li>
	 * @return �����˹�Ա������Ϣ������
	 * @throws Exception
	 */
	public Map<String, Object> addPre() throws Exception;
	/**
	 * ��֤��Ա�ı����Ϣ
	 * @param eid
	 * @return
	 * @throws Exception
	 */
	public boolean checkEid(int eid) throws Exception;
	/**
	 * ʵ�ֹ�Ա������Ա�������ӣ����õ������ݲ��doCreate()����
	 * @param vo ���������ӵ����ݵ�vo����
	 * @param eid	�����˵ı��
	 * @return
	 * @throws Exception
	 */
	public boolean add(Emp vo,Integer eid) throws Exception;
	/**
	 * ʵ�ֹ���Ա��ģ����ҳ��ѯ�����õķ�����
	 * <li>dao���findAllAdmin()������ȡ�ù���Ա������</li>
	 * <li>dao���getAllAdminCount()������ȡ�ò�ѯ����������</li>
	 * @param eid
	 * @param column
	 * @param keyWord
	 * @param currentPage
	 * @param lineSize
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> list(int eid,String column,String keyWord,int currentPage,int lineSize) throws Exception;
}
