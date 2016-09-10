<%@ page contentType="text/html;charset=utf-8"  %>
<HTML>
<style>
body{font-size:9pt;}
table{border:0px;}
.page{width:99%;clear:left;text-align:left;color:#295568;margin-top:10px;overflow:hidden;}
.page td span{font-weight:bold;}
.pagenav td{margin-left:3px;color:#295568;}
.tz{width:35px;border:solid 1px #7aaebd;height:17px;}
</style>
<BODY>
<table class="page">
		<tr>
			<td>共有 <span><c:out value="${pagination.total}"/></span> 条记录，当前第 <span><c:out value="${pagination.pageid}"/></span> 页，共 <span><c:out value="${pagination.lastid}"/></span> 页</td>
			<td align=right>
				<table>
					<tr class="pagenav">
						<!--设置首页是否出现链接-->
						<c:if test="${pagination.condition != 'empty'}">
						   <c:if test="${pagination.pageid == 1}">
						   	<td><img src="${ctx }/images/page/shouye.gif"  border="0" alt="已经是第一页了"/></td>
						    <td><img src="${ctx }/images/page/shangye.gif"  border="0" alt="已经是第一页了"/></td>
						   </c:if>
						   <c:if test="${pagination.pageid > 1}">
						   	<td><a href="javascript:pagination(1,<c:out value='${pagination.eachpagenum}'/>)"><img src="${ctx }/images/page/shouye.gif"  border="0" alt="首页"/></a></td>
						   	<td><a href="javascript:pagination(<c:out value='${pagination.pageid-1}'/>,<c:out value='${pagination.eachpagenum}'/>)"><img src="${ctx }/images/page/shangye.gif" border="0" alt="上一页" /></a></td>
						   </c:if>
						</c:if>
						
						<!--设置末页是否出现链接-->
						<c:if test="${pagination.condition != 'empty'}">
						   <c:if test="${pagination.pageid < pagination.lastid}">
							<td> <a href="javascript:pagination(<c:out value='${pagination.pageid+1}'/>,<c:out value='${pagination.eachpagenum}'/>)"><img src="${ctx }/images/page/xiaye.gif" border="0" alt="下一页" /></a></td>
						   </c:if>	
						   <c:if test="${pagination.pageid != pagination.lastid}">
						   	<td> <a href="javascript:pagination(<c:out value='${pagination.lastid}'/>,<c:out value='${pagination.eachpagenum}'/>)"><img src="${ctx }/images/page/weiye.gif" border="0" alt="尾页" /></a></td>
						   </c:if>
						   <c:if test="${pagination.pageid == pagination.lastid}">
						    <td><img src="${ctx }/images/page/xiaye.gif" border="0" alt="已经是最后一页了"/></td>
						   	<td><img src="${ctx }/images/page/weiye.gif" border="0" alt="已经是最后一页了"/></td>
						   </c:if>
						</c:if>
						<td>转到<input type="text" name="goto" id="goto" size="2" class="tz" onkeydown="onEnter(${pagination.lastid},${pagination.eachpagenum})" value="${pagination.pageid}"/>页</td>
						<td><img src="${ctx }/images/page/zhuan.gif" style="cursor:pointer;" alt="跳转" onclick="jumpto(${pagination.lastid},${pagination.eachpagenum})"/></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</BODY>
<script>
function pagination(pageid,eachpagenum){
	var target = document.forms[0];
	target.pageid.value = pageid;
	target.eachpagenum.value = eachpagenum;
	target.submit();
}

function jumpto(total,eachpagenum){
	var target = document.forms[0];
	 var reg =/^\d+$/;
     var title = document.getElementById("goto");
          if (reg.test(title.value)){
              var totals = parseInt(total,10);
              if(title.value>0&&title.value<=totals){
                  target.pageid.value = title.value;
				  target.eachpagenum.value = eachpagenum;
				  target.submit();
              }else{
                  if(total===0){
                  	window.alert("当前无可用页码");
                  }else{
                  window.alert("页码范围为1至"+total);
                  }
                  title.value="";
                  return;
              }
          }else{
              window.alert("请输入正确的页码");
              title.value="";
              return;
          }
}

function jumpto2(total,eachpagenum,url){
	 var reg =/^\d+$/;
     var title = document.getElementById("goto");
          if (reg.test(title.value)){
              var totals = parseInt(total,10);
              if(title.value>0&&title.value<=totals){
                  window.location=url+"pageid="+title.value+"&eachpagenum="+eachpagenum;
              }else{
                  if(total===0){
                  	window.alert("当前无可用页码");
                  }else{
                  window.alert("页码范围为1至"+total);
                  }
                  title.value="";
                  return;
              }
          }else{
              window.alert("请输入正确的页码");
              title.value="";
              return;
          }
}

//在跳转到第几页的框中输入页面,直接ENTER,进行跳转
function onEnter(total,eachpagenum){
	if (window.event.keyCode == 13){
			jumpto(total,eachpagenum);
		return false;	
		}
}
</script>
</HTML>
