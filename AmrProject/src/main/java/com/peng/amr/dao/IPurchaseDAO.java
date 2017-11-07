package com.peng.amr.dao;

import java.util.List;
import java.util.Map;

import com.peng.amr.vo.Purchase;

public interface IPurchaseDAO extends IDAO<Integer, Purchase> {
	/**
	 * ʵ�ֲ�ѯ��ǰ�û����������뵥��Ϣ
	 * @param paramMap	�����˲�ѯ������map����
	 * @return
	 * @throws Exception
	 */
	public List<Purchase> findAllByEmp(Map<String, Object> paramMap) throws Exception;
	/**
	 * ���������¼�ĸ���ͳ��
	 * @param eid	��ǰ�û��ı��
	 * @return
	 * @throws Exception
	 */
	public int getAllCountByEmp(Integer eid) throws Exception;
	/**
	 * �������뵥�ı�Ų�ѯ��Ϣ
	 * @param pid	���뵥�ı��
	 * @param eid	��Ա�ı��
	 * @return
	 * @throws Exception
	 */
	public Purchase findByIdAndEmp(Integer pid,Integer eid) throws Exception;
	/**
	 * Ϊ�����ŷ�ҳ��ѯ���е����뵥��Ϣ
	 * @param start	��ʾ��ָ�����п�ʼ��ѯ
	 * @param lineSize	ÿҳ��ʾ��������
	 * @return
	 * @throws Exception
	 */
	public List<Purchase> findAllSimpleSplit(Integer start,Integer lineSize) throws Exception;
	/**
	 * Ϊ������ͳ�Ʋ�ѯ����������
	 * @return
	 * @throws Exception
	 */
	public int getAllCountSimple() throws Exception;
}
