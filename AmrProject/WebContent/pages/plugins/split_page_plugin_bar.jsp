<%@ page pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%-- 
	实现数据的分页显示条的控制，在我们之前你的tmall项目中是将以上的
	http://localhost:80/AmrProject/pages/admin/list.action
	<jsp:include page="split_page_plugin_bar.jsp"/>
	<base href="<%=basePath%>">：只有在一个完整的html或者jsp中才有效，也就是必须在<head></head>中才有效
--%>
<c:url var="pageLink" value="?">
   <c:forEach items="${param}" var="item">
       <c:if test="${item.key!='cp'}">
          <c:param name="${item.key}" value="${item.value}"/>
       </c:if>
   </c:forEach>
</c:url>
<div id="splitBarDiv" style="float:right">
	<ul class="pagination pagination-sm"> 
			      <c:if test="${pager.first}">
   					 <li><a href="<%=basePath%>${url}${pageLink}cp=${pager.cp-1}">上一页</a></li>
   				  </c:if>
   				   <c:if test="${!pager.first}">
   					 	<li class="disabled"><span>上一页</span></li>
   				  </c:if>
   				  <c:if test="${pager.hasPre}">
   				   	  <li class="disabled"><span>...</span></li>
   				  </c:if>
   				  <c:forEach items="${pager.pages}" var="page">
					  <c:if test="${pager.cp==page}">
					      <li  class="active"><a href="<%=basePath%>${url}${pageLink}cp=${page}">${page}</a></li>
					  </c:if>
					  <c:if test="${pager.cp!=page}">
					     <li> <a href="<%=basePath%>${url}${pageLink}cp=${page}">${page}</a></li>
					  </c:if>
					</c:forEach>
				  <c:if test="${pager.hasNext}">
   				  	   <li class="disabled"><span>...</span></li>
   				  </c:if>
   				  <c:if test="${pager.last}">
   				  		<li><a href="<%=basePath%>${url}${pageLink}cp=${pager.cp+1}">下一页</a></li>
   				  </c:if>
   				  <c:if test="${!pager.last}">
   				     <li class="disabled"><span>下一页</span><li>
   				  </c:if>
	     </ul>
</div>