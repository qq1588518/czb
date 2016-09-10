package com.ztenc.oa.proj.function;



import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.Ordered;


public class LogAdvisor implements Ordered {

	
	@SuppressWarnings("unused")
	private int order;

	public void logService(ProceedingJoinPoint call)
			throws Throwable {

		call.proceed();
		
		
	}

	public int getOrder() {
		// TODO 自动生成方法存根
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	

}
