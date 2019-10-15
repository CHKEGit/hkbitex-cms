package com.fh.service.sys.bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.redis.RedisService;
import com.fh.util.Const;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;


@Service("bankService")
public class BankService extends BaseController{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/*
	* 新增
	*/
	public ModelAndView save(PageData pd)throws Exception{
		ModelAndView mv = this.getModelAndView();//获取视图模型
		pd.put("bank_id", this.get32UUID()); // 主键
		/*
		 * pd.put("STATUS", "1"); // 状态
		 * 
		 * ApplicationContext applicationContext =
		 * ContextLoader.getCurrentWebApplicationContext(); RedisService redisService =
		 * (RedisService)applicationContext.getBean("redisService");
		 * redisService.del("bankList");
		 */
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		dao.save("BankMapper.save", pd);
		return mv;
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("BankMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public ModelAndView edit(PageData pd)throws Exception{
		ModelAndView mv = this.getModelAndView();//获取视图模型
		dao.update("BankMapper.edit", pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		/*
		 * ApplicationContext applicationContext =
		 * ContextLoader.getCurrentWebApplicationContext(); RedisService redisService =
		 * (RedisService)applicationContext.getBean("redisService");
		 * redisService.del("bankList");
		 */
		return mv;
	}

	/*
	* 通过id获取数据并跳至修改页面
	*/
	public ModelAndView findById(PageData pd)throws Exception{
		ModelAndView mv = this.getModelAndView();
		try {
			mv.setViewName("sys/bank/bank_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", (PageData)dao.findForObject("BankMapper.findById", pd));// 根据ID读取
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/*
	*列表
	*/
	public ModelAndView list(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		try {
			List<PageData> bankList = (List<PageData>)dao.findForList("BankMapper.datalistPage", page); // 列出Bank列表
			/*
			 * ApplicationContext applicationContext =
			 * ContextLoader.getCurrentWebApplicationContext(); RedisService redisService =
			 * (RedisService)applicationContext.getBean("redisService");
			 * if(!(redisService.exists("bankList"))) {//存在即不取 logBefore(logger,
			 * "存入redis银行卡信息"); redisService.setList("bankList", bankList);//加入缓存--银行卡信息
			 * 
			 * redisService.expire("bankList",1800);//设置缓存时间，单位秒 }
			 */
			mv.setViewName("sys/bank/bank_list");
			/* List<PageData> bankList =redisService.getList("bankList", PageData.class); */
			mv.addObject("varList", bankList);
			mv.addObject("pd", page.getPd());
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/*
	* 修改银行卡状态
	*/
	public void updStatus(String bank_id, String status)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("bank_id", bank_id);
		pd.put("STATUS", status);
		/*
		 * ApplicationContext applicationContext =
		 * ContextLoader.getCurrentWebApplicationContext(); RedisService redisService =
		 * (RedisService)applicationContext.getBean("redisService");
		 * redisService.del("bankList");
		 */
		dao.update("BankMapper.updStatus", pd);
	}
	/*
	*判断数据库银行卡号
	*/
	public Integer selectByyhk(String RECEIVING_PAYMENT_BANK) throws Exception{
		PageData pd = new PageData();
		Integer bol = 0;
		try {
			pd = this.getPageData();
			pd.put("RECEIVING_PAYMENT_BANK", RECEIVING_PAYMENT_BANK);
			List<PageData> varList = (List<PageData>)dao.findForList("BankMapper.selectByyhk",pd);
			if (varList.size() != 0) {
				bol = 1;
			} else {
				bol = 2;
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return bol;
	}
	
	/*
	*列表(全部)导出到excel
	*/
	public ModelAndView listAll(PageData pd)throws Exception{
		ModelAndView mv = new ModelAndView();
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("收款银行"); // 1
			titles.add("收款银行卡号"); // 2
			titles.add("收款开户支行"); // 3
			titles.add("收款账户名"); // 4
			titles.add("创建时间"); // 5
			dataMap.put("titles", titles);
			List<PageData> varOList = (List<PageData>)dao.findForList("BankMapper.listAll", pd);
			List<PageData> varList = new ArrayList<PageData>();
			for (int i = 0; i < varOList.size(); i++) {
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("RECEIVING_BANK_NAME")); // 1
				vpd.put("var2", varOList.get(i).getString("RECEIVING_PAYMENT_BANK")); // 2
				vpd.put("var3", varOList.get(i).getString("RECEIVING_BANK_BRANCH")); // 3
				vpd.put("var4", varOList.get(i).getString("RECEIVING_NAME")); // 4
				vpd.put("var5", varOList.get(i).get("CREATE_TIME").toString()); // 5
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv, dataMap);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("BankMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

