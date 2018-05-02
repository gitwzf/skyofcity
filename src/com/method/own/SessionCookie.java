package com.method.own;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能
 * cookie操作方法集合
 * 
 * */

public class SessionCookie {
	/** 添加cookie
	 * @param resp 对象response
	 * @param str  cookie值
	 * @param name cookie名
	 * @param last 持续时间（秒）
	 */
	public static void addCookie(HttpServletResponse resp,String str,String name,int last){
		Cookie cook=new Cookie(name,str);//放入cookie
		cook.setMaxAge(last);
		resp.addCookie(cook);
	}
	
	/**
	 * 获取cookie
	 * @param req 对象request
	 * @param name cookie名
	 * @return 无效值返回空字符串
	 */
	public static String getCookie(HttpServletRequest req,String name){
		String str="";
		if(req==null||name==null)return str;
		Cookie[] cookies=req.getCookies();
		if(cookies!=null)
		for(Cookie coo:cookies){
			String client=coo.getValue();
			if(name.equals(coo.getName())){
				str=client;
				break;
			}
		}
		return str;
	}

}
