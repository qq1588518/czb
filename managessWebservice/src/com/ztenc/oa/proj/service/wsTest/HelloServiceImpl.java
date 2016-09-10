package com.ztenc.oa.proj.service.wsTest;

import com.ztenc.oa.proj.util.ServiceException;

public class HelloServiceImpl implements HelloService {

	public String sayHello(String name) throws ServiceException{
		// TODO Auto-generated method stub
		//throw new ServiceException();
		return "Hello:safe-"+name;
	}

}
