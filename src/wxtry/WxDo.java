package wxtry;
import interf.ChooseMsg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.News;
import model.WxUser;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.log4j.Logger;
import org.marker.weixin.DefaultSession;
import org.marker.weixin.HandleMessageAdapter;
import org.marker.weixin.MySecurity;
import org.marker.weixin.msg.Data4Item;
import org.marker.weixin.msg.Msg;
import org.marker.weixin.msg.Msg4Event;
import org.marker.weixin.msg.Msg4Image;
import org.marker.weixin.msg.Msg4Video;
import org.marker.weixin.msg.Msg4Voice;
import org.marker.weixin.msg.Msg4ImageText;
import org.marker.weixin.msg.Msg4Location;
import org.marker.weixin.msg.Msg4Music;
import org.marker.weixin.msg.Msg4Text;

import DbXml.Helper_url;

import com.commsen.jwebthumb.WebThumbException;
import com.commsen.jwebthumb.WebThumbFetchRequest;
import com.commsen.jwebthumb.WebThumbJob;
import com.commsen.jwebthumb.WebThumbRequest;
import com.commsen.jwebthumb.WebThumbService;
import com.commsen.jwebthumb.WebThumbFetchRequest.Size;
import com.commsen.jwebthumb.WebThumbRequest.OutputType;
import com.sina.sae.storage.SaeStorage;
import com.sina.sae.util.SaeUserInfo;
import com.unit.time.TimeScheduleHandle;
import com.unit.time.TimeUse;
import com.wzf.mail.MailSender;

import security.MySSLProtocolSocketFactory;

import pubvari.Variable;

import security.CryptoTools;
import servlet.Zoompic;

public class WxDo extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public WxDo() {  }
	
	//用springmvc改造 1.分module
	//TOKEN 是你在微信平台开发模式中设置的哦
	public static final String TOKEN = "D8yuc";

	/**
	 * 处理微信服务器验证
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String signature = request.getParameter("signature");// 微信加密签名
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		String echostr = request.getParameter("echostr");// 随机字符串
	
	// 重写totring方法，得到三个参数的拼接字符串
		List<String> list = new ArrayList<String>(3) {
			private static final long serialVersionUID = 2621444383666420433L;
			public String toString() {
				return this.get(0) + this.get(1) + this.get(2);
			}
		};
		list.add(TOKEN);
		list.add(timestamp);
		list.add(nonce);
		Collections.sort(list);// 排序
		String tmpStr = new MySecurity().encode(list.toString(),
				MySecurity.SHA_1);// SHA-1加密
		Writer out = response.getWriter();
		if (signature.equals(tmpStr)) {
			out.write(echostr);// 请求验证成功，返回随机码
			} else {
			out.write("");
		}
		out.flush();
		out.close();
	}

	
	/**
	 * 处理微信服务器发过来的各种消息，包括：文本、图片、地理位置、音乐等等
	 * 
	 * 
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("responsesession:"+response.get);
		InputStream is = request.getInputStream();
		OutputStream os = response.getOutputStream();
		
		final Dbcon db=new Dbcon();
//		db.setDatabase("w");
		final Logger log = Logger.getLogger("logfile");
		final Variable vari=new Variable();
		final DefaultSession session = DefaultSession.newInstance(); 
		
		session.addOnHandleMessageListener(new HandleMessageAdapter(){
			
			@Override  //重写
			//菜单事件
			public void onEventMsg(Msg4Event msg) {
				log.info("收到微信消息："+msg.getEvent());
				log.info("发送方:"+msg.getFromUserName());
				log.info("接收方:"+msg.getToUserName());
				String user=msg.getFromUserName();//user name
				String reback="";//reback mesge
				
				 try {
				       CryptoTools tools = new CryptoTools();
					if("subscribe".equals(msg.getEvent())){//ding yue msg
						int ev=-3;
					    if(!db.isaddId(user)){
					       db.addOpenid(user);
					       }else{
					    	  //已添加则修改标记
					    	   db.delOpenIdval(user);
					       }
						 String content="";
						  if(ev==0){
							  rebackText(content,msg); 
						  }
						 else if(ev==1){
							 String[] dmid=content.split("\\|");
								Data4Item[] dm=new Data4Item[dmid.length];
								ArrayList<News>array=db.getPicMusById(dmid);
								for(int i=0;i<array.size();i++)
								dm[i] = new Data4Item(array.get(i).getTitle(), array.get(i).getMain(), array.get(i).getUrlo(), ""); 
								
								rebackImage(dm,msg);
						 }
						 else if(ev==2){
							 String musid[]=new String[1];
							 musid[0]=content.split("\\|")[0];
							ArrayList<News>array=db.getPicMusById(musid);
							rebackMusic(array.get(0).getTitle(),array.get(0).getMain(),array.get(0).getUrl(),msg);
						 }
						 else {               //订阅默认回复
							 Class class2=Class.forName("wxtry.Allapi");
						        Object object=(Allapi)class2.newInstance();
						    	Method method = class2.getMethod("Subscribe",Msg.class,DefaultSession.class);
						        method.invoke(object,msg,session);}
						
					}
					 else if("unsubscribe".equals(msg.getEvent())){
						 db.delOpenIdval(user);
					 }
					 else if(!"VIEW".equals(msg.getEvent())){          //菜单事件（除VIEW事件）
						
					 }
				} catch (Exception e) {
					LogWrite(e);
					rebackText("系统错误Exception！",msg);return;
					
				}
			}
			
			@Override
			public void onTextMsg(Msg4Text msg) {
				log.info("收到微信消息："+msg.getContent());
				log.info("发送方:"+msg.getFromUserName());
				log.info("接收方:"+msg.getToUserName());
				log.info("发送时间:"+msg.getCreateTime());
				
				String createtime=msg.getCreateTime();
				 String str=SQLencode(msg.getContent());
				String user=msg.getFromUserName();
				
				String mode="";
				//没有的话添加openid   //判断“fakeid” 跟openid是否相同
				if(!db.isaddId(user)){//相同false
					db.addOpenid(user);
				}
				
			//格式判断  	
		if(str.matches("#[\\S\\s]+#[\\S\\s]+")){//用户加关键词
			String[] params = str.substring(1).split("#");
			String key=params[0];
			String value=params[1];
		   if(!key.matches("[a-zA-Z]")){
			   db.addTypeOnekey(key,value,user);
			   rebackText("关键词 "+key+" 添加成功:"+value, msg);
			   
		   }else{
			   rebackText("关键词添加失败", msg);
			   
		   }
		}else if(str.matches("--[\\S\\s]+--")){
			String key=str.substring(2,str.indexOf("--",2));
			db.delTypeOnekey(key,user);
			 rebackText("关键词 "+key+" 删除成功", msg);
			 
		}else if(str.trim().matches("@[\\S\\s]+@[\\S\\s]+@[\\S\\s]+")){//添加小秘书
			WxUser u=db.getUser(user);
			if(u==null || u.getEmail()==null || u.getEmail().isEmpty()){
				rebackText("请先添加绑定邮件！比如“绑定12345678@qq.com”",msg);return;
			}
			Helper_url helper = db.addHelper(str.substring(1).split("@"), user);
			rebackText("小秘书添加信息将于1分钟后发送到你的邮箱："+u.getEmail(),msg);
			//获取url缩略图
			final String tmpPath=SaeUserInfo.getSaeTmpPath() + "/tmp.jpg";
			  File file=new File(tmpPath);
				String id=getFetch(helper.getUrl());
				getPicById(file,id);
				StorePic(file,user);
				final String email = u.getEmail();
				final String name = helper.getName();
				TimeUse.waitTime(Calendar.MINUTE, 1, new TimeScheduleHandle() {			
					public void execute() {
						//发邮件
						new MailSender("smtp."+vari.email_name.split("@")[1], "25", vari.email_name, vari.email_pass, email,
								"天空之城-小秘书："+ name, "添加成功", false,new String[]{tmpPath});
				}
			});
				 
				
			  
		}else if(str.startsWith("绑定")){
			String value=str.split("绑定")[1];
			if(value.matches("[\\s\\S]+@[\\s\\S]+\\.[\\s\\S]+")){//邮件
				db.updateUserEmail(user,value);
			}
			
			rebackText("绑定成功", msg);
		}
		else{				
				long reId = 0;
				reId = Long.parseLong(db.isReKeyWord(str,user));
				if(db.isVis(user))
					db.addInstruction(user,str);
				else 
						db.addUser(user);
				if(reId>0)
				 db.updateStr(user,reId);
				else{ //oZMIwuIlpJXjizV1-jEqRk35tmQg
					int type0=db.updateKey(str,user);
					  if(type0==1)//添加当前用户图片 关键词
					   reId = Long.parseLong(db.isReKeyWord(str,user));
					  if(reId<=0){
					if(db.getMode(user)!=null)//上次关键词回复
				   reId=Integer.parseInt(db.getMode(user));//获取菜单号
					  }
				
				}
				
				try {
					int inf=db.getType0ById(reId,user);
					log.info("inf="+inf);
					log.info("reId="+reId);
					if(inf==-1){                                        //接口
						String methodName=db.getReContentById(reId,user);
						 Class class2=Class.forName("wxtry.Allapi");
					        Object object=(Allapi)class2.newInstance();
					    	Method method = class2.getMethod(methodName,Msg.class,DefaultSession.class);
					        method.invoke(object,msg,session);
//					        rebackText((String)method.invoke(object,msg), msg);
					        
					}
					
					if(inf==0){                                          //文字
						rebackText(db.getReContent(reId,user),msg);
					}
					if(inf==1){											//图文
						String[] dmid=db.getReContent(reId,user).split("\\|");
						Data4Item[] dm=new Data4Item[dmid.length];
						ArrayList<News>array=db.getPicMusById(dmid);
						for(int i=0;i<array.size();i++)
						dm[i] = new Data4Item(array.get(i).getTitle(), array.get(i).getMain(),array.get(i).getUrlo(),""); 
						rebackImage(dm,msg);
					}
					if(inf==2){											//音频
						String musid[]=new String[1];
						 musid[0]=db.getReContent(reId,user).split("\\|")[0];
						ArrayList<News>array=db.getPicMusById(musid);
						rebackMusic(array.get(0).getTitle(),array.get(0).getMain(),array.get(0).getUrl(),msg);
					}
					if(inf==3){                                          //图片
						String filename=db.getReContent(reId,user);
						String picurl=vari.source_pic_url+user.replace("-", "_")+"/"+filename+".jpg";
						Data4Item data=new Data4Item(str,"",picurl, picurl);
						rebackImage(data, msg);
					}
						
					
				}catch (Exception e) {
					LogWrite(e);
					rebackText("系统错误！",msg);
				}
				
		}	
				

			}
			public void rebackImage(Data4Item[] dm, Msg msg) {
				Msg4ImageText mit = new Msg4ImageText();
				mit.setFromUserName(msg.getToUserName());
				mit.setToUserName(msg.getFromUserName());
				mit.setCreateTime(msg.getCreateTime());
				for (Data4Item d : dm)
					mit.addItem(d);
				mit.setFuncFlag("0");
			   session.callback(mit);
			}

			public void rebackImage(Data4Item dm, Msg msg) {
				Msg4ImageText mit = new Msg4ImageText();
				mit.setFromUserName(msg.getToUserName());
				mit.setToUserName(msg.getFromUserName());
				mit.setCreateTime(msg.getCreateTime());
				mit.addItem(dm);
				mit.setFuncFlag("0");
				session.callback(mit);
			}

			public void rebackText(String reback, Msg msg) {
			    Msg4Text rmsg = new Msg4Text();
				rmsg.setFromUserName(msg.getToUserName());
				rmsg.setToUserName(msg.getFromUserName());
				rmsg.setFuncFlag("0");
				rmsg.setContent(reback);
				session.callback(rmsg);
			}
			public void rebackMusic(String title,String main,String url,Msg msg){
				Msg4Music mm=new Msg4Music();
				mm.setCreateTime(msg.getCreateTime());
				mm.setDescription(main);
				mm.setFromUserName(msg.getToUserName());
				mm.setToUserName(msg.getFromUserName());
				mm.setFuncFlag("0");
				mm.setHQMusicUrl(url);
				mm.setTitle(title);
				session.callback(mm);
			}
			
			public void rebackImage(String medid, Msg msg) {
				Msg4Image rmsg = new Msg4Image();
				rmsg.setFromUserName(msg.getToUserName());
				rmsg.setToUserName(msg.getFromUserName());
				rmsg.setMediaId(medid);
				session.callback(rmsg);
			}
			public void rebackVideo(String medid, Msg msg) {
				Msg4Video rmsg = new Msg4Video();
				rmsg.setFromUserName(msg.getToUserName());
				rmsg.setToUserName(msg.getFromUserName());
				rmsg.setMediaId(medid);
				session.callback(rmsg);
			}
			public void rebackVoice(String medid, Msg msg) {
				Msg4Voice rmsg = new Msg4Voice();
				rmsg.setFromUserName(msg.getToUserName());
				rmsg.setToUserName(msg.getFromUserName());
				rmsg.setMediaId(medid);
				session.callback(rmsg);
			}
			
			@Override
			public void onLocationMsg(Msg4Location msg){
				log.info("收到微信消息x："+msg.getLocation_X());
				log.info("收到微信消息y："+msg.getLocation_Y());
				log.info("发送方:"+msg.getFromUserName());
				log.info("接收方:"+msg.getToUserName());
				String str=new ChooseMsg().onMsg(msg);
				String user=msg.getFromUserName();
				try {
					if(db.isVis(user))
						db.addParamLoca(user,str);
					else 
							db.addUser(user);
				Class class2= Class.forName("wxtry.Allapi");	
		        Object object = (Allapi)class2.newInstance();      //暂定位82
		    	Method method= class2.getMethod("Near",Msg.class,DefaultSession.class);
		    	method.invoke(object,msg,session);
				}catch (Exception e) {
					LogWrite(e);
				}
				}
				
			@Override
			public void onImageMsg(Msg4Image msg){  
				  log.info("来自："+msg.getFromUserName());
				  log.info("发送到："+msg.getToUserName());
				log.info("图片消息 时间："+msg.getCreateTime());
				log.info("图片消息 id："+msg.getMediaId());
				log.info("图片链接："+msg.getPicUrl());
				String picurl=msg.getPicUrl();
				String user=msg.getFromUserName();
				
				 
				//添加图片关键字数据到数据库
				String  time=new Date().getTime()+"";
				db.addKeyPic("3",user,time);
				//下载图片到storage
				try{
					URL url = new URL(picurl);
				InputStream is = url.openStream();
				StorePic(is,time,user);
				}catch(Exception e){
					LogWrite(e);
					rebackText("上传失败", msg);
				}
				rebackText("图片上传成功，请发送对应关键词：", msg);
			}
			
			
			
			@Override
			public void onVideoMsg(Msg4Video msg){  
				  log.info("来自："+msg.getFromUserName());
				  log.info("发送到："+msg.getToUserName());
				log.info("视频消息 时间："+msg.getCreateTime());
				log.info("视频消息 medid："+msg.getMediaId());
				rebackVideo(msg.getMediaId(), msg);
			}
			
			@Override
			public void onVoiceMsg(Msg4Voice msg){  
				  log.info("来自："+msg.getFromUserName());
				  log.info("发送到："+msg.getToUserName());
				log.info("语音消息 时间："+msg.getCreateTime());
				log.info("语音消息medid："+msg.getMediaId());
				//下载  access_token
//				Variable.access_token=getAccessToken(Variable.appid,Variable.appsecret);
				   //http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID
				
				
				//写入sea
//				FileOutputStream outputStream = new FileOutputStream("saestor://domain/test.txt");
//				Writer writer = new OutputStreamWriter(outputStream);
//				writer.write("测试内容");
//				writer.close();
				
				//上传
				   //???Form??? http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE
				   //获取medid
				
				rebackVoice(msg.getMediaId(), msg);
			}
			
			public String SQLencode(String pageid){
				if(pageid==null)return null;
				return pageid.replaceAll("(\\')|(\")|(\\\")|(')|(\\()|(\\))|(\\\\)", "");
			}
			
			public void LogWrite(Exception ex){
				StringWriter sw = new StringWriter(); 
	            ex.printStackTrace(new PrintWriter(sw, true)); 
				log.error(sw.toString());
			}
			
			public String getAccessToken(String appid,String appsecret){
				if(appid==null||appsecret==null)return null;
				String url =" https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
				url=url.replace("APPID", appid).replace("APPSECRET", appsecret);
		    	HttpClient client = new HttpClient();
				Protocol myhttps = new Protocol("https",
						new MySSLProtocolSocketFactory(), 443);
				Protocol.registerProtocol("https", myhttps);
				PostMethod postMethod = new PostMethod(url);
					try {
						client.executeMethod(postMethod);
					
					String str = postMethod.getResponseBodyAsString();
					JSONObject json=JSONObject.fromObject(str);
					System.out.println(json.get("access_token"));
					str=(String) json.get("access_token");
					return str;
					} catch (IOException e) {
						LogWrite(e);
					}
		    	return null;
			}
			
		});
		
		//必须调用这两个方法
                //如果不调用close方法，将会出现响应数据串到其它Servlet中。
		session.process(is, os);//处理微信消息 
		session.close();//关闭Session
	}
	
	/**
	 *新浪云存储图片 1
	 */
	public static void StorePic(InputStream is,String fileName,String user) throws Exception{
		   String realPath = SaeUserInfo.getSaeTmpPath() + "/";
		   OutputStream os = new FileOutputStream(new File(realPath,fileName+".jpg"));
		   byte[] buff = new byte[1024*1024*2],b=new byte[1024];
		   int readed=0,ind=0;
		   while((readed=is.read(b)) != -1) {
		    System.arraycopy(b, 0,buff,ind,  readed);
		    ind+=readed;
		   }
		   os.write(buff,0,ind);
		   is.close();
		   os.close();
		   //复制临时图片到 目标目录
			SaeStorage saesto=new SaeStorage();
			saesto.upload("image",realPath+fileName+".jpg",user.replace('-','_')+"/"+fileName+".jpg");//不替换会出错
	}
	
	/**
	 *新浪云存储图片 2
	 */
	public static void StorePic(File file,String user){
		   String realPath = SaeUserInfo.getSaeTmpPath() + "/";
			SaeStorage saesto=new SaeStorage();
			saesto.upload("image",realPath+file.getName(),user.replace('-','_')+"/"+file.getName());//不替换会出错
	}
	
	
	
	/**
	 *发送生成url缩略图请求 
	 */
	public static String getFetch(String url){//simple-xml-2.0.2.jar
WebThumbService webThumbService = new WebThumbService("6f6b6c1f0b8e5de839bb4522c22211d6");
		
		WebThumbRequest request = new WebThumbRequest(url, OutputType.jpg);
        WebThumbJob job = null;
        try {
                job = webThumbService.sendRequest(request);
        } catch (WebThumbException e) {
                // handle error appropriately 
        }
		
        StringBuilder sb = new StringBuilder();
        sb.append("The server confirms a request for '").append(job.getUrl())
                .append("' was received on '").append(job.getTime())
                .append("'. Thumbnail will be ready in about ").append(job.getEstimate())
                .append(" seconds! This request costs ").append(job.getCost())
                .append(" credit(s). Please use '").append(job.getId())
                .append("' job id, to fetch the thumbnail!");
        System.out.println(sb);
        return job.getId();
	}
	
	/**
	 *获取缩略图 
	 */
	public static void getPicById(File file,String id){//commons.io.2.2.jar
		WebThumbService webThumbService = new WebThumbService("6f6b6c1f0b8e5de839bb4522c22211d6");
	  WebThumbFetchRequest fetchRequest = new WebThumbFetchRequest(id, Size.large);
	        try {
				webThumbService.fetch(fetchRequest, new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (WebThumbException e) {
				e.printStackTrace();
			}
	}


	@Override
	public void init() throws ServletException {
		//启动定时器
		Calendar beginTime = Calendar.getInstance();
		beginTime.set(Calendar.HOUR, 0);
		beginTime.set(Calendar.MINUTE, 10);
		beginTime.set(Calendar.SECOND, 0);
		TimeUse.spaceTime(beginTime, Calendar.DAY_OF_YEAR, 1, new TimeScheduleHandle() {			
			public void execute() {
				//小秘书
				String strRe = new Allapi().getAllInfo("oZMIwuIlpJXjizV1-jEqRk35tmQg");
				 //发送邮件
					if(strRe.isEmpty()){
						strRe = "无信息";
					}
				new MailSender("smtp."+Variable.email_name.split("@")[1], "25", Variable.email_name, Variable.email_pass, "vwzf@sina.com",
						"Information from City of Sky", strRe, false);
		}
	});
	}
	
}


