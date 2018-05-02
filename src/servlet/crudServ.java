package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unit.change.Changekinds;
import com.unit.type.TypeOne;


import DbXml.ObjectArrInf;

import pubvari.Variable;
import wxtry.Dbcon;

import model.News;
import model.Tabletitle;


public class crudServ extends HttpServlet{
	static int  int_table=0;
	static String pag="DbXml";
	static ArrayList<String> items=new ArrayList<String>();
	static ArrayList<String> itemArr=new ArrayList<String>();
	static ArrayList<String[]> dataList;
	
	static ArrayList<Tabletitle> titles;
	
	static{//待实例化类
		items.add("Helper_url");
		itemArr.add("HelperurlArr");
		items.add("Keyword");
		itemArr.add("KeywordArr");
		items.add("Openid_wid");
		itemArr.add("OpenidwidArr");
		items.add("Pic_mus_reback");
		itemArr.add("PicmusrebackArr");
		items.add("Re_keyword");
		itemArr.add("RekeywordArr");
		items.add("Virecord");
		itemArr.add("VirecordArr");//小秘书功能异常问题->(选做：表联动字段  )  
		}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		new Dbcon();
		String tab_id=req.getParameter("table");
		if(TypeOne.isInt(tab_id)){
			int_table=Integer.parseInt(tab_id);
		}
		
		String field=req.getParameter("field");//搜索  null或“”则不执行
		String svalue=req.getParameter("svalue");
		if(svalue!=null && !svalue.isEmpty())
		svalue=new String(svalue.getBytes("iso-8859-1"),"utf-8");
		
		req.getSession().setAttribute("tables",items);
		req.getSession().setAttribute("titles", titles=getTabTitles());//可以加类型，图片、标题、段落  对应不同处理方式
		req.getSession().setAttribute("datalist",dataList=getDatalist(field,svalue));
		req.getRequestDispatcher("/crud.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		String first=req.getParameter("first");
		String[] editoritem=req.getParameterValues("editoritem");
		if(first==null){
		 AddOrUpdate(editoritem);
		 write();
		req.getSession().setAttribute("datalist",dataList= getDatalist(null,null));
		req.getRequestDispatcher("/crud.jsp").forward(req, resp);
		}else
		{
			boolean flag=delByFirst(first);
			write();
			PrintWriter pw=resp.getWriter();
			pw.write(flag==true?"11":"00");
			pw.flush();
			pw.close();
		}
	}
	
	public boolean delByFirst(String svalue){
		try{
		Object arr=Variable.mapDbList.get(itemArr.get(int_table));
		if(arr!=null){
			ObjectArrInf oArr=(ObjectArrInf)arr;
		ArrayList dataList=	oArr.getList();
		  for(int i=dataList.size()-1;i>=0;i--){
			  Object obj=dataList.get(i);
			  Field[] fields=obj.getClass().getDeclaredFields();
			  Field field=fields[0];
			  field.setAccessible(true);
			  String fvalue=Changekinds.returnStr(field.get(obj));
			  if(svalue.equals(fvalue)){
				  dataList.remove(obj);
			  }
		  }
		}
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
	}
	
	public ArrayList<Tabletitle> getTabTitles(){
		String tabName=items.get(int_table);
		ArrayList<Tabletitle> array=new ArrayList<Tabletitle>();
		Tabletitle t=null;
			try{
			Class cla=Class.forName(pag+"."+tabName);
			Field[] fs=cla.getDeclaredFields();
			 for(Field fss:fs){
					t=new Tabletitle();
					t.setComment(fss.getName());
					t.setLength("200");
					t.setName(fss.getName());
					t.setType(null);
					
				array.add(t);
				}
			}catch(Exception ex){
			    ex.printStackTrace();	
			}
		return array;
	}
	
	/**
	 *获取数据列表 
	 * @throws Exception 
	 */
	public ArrayList<String[]> getDatalist(String field,String value){
		//获取数据容器
		ArrayList<String[]> reArray=new ArrayList<String[]>();
		try{
		Object arr=Variable.mapDbList.get(itemArr.get(int_table));
		if(arr!=null){
			ObjectArrInf oArr=(ObjectArrInf)arr;
		ArrayList dataList=	oArr.getList();
			if(dataList!=null){
				String[] strArr;
				Object obj;
				fir:
				for(int i=0;i<dataList.size();i++){
					obj=dataList.get(i);
					if(field==null || field.isEmpty()){
						strArr=BeanToArray(obj);
						reArray.add(strArr);
					}else{
					Field[] fields=obj.getClass().getDeclaredFields();
					String lvalue;
					for(Field fss:fields){
						fss.setAccessible(true);
						lvalue=fss.get(obj).toString();
						if(fss.getName().equals(field)&&lvalue.matches("[\\s\\S]*"+value+"[\\s\\S]*"))
					    {
						strArr=BeanToArray(obj);
						reArray.add(strArr);
						continue fir;
					    }
				     }
					}
			}
		  }
		}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return reArray;
	}
	
	public String[] BeanToArray(Object obj) throws Exception{
		String[] reArr = null;
		if(obj==null)
			return reArr;
		Field[] fields=obj.getClass().getDeclaredFields();
		reArr=new String[fields.length];
		for(int i=0;i<fields.length;i++){
			fields[i].setAccessible(true);
			reArr[i]=Changekinds.returnStr(fields[i].get(obj));
		}
		return reArr;
	}
	
	public void AddOrUpdate(String[] str){
		boolean addFlag=false;
		String[] tempArr;
		for(int i=0;i<dataList.size();i++){
			tempArr=dataList.get(i);
			for(int j=0;j<titles.size();j++){
				if(!str[j].equals(tempArr[j])){
					addFlag=true;
					break;
				}
			}
			
		}
		if(addFlag){
			Object obj=ArrayToBean(str);
			Object arr=Variable.mapDbList.get(itemArr.get(int_table));
			if(arr!=null){
			ObjectArrInf oArr=(ObjectArrInf)arr;
			ArrayList dataList=	oArr.getList();
			dataList.add(obj);
			}
		}
		
	}
	
	public Object ArrayToBean(String[] str){
		Object obj=null;
		try{
			String tabName=items.get(int_table);
		    obj=Class.forName(pag+"."+tabName).newInstance();
			Field[] fs=obj.getClass().getDeclaredFields();
			Field fss;
			 for(int i=0;i<fs.length;i++){
				 	fss=fs[i];
				 	fss.setAccessible(true);
				 	fss.set(obj, str[i]);
				}
			}catch(Exception ex){
			    ex.printStackTrace();	
			}
			return obj;
	}
	
	public static void main(String[] args) throws SecurityException, ClassNotFoundException {
		 System.out.println("风123方法".matches("[\\s\\S]*123[\\s\\S]*"));
	}

	
	public void write() {
		Dbcon.WriteXml();
	}

}
