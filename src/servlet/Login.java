package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Manageruser;

import org.apache.log4j.Logger;

import pubvari.Variable;


public class Login extends HttpServlet{
	Logger log = Logger.getLogger("logfile");
	public Variable vari=new Variable();//¿ÕµÄ£¿£¡
   @Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
//		req.setCharacterEncoding("iso-8859-1");
		req.getSession().removeAttribute("manager");
		System.out.println("back...xs");
		//resp.sendRedirect("/"+project+"/index.jsp");
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String username=req.getParameter("username");
//		log.info(username);
		if(username!=null){
			String password=req.getParameter("password");
			String rember=req.getParameter("rember");
			String saveTime=req.getParameter("saveTime");
			log.info("rember="+rember);
			log.info("saveTime0="+saveTime);
		username=username.replace("'", "");//·Àsql×¢Èë
			password=password.replace("'", "");
			if(username.equals("admin")){
			Cookie cook=new Cookie("client", username+"|"+password);
			cook.setMaxAge(Integer.parseInt(saveTime)*24*3600);
			resp.addCookie(cook);
			if(rember!=null){Cookie cook1=new Cookie("clientuser", username);
			cook1.setMaxAge(365*24*3600);
			resp.addCookie(cook1);}
			req.setAttribute("manager",new Manageruser(username));
			} 
			resp.sendRedirect("/index.jsp");
		}
		else
		resp.sendRedirect("/login.jsp");
	}
     
}
