package com.fh.controller.sys.bank;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.sys.bank.BankService;
import com.fh.service.system.redis.RedisService;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.sun.java.swing.plaf.windows.WindowsBorders.DashedBorder;
import com.fh.util.Jurisdiction;

/** 
 * 类名称：BankController
 * 创建人：FH 
 * 创建时间：2019-09-26
 */
@Controller
@RequestMapping(value="/bank")
public class BankController extends BaseController {
	
	String menuUrl = "bank/list.do"; //菜单地址(权限用)
	@Resource(name="bankService")
	private BankService bankService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Bank");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("bank_id", this.get32UUID());	//主键
		pd.put("STATUS", "1");	//状态
		bankService.save(pd);
		ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
		RedisService redisService = (RedisService)applicationContext.getBean("redisService");
		redisService.del("bankList");
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Bank");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			bankService.delete(pd);
			out.write("success");
			ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
			RedisService redisService = (RedisService)applicationContext.getBean("redisService");
			redisService.del("bankList");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改Bank");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		bankService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
		RedisService redisService = (RedisService)applicationContext.getBean("redisService");
		redisService.del("bankList");
		return mv;
	}

	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增Bank页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("sys/bank/bank_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Bank页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = bankService.findById(pd);	//根据ID读取
			mv.setViewName("sys/bank/bank_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Bank");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				bankService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
			ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
			RedisService redisService = (RedisService)applicationContext.getBean("redisService");
			redisService.del("bankList");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Bank");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
			RedisService redisService = (RedisService)applicationContext.getBean("redisService");
			if(!(redisService.exists("bankList"))) {//存在即不取
				List<PageData>	bankList = bankService.list(page);	//列出Bank列表
				logBefore(logger, "存入redis银行卡信息");
				redisService.setList("bankList", bankList);//加入缓存--银行卡信息
				/*
				 * redisService.expire("bankList",1800);//设置缓存时间，单位秒
				 */			}
			mv.setViewName("sys/bank/bank_list");
			List<PageData> bankList =redisService.getList("bankList", PageData.class);
			mv.addObject("varList", bankList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 修改状态
	 */
	@RequestMapping(value="/updStatus")
	@ResponseBody
	public Integer updStatus(String bank_id,String STATUS) throws Exception{
		logBefore(logger, "修改status");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("bank_id",bank_id);
		pd.put("STATUS",STATUS);
		bankService.updStatus(pd);
		ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
		RedisService redisService = (RedisService)applicationContext.getBean("redisService");
		redisService.del("bankList");
		return 1;
	}
	
	/**
	 * 判断数据库银行卡号
	 */
	@RequestMapping(value="/selectByyhk")
	@ResponseBody
	public Integer selectByyhk(String RECEIVING_PAYMENT_BANK) {
		logBefore(logger, "判断输入的银行卡号是否在数据库存在");
		PageData pd = new PageData();
		Integer bol = 0;
		try{
			pd = this.getPageData();
			pd.put("RECEIVING_PAYMENT_BANK",RECEIVING_PAYMENT_BANK);
			List<PageData> varList = bankService.selectByyhk(pd);
			if (varList.size() != 0) {
				bol = 1;
			}else {
				bol = 2;
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return bol;
	}
	
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出Bank到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("收款银行");	//1
			titles.add("收款银行卡号");	//2
			titles.add("收款开户支行");	//3
			titles.add("收款账户名");	//4
			titles.add("创建时间");	//5
			titles.add("修改时间");	//6
			titles.add("状态");	//7
			dataMap.put("titles", titles);
			List<PageData> varOList = bankService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("RECEIVING_BANK_NAME"));	//1
				vpd.put("var2", varOList.get(i).getString("RECEIVING_PAYMENT_BANK"));	//2
				vpd.put("var3", varOList.get(i).getString("RECEIVING_BANK_BRANCH"));	//3
				vpd.put("var4", varOList.get(i).getString("RECEIVING_NAME"));	//4
				vpd.put("var5", varOList.get(i).get("CREATE_TIME").toString());	//5
				vpd.put("var6", varOList.get(i).get("UPDATE_TIME").toString());	//6
				vpd.put("var7", varOList.get(i).get("STATUS").toString());	//7
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}