package com.peng.amr.service;

public interface ILevelService {
	/**
	 * �������Ĺ����Ƿ���ָ���Ĺ�Ա�ȼ�֮��
	 * @param salary �����н������
	 * @param lid ��Ա�ȼ���Ϣ
	 * @return �������ȼ���֤����true�����򷵻�false
	 * @throws Exception
	 */
	public boolean checkSalary(double salary,int lid) throws Exception;
}
