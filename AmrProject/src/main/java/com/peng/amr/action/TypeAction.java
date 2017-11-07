package com.peng.amr.action;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.peng.amr.adapter.AbstractActionAdapter;
import com.peng.amr.service.ISubtypeService;
import com.peng.amr.vo.Subtype;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/pages/type/*")
public class TypeAction extends AbstractActionAdapter {
	@Resource
	private ISubtypeService subtypeService;
	@RequestMapping("getSubtype")
	public void getSubtype(Integer tid,HttpServletResponse response) {
		try {
			List<Subtype> all = this.subtypeService.list(tid);
			Iterator<Subtype> iter = all.iterator();
			//创建一个JSON对象数组，保存的是JSON对象
			JSONArray array = new JSONArray();
			while(iter.hasNext()) {
				Subtype sub = iter.next();
				//创建一个JSON对象
				JSONObject temp = new JSONObject();
				//将子类别的编号保存到JSON对象中
				temp.put("stid", sub.getStid());
				//将子类别的名称保存到JSON对象中
				temp.put("title", sub.getTitle());
				//将JSON对象保存到JSON数组中
				array.add(temp);
			}
			//将JSON的数组输出
			super.print(response, array);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
