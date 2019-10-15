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
 * 类名称：BankController 创建人：FH 创建时间：2019-09-26
 */
@Controller
@RequestMapping(value = "/bank")
public class BankController extends BaseController {

	String menuUrl = "bank/list.do"; // 菜单地址(权限用)
	@Resource(name = "bankService")
	private BankService bankService;

	/**
	 * 新增
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save() throws Exception {
		logBefore(logger, "新增Bank");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		return bankService.save(pd);
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	public void delete(PrintWriter out) {
		logBefore(logger, "删除Bank");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
			return;
		} // 校验权限
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			bankService.delete(pd);
			out.write("success"); // 输出success
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

	}

	/**
	 * 修改
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit() throws Exception {
		logBefore(logger, "修改Bank");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
			return null;
		} // 校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		return bankService.edit(pd);
	}

	/**
	 * 去新增页面
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView goAdd() {
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
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEdit() throws Exception {
		logBefore(logger, "去修改Bank页面");
		PageData pd = new PageData();
		pd = this.getPageData();
		return bankService.findById(pd);
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		logBefore(logger, "列表Bank");
		// if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		return bankService.list(page).addObject(Const.SESSION_QX, this.getHC()); // 按钮权限
	}

	/**
	 * 修改银行卡状态
	 */
	@RequestMapping(value = "/updStatus")
	@ResponseBody
	public Integer updStatus(String bank_id, String STATUS) throws Exception {
		logBefore(logger, "修改status");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {return null;} // 校验权限
		bankService.updStatus(bank_id,STATUS);
		return 1;
	}

	/**
	 * 判断数据库银行卡号
	 * @throws Exception 
	 */
	@RequestMapping(value = "/selectByyhk")
	@ResponseBody
	public Integer selectByyhk(String RECEIVING_PAYMENT_BANK) throws Exception {
		logBefore(logger, "判断输入的银行卡号是否在数据库存在");
		return bankService.selectByyhk(RECEIVING_PAYMENT_BANK);
	}

	/*
	 * 导出到excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "/excel")
	public ModelAndView exportExcel() throws Exception {
		logBefore(logger, "导出Bank到excel");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
			return null;
		}
		PageData pd = new PageData();
		pd = this.getPageData();
		return bankService.listAll(pd);
	}

	/**
	 * 批量删除
	 */
	/*
	 * @RequestMapping(value = "/deleteAll")
	 * 
	 * @ResponseBody public Object deleteAll() { logBefore(logger, "批量删除Bank"); if
	 * (!Jurisdiction.buttonJurisdiction(menuUrl, "dell")) {return null;} // 校验权限
	 * PageData pd = new PageData(); Map<String, Object> map = new HashMap<String,
	 * Object>(); try { pd = this.getPageData(); List<PageData> pdList = new
	 * ArrayList<PageData>(); String DATA_IDS = pd.getString("DATA_IDS"); if (null
	 * != DATA_IDS && !"".equals(DATA_IDS)) { String ArrayDATA_IDS[] =
	 * DATA_IDS.split(","); bankService.deleteAll(ArrayDATA_IDS); pd.put("msg",
	 * "ok"); } else { pd.put("msg", "no"); } pdList.add(pd); map.put("list",
	 * pdList);
	 * 
	 * ApplicationContext applicationContext =
	 * ContextLoader.getCurrentWebApplicationContext(); RedisService redisService =
	 * (RedisService)applicationContext.getBean("redisService");
	 * redisService.del("bankList");
	 * 
	 * } catch (Exception e) { logger.error(e.toString(), e); } finally {
	 * logAfter(logger); } return AppUtil.returnObject(pd, map); }
	 */
	
	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}
}
