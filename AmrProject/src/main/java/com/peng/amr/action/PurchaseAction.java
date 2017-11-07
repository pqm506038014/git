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
	 * ʵ�����뵥���ύ
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
					//������forward.jsp�е���ʾ��Ϣ����ת·��
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
	 * ʵ�����뵥��Ϣ�б��·��
	 * @param request
	 * @return
	 */
	@RequestMapping("apply")
	public ModelAndView apply(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(27, request)) {//��Ҫ27��Ȩ��
			SplitUtil split = new SplitUtil(this);//�����û��ύ���йط�ҳ�Ĳ����Ķ���
			super.handleSplit(request, split);//���û��ύ�Ĳ�������spl���д���
			try {
				//����ҵ���ķ�����ѯ���ݣ����ݱ��浽map������
				Map<String, Object> map = this.purchaseService.listByEmp(super.getEid(request),
						split.getCurrentPage(), split.getLineSize());
				//��map�е�����ȡ�����浽ModelAndView��(request���Է�Χ)
				mav.addObject("allPurchases",map.get("allPurchases"));
				//������ǵ�ǰ������·����Ŀ����Ϊ��һҳ����һҳ�ṩ·��
				mav.addObject("url",super.getMsg("purchase.apply.action"));
				mav.addObject("pager",map.get("pager"));
			} catch(Exception e) {
				e.printStackTrace();
			}
			//��ת
			mav.setViewName(super.getMsg("purchase.apply.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	/**
	 * �����˲�ѯ���뵥����ķ���
	 * @param pid
	 * @param request
	 * @return
	 */
	@RequestMapping("show")
	public ModelAndView show(Integer pid,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(27, request)) {
			try {
				//����ѯ������Ϣ���浽request���Է�Χ
				mav.addObject("purchase", this.purchaseService.getByEmp(super.getEid(request), pid));
			}catch(Exception e) {
				e.printStackTrace();
			}
			//��ת�����뵥����ҳ��
			mav.setViewName(super.getMsg("purchase.show.page"));
		}else {
			mav.setViewName(super.getMsg("errors.page"));
		}
		return mav;
	}
	/**
	 * Ϊ�����Ų�ѯ���뵥��Ϣ
	 * @param request
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(super.isAuth(41, request)) {
			SplitUtil split = new SplitUtil(this);//�����û��ύ���йط�ҳ�Ĳ����Ķ���
			super.handleSplit(request, split);//���û��ύ�Ĳ�������spl���д���
			try {
				//����ҵ���ķ�����ѯ���ݣ����ݱ��浽map������
				Map<String, Object> map = this.purchaseService.listSimple(super.getEid(request),
						split.getCurrentPage(), split.getLineSize());
				//��map�е�����ȡ�����浽ModelAndView��(request���Է�Χ)
				mav.addObject("allPurchases",map.get("allPurchases"));
				//������ǵ�ǰ������·����Ŀ����Ϊ��һҳ����һҳ�ṩ·��
				mav.addObject("url",super.getMsg("purchase.list.action"));
				mav.addObject("pager",map.get("pager"));
			} catch(Exception e) {
				e.printStackTrace();
			}
			//��ת
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
				//����ѯ������Ϣ���浽request���Է�Χ
				mav.addObject("purchase", this.purchaseService.show(super.getEid(request), pid));
			}catch(Exception e) {
				e.printStackTrace();
			}
			//��ת�����뵥����ҳ��
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
		return "���뵥";
	}

}
