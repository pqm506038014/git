package com.peng.amr.service;

import java.util.List;
import java.util.Map;

import com.peng.amr.vo.Details;

public interface IDetailsService {
	/**
	 * �����嵥ǰ��׼������
	 * @param eid	��Ա�ı�ţ�����Ȩ����֤
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> addPre(Integer eid) throws Exception;
	/**
	 * �������ݵĲ������
	 * @param vo	���������Ϣ��vo��
	 * @param eid	��Ա�ı�ţ�����Ȩ����֤
	 * @return
	 * @throws Exception
	 */
	public boolean add(Details vo,Integer eid) throws Exception;
	/**
	 * ʵ�ִ����嵥�Ĳ�ѯ�����õ������ݲ��findAllDetials()����
	 * @param eid	��ǰ�û��ı��
	 * @return
	 * @throws Exception
	 */
	public List<Details> listDetails(Integer eid) throws Exception;
	/**
	 * ʵ�ָ��ݱ�Ž�������ɾ�������������ݲ��findByPhoto()������doRemoveBatch()����
	 * @param eid	�����˵ı�ţ�����Ȩ����֤
	 * @param ids	Ҫɾ�����嵥��ŵļ���
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> rm(Integer eid,List<Integer> ids) throws Exception;
	/**
	 * ʵ���������޸ģ����õ������ݲ��doUpdateAmount()��findByPhoto()����
	 * @param eid	�����˵ı�ţ�Ȩ����֤
	 * @param dinfo	������Ǳ�ź�������key�Ǳ�ţ�value������
	 * @param ids	������Ҫɾ�����嵥�ı�ŵļ���
	 * @return	���ص����� <br>
	 * <li>��ɾ�����嵥����Ϣ��Ŀ���Ƿ��ؿ��Ʋ��н��ж�Ӧ����Ƭɾ��</li>
	 * <li>�����޸ĵı�ǣ�true����false</li>
	 * @throws Exception
	 */
	public Map<String, Object> editAmount(Integer eid,Map<Integer, Integer> dinfo,List<Integer> ids) throws Exception;
	/**
	 *  ʵ���޸�ǰ�����ݻ��ԣ���Ҫ����һ�������Ϣ�����������Ϣ��������Ϣ
	 * @param did	�嵥���
	 * @param eid	�����˵ı��
	 * @return	���ص�������һ��map���ϣ������е������У�<br>
	 * <li>key=allTypes,value=ITypeDAO.findAll()</li>
	 * <li>key=allSubtypes,value=ISubtypeDAO.findByType()</li>
	 * <li>key=details,value=IDetailsDAO.findByIdForPrebuy()</li>
	 * @throws Exception
	 */
	public Map<String, Object> editPre(Integer did,Integer eid) throws Exception;
	/**
	 * ʵ���嵥�ı༭�����õ������ݲ��doUpdatePrebuy()
	 * @param eid	�����˵ı��
	 * @param vo	�������޸���Ϣ��vo����
	 * @return
	 * @throws Exception
	 */
	public boolean edit(Integer eid,Details vo) throws Exception;
}
