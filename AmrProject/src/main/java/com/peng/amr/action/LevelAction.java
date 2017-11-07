package com.peng.amr.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.peng.amr.adapter.AbstractActionAdapter;
import com.peng.amr.service.ILevelService;
@Controller
@RequestMapping("/pages/level/*")
public class LevelAction extends AbstractActionAdapter{
	@Resource
	private ILevelService levelService;
	@RequestMapping("/checkSalary")//因为访问这个路径的请求一定是异步请求，所以这里的返回值可以是void
	public void checkSalary(int lid ,double salary, HttpServletResponse response) {
		try {
			super.print(response, this.levelService.checkSalary(salary, lid));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
