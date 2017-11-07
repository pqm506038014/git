package com.peng.amr.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.peng.amr.adapter.AbstractActionAdapter;
import com.peng.amr.service.IPurchaseService;
import com.peng.amr.vo.Purchase;
import com.peng.util.SplitUtil;
@Controller
@RequestMapping("/pages/purchase/*")
public class PurchaseAction extends AbstractActionAdapter{
	@Resource
	private IPurchaseService purchaseService;
	/**
	 * 实现申请单的提交
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping("add")
	public ModelAndView add(HttpServletRequest request,Purchase vo) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(30, request)) {
			try {
				if(this.purchaseService.add(super.getEid(request), vo)) {
					//设置在forward.jsp中的提示信息和跳转路径
					super.setMsgAndUrl("vo.add.success", "purchase.apply.action", mav);
				}else {
					super.setMsgAndUrl("vo.add.failure", "purchase.apply.action", mav);
				}
			} catch (Exception e) {
				e.printStackTrace();
				super.setMsgAndUrl("vo.add.failure", "purchase.apply.action", mav);
			}
			mav.setViewName(super.getMsg("forward.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	/**
	 * 实现申请单信息列表的路径
	 * @param request
	 * @return
	 */
	@RequestMapping("apply")
	public ModelAndView apply(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(27, request)) {//需要27号权限
			SplitUtil split = new SplitUtil(this);//处理用户提交的有关分页的参数的对象
			super.handleSplit(request, split);//将用户提交的参数交给spl进行处理
			try {
				//调用业务层的方法查询数据，数据保存到map集合中
				Map<String, Object> map = this.purchaseService.listByEmp(super.getEid(request),
						split.getCurrentPage(), split.getLineSize());
				//将map中的数据取出保存到ModelAndView中(request属性范围)
				mav.addObject("allPurchases",map.get("allPurchases"));
				//保存的是当前方法的路径，目的是为上一页和下一页提供路径
				mav.addObject("url",super.getMsg("purchase.apply.action"));
				mav.addObject("pager",map.get("pager"));
			} catch(Exception e) {
				e.printStackTrace();
			}
			//跳转
			mav.setViewName(super.getMsg("purchase.apply.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	/**
	 * 申请人查询申请单详情的方法
	 * @param pid
	 * @param request
	 * @return
	 */
	@RequestMapping("show")
	public ModelAndView show(Integer pid,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(27, request)) {
			try {
				//将查询到的信息保存到request属性范围
				mav.addObject("purchase", this.purchaseService.getByEmp(super.getEid(request), pid));
			}catch(Exception e) {
				e.printStackTrace();
			}
			//跳转到申请单详情页面
			mav.setViewName(super.getMsg("purchase.show.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	/**
	 * 为财务部门查询申请单信息
	 * @param request
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(41, request)) {
			SplitUtil split = new SplitUtil(this);//处理用户提交的有关分页的参数的对象
			super.handleSplit(request, split);//将用户提交的参数交给spl进行处理
			try {
				//调用业务层的方法查询数据，数据保存到map集合中
				Map<String, Object> map = this.purchaseService.listSimple(super.getEid(request),
						split.getCurrentPage(), split.getLineSize());
				//将map中的数据取出保存到ModelAndView中(request属性范围)
				mav.addObject("allPurchases",map.get("allPurchases"));
				//保存的是当前方法的路径，目的是为上一页和下一页提供路径
				mav.addObject("url",super.getMsg("purchase.list.action"));
				mav.addObject("pager",map.get("pager"));
			} catch(Exception e) {
				e.printStackTrace();
			}
			//跳转
			mav.setViewName(super.getMsg("purchase.list.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	
	@RequestMapping("show5")
	public ModelAndView show5(Integer pid,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(41, request)) {
			try {
				//将查询到的信息保存到request属性范围
				mav.addObject("purchase", this.purchaseService.show(super.getEid(request), pid));
			}catch(Exception e) {
				e.printStackTrace();
			}
			//跳转到申请单详情页面
			mav.setViewName(super.getMsg("purchase.show.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	

	@Override
	public String getSaveFileDiv() {
		// TODO Auto-generated method stub
		return super.getSaveFileDiv();
	}
	@Override
	public Object getFlag() {
		return "申请单";
	}

}
