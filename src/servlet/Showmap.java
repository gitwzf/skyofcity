package servlet;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class Showmap extends HttpServlet{
	 Logger log = Logger.getLogger("logfile");
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String length=req.getParameter("length");
		if(length!=null){
		String name[]=req.getParameterValues("name");
		String address[]=req.getParameterValues("address");
		String location_x[]=req.getParameterValues("location_x");
		String location_y[]=req.getParameterValues("location_y");
		String names = "",addresss= "",location_xs= "",location_ys= "";
		String query=req.getParameter("query");
	//	if(query!=null)query=new String(query.getBytes("iso8859-1"),"utf-8");
		log.info("query="+query);
		String str="";
	if(name!=null){
		req.setAttribute("query", URLEncoder.encode(query,"utf-8"));
		for(int i=0;i<name.length;i++)	
		{  
			str+="var point = new BMap.Point("+location_y[i]+","+location_x[i]+");\n"+
			"mp.centerAndZoom(point, 18);\n"+
			"var marker"+i+" = new BMap.Marker(point);\n"+
			"mp.addOverlay(marker"+i+");\n"+
			"var opts = {\n"+
			"width : 150, \n"+
			"height: 105,\n"+
			"title : \""+name[i]+"\" \n"+
			"}\n"+
			"var infoWindow"+i+" = new BMap.InfoWindow(\""+address[i]+"\", opts);\n"+
			"mp.openInfoWindow(infoWindow"+i+", mp.getCenter()); \n"+
			"marker"+i+".addEventListener('click',function(){ marker"+i+".openInfoWindow(infoWindow"+i+");}); \n"; 
		}
		req.setAttribute("str", URLEncoder.encode(str,"utf-8"));
		 resp.setCharacterEncoding("utf-8");
		req.getRequestDispatcher("/showmap.jsp").forward(req, resp);
		}
		}else resp.sendRedirect("http://wap.baidu.com/");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		super.doPost(req, resp);
	}
      
}
