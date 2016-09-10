package com.ztenc.oa.proj.test.unit;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


/**
 * <p>文件名称: BaseUnitTest.java </p>
 * <p>文件描述: 单元测试基类，所有单元测试必须继承自此类</p>
 * <p>版权所有: 版权所有(C)2006-2007</p>
 * <p>公    司:中兴软件技术(南昌)有限公司</p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 * <p>完成日期：2007-9-17</p>
 * <p>修改记录1: </p>
 * <pre>
 *    修改日期:
 *    版 本 号：
 *    修 改 人：
 *    修改内容:
 * </pre>
 * @version 1.0
 * @author  王荣
 */
public class BaseUnitTest extends AbstractTransactionalDataSourceSpringContextTests {
	
	
	protected String[] getConfigLocations() {

        return new String[]{"com/ztenc/oa/proj/springConfig/proj-unit-data.xml", 
        		"com/ztenc/oa/proj/springConfig/proj-unit-service.xml",
        		"com/ztenc/oa/proj/springConfig/config/proj-*.xml",
        		"com/ztenc/oa/proj/springConfig/config/SMS-*.xml"};
        

        
 } 
}