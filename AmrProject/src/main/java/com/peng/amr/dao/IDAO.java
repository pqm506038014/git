package com.peng.amr.dao;

import java.util.List;

public interface IDAO <K,V>{
	
	public int doCreate(V vo) throws Exception;
	public int doUpdate(V vo) throws Exception;
	public int doRemoveBatch(List<K> ids) throws Exception;
	public V findById(K id) throws Exception;
	public List<V> findAll() throws Exception;
	public List<V> findAllSplit(String column,String keyWord,Integer currentPage,Integer lineSize) throws Exception;
	public int findAllCount(String column,String keyWord) throws Exception;
}
