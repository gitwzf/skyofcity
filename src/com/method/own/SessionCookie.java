package com.method.own;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ����
 * cookie������������
 * 
 * */

public class SessionCookie {
	/** ���cookie
	 * @param resp ����response
	 * @param str  cookieֵ
	 * @param name cookie��
	 * @param last ����ʱ�䣨�룩
	 */
	public static void addCookie(HttpServletResponse resp,String str,String name,int last){
		Cookie cook=new Cookie(name,str);//����cookie
		cook.setMaxAge(last);
		resp.addCookie(cook);
	}
	
	/**
	 * ��ȡcookie
	 * @param req ����request
	 * @param name cookie��
	 * @return ��Чֵ���ؿ��ַ���
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
