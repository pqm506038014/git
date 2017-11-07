package com.peng.amr.service;

import java.util.Map;

import com.peng.amr.vo.Purchase;

public interface IPurchaseService {
	/**
	 * ʵ���嵥�������ύ�����õķ����ǣ�<br>
	 * <li>IDetailsDAO.doUpdateByPurchase()</li>
	 * <li>IPurchaseDAO.doCreate()</li>
	 * @param eid	�����˵ı��
	 * @param vo	��������������Ϣ��vo����
	 * @return
	 * @throws Exception
	 */
	public boolean add(Integer eid,Purchase vo) throws Exception;
	/**
	 * ʵ�����뵥�Ĳ�ѯ�����õ������ݲ��findAllByEmp()��getAllCountByEmp()����
	 * @param eid	��ǰ�û��ı��
	 * @param currentpage	��ǰҳ
	 * @param lineSize	ÿҳ��ʾ������
	 * @return	���ص����ݣ�<br>
	 * <li>���뵥����Ϣ��key=allPurchases,value=IPurchaseDAO.findAllByEmp()</li>
	 * <li>��ҳ����Ϣ��key=pager,value=new Page(currentPage,allPages)</li>
	 * @throws Exception
	 */
	public Map<String, Object> listByEmp(Integer eid,Integer currentPage,Integer lineSize) throws Exception;
	/**
	 * ʵ�ָ������뵥��ź��û��ı�Ų�ѯ���ݣ����õ������ݲ��findByIdAndEmp()����
	 * @param eid	�����˵ı��
	 * @param pid	���뵥�ı��
	 * @return	��������ݷ������뵥����ϸ��Ϣ��û�з���null
	 * @throws Exception
	 */
	public Purchase getByEmp(Integer eid,Integer pid) throws Exception;
	/**
	 * Ϊ�����Ų�ѯ�嵥��Ϣ���������ݲ��findAllSimpleSplit()��getAllCountSimple()����
	 * @param eid	��ǰ�û��ı�ţ�����Ȩ����֤��
	 * @param currentPage	��ǰҳ
	 * @param lineSize		ÿҳ��ʾ��������
	 * @return	���ص����ݣ�<br>
	 * <li>���뵥����Ϣ��key=allPurchases,value=IPurchaseDAO.findAllSimpleSplit()</li>
	 * <li>��ҳ����Ϣ��key=pager,value=new Page(currentPage,allPages)</li>
	 * @throws Exception
	 */
	public Map<String, Object> listSimple(Integer eid,Integer currentPage,Integer lineSize) throws Exception;
	/**
	 * �������뵥�ı��Ǻã�������ϸ��Ϣ�Ĳ�ѯ�����õ������ݲ��findById()����
	 * @param eid	��ǰ�û��ı��
	 * @param pid	���뵥�ı��
	 * @return
	 * @throws Exception
	 */
	public Purchase show(Integer eid,Integer pid) throws Exception;
}
