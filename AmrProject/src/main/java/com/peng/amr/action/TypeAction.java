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
			//����һ��JSON�������飬�������JSON����
			JSONArray array = new JSONArray();
			while(iter.hasNext()) {
				Subtype sub = iter.next();
				//����һ��JSON����
				JSONObject temp = new JSONObject();
				//�������ı�ű��浽JSON������
				temp.put("stid", sub.getStid());
				//�����������Ʊ��浽JSON������
				temp.put("title", sub.getTitle());
				//��JSON���󱣴浽JSON������
				array.add(temp);
			}
			//��JSON���������
			super.print(response, array);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
