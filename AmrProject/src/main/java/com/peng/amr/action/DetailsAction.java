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
	 * 增加前的处理方法，查询到所有的父类别信息，之后跳转到申请单的页面
	 * @param request
	 * @return
	 */
	
	@RequestMapping("addPre")
	public ModelAndView addPre(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		try {
			Map<String, Object> map = this.detailsService.addPre(super.getEid(request));
			mav.addObject("allTypes",map.get("allTypes"));
			//跳转到指定页面，跳转到编辑信息的页面
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
		if(super.isAuth(25, request)) {//权限验证
			
			try {
				if(this.detailsService.add(vo, super.getEid(request))) {
					super.saveUploadFile(pic, request, vo.getPhoto());
					//保存提示信息，该信息是在forward.page页面输出
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
		if(super.isAuth(25, request)) {//权限验证
			List<Integer> deletelist = new ArrayList<Integer>();
			if(!(deletestr == null || "".equals(deletestr))) {
				String deleteResult[] = deletestr.split("\\|");
				for(int i=0;i<deleteResult.length;i++) {
					deletelist.add(Integer.parseInt(deleteResult[i]));
				}
				//实现删除操作，返回map，该集合中保存了所有的清单信息和删除标记
				try {
					Map<String, Object> map = this.detailsService.rm(super.getEid(request), deletelist);
					List<Details> allDetails = (List<Details>) map.get("allDetails");
					if(allDetails != null) {
						Iterator<Details> iter = allDetails.iterator();
						while(iter.hasNext()) {
							//逐步删除对应的照片信息
							super.deleteFile(request, iter.next().getPhoto());
						}
					}
					//将最终的删除结果返回给客户端
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
		if(super.isAuth(25, request)) {//权限验证
			String updateResult[] = updatestr.split("\\|");//将数量不为0的字符串拆分
			//updateMap保存的是需要修改的清单和数量：key=编号，value=数量
			Map<Integer, Integer> updateMap = new HashMap<Integer, Integer>();
			for (int i = 0; i < updateResult.length; i++) {
				String temp[] = updateResult[i].split(":");
				updateMap.put(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
			}
			//保存要删除的清单编号
			List<Integer> deleteList = new ArrayList<Integer>();
			if(!(deletestr == null || "".equals(deletestr))) {
				String deleteResult[] = deletestr.split("\\|");
				for (int i = 0; i < deleteResult.length; i++) {
					deleteList.add(Integer.parseInt(deleteResult[i]));
				}
			}
			//数据准备完毕之后调用业务层的方法实现处理
			try {
				Map<String, Object> map = 
						this.detailsService.editAmount(super.getEid(request), updateMap, deleteList);
				List<Details> allDetails = (List<Details>) map.get("allDetails");
				if(allDetails != null) {
					Iterator<Details> iter = allDetails.iterator();
					while(iter.hasNext()) {
						//删除对应的照片信息
						super.deleteFile(request, iter.next().getPhoto());
					}
				}
				//输出最终处理标记
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
	 * 编辑前的准备操作
	 * @param did	操作人的编号
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
	 * 实现清单的编辑
	 * @param vo
	 * @param pic
	 * @param request
	 * @return
	 */
	@RequestMapping("edit")
	public ModelAndView edit(Details vo,MultipartFile pic,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(25, request)) {//权限验证
			vo.setEmp(new Emp());
			vo.getEmp().setEid(super.getEid(request));
			try {
				if(this.detailsService.edit(super.getEid(request), vo)) {
					//如果从客户端重上传了照片，则覆盖之前的照片
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
		return "待购物品";
	}
}
