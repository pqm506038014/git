package com.peng.amr.action;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.peng.amr.adapter.AbstractActionAdapter;
import com.peng.amr.service.IAdminService;
import com.peng.amr.vo.Emp;
import com.peng.util.MD5Code;
import com.peng.util.SplitUtil;
@Controller
@RequestMapping("/pages/admin/*")
public class AdminAction extends AbstractActionAdapter{
	@Resource
	private IAdminService adminService;
	/**
	 * �����ӹ���Ա֮ǰ��Ҫ���ʸ÷�������Ҫ��Ŀ���ǽ���Ա�ļ����ѯ֮�󱣴浽request���Է�Χ֮������ת���༭ҳ���ȡ
	 * @param request
	 * @return
	 */
	@RequestMapping("addPre")
	public ModelAndView addPre(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(1, request)) {//����Ȩ����֤��Ҫ��֤��ǰ���û��и�Ȩ�޲ſ��Բ�����Ҳ����Ҫ��֤��ǰ�������û���1��Ȩ�ޣ�
			try {
				Map<String, Object> map = this.adminService.addPre();
				//������ǹ�Ա�ļ�����Ϣ
				mav.addObject("allLevels",map.get("allLevels"));
				mav.addObject("url",super.getMsg("admin.list.action"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			//��ת��/pages/admin/admin_add.jsp
			mav.setViewName(super.getMsg("admin.add.page"));
		}else {
			//ʧ������ת��/errors.jsp
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	@RequestMapping("/checkEid")
	public void checkEid(int eid,HttpServletResponse response) {
		try {
			super.print(response, this.adminService.checkEid(eid));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/add")
	public ModelAndView add(Emp vo,MultipartFile pic,HttpServletRequest requset) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(2, requset)) {//��֤��ǰ�û��Ƿ���Ȩ��
			vo.setPhoto(super.createSingleFileName(pic));//ͼƬ��������Ҫ���浽���ݿ�
			vo.setHeid(super.getEid(requset));//���ò����˵ı��
			vo.setPassword(new MD5Code().getMD5ofStr(vo.getPassword()));//����ļ��ܴ���
			try {
				if(this.adminService.add(vo, super.getEid(requset))) {//����ҵ���ķ����������ݵ�����
					super.saveUploadFile(pic, requset, vo.getPhoto());//��ӳɹ�����Ƭ���浽������
					super.setMsgAndUrl("vo.add.success", "admin.add.action", mav);//��������ʾ��Ϣ����ת·��
				}else {//������ʾ��Ϣ����ת·��
					super.setMsgAndUrl("vo.add.failure", "admin.add.action", mav);
				}
			}catch(Exception e) {
				super.setMsgAndUrl("vo.add.failure", "admin.add.action", mav);
				e.printStackTrace();
			}
			mav.setViewName(super.getMsg("forward.page"));
		}else {//û��Ȩ������ת������ҳ��
			mav.setViewName(super.getMsg("errors.page"));
		}
		
		return mav;
	}
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(3, request)) {//Ȩ�޵���֤
			//����һ����ҳ��������Ĺ��������this��ʾ��ǰ�Ķ��󣬴��������Ŀ����ȡ�������и�����һЩ��Ϣ
			SplitUtil split = new SplitUtil(this);
			//���ø����еķ������пͻ��˴��ݵĲ����Ĵ���request�����ڸ�����ȡ�ò�����split�ڸ����д������
			super.handleSplit(request, split);
			try {
				Map<String, Object> map = this.adminService.list(super.getEid(request), split.getColumn(),
						split.getKeyword(),split.getCurrentPage(), split.getLineSize());
				//������ǲ鿴����Ա��·������Ҫ�ڷ�ҳ��ʱ���õ�������˵��һҳ
				map.put("url", super.getMsg("admin.list.action"));
				super.setPageMsg(map, mav);
			}catch(Exception e) {
				e.printStackTrace();
			}
			//��ת������Ա���б�ҳ�棨/pages/admin/admin_list.jsp��
			mav.setViewName(super.getMsg("admin.list.page"));
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
		return "����Ա";
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
