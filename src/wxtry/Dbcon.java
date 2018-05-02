package wxtry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.News;
import model.Retxt;
import model.Retype;
import model.Rule;
import model.WxUser;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import pubvari.Variable;
import DbXml.Helper_url;
import DbXml.HelperurlArr;
import DbXml.Keyword;
import DbXml.KeywordArr;
import DbXml.Openid_wid;
import DbXml.OpenidwidArr;
import DbXml.Pic_mus_reback;
import DbXml.PicmusrebackArr;
import DbXml.Re_keyword;
import DbXml.RekeywordArr;
import DbXml.Virecord;
import DbXml.VirecordArr;

import com.sina.cloudstorage.auth.AWSCredentials;
import com.sina.cloudstorage.auth.BasicAWSCredentials;
import com.sina.cloudstorage.services.scs.SCS;
import com.sina.cloudstorage.services.scs.SCSClient;
import com.sina.cloudstorage.services.scs.model.ObjectListing;
import com.sina.cloudstorage.services.scs.model.S3Object;
import com.sina.sae.util.SaeUserInfo;

public class Dbcon {
	private static final String tag="xmlxml";
	final static Logger log = Logger.getLogger("logfile");
	private static Serializer serializer;
	private static String[] fileNameList=new String[]{"helper_url.xml","keyword.xml"
			,"openid_wid.xml","pic_mus_reback.xml","re_keyword.xml","virecord.xml"};
	private static File result_Helper=new File(fileNameList[0]);
	private static File result_Keyword=new File(fileNameList[1]);
	private static File result_Openidwid=new File(fileNameList[2]);
	private static File result_Picmus=new File(fileNameList[3]);
	private static File result_Rekeyword=new File(fileNameList[4]);
	private static File result_Virecord=new File(fileNameList[5]);
	private static File[] fileList=new File[]{result_Helper,result_Keyword,result_Openidwid,result_Picmus,result_Rekeyword,result_Virecord};
	private static SCS conn;
//	private static FTPClient ftp =new FTPClient();
	static{
		serializer = new Persister();
//		try {
//		    ftp.connect("ftp.sinas3.com",10021);
//		    ftp.login(Variable.accessKey, Variable.secretKey);
//		    ftp.enterLocalPassiveMode();
//		    for(int i=0;i<fileNameList.length;i++){
//		    ftp.retrieveFile(tag+"/"+fileNameList[i], new FileOutputStream(fileList[i]));
//		    }
//		} catch (SocketException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		initDb(fileList);
		
		
		//加载xml数据库
		ReadXmlHelper();
		ReadXmlKeyword();
		ReadXmlOpenidwid();
		ReadXmlPicmus();
		ReadXmlRekeyword();
		ReadXmlVirecord();
		Variable.setDb();
	}

	/**
	 * 
	 * @description :初始化存储文件
	 * void
	 */
	private static void initDb(File[] files){
		AWSCredentials credentials = new BasicAWSCredentials(Variable.accessKey, Variable.secretKey);
		conn = new SCSClient(credentials);
		for(File file:files){
		 getObject(conn,file);
		}
	}
	
	/**
	 * 下载文件
	 * @description :
	 * void
	 */
	public static void getObject(SCS conn,File file){
	    //SDKGlobalConfiguration.setGlobalTimeOffset(-60*5);//自定义全局超时时间5分钟以后(可选项)
	    S3Object s3Obj = conn.getObject(tag, file.getName());
	    InputStream in = s3Obj.getObjectContent();
	    byte[] buf = new byte[1024];
	    OutputStream out = null;
	    try {
	        out = new FileOutputStream(file);
	        int count;
	        while( (count = in.read(buf)) != -1)
	        {
	           if( Thread.interrupted() )
	           {
	               throw new InterruptedException();
	           }
	           out.write(buf, 0, count);
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }finally{
	        //SDKGlobalConfiguration.setGlobalTimeOffset(0);//还原超时时间
	        try {
	            out.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        try {
	            in.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public static void ReadXmlHelper(){
		try {
			Variable.dbHelperList=serializer.read(HelperurlArr.class, result_Helper);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} 
	}
	public static void ReadXmlKeyword(){
		try {
			Variable.dbKeywordList=serializer.read(KeywordArr.class, result_Keyword);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} 
	}
	public static void ReadXmlOpenidwid(){
		try {
			Variable.dbOpenidwidList=serializer.read(OpenidwidArr.class, result_Openidwid);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} 
	}
	public static void ReadXmlPicmus(){
		try {
			Variable.dbPicmusList=serializer.read(PicmusrebackArr.class, result_Picmus);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} 
	}
	public static void ReadXmlRekeyword(){
		try {
			Variable.dbRekeywordList=serializer.read(RekeywordArr.class, result_Rekeyword);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} 
	}
	public static void ReadXmlVirecord(){
		try {
			Variable.dbVirecordList=serializer.read(VirecordArr.class, result_Virecord);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} 
	}

	/**
	 * 回写
	 */
	public static void WriteXml(){
		try {
			{
				File file=new File(SaeUserInfo.getSaeTmpPath() + "/","a.txt");
				serializer.write(Variable.dbHelperList, file);
				ToStoreFile(file,0);
			}
			{
				File file=new File(SaeUserInfo.getSaeTmpPath() + "/","a.txt");;//SAE直接write到store出错 
				serializer.write(Variable.dbKeywordList, file);
				ToStoreFile(file,1);
			}
			{
				File file=new File(SaeUserInfo.getSaeTmpPath() + "/","a.txt");;//SAE直接write到store出错 
				serializer.write(Variable.dbOpenidwidList,file);
				ToStoreFile(file, 2);
			}
			{
				File file=new File(SaeUserInfo.getSaeTmpPath() + "/","a.txt");;//SAE直接write到store出错 
				serializer.write(Variable.dbPicmusList,file);
				ToStoreFile(file, 3);
			}
			{
				File file=new File(SaeUserInfo.getSaeTmpPath() + "/","a.txt");;//SAE直接write到store出错 
				serializer.write(Variable.dbRekeywordList,file);
				ToStoreFile( file,4);
			}
			{
				File file=new File(SaeUserInfo.getSaeTmpPath() + "/","a.txt");;//SAE直接write到store出错 
				serializer.write(Variable.dbVirecordList, file);
				ToStoreFile(file,5);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} 
	}
	
	public static void WriteXmlHelper(){
		try {
			File file=new File(SaeUserInfo.getSaeTmpPath() + "/","a.txt");;//SAE直接write到store出错 
			serializer.write(Variable.dbHelperList, file);
			ToStoreFile(file,0);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} 
	}
	public static void WriteXmlKeyword(){
		try {
			File file=new File(SaeUserInfo.getSaeTmpPath() + "/","a.txt");;//SAE直接write到store出错 
			serializer.write(Variable.dbKeywordList, file);
			ToStoreFile(file,1);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} 
	}
	public static void WriteXmlOpenidwid(){
		try {
			File file=new File(SaeUserInfo.getSaeTmpPath() + "/","a.txt");;//SAE直接write到store出错 
			serializer.write(Variable.dbOpenidwidList,file);
			ToStoreFile(file, 2);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} 
	}
	public static void WriteXmlPicmus(){
		try {
			File file=new File(SaeUserInfo.getSaeTmpPath() + "/","a.txt");;//SAE直接write到store出错 
			serializer.write(Variable.dbPicmusList,file);
			ToStoreFile(file, 3);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} 
	}
	public static void WriteXmlRekeyword(){
		try {
			File file=new File(SaeUserInfo.getSaeTmpPath() + "/","a.txt");;//SAE直接write到store出错 
			serializer.write(Variable.dbRekeywordList,file);
			ToStoreFile( file,4);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} 
	}
	public static void WriteXmlVirecord(){
		try {
			File file=new File(SaeUserInfo.getSaeTmpPath() + "/","a.txt");;//SAE直接write到store出错 
			serializer.write(Variable.dbVirecordList, file);
			ToStoreFile(file,5);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		} 
	}
	/**
	 *保存到SAE store工具方法
	 * @throws IOException 
	 */
	public static void ToStoreFile(File tmpFile,int seq) throws IOException{
//		FileInputStream fis=new FileInputStream(tmpFile);
//		ftp.storeFile(tag+"/"+fileNameList[seq], fis)
		conn.putObject(tag, fileNameList[seq], tmpFile);
	}

	public int updateKey(String str,String openid){
		//成功修改关键词返回1
		int reI=0;
		for(Re_keyword k : Variable.dbRekeywordList.getRekeywordList()){
			if(openid.equals(k.getOpenid()) && (k.getKey0()==null || "".equals(k.getKey0()))){
				k.setKey0(str.toLowerCase());
				//写入
				WriteXmlRekeyword();
				reI=1;break;
			}
		}
		//其他返回0
		return reI;
		
	}
	
    /**
     * 添加图片唯一名称
     * @return  上次未设关键词图片名（执行覆盖操作）  或  新图片名
     * 
     * */
	public void addKeyPic(String type,String openid,String time){
		boolean flag=false;//存在标记 
		//成功找到关键词
		for(Re_keyword k : Variable.dbRekeywordList.getRekeywordList()){
			if((type).equals(k.getType0()) 
					&& k.getOpenid().equals(openid) 
					&& (k.getKey0()==null || "".equals(k.getKey0()))){
                flag=true;
                break;
				
			}
		}
		//没有则 新增
		if(!flag){
			//写入
			Variable.dbRekeywordList.getRekeywordList().add(new Re_keyword(new Date().getTime()+"",type, time, openid));
			WriteXmlRekeyword();
		}
		
	}
	
	public void delTypeOnekey(String key,String openid){
		Serializer serializer = new Persister();
		File result = new File("classes/Dbxml/re_keyword.xml");
		Re_keyword j=null;
		for(Re_keyword k : Variable.dbRekeywordList.getRekeywordList()){
			if(key.toLowerCase().equals(k.getKey0()) && openid.equals(k.getOpenid())){
				j=k;
				break;
			}
		}
		Variable.dbRekeywordList.getRekeywordList().remove(j);
		WriteXmlRekeyword();
	}
	
	public void addTypeOnekey(String key,String content,String openid){
		//写入
		Variable.dbRekeywordList.getRekeywordList().add(new Re_keyword("0", "0", "common", key, "1", content, null, openid));
		WriteXmlRekeyword();
	}
	
	public Helper_url addHelper(String[] params,String openid){
		Helper_url helper = new Helper_url(params, openid);
		Variable.dbHelperList.getHelperList().add(helper);
		WriteXmlHelper();
		return helper;
	}
	
	public boolean isVis(String user) {
		for(Virecord v : Variable.dbVirecordList.getVirecordList()){
			if(user.equals(v.getName())){
				return true;
			}
		}
		return false;
	}

	public String getInstruction(String user) {
		for(Virecord v : Variable.dbVirecordList.getVirecordList()){
			if(user.equals(v.getName())){
				return v.getInstruction();
			}
		}
		return "none";
	}
	public String getParamLoca(String user){
		for(Virecord v : Variable.dbVirecordList.getVirecordList()){
			if(user.equals(v.getName())){
				return v.getParam_loca();
			}
		}
		return "none";
	}

	public void addUser(String user){
		Variable.dbVirecordList.getVirecordList().add(new Virecord(user));
		WriteXmlVirecord();
	}

	public void updateStr(String user, long reid){
		for(Virecord v : Variable.dbVirecordList.getVirecordList()){
			if(user.equals(v.getName())){
				v.setMode(reid+"");
				v.setInstruction("");
				WriteXmlVirecord();
				break;
			}
		}
	}

	public String getMode(String user) {
		for(Virecord v : Variable.dbVirecordList.getVirecordList()){
			if(user.equals(v.getName())){
				return v.getMode()+"";
			}
		}
		return null;

	}

	public String getReback(String user) {
		for(Keyword k : Variable.dbKeywordList.getKeywordList())
		   for(Virecord v : Variable.dbVirecordList.getVirecordList())
		{
			  if(v.getName().equals(user) && v.getInstruction().indexOf(k.getWord())>0 )
			return k.getReback();
		}
		return "";
	}

	public void addInstruction(String user, String str) {
		byte[] b;
		int i=0;
		for(byte bb:b=str.substring(0, str.length() > 200 ? 199 : str.length()).trim().getBytes())
			b[i++]=(byte) (bb>127?(bb-127):bb);//去16进制
		str=new String(b).replace("\'", "\\\'");
		for(Virecord v:Variable.dbVirecordList.getVirecordList()){
			if(user.equals(v.getName())){
				v.setInstruction(str);
				WriteXmlVirecord();//TODO确定回写信息是否成功
				break;
			}
		}
	}
	public void addParamLoca(String user, String str){
		for(Virecord v:Variable.dbVirecordList.getVirecordList()){
			if(user.equals(v.getName())){
				v.setInstruction(str.substring(0, str.length() > 200 ? 199 : str.length())
						.trim());
				WriteXmlVirecord();
				break;
			}
		}
	}


	public ArrayList<WxUser> getUsers() {
		ArrayList arr = new ArrayList();
		WxUser user=null;
		for(Openid_wid o : Variable.dbOpenidwidList.getOpenidwidList()){
			if(o.getEmail()!=null && o.getEmail().matches("[\\s\\S]+@[\\s\\S]+\\.[\\s\\S]+")){
				user=new WxUser();
				user.setFakeId(o.getOpenid());
				user.setEmail(o.getEmail());
				arr.add(user);
		}
		}
		return arr;

	}
	public WxUser getUser(String openid) {
		WxUser user=null;
		for(Openid_wid o : Variable.dbOpenidwidList.getOpenidwidList()){
			if(openid.equals(o.getOpenid())){
				user=new WxUser();
				user.setFakeId(o.getOpenid());
				user.setEmail(o.getEmail());
			}
		}
		return user;
	}
	
	public String isReKeyWord(String str,String openid){
		str = str.toLowerCase();
		for(Re_keyword r :Variable.dbRekeywordList.getRekeywordList()){
			if((r.getOpenid()==null || r.getOpenid().length()==0 || r.getOpenid()==openid)
					&& str.equals(r.getKey0())
					&& r.getRe_id().equals("1")){
				return r.getId();
			}
		}
		
		for(Re_keyword r :Variable.dbRekeywordList.getRekeywordList()){
			if((r.getOpenid()==null || r.getOpenid().length()==0 || r.getOpenid()==openid)
					&& r.getKey0().indexOf(str)>0
					&& r.getRe_id().equals("2")){
				return r.getId();
			}
			
		}
		return "-2";

	}

	public int getType0ById(long id,String openid){
		for(Re_keyword r :Variable.dbRekeywordList.getRekeywordList()){
			if((r.getOpenid()==null || r.getOpenid().length()==0 || r.getOpenid()==openid)
					&& r.getId().equals(id+"")){
				return Integer.parseInt(r.getType0());
			}
		}
		return 0;
	}

	public String getReContentById(long id,String openid){
		for(Re_keyword r :Variable.dbRekeywordList.getRekeywordList()){
			if((r.getOpenid()==null || r.getOpenid().length()==0 || r.getOpenid()==openid)
					&& r.getId().equals(id+"")){
				return r.getContent();
			}
		}
		return "";

	}

	public void addOpenid(String openid){
		Variable.dbOpenidwidList.getOpenidwidList().add(new Openid_wid(openid));
		WriteXmlOpenidwid();
	}
	
	public boolean isaddId(String openid)//有没添加过
	 {
		for(Openid_wid o :Variable.dbOpenidwidList.getOpenidwidList()){
			if(openid.equals(o.getOpenid()))
				return true;
			
		}
		return false;
	}
	
	public String getTxtback(String name, String str,String openid) {
		for(Re_keyword r :Variable.dbRekeywordList.getRekeywordList()){
			if((r.getOpenid()==null || r.getOpenid().length()==0 || r.getOpenid()==openid)
					&& r.getName().equals(name)
					&& r.getKey0().equals(str)){
				return r.getContent();
			}
		}
		return "";
	}

	public void delOpenIdval(String openid){
		Openid_wid k=null;
		for(Openid_wid o :Variable.dbOpenidwidList.getOpenidwidList()){
			if(openid.equals(o.getOpenid())){
				k=o;
				break;
			}
		}
		Variable.dbOpenidwidList.getOpenidwidList().remove(k);
		WriteXmlOpenidwid();
	}

	public void updateUserEmail(String openid, String email){
		for(Openid_wid o :Variable.dbOpenidwidList.getOpenidwidList()){
			if(openid.equals(o.getOpenid())){
				o.setEmail(email);
				WriteXmlOpenidwid();
				break;
			}
		}
		
	}
	
	public String getReContent(int id,int type){
		String str="";
		for(Pic_mus_reback p: Variable.dbPicmusList.getPicmusrebackList()){
			if(p.getId().equals(id+"") && p.getType().equals(type+"")){
				str+= p.getId()+";";
			}
		}
		
		return str;
	}
	public String getReContent(long id,String openid){
		for(Re_keyword r :Variable.dbRekeywordList.getRekeywordList()){
			if((r.getOpenid()==null || r.getOpenid().length()==0 || r.getOpenid()==openid)
					&& r.getId().equals(id+"")){
				return r.getContent();
			}
		}
		return "";
		
	}

	public ArrayList <News>getPicMusById(String[] id) {
		ArrayList<News>arr = new ArrayList<News>();
		News n=null;
		for (String i : id) {
		for(Pic_mus_reback p: Variable.dbPicmusList.getPicmusrebackList()){
			if(i.equals(p.getId()+"")){
				n = new News();
				n.setTitle(p.getTitle());
				n.setMain(p.getMain());
				n.setUrlo(p.getUrlo());
				n.setUrl(p.getUrl());
				n.setPty(p.getPty());
				n.setPid(p.getPid()+"");
				arr.add(n);
			}
		}
		
		}
		return arr;
	}

	
	public List<Rule> getTriggerUrl(String openid){
		List<Rule> rules=new ArrayList<Rule>();
		Rule rule=null;
		for(Helper_url h :Variable.dbHelperList.getHelperList()){
			if(h.getTag().equals(openid)&& h.getRegex()!=null && h.getRegex().length()>0){
				rule=new Rule();
				rule.setBeg(h.getBeg());
				rule.setEnd(h.getEnd());
				rule.setUrl(h.getUrl());
				rule.setName(h.getName());
				rule.setRegex(h.getRegex());
				rule.setMethod(h.getMethod());
				rules.add(rule);
				
			}
			
		}
		return rules;
	}
	

	public void addTxtrekey(String type,String name,String key,String keystyle,String con){
		boolean flag=false;//添加或修改标记
		con=con.replace("\'", "\\\'").replace(";", "|");
		for(Re_keyword r : Variable.dbRekeywordList.getRekeywordList()){
			if(name.equals(r.getName()) && key.equals(r.getKey0())){
				r.setType0(type);
				r.setRe_id(keystyle);
				r.setContent(con);
				flag=true;
				break;
			}
		}
		//新增
		if(!flag){
			Re_keyword re=new Re_keyword(type, name, key, keystyle,con);
			Variable.dbRekeywordList.getRekeywordList().add(re);
		}
		WriteXmlRekeyword();
		}

	public void addNewsrekey(String name,String key,String keystyle,String n_id){
		key = key.toLowerCase().trim();
		n_id=n_id.replace(";", "|");
		boolean flag=false;
		for(Re_keyword r : Variable.dbRekeywordList.getRekeywordList()){
			if(name.equals(r.getName()) && key.equals(r.getKey0())){
				r.setRe_id(keystyle);
				r.setContent(n_id);
				//删除字条目
				String del_ids=r.getContent();
				String d_ids[]=del_ids.split("\\|");
				Map<String, String> map=new HashMap<String, String>();
				for(String d : d_ids){
					map.put(d, d);
				}
				Pic_mus_reback p=null;
					 for(int j=Variable.dbPicmusList.getPicmusrebackList().size()-1;j>=0;j--){
						 p=Variable.dbPicmusList.getPicmusrebackList().get(j);
						 if(map.containsKey(p.getId()+"")){
							 Variable.dbPicmusList.getPicmusrebackList().remove(p);
						 }
					 }
				
				flag=true;
				break;
			}
		}
		//新增
		if(!flag){
			Re_keyword re=new Re_keyword("1", name, key, keystyle,n_id);
			Variable.dbRekeywordList.getRekeywordList().add(re);
		}
		WriteXmlRekeyword();
		
	}
	public String addNesItem(String type,String[] title,String[] main,String[] urlo,String pty[],String pid[],String[] url,int count) {
		String id="";
		for(int i = 0;i<count;i++){
			long timeId=new Date().getTime();
			Variable.dbPicmusList.getPicmusrebackList().add(new Pic_mus_reback(timeId+"","0", "1", title[i], main[i], urlo[i], pty[i], url[i],pid[i]));
			id+=timeId+";";
		}
		WriteXmlPicmus();
		return id;
	}
	
	public void addMusrekey(String name,String key,String keystyle,long id){
		boolean flag=false;
		for(Re_keyword r : Variable.dbRekeywordList.getRekeywordList()){
			if(name.equals(r.getName()) && key.equals(r.getKey0())){
				r.setRe_id(keystyle);
				r.setContent(id+"");
				flag=true;
				//删除旧条目
				String del_id=r.getContent();
				Pic_mus_reback pp=null;
					 for(Pic_mus_reback p : Variable.dbPicmusList.getPicmusrebackList()){
						 if(del_id.equals(p.getId()+"")){
							 pp=p;
						 }
					 }
			    if(pp!=null){
			    	Variable.dbPicmusList.getPicmusrebackList().remove(pp);
			    	WriteXmlPicmus();
			    }		 
					 
				break;
			}
		}
		if(!flag){
			Variable.dbRekeywordList.getRekeywordList().add(new Re_keyword("2", name, key, keystyle, id+""));
		}
		WriteXmlRekeyword();
		
	}
	
	public long addMusItem(String type,String title,String main,String urlo,String url){
		long timeId=new Date().getTime();
		Variable.dbPicmusList.getPicmusrebackList().add(new Pic_mus_reback(timeId+"", "1",type, title, main, urlo, "1", url, "1"));
		WriteXmlPicmus();
		return timeId;
	}
	
	public Retxt getTxtarrayByname(String name){
		Retxt txt=new Retxt();
		StringBuffer key0s=new StringBuffer(),reids=new StringBuffer();
		for(Re_keyword r : Variable.dbRekeywordList.getRekeywordList()){
			if(name.equals(r.getName())){
				txt.setName(name);
				txt.setType(r.getType0());
				key0s.append(r.getKey0()+" ");
				reids.append(r.getRe_id()+" ");
				txt.setContent(r.getContent());
			}
		}
		txt.setKey(key0s.toString().split(" "));
		txt.setKeystyle(reids.toString().split(" "));
		return txt;
	}
	public Retxt getNewsarrayByname(String name){
		Retxt txt=new Retxt();
		StringBuffer key0s=new StringBuffer(),reids=new StringBuffer(),contents=new StringBuffer();
		for(Re_keyword r : Variable.dbRekeywordList.getRekeywordList()){
			if(name.equals(r.getName())&& "1".equals(r.getType0())){
				txt.setName(name);
				txt.setType(r.getType0());
				key0s.append(r.getKey0()+" ");
				reids.append(r.getRe_id()+" ");
				contents.append(r.getContent()+" ");
			}
		}
				txt.setKey(key0s.toString().split(" "));
				txt.setKeystyle(reids.toString().split(" "));
		
		String[] ids=contents.toString().split(" ");
		String cons="";
		for(String id:ids[0].split("\\|")){
			for(Pic_mus_reback p : Variable.dbPicmusList.getPicmusrebackList()){
				if(id.equals(p.getId()+"")){
					cons+=p.getTitle()+"|"+p.getMain()+"|"+p.getUrlo()+"|"+p.getPty()+"|"+p.getPid()+"|"+p.getUrl().split("\\?")[0]+";";
				}
				
			}
		}
			txt.setContent(cons);
		return txt;
	}
	public Retxt getMusarrayByname(String name){
		Retxt txt=new Retxt();
		StringBuffer key0s=new StringBuffer(),reids=new StringBuffer(),contents=new StringBuffer();
		for(Re_keyword r : Variable.dbRekeywordList.getRekeywordList()){
			if(name.equals(r.getName())&& "2".equals(r.getType0())){
				txt.setName(name);
				txt.setType(r.getType0());
				key0s.append(r.getKey0()+" ");
				reids.append(r.getRe_id()+" ");
				contents.append(r.getContent()+" ");
			}
		}
				txt.setKey(key0s.toString().split(" "));
				txt.setKeystyle(reids.toString().split(" "));
		
		String[] ids=contents.toString().split(" ");
		String cons="";
			for(Pic_mus_reback p : Variable.dbPicmusList.getPicmusrebackList()){
				if(ids[0].replace("|", "").equals(p.getId()+"")){
					cons+=p.getTitle()+"|"+p.getMain()+"|"+p.getUrlo()+"|"+p.getPty()+"|"+p.getPid()+"|"+p.getUrl()+"http;";
				}
				
			}
			txt.setContent(cons);
		return txt;
	}
	public void delKeyword(String name){
		String id="";
    	String ids="";
		for(Re_keyword r : Variable.dbRekeywordList.getRekeywordList()){
			if(name.equals(r.getName())){
				id+=r.getId()+";";
    		ids+=r.getContent();
			}
		}

      for(String str:id.split(";")){
    	  Pic_mus_reback pp=null;
	  for(Pic_mus_reback p :Variable.dbPicmusList.getPicmusrebackList()){
		  if(str.equals(p.getKeyid())){
			  pp=p;
			  break;
		  }
	  }
//	  Variable.dbPicmusList.getPicmusrebackList().remove(pp);
	  for(Virecord v :Variable.dbVirecordList.getVirecordList()){
		  if(str.equals(v.getMode())){
			  v.setMode("0");
		  }
	  }
	}
      
	for(String str:ids.split(";")){
		Pic_mus_reback pp=null;
		for(Pic_mus_reback p :Variable.dbPicmusList.getPicmusrebackList()){
			  if(str.equals(p.getKeyid())){
				  pp=p;
				  break;
			  }
		  }
		  Variable.dbPicmusList.getPicmusrebackList().remove(pp);
		}
	    Re_keyword rr=null;
	    for(Re_keyword r : Variable.dbRekeywordList.getRekeywordList()){
	    	if(name.equals(r.getName())){
	    		rr=r;
	    		break;
	    	}
	    }
	    Variable.dbRekeywordList.getRekeywordList().remove(rr);
	    
	    WriteXmlPicmus();
	    WriteXmlRekeyword();
	    WriteXmlVirecord();
    }
	
    public ArrayList<Retype> getRearray(){
		boolean flag=false;
		Retype txt=new Retype();
		ArrayList<Retype> array=new ArrayList<Retype>();
		StringBuffer buffer=new StringBuffer(),buffer1=new StringBuffer(),buffer2=new StringBuffer(),buffer3=new StringBuffer();
		for(Re_keyword r :Variable.dbRekeywordList.getRekeywordList()){
			 if(!txt.name.equals(r.getName())){
				 txt=new Retype(); 
				 buffer=new StringBuffer();
				 buffer1=new StringBuffer();
				 buffer2=new StringBuffer();
				 flag=true;
			 }
			 txt.setName(r.getName());
			 txt.setIdentity(r.getIdentity());
			 txt.setType(r.getType0());
			 buffer.append(r.getKey0()+" ");
			 buffer1.append(r.getRe_id()+" ");
			 buffer2.append(r.getContent()+" ");
			txt.setKey(buffer.toString().split(" "));
			txt.setKeystyle(buffer1.toString().split(" "));
			txt.setContent(buffer2.toString().split(" "));
	   if(flag)
			array.add(txt);
	   flag=false;
		}
		
		return array;
	}
	public ArrayList<Retype> getRearray(String key){
		boolean flag1=false,flag2=false;
		Retype txt=new Retype();
		StringBuffer buffer=new StringBuffer(),buffer1=new StringBuffer(),buffer2=new StringBuffer(),buffer3=new StringBuffer();
		String[] str;
		ArrayList array=new ArrayList();
		for(Re_keyword r :Variable.dbRekeywordList.getRekeywordList()){
			 if(!txt.name.equals(r.getName())){
				 txt=new Retype(); 
				 buffer=new StringBuffer();
				 buffer1=new StringBuffer();
				 buffer2=new StringBuffer();
				 flag1=true;flag2=false;
			 }
			 txt.setName(r.getName());
			 txt.setIdentity(r.getIdentity());
			 txt.setType(r.getType0());
			 buffer.append(r.getKey0()+" ");
			 String[] str1=buffer.toString().split(" ");
			 for(int i=0;i<str1.length;i++)
				 if(key.equals(str1[i]))flag2=true;
			 buffer1.append(r.getRe_id()+" ");
			 buffer2.append(r.getContent()+" ");
			txt.setKey(buffer.toString().split(" "));
			txt.setKeystyle(buffer1.toString().split(" "));
			txt.setContent(buffer2.toString().split(" "));
	   if(flag1&&flag2)
			array.add(txt);
	   flag1=false;
		
		}
		return array;
	}
	
	public void setWelcome(String name){
		for(Re_keyword r :Variable.dbRekeywordList.getRekeywordList()){
			if("welcome".equals(r.getIdentity())){
				r.setIdentity("");
			}
		}
		for(Re_keyword r :Variable.dbRekeywordList.getRekeywordList()){
			if(name.equals(r.getName())){
				r.setIdentity("welcome");
			}
		}
		WriteXmlRekeyword();
	}
	public void setDefault(String name){
		for(Re_keyword r :Variable.dbRekeywordList.getRekeywordList()){
			if("default".equals(r.getIdentity())){
				r.setIdentity("");
			}
		}
		for(Re_keyword r :Variable.dbRekeywordList.getRekeywordList()){
			if(name.equals(r.getName())){
				r.setIdentity("default");
			}
		}
		WriteXmlRekeyword();
	}
	public static void main(String[] args) {
		System.out.println(Variable.dbOpenidwidList.getOpenidwidList().size());
	}
}
