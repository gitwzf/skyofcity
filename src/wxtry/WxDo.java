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
	
	//��springmvc���� 1.��module
	//TOKEN ������΢��ƽ̨����ģʽ�����õ�Ŷ
	public static final String TOKEN = "D8yuc";

	/**
	 * ����΢�ŷ�������֤
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String signature = request.getParameter("signature");// ΢�ż���ǩ��
		String timestamp = request.getParameter("timestamp");// ʱ���
		String nonce = request.getParameter("nonce");// �����
		String echostr = request.getParameter("echostr");// ����ַ���
	
	// ��дtotring�������õ�����������ƴ���ַ���
		List<String> list = new ArrayList<String>(3) {
			private static final long serialVersionUID = 2621444383666420433L;
			public String toString() {
				return this.get(0) + this.get(1) + this.get(2);
			}
		};
		list.add(TOKEN);
		list.add(timestamp);
		list.add(nonce);
		Collections.sort(list);// ����
		String tmpStr = new MySecurity().encode(list.toString(),
				MySecurity.SHA_1);// SHA-1����
		Writer out = response.getWriter();
		if (signature.equals(tmpStr)) {
			out.write(echostr);// ������֤�ɹ������������
			} else {
			out.write("");
		}
		out.flush();
		out.close();
	}

	
	/**
	 * ����΢�ŷ������������ĸ�����Ϣ���������ı���ͼƬ������λ�á����ֵȵ�
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
			
			@Override  //��д
			//�˵��¼�
			public void onEventMsg(Msg4Event msg) {
				log.info("�յ�΢����Ϣ��"+msg.getEvent());
				log.info("���ͷ�:"+msg.getFromUserName());
				log.info("���շ�:"+msg.getToUserName());
				String user=msg.getFromUserName();//user name
				String reback="";//reback mesge
				
				 try {
				       CryptoTools tools = new CryptoTools();
					if("subscribe".equals(msg.getEvent())){//ding yue msg
						int ev=-3;
					    if(!db.isaddId(user)){
					       db.addOpenid(user);
					       }else{
					    	  //��������޸ı��
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
						 else {               //����Ĭ�ϻظ�
							 Class class2=Class.forName("wxtry.Allapi");
						        Object object=(Allapi)class2.newInstance();
						    	Method method = class2.getMethod("Subscribe",Msg.class,DefaultSession.class);
						        method.invoke(object,msg,session);}
						
					}
					 else if("unsubscribe".equals(msg.getEvent())){
						 db.delOpenIdval(user);
					 }
					 else if(!"VIEW".equals(msg.getEvent())){          //�˵��¼�����VIEW�¼���
						
					 }
				} catch (Exception e) {
					LogWrite(e);
					rebackText("ϵͳ����Exception��",msg);return;
					
				}
			}
			
			@Override
			public void onTextMsg(Msg4Text msg) {
				log.info("�յ�΢����Ϣ��"+msg.getContent());
				log.info("���ͷ�:"+msg.getFromUserName());
				log.info("���շ�:"+msg.getToUserName());
				log.info("����ʱ��:"+msg.getCreateTime());
				
				String createtime=msg.getCreateTime();
				 String str=SQLencode(msg.getContent());
				String user=msg.getFromUserName();
				
				String mode="";
				//û�еĻ����openid   //�жϡ�fakeid�� ��openid�Ƿ���ͬ
				if(!db.isaddId(user)){//��ͬfalse
					db.addOpenid(user);
				}
				
			//��ʽ�ж�  	
		if(str.matches("#[\\S\\s]+#[\\S\\s]+")){//�û��ӹؼ���
			String[] params = str.substring(1).split("#");
			String key=params[0];
			String value=params[1];
		   if(!key.matches("[a-zA-Z]")){
			   db.addTypeOnekey(key,value,user);
			   rebackText("�ؼ��� "+key+" ��ӳɹ�:"+value, msg);
			   
		   }else{
			   rebackText("�ؼ������ʧ��", msg);
			   
		   }
		}else if(str.matches("--[\\S\\s]+--")){
			String key=str.substring(2,str.indexOf("--",2));
			db.delTypeOnekey(key,user);
			 rebackText("�ؼ��� "+key+" ɾ���ɹ�", msg);
			 
		}else if(str.trim().matches("@[\\S\\s]+@[\\S\\s]+@[\\S\\s]+")){//���С����
			WxUser u=db.getUser(user);
			if(u==null || u.getEmail()==null || u.getEmail().isEmpty()){
				rebackText("������Ӱ��ʼ������硰��12345678@qq.com��",msg);return;
			}
			Helper_url helper = db.addHelper(str.substring(1).split("@"), user);
			rebackText("С���������Ϣ����1���Ӻ��͵�������䣺"+u.getEmail(),msg);
			//��ȡurl����ͼ
			final String tmpPath=SaeUserInfo.getSaeTmpPath() + "/tmp.jpg";
			  File file=new File(tmpPath);
				String id=getFetch(helper.getUrl());
				getPicById(file,id);
				StorePic(file,user);
				final String email = u.getEmail();
				final String name = helper.getName();
				TimeUse.waitTime(Calendar.MINUTE, 1, new TimeScheduleHandle() {			
					public void execute() {
						//���ʼ�
						new MailSender("smtp."+vari.email_name.split("@")[1], "25", vari.email_name, vari.email_pass, email,
								"���֮��-С���飺"+ name, "��ӳɹ�", false,new String[]{tmpPath});
				}
			});
				 
				
			  
		}else if(str.startsWith("��")){
			String value=str.split("��")[1];
			if(value.matches("[\\s\\S]+@[\\s\\S]+\\.[\\s\\S]+")){//�ʼ�
				db.updateUserEmail(user,value);
			}
			
			rebackText("�󶨳ɹ�", msg);
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
					  if(type0==1)//��ӵ�ǰ�û�ͼƬ �ؼ���
					   reId = Long.parseLong(db.isReKeyWord(str,user));
					  if(reId<=0){
					if(db.getMode(user)!=null)//�ϴιؼ��ʻظ�
				   reId=Integer.parseInt(db.getMode(user));//��ȡ�˵���
					  }
				
				}
				
				try {
					int inf=db.getType0ById(reId,user);
					log.info("inf="+inf);
					log.info("reId="+reId);
					if(inf==-1){                                        //�ӿ�
						String methodName=db.getReContentById(reId,user);
						 Class class2=Class.forName("wxtry.Allapi");
					        Object object=(Allapi)class2.newInstance();
					    	Method method = class2.getMethod(methodName,Msg.class,DefaultSession.class);
					        method.invoke(object,msg,session);
//					        rebackText((String)method.invoke(object,msg), msg);
					        
					}
					
					if(inf==0){                                          //����
						rebackText(db.getReContent(reId,user),msg);
					}
					if(inf==1){											//ͼ��
						String[] dmid=db.getReContent(reId,user).split("\\|");
						Data4Item[] dm=new Data4Item[dmid.length];
						ArrayList<News>array=db.getPicMusById(dmid);
						for(int i=0;i<array.size();i++)
						dm[i] = new Data4Item(array.get(i).getTitle(), array.get(i).getMain(),array.get(i).getUrlo(),""); 
						rebackImage(dm,msg);
					}
					if(inf==2){											//��Ƶ
						String musid[]=new String[1];
						 musid[0]=db.getReContent(reId,user).split("\\|")[0];
						ArrayList<News>array=db.getPicMusById(musid);
						rebackMusic(array.get(0).getTitle(),array.get(0).getMain(),array.get(0).getUrl(),msg);
					}
					if(inf==3){                                          //ͼƬ
						String filename=db.getReContent(reId,user);
						String picurl=vari.source_pic_url+user.replace("-", "_")+"/"+filename+".jpg";
						Data4Item data=new Data4Item(str,"",picurl, picurl);
						rebackImage(data, msg);
					}
						
					
				}catch (Exception e) {
					LogWrite(e);
					rebackText("ϵͳ����",msg);
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
				log.info("�յ�΢����Ϣx��"+msg.getLocation_X());
				log.info("�յ�΢����Ϣy��"+msg.getLocation_Y());
				log.info("���ͷ�:"+msg.getFromUserName());
				log.info("���շ�:"+msg.getToUserName());
				String str=new ChooseMsg().onMsg(msg);
				String user=msg.getFromUserName();
				try {
					if(db.isVis(user))
						db.addParamLoca(user,str);
					else 
							db.addUser(user);
				Class class2= Class.forName("wxtry.Allapi");	
		        Object object = (Allapi)class2.newInstance();      //�ݶ�λ82
		    	Method method= class2.getMethod("Near",Msg.class,DefaultSession.class);
		    	method.invoke(object,msg,session);
				}catch (Exception e) {
					LogWrite(e);
				}
				}
				
			@Override
			public void onImageMsg(Msg4Image msg){  
				  log.info("���ԣ�"+msg.getFromUserName());
				  log.info("���͵���"+msg.getToUserName());
				log.info("ͼƬ��Ϣ ʱ�䣺"+msg.getCreateTime());
				log.info("ͼƬ��Ϣ id��"+msg.getMediaId());
				log.info("ͼƬ���ӣ�"+msg.getPicUrl());
				String picurl=msg.getPicUrl();
				String user=msg.getFromUserName();
				
				 
				//���ͼƬ�ؼ������ݵ����ݿ�
				String  time=new Date().getTime()+"";
				db.addKeyPic("3",user,time);
				//����ͼƬ��storage
				try{
					URL url = new URL(picurl);
				InputStream is = url.openStream();
				StorePic(is,time,user);
				}catch(Exception e){
					LogWrite(e);
					rebackText("�ϴ�ʧ��", msg);
				}
				rebackText("ͼƬ�ϴ��ɹ����뷢�Ͷ�Ӧ�ؼ��ʣ�", msg);
			}
			
			
			
			@Override
			public void onVideoMsg(Msg4Video msg){  
				  log.info("���ԣ�"+msg.getFromUserName());
				  log.info("���͵���"+msg.getToUserName());
				log.info("��Ƶ��Ϣ ʱ�䣺"+msg.getCreateTime());
				log.info("��Ƶ��Ϣ medid��"+msg.getMediaId());
				rebackVideo(msg.getMediaId(), msg);
			}
			
			@Override
			public void onVoiceMsg(Msg4Voice msg){  
				  log.info("���ԣ�"+msg.getFromUserName());
				  log.info("���͵���"+msg.getToUserName());
				log.info("������Ϣ ʱ�䣺"+msg.getCreateTime());
				log.info("������Ϣmedid��"+msg.getMediaId());
				//����  access_token
//				Variable.access_token=getAccessToken(Variable.appid,Variable.appsecret);
				   //http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID
				
				
				//д��sea
//				FileOutputStream outputStream = new FileOutputStream("saestor://domain/test.txt");
//				Writer writer = new OutputStreamWriter(outputStream);
//				writer.write("��������");
//				writer.close();
				
				//�ϴ�
				   //???Form??? http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE
				   //��ȡmedid
				
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
		
		//�����������������
                //���������close���������������Ӧ���ݴ�������Servlet�С�
		session.process(is, os);//����΢����Ϣ 
		session.close();//�ر�Session
	}
	
	/**
	 *�����ƴ洢ͼƬ 1
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
		   //������ʱͼƬ�� Ŀ��Ŀ¼
			SaeStorage saesto=new SaeStorage();
			saesto.upload("image",realPath+fileName+".jpg",user.replace('-','_')+"/"+fileName+".jpg");//���滻�����
	}
	
	/**
	 *�����ƴ洢ͼƬ 2
	 */
	public static void StorePic(File file,String user){
		   String realPath = SaeUserInfo.getSaeTmpPath() + "/";
			SaeStorage saesto=new SaeStorage();
			saesto.upload("image",realPath+file.getName(),user.replace('-','_')+"/"+file.getName());//���滻�����
	}
	
	
	
	/**
	 *��������url����ͼ���� 
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
	 *��ȡ����ͼ 
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
		//������ʱ��
		Calendar beginTime = Calendar.getInstance();
		beginTime.set(Calendar.HOUR, 0);
		beginTime.set(Calendar.MINUTE, 10);
		beginTime.set(Calendar.SECOND, 0);
		TimeUse.spaceTime(beginTime, Calendar.DAY_OF_YEAR, 1, new TimeScheduleHandle() {			
			public void execute() {
				//С����
				String strRe = new Allapi().getAllInfo("oZMIwuIlpJXjizV1-jEqRk35tmQg");
				 //�����ʼ�
					if(strRe.isEmpty()){
						strRe = "����Ϣ";
					}
				new MailSender("smtp."+Variable.email_name.split("@")[1], "25", Variable.email_name, Variable.email_pass, "vwzf@sina.com",
						"Information from City of Sky", strRe, false);
		}
	});
	}
	
}


