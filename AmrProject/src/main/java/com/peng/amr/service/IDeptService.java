package com.peng.amr.service;

import java.util.List;

import com.peng.amr.vo.Dept;

public interface IDeptService {
	/**
	 * ��ѯ���еĲ�����Ϣ�����õ���dao���findall()����
	 * @param eid	��ǰ��Ա�ı�ţ�����Ȩ����֤
	 * @return
	 * @throws Exception
	 */
	public List<Dept> list(Integer eid) throws Exception;
	/**
	 * ʵ�ֲ�����Ϣ���޸�
	 * @param eid	�����˵ı�ţ�ʵ��Ȩ�޵���֤
	 * @param vo	Ҫ�޸ĵ�����
	 * @return
	 * @throws Exception
	 */
	public boolean edit(Integer eid,Dept vo) throws Exception; 
}
