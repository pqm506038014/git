package com.peng.amr.service;

import java.util.List;

import com.peng.amr.vo.Action;

public interface IActionService {
	/**
	 * ����Ȩ�����Ų�ѯȨ����Ϣ
	 * @param eid	�����˵ı��
	 * @param gid	Ȩ������
	 * @return
	 * @throws Exception
	 */
	public List<Action> list(Integer eid,Integer gid) throws Exception;
}
