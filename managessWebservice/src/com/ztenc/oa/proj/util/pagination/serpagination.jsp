<html>
	<script language="javascript" src="${ctx}/scripts/pagination.js"></script>
<body>
<%@ page contentType="text/html;charset=utf-8"  %>
<!-- 如果传递的参数为空 -->
<c:if test="${pagination.condition!=null}">
     <table border="0" cellspacing="0">
      <tr>
      　　<c:if test="${pagination.condition!='empty'}">
              <td>第<c:out value="${pagination.pageid}"/>页</td>
          </c:if>
          <td>共<c:out value="${pagination.lastid}"/>页(共<c:out value="${pagination.total}"/>条记录)</td>
             跳转到第<input type="text" name="goto" id="goto" size="2" onkeydown="onEnter(${pagination.lastid},${pagination.eachpagenum})">页<input type="button" value="跳转" onclick="jumpto(${pagination.lastid},${pagination.eachpagenum})">         
      <!--设置首页是否出现链接-->
      <c:if test="${pagination.condition != 'empty'}">
          <c:if test="${pagination.pageid == 1}">
          	<td>首页</td>
          </c:if>
          <c:if test="${pagination.pageid > 1}">
          	 <td><a href="javascript:pagination(1,<c:out value='${pagination.eachpagenum}'/>)">首页</a></td>
          </c:if>
       </c:if>
      <c:if test="${pagination.condition == 'first_down_empty'}">
          <td>上段</td>
          <td>下段</td>
       </c:if>
       <c:if test="${pagination.condition == 'first_down_notempty'}">
          <td>上段</td>
          <td><a href="javascript:pagination(<c:out value='${pagination.downid}'/>,<c:out value='${pagination.eachpagenum}'/>)">下段</a></td>
       </c:if>
       <c:if test="${pagination.condition == 'middle'}">
          <td><a href="javascript:pagination(<c:out value='${pagination.upid}'/>,<c:out value='${pagination.eachpagenum}'/>)">上段</a></td>
          <td><a href="javascript:pagination(<c:out value='${pagination.downid}'/>,<c:out value='${pagination.eachpagenum}'/>)">下段</a></td>
       </c:if>
       <c:if test="${pagination.condition == 'last_up_notempty'}">
          <td><a href="javascript:pagination(<c:out value='${pagination.upid}'/>,<c:out value='${pagination.eachpagenum}'/>)">上段</a></td>
          <td>下段</td>
       </c:if>
       <!--设置末页是否出现链接-->
      <c:if test="${pagination.condition != 'empty'}">
          <c:if test="${pagination.pageid != pagination.lastid}">
          	 <td><a href="javascript:pagination(<c:out value='${pagination.lastid}'/>,<c:out value='${pagination.eachpagenum}'/>)">末页</a></td>
          </c:if>
          <c:if test="${pagination.pageid == pagination.lastid}">
          	 <td>末页</td>
          </c:if>
       </c:if>
       <c:if test="${pagination.condition == 'empty'}">
       </c:if>
       <td><%//显示页码链接 %>
           <c:forEach begin="${pagination.linkstartnum}" end="${pagination.linkendnum}" step="1" var="i">
               <c:if test="${pagination.pageid == i}">
                   <b><c:out value='${i}'/></b>
               </c:if>
               <c:if test="${pagination.pageid != i}">
                   <a href="javascript:pagination(<c:out value='${i}'/>,<c:out value='${pagination.eachpagenum}'/>)"><c:out value='${i}'/></a>
               </c:if>                         
           </c:forEach>
       </td>       
      </tr>
    </table>
    </c:if>
    </body>
   </html>