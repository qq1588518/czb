package com.ztenc.oa.proj.function.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;

/**
 * <p>文件名称: 题目名称</p>
 * <p>文件描述: 本类描述</p>
 * <p>版权所有: 版权所有(C)2007-2009</p>
 * <p>公    司: 中兴软件技术(南昌)有限公司</p>
 * <p>内容摘要: 对所有方法进行日志记录</p>
 * <p>其他说明: // 其它内容的说明</p>
 * <p>完成日期：2008-3-31</p>
 * <p>修改记录1: // 修改历史记录，包括修改日期、修改者及修改内容</p>
 * <pre>
 *    修改日期：
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * @version 1.0
 * @author  曹杰
 */
@Aspect
public class LogAspect implements Ordered {
	
	Log log =LogFactory.getLog(this.getClass());
	
	public int order;
	
	public void setOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		// TODO 自动生成方法存根
		return 0;
	}
    
	@Pointcut("execution(* com.ztenc.oa.proj..*.*(..))")
    public void logMethodPointcut(){};
	
    @Around("com.ztenc.oa.proj.function.aspect.LogAspect.logMethodPointcut()")
    public Object LogMethodAdvisor(ProceedingJoinPoint pjp ) throws Throwable{
    	Object retval = null;
    	//System.out.println(pjp.getTarget()+"类的"+pjp.getSignature().getName()+"方法开始执行");
    	log.info(pjp.getTarget()+"类的"+pjp.getSignature().getName()+"方法开始执行");
    	try {
    		retval =pjp.proceed();
		} catch (Throwable e) {
		 //System.out.println(pjp.getTarget()+"类的"+pjp.getSignature().getName()+"方法出现异常结束");
		 log.error(pjp.getTarget()+"类的"+pjp.getSignature().getName()+"方法出现异常结束");
		 System.out.println("-----------------------------------");
		 throw e;		 
		}	
			///System.out.println(pjp.getTarget()+"类的"+pjp.getSignature().getName()+"方法正常结束");
			log.info(pjp.getTarget()+"类的"+pjp.getSignature().getName()+"方法正常结束");
			return retval;
		
    	
    }
    
    
    
}
