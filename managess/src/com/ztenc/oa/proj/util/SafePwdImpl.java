package com.ztenc.oa.proj.util;

import org.acegisecurity.providers.encoding.Md5PasswordEncoder;

public class SafePwdImpl implements SafePwd {

	    String pwdSalt;
		public String converPwd(String inputPwd) {
			Md5PasswordEncoder md5encoder = new Md5PasswordEncoder();
		    inputPwd = md5encoder.encodePassword(inputPwd, pwdSalt);
			return inputPwd;
		}
		public String getPwdSalt() {
			return pwdSalt;
		}
		public void setPwdSalt(String pwdSalt) {
			this.pwdSalt = pwdSalt;
		}

}
