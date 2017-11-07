package com.peng.amr.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.peng.amr.adapter.AbstractActionAdapter;
import com.peng.amr.service.IGroupsService;
@Controller
@RequestMapping("/pages/groups/*")
public class GroupsAction extends AbstractActionAdapter {
	@Resource
	private IGroupsService groupsService;
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,int did) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(6, request)) {
			try {
				mav.addObject("allGroups",this.groupsService.findAllByDept(super.getEid(request), did));
				mav.setViewName(super.getMsg("groups.list.page"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
}
