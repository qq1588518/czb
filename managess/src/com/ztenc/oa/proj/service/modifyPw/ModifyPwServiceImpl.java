package com.ztenc.oa.proj.service.modifyPw;

import java.sql.Array;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ztenc.oa.proj.json.*;
import com.ztenc.oa.proj.util.SafePwd;
import com.ztenc.oa.proj.bean.*;
import com.ztenc.oa.proj.dao.modifyPw.ModifyPwDao;

public class ModifyPwServiceImpl implements ModifyPwService {
    
    private ModifyPwDao modifyPwDao;
    private SafePwd safePwd;
    
    public void setModifyPwDao(ModifyPwDao modifyPwDao) {
        this.modifyPwDao = modifyPwDao;
    }
    
    public void setSafePwd(SafePwd safePwd) {
        this.safePwd = safePwd;
    }
    
    /*
	 *
	 * */
    public String checkPassword(String username, String oldPassword) {
        String rtn = "";
        System.out.println("username=" + username);
        System.out.println("oldPassword=" + oldPassword);
        String password = safePwd.converPwd(oldPassword);
        System.out.println(password);
        String username1 = username;
        rtn = String.valueOf(modifyPwDao.checkPassword(username1, password));
        return rtn;
    }
    
    /*
     *
     * */
    public int modifyPassword(String verifypassword, String username) {
        String verifypwd = safePwd.converPwd(verifypassword);
        if (modifyPwDao.updatePw(verifypwd, username)) {
            return 1;
        } else {
            return 0;
        }
    }
    
}
