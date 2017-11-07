package com.peng.amr.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.peng.amr.adapter.AbstractActionAdapter;
import com.peng.amr.service.IAdminService;
import com.peng.amr.service.IEmpService;
import com.peng.amr.vo.Emp;
import com.peng.util.MD5Code;
import com.peng.util.SplitUtil;
@Controller
@RequestMapping("/pages/emp/*")
public class EmpAction extends AbstractActionAdapter{
	@Resource
	private IEmpService empService;
	@Resource
	private IAdminService adminService;
	@RequestMapping("addPre")
	public ModelAndView addPre(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(12, request)) {
			try {
				Map<String, Object> map = this.empService.addPre();
				mav.addObject("allLevels",map.get("allLevels"));
				mav.addObject("allDepts",map.get("allDepts"));
				mav.addObject("url", super.getMsg("emp.list.action"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			//��תҳ��
			mav.setViewName(super.getMsg("emp.add.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	/**
	 * ���ӹ�Ա�ķ���
	 * @param vo
	 * @param pic
	 * @param requset
	 * @return
	 */
	@RequestMapping("add")
	public ModelAndView add(Emp vo,MultipartFile pic,HttpServletRequest requset) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(12, requset)) {//��֤��ǰ�û��Ƿ���Ȩ��
			vo.setPhoto(super.createSingleFileName(pic));//ͼƬ��������Ҫ���浽���ݿ�
			vo.setHeid(super.getEid(requset));//���ò����˵ı��
			vo.setPassword(new MD5Code().getMD5ofStr(vo.getPassword()));//����ļ��ܴ���
			try {
				if(this.empService.add(vo, super.getEid(requset))) {//����ҵ���ķ����������ݵ�����
					super.saveUploadFile(pic, requset, vo.getPhoto());//��ӳɹ�����Ƭ���浽������
					super.setMsgAndUrl("vo.add.success", "emp.add.action", mav);//��������ʾ��Ϣ����ת·��
				}else {//������ʾ��Ϣ����ת·��
					super.setMsgAndUrl("vo.add.failure", "emp.add.action", mav);
				}
			}catch(Exception e) {
				super.setMsgAndUrl("vo.add.failure", "emp.add.action", mav);
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("forward.page"));
		}else {//û��Ȩ������ת������ҳ��
			mav.setViewName(super.getMsg("errors.page"));
		}
		
		return mav;
	}
	/**
	 * ��ʾ��Ա���б���Ϣ
	 * @param request
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(13, request)) {
			SplitUtil split = new SplitUtil(this);
			super.handleSplit(request, split);
			try {
				Map<String, Object> map = this.empService.list(super.getEid(request), split.getColumn(),
						split.getKeyword(), split.getCurrentPage(), split.getLineSize());
				map.put("url", super.getMsg("emp.list.action"));
				super.setPageMsg(map, mav);
			}catch(Exception e) {
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("emp.list.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	
	/**
	 * ʵ�ִӲ˵���ת���༭ҳ�棬���ҽ����ݻ��Ե��༭ҳ��
	 * @param eid	���޸ĵĹ�Ա�ı��
	 * @param request
	 * @return
	 */
	@RequestMapping("editPre")
	public ModelAndView editPre(Integer eid,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(15, request)) {
			try {
				Map<String, Object> map = this.empService.editPre(eid);
				mav.addObject("allLevels",map.get("allLevels"));
				mav.addObject("allDepts",map.get("allDepts"));
				mav.addObject("editEmp",map.get("emp"));
			}catch(Exception e) {
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("emp.edit.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	/**
	 * ʵ�ֹ�Ա�ı༭
	 * @param vo
	 * @param pic
	 * @param request
	 * @return
	 */
	@RequestMapping("edit")
	public ModelAndView edit(Emp vo,MultipartFile pic,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(15, request)) {//��֤Ȩ��
			if(pic != null&&pic.getSize() > 0) {//����һ���µ�ͼƬ������
				vo.setPhoto(super.createSingleFileName(pic));
			}
			vo.setHeid(super.getEid(request));
			if(vo.getPassword() == null||"".equals(vo.getPassword())) {
				vo.setPassword(null);
			}else {
				vo.setPassword(new MD5Code().getMD5ofStr(vo.getPassword()));
			}
			try {
				if(this.empService.edit(vo, super.getEid(request))) {
					super.saveUploadFile(pic, request, vo.getPhoto());
					super.setMsgAndUrl("vo.edit.success", "emp.list.action", mav);
				}else {
					super.setMsgAndUrl("vo.edit.failure", "emp.list.action", mav);
				}
			}catch(Exception e) {
				super.setMsgAndUrl("vo.edit.failure", "emp.list.action", mav);
			}
			mav.setViewName(super.getMsg("forward.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	@Override
	public String getSaveFileDiv() {
		return "/upload/emp/";
	}
	@Override
	public Object getFlag() {
		return "��Ա";
	}
	@Override
	public String getDefaultColumn() {
		return "name";
	}
	@Override
	public String getColumnData() {
		return "��Ա����:name|��Ա���:eid|��Ա�绰:phone";
	}
}
