package com.peng.amr.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.peng.amr.adapter.AbstractActionAdapter;
import com.peng.amr.service.IDetailsService;
import com.peng.amr.vo.Details;
import com.peng.amr.vo.Emp;
@Controller
@RequestMapping("pages/res/*")
public class DetailsAction  extends AbstractActionAdapter{
	@Resource
	private IDetailsService detailsService;
	/**
	 * ����ǰ�Ĵ���������ѯ�����еĸ������Ϣ��֮����ת�����뵥��ҳ��
	 * @param request
	 * @return
	 */
	
	@RequestMapping("addPre")
	public ModelAndView addPre(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		try {
			Map<String, Object> map = this.detailsService.addPre(super.getEid(request));
			mav.addObject("allTypes",map.get("allTypes"));
			//��ת��ָ��ҳ�棬��ת���༭��Ϣ��ҳ��
			//mav.setViewName(super.getMsg("details.add.page"));
			mav.addObject("url",super.getMsg("details.add.action"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		mav.setViewName(super.getMsg("details.add.page"));
		return mav;
	}
	
	@RequestMapping("add")
	public ModelAndView add(Details vo,MultipartFile pic,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(pic != null||pic.getSize()>0) {
			vo.setPhoto(super.createSingleFileName(pic));
		}
		if(super.isAuth(25, request)) {//Ȩ����֤
			
			try {
				if(this.detailsService.add(vo, super.getEid(request))) {
					super.saveUploadFile(pic, request, vo.getPhoto());
					//������ʾ��Ϣ������Ϣ����forward.pageҳ�����
					super.setMsgAndUrl("vo.add.success", "details.list.action", mav);
				}else {
					super.setMsgAndUrl("vo.add.failure", "details.list.action", mav);
				}
			}catch(Exception e) {
				super.setMsgAndUrl("vo.add.failure", "details.list.action", mav);
			}
			mav.setViewName(super.getMsg("forward.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	
	@RequestMapping("prebuy")
	public ModelAndView listDetails(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(isAuth(25, request)) {
			try {
				mav.addObject("allDetails", this.detailsService.listDetails(super.getEid(request)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("details.list.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	
	@RequestMapping("rm")
	public void rm(HttpServletRequest request,HttpServletResponse response,String deletestr) {
		if(super.isAuth(25, request)) {//Ȩ����֤
			List<Integer> deletelist = new ArrayList<Integer>();
			if(!(deletestr == null || "".equals(deletestr))) {
				String deleteResult[] = deletestr.split("\\|");
				for(int i=0;i<deleteResult.length;i++) {
					deletelist.add(Integer.parseInt(deleteResult[i]));
				}
				//ʵ��ɾ������������map���ü����б��������е��嵥��Ϣ��ɾ�����
				try {
					Map<String, Object> map = this.detailsService.rm(super.getEid(request), deletelist);
					List<Details> allDetails = (List<Details>) map.get("allDetails");
					if(allDetails != null) {
						Iterator<Details> iter = allDetails.iterator();
						while(iter.hasNext()) {
							//��ɾ����Ӧ����Ƭ��Ϣ
							super.deleteFile(request, iter.next().getPhoto());
						}
					}
					//�����յ�ɾ��������ظ��ͻ���
					super.print(response, map.get("flag"));
				} catch (Exception e) {
					e.printStackTrace();
					super.print(response, false);
				}
			}
		}else {
			super.print(response, false);
		}
	}
	
	@RequestMapping("editAmount")
	public void editAmount(String updatestr,String deletestr,HttpServletRequest request,HttpServletResponse response) {
		if(super.isAuth(25, request)) {//Ȩ����֤
			String updateResult[] = updatestr.split("\\|");//��������Ϊ0���ַ������
			//updateMap���������Ҫ�޸ĵ��嵥��������key=��ţ�value=����
			Map<Integer, Integer> updateMap = new HashMap<Integer, Integer>();
			for (int i = 0; i < updateResult.length; i++) {
				String temp[] = updateResult[i].split(":");
				updateMap.put(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
			}
			//����Ҫɾ�����嵥���
			List<Integer> deleteList = new ArrayList<Integer>();
			if(!(deletestr == null || "".equals(deletestr))) {
				String deleteResult[] = deletestr.split("\\|");
				for (int i = 0; i < deleteResult.length; i++) {
					deleteList.add(Integer.parseInt(deleteResult[i]));
				}
			}
			//����׼�����֮�����ҵ���ķ���ʵ�ִ���
			try {
				Map<String, Object> map = 
						this.detailsService.editAmount(super.getEid(request), updateMap, deleteList);
				List<Details> allDetails = (List<Details>) map.get("allDetails");
				if(allDetails != null) {
					Iterator<Details> iter = allDetails.iterator();
					while(iter.hasNext()) {
						//ɾ����Ӧ����Ƭ��Ϣ
						super.deleteFile(request, iter.next().getPhoto());
					}
				}
				//������մ�����
				super.print(response, map.get("flag"));
			}catch(Exception e) {
				e.printStackTrace();
				super.print(response, false);
			}
		}else {
			super.print(response, false);
		}
	}
	/**
	 * �༭ǰ��׼������
	 * @param did	�����˵ı��
	 * @param request
	 * @return
	 */
	@RequestMapping("editPre")
	public ModelAndView editPre(Integer did,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(25, request)) {
			try {
				Map<String, Object> map = this.detailsService.editPre(did, super.getEid(request));
				mav.addObject("allTypes",map.get("allTypes"));
				mav.addObject("allSubtypes",map.get("allSubtypes"));
				mav.addObject("details",map.get("details"));
			}catch(Exception e) {
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("details.edit.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	/**
	 * ʵ���嵥�ı༭
	 * @param vo
	 * @param pic
	 * @param request
	 * @return
	 */
	@RequestMapping("edit")
	public ModelAndView edit(Details vo,MultipartFile pic,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(25, request)) {//Ȩ����֤
			vo.setEmp(new Emp());
			vo.getEmp().setEid(super.getEid(request));
			try {
				if(this.detailsService.edit(super.getEid(request), vo)) {
					//����ӿͻ������ϴ�����Ƭ���򸲸�֮ǰ����Ƭ
					if(pic != null && pic.getSize() > 0) {
						super.saveUploadFile(pic, request, vo.getPhoto());
					}
					super.setMsgAndUrl("vo.edit.success", "details.list.action", mav);
				}else {
					super.setMsgAndUrl("vo.edit.failure", "details.list.action", mav);
				}
			}catch(Exception e) {
				e.printStackTrace();
				super.setMsgAndUrl("vo.edit.failure", "details.list.action", mav);
			}
			mav.setViewName(super.getMsg("forward.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	
	
	@Override
	public String getSaveFileDiv() {
		return "/upload/res/";
	}
	@Override
	public Object getFlag() {
		return "������Ʒ";
	}
}
