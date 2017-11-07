package com.peng.amr.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.peng.amr.adapter.AbstractActionAdapter;
import com.peng.amr.service.IActionService;
@Controller
@RequestMapping("/pages/action/*")
public class ActionAction extends AbstractActionAdapter {
	@Resource
	private IActionService actionService;
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,int gid) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(6, request)) {
			try {
				mav.addObject("allActions",this.actionService.list(super.getEid(request), gid));
				mav.setViewName(super.getMsg("action.list.page"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
}
