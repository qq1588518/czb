package com.ztenc.oa.proj.test.unit.service;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.ztenc.oa.proj.bean.Member;
import com.ztenc.oa.proj.bean.Subject;
import com.ztenc.oa.proj.dao.member.MemberDao;
import com.ztenc.oa.proj.dao.subject.SubjectDao;

import com.ztenc.oa.proj.service.column.ColumnConService;
import com.ztenc.oa.proj.service.courseware.CoursewareService;
import com.ztenc.oa.proj.service.member.MemberService;
import com.ztenc.oa.proj.test.unit.BaseUnitTest;
import com.ztenc.oa.proj.util.CUtil;

public class TestService extends BaseUnitTest {

	/**
	 * 
	 */
//	public void testFirstTest()
//	{
//		ReadService hello = (ReadService)applicationContext.getBean("readService");
//		//hello.readMap("");
//		hello.saveSuccor("{companyname:'中兴软件',address:'13000000000',tel:'13000000000',province:'江西',city:'九江',region:'九江,location:'250203',coporation:'hyh',repairarea:'1'}");
//		this.setComplete();
//	}
//	public void testDao(){
//		MemberService dao = (MemberService)applicationContext.getBean("memberService");
//		Member member = new Member();
//		//member.setCode(code);
//		member.setMachineid("234");
//		member.setTelno("45435");
//		member.setMemo("dsfasd");
//		member.setMembername("5645");
//		String l =dao.addMember("234", "45435", "5645", "", "dsfasd");
//		this.setComplete();
//		System.out.println(l.length());
//	}
	public void testService(){
//		MemberService ser = (MemberService)applicationContext.getBean("memberService");
//		Member member = new Member();
//		//member.setCode(code);
//		//member.setMachineid("234");
//		//member.setTelno("45435");
//		//member.setMemo("dsfasd");
//		//member.setMembername("5645");
//		//ser.addMember("123", "34345", "34432", "", "3432sdf");CoursewareService.java
//		String reslut = ser.modifyMember("XAVUOT0A", "women", "777","777","2","777","abcdefg");
		
		ColumnConService courseware = (ColumnConService)applicationContext.getBean("columnConService");
		int len = 3;
		String filenames = "";
		String total_filenames = "";
		String datetime11 = "";
		String url = "";
		for(int i=0;i<len;i++){
			SimpleDateFormat tempDate11 = new SimpleDateFormat("yyMMddHHmmss");
			Calendar p_cal2 = Calendar.getInstance();
	        java.util.Date p_date2 = p_cal2.getTime();
	        datetime11 = tempDate11.format(p_date2)+CUtil.createBillNo(6)+".html";
	        filenames += datetime11+",";
	        if(i==len-1){
	        	total_filenames = tempDate11.format(p_date2)+CUtil.createBillNo(6)+".html";
	        }
    	}
		String title = "hyh";
		filenames = filenames.substring(0, filenames.length()-1);
    	for(int y=0;y<len;y++){
    	    //modify by Tim 20110810
    		//url += courseware.saveContentToFile(title,"我爱你","C:\\Program Files\\Apache Software Foundation\\Tomcat 5.5\\webapps\\managess\\",filenames,y,"<div id='total0' style=display:none>我爱你111</div><div id='total1' style=display:none>我爱你222</div><div id='total2' style=display:none>我爱你333</div>",total_filenames)+",";
    	}
    	url = url.substring(0, url.length()-1);
    	System.out.println("url=="+url);
		this.setComplete();
//		Courseware ware = new Courseware();
//		ware.setCoursewarename("kkk");
//		ware.setCoursewareno("XCXJKJJK");
//		ware.setOtherUrl("dsadfsf");
//		ware.setUrl("dsfadf");
//		ware.setTitleimg("dksjf");
//		CoursewareDao dao = (CoursewareDao)applicationContext.getBean("CoursewareDao");
//		dao.saveInfo(ware);
		System.out.print("kk");
	}
	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestService.class);

	}
}
