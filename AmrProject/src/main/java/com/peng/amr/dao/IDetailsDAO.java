package com.peng.amr.dao;

import java.util.List;

import com.peng.amr.vo.Details;

public interface IDetailsDAO extends IDAO<Integer, Details> {
	/**
	 * 实现待购清单的所有信息的查询
	 * @param eid
	 * @return
	 * @throws Exception
	 */
	public List<Details> findAllDetails(Integer eid) throws Exception;
	/**
	 * 根据清单的编号进行查询
	 * @param ids	保存了要删除的清单编号的集合
	 * @return
	 * @throws Exception
	 */
	public List<Details> findByPhoto(List<Integer> ids) throws Exception;
	/**
	 * 实现清单数量的修改
	 * @param vo	包含了要修改的清单的编号和数量
	 * @return
	 * @throws Exception
	 */
	public int doUpdateAmount(Details vo) throws Exception;
	/**
	 * 实现修改前的数据回显
	 * @param did	清单的编号
	 * @param eid	操作的人的编号
	 * @return
	 * @throws Exception
	 */
	public Details findByIdForPrebuy(Integer did,Integer eid) throws Exception;
	/**
	 * 实现清单的信息修改
	 * @param vo	包含了要修改的数据的vo对象
	 * @return
	 * @throws Exception
	 */
	public int doUpdatePrebuy(Details vo) throws Exception;
	/**
	 * 实现代购清单的pid修改
	 * @param pid	自增长返回的申请单的编号
	 * @param eid	需要申请人和清单的填写人保持一致
	 * @return
	 * @throws Exception
	 */
	public int doUpdateByPurchase(Integer pid,Integer eid) throws Exception;
	/**
	 * 根据申请单的编号进行查询
	 * @param pid	申请单的编号
	 * @return
	 * @throws Exception
	 */
	public List<Details> findAllByPurchase(Integer pid) throws Exception;
}
