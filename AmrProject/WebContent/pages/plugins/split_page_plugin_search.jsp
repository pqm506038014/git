<%@ page pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%--
	  实现数据搜索条的控制
     <jsp:include page="split_page_plugin_search.jsp"/>
--%>
<%
	request.setCharacterEncoding("UTF-8") ;
    String url = basePath + (String)request.getAttribute("url");//因为这是一个表单，为了组装出表单的提交地址
	String columnData = (String)request.getAttribute("columnData") ;//取得下拉列表的数据，之后在下面进行拆分
	String column = (String)request.getAttribute("column") ;  //取得上一次查询的时候使用的字段
%>
<%-- <%=columnData%> --%>
<div class="row">
	<form action="<%=url%>" method="post" class="form-group"  id="splitSearchForm">
		<fieldset>
			<div class="form-group">
				<div class="col-md-2">&nbsp;</div>
				<%
					if (columnData != null){
				%>
					<div class="col-md-3">
						<select id="col" name="col" class="form-control">
				<%
					 String result [] = columnData.split("\\|") ;
					 for (int x = 0 ; x < result.length ; x ++) {
					   String temp[] = result[x].split(":") ;
				%>
						 <option value="<%=temp[1]%>" <%=column.equals(temp[1])?"selected":""%>><%=temp[0]%></option>
				<%
					   }
				%>
						</select>
					</div>
				<%
					}
				%>
				<div class="col-md-5">
					<input type="text" name="kw" id="kw" class="form-control input-sm"
						value="${keyWord}" placeholder="请输入检索关键字">
				</div>
				<div class="col-md-2">
					<button type="submit" class="btn btn-primary">检索</button>
					<input type="hidden" name="${paramName}" value="${paramValue}">
					<input type="hidden" name="cp" value="1">
				</div>
			</div>
		</fieldset>
	</form>
</div>
<div class="row">
	<div class="col-md-7 col-md-push-5"> 
		<p class="text-info">满足查询条件的数据量：${pager.count}</p>
	</div>
</div>