package com.fh.service.sys.bank;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("bankService")
public class BankService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("BankMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("BankMapper.delete", pd);
	}
	
	/*
	* 修改银行卡状态
	*/
	public void updStatus(PageData pd)throws Exception{
		dao.update("BankMapper.updStatus", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("BankMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("BankMapper.datalistPage", page);
	}
	
	/*
	*判断数据库银行卡号
	*/
	public List<PageData> selectByyhk(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("BankMapper.selectByyhk",pd);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("BankMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BankMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("BankMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

