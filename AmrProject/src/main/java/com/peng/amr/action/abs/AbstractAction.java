package com.peng.amr.action.abs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.peng.amr.vo.Action;
import com.peng.amr.vo.Emp;
import com.peng.amr.vo.Groups;
import com.peng.util.SplitUtil;

public abstract class AbstractAction {
	@Resource // �����Զ���ʵ����װ����ȥ
	private MessageSource messageSource;
	/**
	 * ����ת���ķ���
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	/**
	 * ��������Ҫ��msg��url����
	 * @param msgKey
	 * @param urlKey
	 * @param mav
	 */
	public void setMsgAndUrl(String msgKey,String urlKey,ModelAndView mav) {
		mav.addObject("msg",this.getMsg(msgKey));
		mav.addObject("url",this.getMsg(urlKey));
	}
	/**
	 * ���ò�����Դ�ļ����ݵĶ�ȡ����
	 * @param key ��Դ�ļ���key��Ϣ
	 * @return
	 */
	public String getMsg(String key) {
		try {
			return this.messageSource.getMessage(key, new Object[] {this.getFlag()},Locale.getDefault());
		}catch(Exception e) {
			return null;
		}
	}

	/**
	 * ��������������
	 * @param key
	 * @param args
	 * @return
	 */
	public String getMsg(String key, Object... args) {
		try{
			return this.messageSource.getMessage(key, args, Locale.getDefault());
		}catch(Exception e) {
			return null;
		}
	}
	/**
	 * ȡ���ļ����ƣ����û���ϴ����򷵻ص��ǡ�nophoto.png���ļ�
	 * @param file �����ϴ��ļ�
	 * @return
	 */
	public String createSingleFileName(MultipartFile file) {
		if(file == null) {
			return "nophoto.png";
		}
		if(file.getSize() <= 0) {
			return "nophoto.png";
		}
		return UUID.randomUUID()+"."+this.getFileExt(file.getContentType());
	}
	/*public String createNewFileName(MultipartFile file, HttpServletRequest req) {
		if (!file.isEmpty()) {
			Date date = new Date();
			return req.getSession().getId() + date.getTime() + "_" + file.getOriginalFilename();
		} else {
			return null;
		}
	}*/
	/**
	 * ɾ���ļ�����Ϣ
	 * @param req
	 * @param fileName
	 */
	public void deleteFile(HttpServletRequest req,String fileName) {
		if(!"nophoto.png".equals(fileName)) {//�����ϵͳĬ�ϵ���Ƭ����ɾ��
			String filePath=req.getServletContext().getRealPath(this.getSaveFileDiv())+fileName;
			File file = new File(filePath);
			if(file.exists()) {
				file.delete();
			}
		}
	}

	/**
	 * �ļ��ı��洦�����
	 * @param file �������е��ļ���Ϣ
	 * @param request
	 * @param fileName
	 * @return
	 */
	public boolean saveUploadFile(MultipartFile file,HttpServletRequest request,String fileName) {
		String filePath=request.getServletContext().getRealPath(this.getSaveFileDiv())+fileName;
		File outfile = new File(filePath);
		if(file.getSize()>0) {
			OutputStream output = null;
			InputStream input = null;
			try {
				if(!outfile.getParentFile().exists()) {
					outfile.getParentFile().mkdirs();
				}
				output = new FileOutputStream(outfile);
				input = file.getInputStream();
				byte data[] = new byte[2048];
				int temp = 0;
				while((temp=input.read(data)) != -1) {
					output.write(data, 0, temp);
				}
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}finally {
				if(output != null) {
					 try {
						output.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(input != null) {
					try {
						input.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}
	/**
	 * ȡ���ļ��ĺ�׺
	 * @param contentType
	 * @return
	 */
	public String getFileExt(String contentType) {
		if("image/bmp".equals(contentType)) {
			return "bmp";
		}else if("image/gif".equals(contentType)) {
			return "gif";
		}else if("image/jpeg".equals(contentType)) {
			return "jpeg";
		}else if("image/png".equals(contentType)) {
			return "png";
		}
		return null;
	}
	/**
	 * ȡ����ǰ��½�û���������Ϣ
	 * @param request
	 * @return
	 */
	public Emp getEmp(HttpServletRequest request) {
		return (Emp) request.getSession().getAttribute("emp");
	}
	/**
	 * ȡ�õ�ǰ��½�û��ı��
	 * @param request
	 * @return
	 */
	public Integer getEid(HttpServletRequest request) {
		return ((Emp) request.getSession().getAttribute("emp")).getEid();
	}
	/**
	 * �жϵ�ǰ���û��Ƿ��и�Ȩ��
	 * @param actid ��ǰ��½�û���Ȩ��id
	 * @param request
	 * @return
	 */
	public boolean isAuth(int actid,HttpServletRequest request) {
		//ȡ����ǰ�û���������Ϣ
		Emp emp = this.getEmp(request);
		//������ǰ�û���Ȩ������Ϣ
		Iterator<Groups> iterGup = emp.getDept().getAllGroups().iterator();
		while(iterGup.hasNext()) {
			Groups gup = iterGup.next();
			//����ÿ��Ȩ�����Ȩ����Ϣ
			Iterator<Action> iterAct = gup.getAllActions().iterator();
			while(iterAct.hasNext()) {
				Action act = iterAct.next();
				//���ָ����Ȩ�޵ı���ڵ�ǰ��½���û���Ȩ�ޱ�ŵķ�Χ����ʾ��Ȩ�ޣ�����true
				if(act.getActid().equals(actid)) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * ʵ����Ϣ���������
	 * @param response
	 * @param obj
	 */
	public void print(HttpServletResponse response ,Object obj) {
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			out = response.getWriter();
			out.print(obj);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			out.close();
		}
	}
	/**
	 * ����ͻ��˴��ݵĲ���
	 * @param request	��ҪĿ����ȡ�ÿͻ��˴��ݵĲ���
	 * @param split	���ǹ�����Ķ��󣬴���ͻ��˴��ݵĲ���
	 */
	public void handleSplit(HttpServletRequest request,SplitUtil split) {
		//��ȡ�õĲ������浽split������
		split.setCp(request.getParameter("cp"));
		split.setKw(request.getParameter("kw"));
		split.setLs(request.getParameter("ls"));
		split.setCol(request.getParameter("col"));
		//����Ҫ��ҳ���ȡ��һЩ�������浽request��
		request.setAttribute("column", split.getColumn());
		request.setAttribute("keyWord", split.getKeyword());
		//��ҳ�������������б�����ݣ����������������ж���
		request.setAttribute("columnData", this.getColumnData());
	}
	/**
	 * ����ѯ�������ݴ�map������ȡ���󱣴浽ModelAndView��
	 * @param map
	 * @param mav
	 */
	public void setPageMsg(Map<String, Object> map,ModelAndView mav) {
		mav.addObject("allEmps",map.get("allEmps"));
		mav.addObject("pager",map.get("pager"));
		mav.addObject("url",map.get("url"));
	}
	/**
	 * ʵ���б��ģ����ҳ��ѯ�ֶΣ���ʽ�ǣ�����ǩ���ֶ�|��ǩ���ֶ�|��ǩ���ֶ�|��
	 * @return
	 */
	public abstract String getColumnData();
	/**
	 * �õ���ҳ��Ĭ���У����������������ָ����
	 * @return
	 */
	public abstract String getDefaultColumn();
	/**
	 * ����CRUD����ʱ��ִ�б��
	 * @return
	 */
	public abstract Object getFlag();
	/**
	 * ȡ���ϴ��ļ���·��
	 * @return
	 */
	public abstract String getSaveFileDiv() ;
}
