<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
	Exception ex = (Exception) request.getAttribute("exception");
	String title = "";
	String errWord = "";
	String backURL = "";
	String refresh = "";
	int jump = 0;
	String msg = ex.getMessage();
	String[] msgs = msg.split("\\|");
	if (msgs.length == 1) {
		title = "错误信息";
		errWord = msgs[0].trim();
		backURL = "";
	} else if (msgs.length == 3) {
		title = msgs[0];
		errWord = msgs[1].trim();
		backURL = msgs[2].trim();

	} else if (msgs.length == 4) {
		title = msgs[0].trim();
		errWord = msgs[1].trim();
		backURL = msgs[2].trim();
		refresh = msgs[3].trim();
		try {
			jump = Integer.parseInt(refresh);
		} catch (Exception e) {
			jump = 0;
			e.printStackTrace();

		}
	} else {
		title = "错误信息";
		errWord = "系统异常";
		backURL = "";
	}
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="${ctx}/css/menu.css" rel="stylesheet"></link>
		<link href="${ctx}/css/font_table_style.css" rel="stylesheet"></link>
		<link href="${ctx}/css/font.css" rel="stylesheet"></link>
		<title><%=title%>
		</title>
		<%
		if (jump > 0) {
		%>
		<script type="text/javascript" charset=utf-8>
        setTimeout(jumpToURL,<%=jump%>); 
        
		function jumpToURL()
		{ 
			window.location="<%=backURL%>";
		} 
		</script>
		<%
		}
		%>
	</head>

	<body>
		<table width="742" height="525" align="center">
			<!--这个框的大小（742 525）-->
			<tr>
				<td width="5" height="73">
					&nbsp;
				</td>
				<td width="723" bgcolor="#5AACF6" class="big_tile">
					<%=title%>
				</td>
				<td width="3">
					&nbsp;
				</td>
			</tr>
			<tr bordercolor="#DE7A42">
				<td height="58" colspan="3">
					<table width="717" height="38">
						<tr>
							<td width="44">
								&nbsp;
							</td>
							<td width="84">
								<img src="${ctx}/images/S.jpg" width="71" height="48" />
							</td>
							<td width="573" class="STYLE6">
								<span class="smo_title"><%=title%> </span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr bordercolor="#DE7A42">
				<td colspan="3" align="left" valign="top">

					<form method="post" action="">
						<table width="722" height="213" align="center">
							<tr>
								<td align="center">
									<%
									if (jump > 0) {
									%>
									<%=errWord%>
									<%
									} else {
									%>
									<%=errWord%>
									<br>
									<%
									if (!("").equals(backURL) && backURL != null) {
									%>
									<a href="<%=backURL%>">返回</a>
									<%
												} else {
												String ref = request.getHeader("REFERER");
									%>
									<a href="#" onclick="javascript:window.location='<%=ref%>'">返回上一页</a>
									<%
										}
									}
									%>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
