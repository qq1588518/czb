package com.ztenc.oa.proj.util.pagination;

import javax.servlet.http.HttpServletRequest;



/**
 * <p>文件名称: 分页显示处理BO类</p>
 * <p>文件描述: 分页显示处理</p>
 * <p>版权所有: 版权所有(C)2007-2009</p>
 * <p>公    司: 中兴软件技术(南昌)有限公司</p>
 * <p>内容摘要: 实现了分页的功能，通过指定的信息,设定分页的形式显示的状态</p>
 * <p>其他说明: // 其它内容的说明</p>
 * <p>完成日期：2007-7-18</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * <p>修改记录2：…</p>
 * @version 1.0
 * @author  曹杰    
 */
public class PaginationBOImpl implements PaginationBOI{
	/*不使用IOC注入注释下列代码 将第45行代码取消注释*/
	/*----------------IOC注入------------------------*/
	PaginationBean paginationBean;
	public void setPaginationBean(PaginationBean paginationBean) {
		this.paginationBean = paginationBean;
	}
	/*-----------------IOC注入------------------------*/
	
	/**
	  * 函数的目的/功能: 通过指定内容,个数等，指定当前显示的状态。由前台根据状态决定分页的格式
      * @param pageid      第几页
      * @param eachpagenum 每页显示的信息数量
      * @param index       预留
      * @param total       总的记录条数
      * @param request     请求对象

	  */
	public void Pagination(int pageid,int eachpagenum,int index,
			                int total,HttpServletRequest request){
		// 保存发送至前台的信息
		//PaginationBean pbean=new PaginationBean();--不提供IOC注入，使用此行
		// 导航链接的启始页码
		int linkstartnum = 0;
        // 导航链接的结束页码
		int linkendnum = 0;
		//共多少段
		int total_item = 0;
		//共多少页
		int pages = 0;
		if(total % eachpagenum == 0){
			pages = total / eachpagenum;
		}else{
			pages = total / eachpagenum +1;
		}
		//获得共多少段内容
		if (pages%10 != 0){
			total_item = (pages / 10) * 10 + 1;
		}else{
			total_item = (pages / 10 - 1) * 10 + 1;
		}
		// 获得导航链接的启始页码1---9为1开始，11-20为11开始，21-30为21开始
		if (pageid%10 != 0){
			linkstartnum = (pageid / 10) * 10 + 1;
		}else{
			linkstartnum = (pageid / 10 - 1) * 10 + 1;
		}
		paginationBean.setLinkstartnum(linkstartnum);
		//如果处于第1段中
		if(linkstartnum == 1){
			//而且下段不为空
			if(total_item - linkstartnum > 0){
				paginationBean.setCondition("first_down_notempty");
			}
			//下段为空
			if(total_item - linkstartnum == 0){
				paginationBean.setCondition("first_down_empty");
			}
		}
		//如果处于中间段中
		if(linkstartnum>1&linkstartnum<total_item){
			paginationBean.setCondition("middle");
		}
		//如果处于末段中
		if(linkstartnum == total_item & linkstartnum != 1){
			paginationBean.setCondition("last_up_notempty");
		}
		//上1段的内容
		paginationBean.setUpid(new Integer(linkstartnum-10).toString());
		//下1段的内容
		paginationBean.setDownid(new Integer(linkstartnum+10).toString());
		/*-----------设置导航下标显示---------------*/
		// 信息刚满 如每页15条记录，刚好有45条
		if (total % eachpagenum == 0){
			paginationBean.setLastid(new Integer(total / eachpagenum ).toString());
			// 如果出现导航为1--10，但是末页为8的情况，显示1--8
			if(linkstartnum + 9 > (total / eachpagenum) ){
				linkendnum = total / eachpagenum;
			}else{
				linkendnum = linkstartnum + 9;
			}
		}
		// 信息不满 如每页15条记录，刚好有46条
		else{
			paginationBean.setLastid(new Integer(total / eachpagenum + 1).toString());
            // 如果出现导航为1--10，但是末页为8的情况，显示1--8
			if(linkstartnum + 9 > (total / eachpagenum) ){
				linkendnum = total / eachpagenum + 1;
			}else{
				linkendnum = linkstartnum + 9;
			}
		}
		paginationBean.setLinkendnum(linkendnum);
		//保存当前第几页
		paginationBean.setPageid(pageid);
		//保存每页显示的条数
		paginationBean.setEachpagenum(eachpagenum);
		//保存总的条数
		paginationBean.setTotal(total);
		//如果没有搜索结果的情况
		if(total == 0){
			paginationBean.setCondition("empty");
		}
        request.setAttribute("pagination",paginationBean);
	}

}
