package com.peng.amr.dao;

import java.util.List;

import com.peng.amr.vo.Details;

public interface IDetailsDAO extends IDAO<Integer, Details> {
	/**
	 * ʵ�ִ����嵥��������Ϣ�Ĳ�ѯ
	 * @param eid
	 * @return
	 * @throws Exception
	 */
	public List<Details> findAllDetails(Integer eid) throws Exception;
	/**
	 * �����嵥�ı�Ž��в�ѯ
	 * @param ids	������Ҫɾ�����嵥��ŵļ���
	 * @return
	 * @throws Exception
	 */
	public List<Details> findByPhoto(List<Integer> ids) throws Exception;
	/**
	 * ʵ���嵥�������޸�
	 * @param vo	������Ҫ�޸ĵ��嵥�ı�ź�����
	 * @return
	 * @throws Exception
	 */
	public int doUpdateAmount(Details vo) throws Exception;
	/**
	 * ʵ���޸�ǰ�����ݻ���
	 * @param did	�嵥�ı��
	 * @param eid	�������˵ı��
	 * @return
	 * @throws Exception
	 */
	public Details findByIdForPrebuy(Integer did,Integer eid) throws Exception;
	/**
	 * ʵ���嵥����Ϣ�޸�
	 * @param vo	������Ҫ�޸ĵ����ݵ�vo����
	 * @return
	 * @throws Exception
	 */
	public int doUpdatePrebuy(Details vo) throws Exception;
	/**
	 * ʵ�ִ����嵥��pid�޸�
	 * @param pid	���������ص����뵥�ı��
	 * @param eid	��Ҫ�����˺��嵥����д�˱���һ��
	 * @return
	 * @throws Exception
	 */
	public int doUpdateByPurchase(Integer pid,Integer eid) throws Exception;
	/**
	 * �������뵥�ı�Ž��в�ѯ
	 * @param pid	���뵥�ı��
	 * @return
	 * @throws Exception
	 */
	public List<Details> findAllByPurchase(Integer pid) throws Exception;
}
