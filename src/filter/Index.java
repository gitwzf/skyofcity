package filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import pubvari.Variable;
import wxtry.Dbcon;

import model.Manageruser;


public class Index implements javax.servlet.Filter{
	public Variable vari=new Variable();
	public void destroy() {
		
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		Logger log = Logger.getLogger("logfile");
		String username;
		String password;
		int ips=0;    //获取ip
		String ip=request.getRemoteAddr();
		String ip_headers[]={"x-forwarded-for","Proxy-Client-IP","WL-Proxy-Client-IP","HTTP_CLIENT_IP","HTTP_X_FORWARDED_FOR"};
		 while(ips<ip_headers.length&&(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)||"127.0.0.1".equals(ip))){
		   ip = ((HttpServletRequest) request).getHeader(ip_headers[ips++]); 
		    }
		log.info("来访者ip:"+ip);
		boolean flag=true;
		HttpServletRequest hsr=(HttpServletRequest) request;
		String pub=(String) hsr.getSession().getAttribute("pub");
		Manageruser mana=(Manageruser) hsr.getSession().getAttribute("manager");
		Cookie[] cookies=hsr.getCookies();
		if(cookies!=null)
		for(Cookie coo:cookies){
			String client=coo.getValue();
			if(client.split("\\|").length==2){
				username=client.split("\\|")[0].replace("'", "");
				password=client.split("\\|")[1].replace("'", "");
				log.info("username1="+username);
				if(username.equals("admin")){ 
					hsr.getSession().setMaxInactiveInterval(15*60);
					hsr.getSession().setAttribute("manager",new Manageruser(username));
					flag=false;
					chain.doFilter(request, response);}
			}
			if("clientuser".equals(coo.getName()))
			hsr.getSession().setAttribute("clientuser", coo.getValue());
		}
		if(flag){
	if(mana!=null)log.info("manauser="+mana.getUser());
		if(pub==null||mana==null){
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		log.info("pub===null");
		dispatcher.forward(request, response); 
		return;
		}
		else
		    chain.doFilter(request, response);
	}
	}    
	public void init(FilterConfig filterConfig) throws ServletException {
		
		
	}

	
	
      
}
