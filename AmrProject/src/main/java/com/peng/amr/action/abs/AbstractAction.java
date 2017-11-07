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
	@Resource // 可以自动将实现类装配上去
	private MessageSource messageSource;
	/**
	 * 日期转换的方法
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	}
	/**
	 * 设置所需要的msg和url数据
	 * @param msgKey
	 * @param urlKey
	 * @param mav
	 */
	public void setMsgAndUrl(String msgKey,String urlKey,ModelAndView mav) {
		mav.addObject("msg",this.getMsg(msgKey));
		mav.addObject("url",this.getMsg(urlKey));
	}
	/**
	 * 设置操作资源文件数据的读取处理
	 * @param key 资源文件的key信息
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
	 * 传递任意多个参数
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
	 * 取得文件名称，如果没有上传，则返回的是“nophoto.png”文件
	 * @param file 接受上传文件
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
	 * 删除文件的信息
	 * @param req
	 * @param fileName
	 */
	public void deleteFile(HttpServletRequest req,String fileName) {
		if(!"nophoto.png".equals(fileName)) {//如果是系统默认的照片则不让删除
			String filePath=req.getServletContext().getRealPath(this.getSaveFileDiv())+fileName;
			File file = new File(filePath);
			if(file.exists()) {
				file.delete();
			}
		}
	}

	/**
	 * 文件的保存处理操作
	 * @param file 包含所有的文件信息
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
	 * 取得文件的后缀
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
	 * 取出当前登陆用户的所有信息
	 * @param request
	 * @return
	 */
	public Emp getEmp(HttpServletRequest request) {
		return (Emp) request.getSession().getAttribute("emp");
	}
	/**
	 * 取得当前登陆用户的编号
	 * @param request
	 * @return
	 */
	public Integer getEid(HttpServletRequest request) {
		return ((Emp) request.getSession().getAttribute("emp")).getEid();
	}
	/**
	 * 判断当前的用户是否有该权限
	 * @param actid 当前登陆用户的权限id
	 * @param request
	 * @return
	 */
	public boolean isAuth(int actid,HttpServletRequest request) {
		//取出当前用户的所有信息
		Emp emp = this.getEmp(request);
		//迭代当前用户的权限组信息
		Iterator<Groups> iterGup = emp.getDept().getAllGroups().iterator();
		while(iterGup.hasNext()) {
			Groups gup = iterGup.next();
			//迭代每个权限组的权限信息
			Iterator<Action> iterAct = gup.getAllActions().iterator();
			while(iterAct.hasNext()) {
				Action act = iterAct.next();
				//如果指定的权限的编号在当前登陆的用户的权限编号的范围，表示有权限，返回true
				if(act.getActid().equals(actid)) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 实现信息的输出操作
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
	 * 处理客户端传递的参数
	 * @param request	主要目的是取得客户端传递的参数
	 * @param split	这是工具类的对象，处理客户端传递的参数
	 */
	public void handleSplit(HttpServletRequest request,SplitUtil split) {
		//将取得的参数保存到split对象中
		split.setCp(request.getParameter("cp"));
		split.setKw(request.getParameter("kw"));
		split.setLs(request.getParameter("ls"));
		split.setCol(request.getParameter("col"));
		//将需要在页面获取的一些参数保存到request中
		request.setAttribute("column", split.getColumn());
		request.setAttribute("keyWord", split.getKeyword());
		//在页面中生成下拉列表的数据，具体数据在子类中定义
		request.setAttribute("columnData", this.getColumnData());
	}
	/**
	 * 将查询到的数据从map集合中取出后保存到ModelAndView中
	 * @param map
	 * @param mav
	 */
	public void setPageMsg(Map<String, Object> map,ModelAndView mav) {
		mav.addObject("allEmps",map.get("allEmps"));
		mav.addObject("pager",map.get("pager"));
		mav.addObject("url",map.get("url"));
	}
	/**
	 * 实现列表的模糊分页查询字段，格式是：“标签：字段|标签：字段|标签：字段|”
	 * @return
	 */
	public abstract String getColumnData();
	/**
	 * 得到分页的默认列，具体的内容是子类指定的
	 * @return
	 */
	public abstract String getDefaultColumn();
	/**
	 * 返回CRUD操作时的执行标记
	 * @return
	 */
	public abstract Object getFlag();
	/**
	 * 取得上传文件的路径
	 * @return
	 */
	public abstract String getSaveFileDiv() ;
}
