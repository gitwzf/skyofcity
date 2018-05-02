package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import pubvari.Variable;
import wxtry.Dbcon;


public class le111Serv extends HttpServlet{
	Logger log = Logger.getLogger("logfile");
       public Variable vari=new Variable();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Dbcon db=new Dbcon();
		String name = null;
		String act=req.getParameter("act");
		String word=req.getParameter("word");
			if("delete".equals(act)) {
				log.info("delname="+name);
				db.delKeyword(name);
			} else if("setwelcome".equals(act)){
				db.setWelcome(name);
				req.getSession().setAttribute("info","设置欢迎消息成功！");
			}
			else if("setdefault".equals(act)){
				db.setDefault(name);
				req.getSession().setAttribute("info","设置默认消息成功！");
				}
			else if("delwelcome".equals(act)){
				db.setWelcome("");
				req.getSession().setAttribute("info","取消欢迎消息成功！");
			}
			else if("deldefault".equals(act)){
				db.setDefault("");
				req.getSession().setAttribute("info","取消默认消息成功！");
				}
		String kind=req.getParameter("kind");
		req.getSession().setAttribute("kinds",kind);
			
			
			
		req.getSession().setAttribute("rearray",db.getRearray());
		if(word!=null)req.getSession().setAttribute("rearray",db.getRearray(word));
		resp.sendRedirect("/le111.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		log.info("post");
		resp.sendRedirect("/le111.jsp");
	}

}
