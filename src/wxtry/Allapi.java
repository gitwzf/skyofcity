package wxtry;
import pubvari.Variable;
import interf.ChooseMsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Rule;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.marker.weixin.DefaultSession;
import org.marker.weixin.msg.Data4Item;
import org.marker.weixin.msg.Msg;
import org.marker.weixin.msg.Msg4Event;
import org.marker.weixin.msg.Msg4ImageText;
import org.marker.weixin.msg.Msg4Music;
import org.marker.weixin.msg.Msg4Text;
import org.xml.sax.InputSource;

import com.unit.type.TypeOne;
/*功能优化:用户输入 退出 返回  设空--》把a秘书化
 *         
 * */
public class Allapi {
	public String str;
	public String user;
	public Variable vari=new Variable();
	static Logger log = Logger.getLogger("logfile");

	public void Subscribe(Msg msg, DefaultSession session) throws Exception {
		String str="使用存储功能格式如下：\n【文字】 #自定义关键词#内容（请尽量不要涉及财务与隐私，内容需在650字以内）\n【图片】发送图片（限200张）后，系统会提醒添加关键词\n" +
				"【删除】--自定义关键词--\n【关键词列表】我的关键词\n使用小秘书功能格式如下：@主题@完整网页地址@正则匹配（参考：[\\s\\S]+）[@beg@end]\nTIPS:试试大小写单字母（a-Z） 比如 “g”？";
	      rebackText(str, msg, session);
	}
	
	/**
	 *(定时)任务：小秘书
	 * @throws JDOMException 
	 * @throws IOException 
	 */
	public void SecretaryHelperWork(Msg msg, DefaultSession session){
		String strRe=getAllInfo(msg.getFromUserName());
		if(TypeOne.isEmpty(strRe)){
			strRe="无信息";
		}
	   rebackText(strRe.length()>20?strRe.substring(0,20):strRe, msg, session);
	}
	
	public String getAllInfo(String openid){
		Dbcon db=new Dbcon();
		String strRe="";
	    List<Rule> rules=db.getTriggerUrl(openid);
		for(Rule rule:rules){
		StringBuffer strb = null;
		try {
			strb = httpRequest1(rule.getUrl(),"utf-8","");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} //TODO 功能点流程日志
		log.info("Allapi.getAllInfo:"+strb);
		String str=strb.toString();
		if(str!=null&&str.indexOf(rule.getBeg())>0)  
			str=str.split(rule.getBeg())[1].split(rule.getEnd())[0];
		//正则
		Pattern pat=Pattern.compile(rule.getRegex());
		Matcher mat=pat.matcher(str);
	   if(mat.matches()){	
		   strRe+=String.format("[%s][%s]",rule.getName(),mat.group());//rule.getName()+"\n";
	   }
	   }
		return strRe;
	}
	
	public void Near(Msg msg, DefaultSession session)
	throws IOException, JDOMException {
    Dbcon db=new Dbcon();
   str = new ChooseMsg().onMsg(msg);
    user = msg.getFromUserName();
    if(TypeOne.isEmpty(db.getInstruction(user))){
    	rebackText("请发送关键词/:?", msg, session);
    	return;
    }
    if (TypeOne.isEmpty(db.getParamLoca(user))) {
	rebackText("/::)请发送你的位置信息（点击输入框旁的“+”）：", msg, session);
	return;
}  
     Data4Item[] dm=Nearby(db.getInstruction(user),db.getParamLoca(user),"2000");
     rebackImage(dm, msg, session);
}
	public void Delins(Msg msg, DefaultSession session){ //简单回复 ‘退出’
		rebackText("[飞吻][菜单]\nb-时文 d-天气  f-英汉互译 g-周边查询",msg,session);
	
	}

	public void Translate(Msg msg, DefaultSession session)
			throws IOException, JDOMException {
		Dbcon db=new Dbcon();
		str = new ChooseMsg().onMsg(msg);
		log.info("f_str="+str);
		user = msg.getFromUserName();
		if (TypeOne.isEmpty(db.getInstruction(user))) {
			rebackText("请输入翻译内容/:ok：", msg, session);
			return;
		}
		rebackText(new Allapi().Translate(str), msg, session);
	}

	public void Train(Msg msg, DefaultSession session)
			throws IOException {
		Dbcon db=new Dbcon();
		str = new ChooseMsg().onMsg(msg);
		user = msg.getFromUserName();
		if (TypeOne.isEmpty(db.getInstruction(user))) {
			rebackText("请按格式输入出发站,到达站（如北京南，上海，11-4），日期默认为当日/:8-)", msg, session);
			return;
		}
		
			String str0 = "";
			// StringBuffer buffer=new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass()
				    .getResourceAsStream("/train1.txt"),"utf-8"));
			str0 = br.readLine();
			// log.info("str0="+str0);
			br.close();
			try {
				String str00=str0.substring(str0.indexOf(str.split("，")[0]));
				String str01=str0.substring(str0.indexOf(str.split("，")[1]));
				String str1=str00.substring(str00.indexOf("|")+1,str00.indexOf("|")+4);
				String str2=str01.substring(str01.indexOf("|")+1,str01.indexOf("|")+4);
				String str3=str.split("，").length<3?new SimpleDateFormat("MM-dd").format(new Date()):str.split("，")[2];
				rebackText(new Allapi()
						.TrainLook(str1, str2, str3), msg, session);
			} catch (Exception e) {
				rebackText("票已售罄或网络有误!", msg, session);
			}
		
	}

	public void Weather(Msg msg, DefaultSession session)
			throws UnsupportedEncodingException {
		Dbcon db=new Dbcon();
		str = new ChooseMsg().onMsg(msg);
		user = msg.getFromUserName();
		log.info(user);
		if (TypeOne.isEmpty(db.getInstruction(user))) {
			rebackText("请输入城市名/:sun", msg, session);
			return;
		}
		String str0 = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass()
				    .getResourceAsStream("/weatherCode.txt"),"utf-8"));
			try {
				do {
					str0 = br.readLine();
				} while (str0!=null&&!str0.endsWith(str) && !str0.startsWith("101340904"));
				br.close();
			} catch (IOException e) {
				log.error(e);
			}
			System.out.println(4);
			try {
				rebackText(new Weatherapi().Weather(str0.substring(0, 9)), msg,
						session);
			} catch (Exception e) {
				rebackText("抱歉，没有这个城市的数据!/::(", msg, session);
			}
	
	}

	public void Songs(Msg msg, DefaultSession session){
		Dbcon db=new Dbcon();
		str = new ChooseMsg().onMsg(msg)+"  $";
		user = msg.getFromUserName();
		if (TypeOne.isEmpty(db.getInstruction(user))) {
			rebackText("请输入歌名和歌手，如（明月几时有 王菲）/:8-)", msg, session);
			return;
		}
		try {
			String str0=new Allapi().Music(str.split(" ")[0], str.split(" ")[1]);
			if(!str0.startsWith("http://")){rebackText("抱歉，找不到这首歌！", msg, session);return;}
			rebackMusic("听轻松音乐，放松心情！",new ChooseMsg().onMsg(msg),str0,msg,session);
		} catch (Exception e) {
			log.error(e);
			rebackText("抱歉，找不到这首歌！", msg, session);
			// mm.setMusicUrl(db.getCon(str));
		}
	}

	public void News(Msg msg, DefaultSession session) throws
			IOException, JDOMException {
		Dbcon db=new Dbcon();
		str = new ChooseMsg().onMsg(msg);
		user = msg.getFromUserName();
		if (TypeOne.isEmpty(db.getInstruction(user))) {
			rebackText("/:?要搜索什么内容呢", msg, session);
			return;
		}
		ArrayList<ArrayList> array1 = new Allapi().News(str);
		Data4Item[] dm = new Data4Item[array1.size()];
		if (array1.size() > 0) {
			for (int i = 0; i < array1.size(); i++) {
				// log.info("aaaa");
				dm[i] = new Data4Item(array1.get(i).get(1).toString(), array1
						.get(i).get(1).toString(), "", array1.get(i).get(0)
						.toString());
			}
		}
		rebackImage(dm, msg, session);
	}

	public void Robot(Msg msg, DefaultSession session) {
		Dbcon db=new Dbcon();
		str =new ChooseMsg().onMsg(msg);
		user = msg.getFromUserName();
		String reback = "幸福想你了,祝你天天好心情/::),不如来看看图听听歌吧~";
		db.addInstruction(user, str);
		if (!TypeOne.isEmpty(db.getReback(user)))
			reback = db.getReback(user);
		rebackText(reback, msg, session);
	}

	public void rebackImage(Data4Item[] dm, Msg msg, DefaultSession session) {
		// Data4Item d1 = new Data4Item("Play", "take a try",
		// "http://teacher.3xy.com.cn/sxyAdminData/images/resource/jpg/200805/20080517154256287.jpg",
		// "www.qq.com");
		Dbcon db=new Dbcon(); 
		Msg4ImageText mit = new Msg4ImageText();
		mit.setFromUserName(msg.getToUserName());
		mit.setToUserName(msg.getFromUserName());
		mit.setCreateTime(msg.getCreateTime());
		for (Data4Item d : dm)
			mit.addItem(d);
		mit.setFuncFlag("0");
		session.callback(mit);
	}

	public void rebackImage(Data4Item dm, Msg msg, DefaultSession session) {
		Msg4ImageText mit = new Msg4ImageText();
		mit.setFromUserName(msg.getToUserName());
		mit.setToUserName(msg.getFromUserName());
		mit.setCreateTime(msg.getCreateTime());
		mit.addItem(dm);
		mit.setFuncFlag("0");
		session.callback(mit);
	}

	public void rebackText(String reback, Msg msg, DefaultSession session){//reback<1500字符
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Dbcon db=new Dbcon();
		Msg4Text rmsg = new Msg4Text();
		rmsg.setFromUserName(msg.getToUserName());
		rmsg.setToUserName(msg.getFromUserName());
		rmsg.setFuncFlag("0");
		rmsg.setContent(reback);
		session.callback(rmsg);
	}

	public void rebackMusic(String title,String main,String url,Msg msg,DefaultSession session){
		Dbcon db=new Dbcon();
		Msg4Music mm=new Msg4Music();
		mm.setCreateTime(msg.getCreateTime());
		mm.setDescription(main);
		mm.setFromUserName(msg.getToUserName());
		mm.setToUserName(msg.getFromUserName());
		mm.setFuncFlag("0");
	//	log.info(new allapi().Music(str.split("，")[0],str.split("，")[1]));
		mm.setHQMusicUrl(url);
		mm.setTitle(title);
		session.callback(mm);
	}
	public  Data4Item[] Nearby(String query,String location,String r) throws IOException, JDOMException{
		 if(TypeOne.isEmpty(r))r="2000";
		 String url="http://api.map.baidu.com/place/v2/search?&query=QUERY&location=LOCATION&radius=RADIUS&output=json&ak=AC39610b83a798c57469d1fc0a50cdb5";
		url=url.replace("QUERY", query).replace("LOCATION", location).replace("RADIUS", r);
		 StringBuffer buffer = httpRequest1(url, "utf-8","");  
		    JSONObject jsonObject=JSONObject.fromObject(buffer.toString());  
              JSONArray json0=jsonObject.getJSONArray("results");
              //JSONArray json1=json0.getJSONArray(key)
              String str="";
              String str1="",str2="";
              int j=1;
              String urlo=vari.URL+"/indexx.jsp?length="+j+"&query="+URLEncoder.encode(query,"utf-8");
             Data4Item dm[]=new Data4Item[(json0.size()>7?8:json0.size())+1];
            for(int i=0;i<json0.size()&&i<=7;i++){ j=i;
			 String name=json0.getJSONObject(i).getString("name");
			 String address=json0.getJSONObject(i).getString("address");
			 str+="名称："+name+"\n";
			 str+=address+"\n";
			 String json00=json0.getJSONObject(i).getJSONObject("location").getString("lat");
			 String json01=json0.getJSONObject(i).getJSONObject("location").getString("lng");
			 str1=json01+","+json00;str2+="m,B|";
			 Double x1=Double.parseDouble(json00)*Math.PI/180;
			 Double y1=Double.parseDouble(json01)*Math.PI/180;
			 Double x2=Double.parseDouble(location.split(",")[0])*Math.PI/180;
			 Double y2=Double.parseDouble(location.split(",")[1])*Math.PI/180;
			 Double d=6371004*Math.acos(Math.sin(x1)*Math.sin(x2)+Math.cos(x1)*Math.cos(x2)*Math.cos(y1-y2));
//			 d(x1,y1,x2,y2)=r*arccos(sin(x1)*sin(x2)+cos(x1)*cos(x2)*cos(y1-y2))
			 str+="方向："+(y1>y2?"东":"西")+(x1>x2?"北":"南")+"\n";
			 String distance=new DecimalFormat("0").format(d)+"米";
			 String teltphone="";
			 if(json0.getJSONObject(i).containsKey("telephone"))teltphone=json0.getJSONObject(i).getString("telephone")+"\n";
			 String str0=json0.getJSONObject(i).getString("uid");
			 // str+="详细信息：\n"+ nearbyInf(str0)+"\n";
			 String str00="&name="+URLEncoder.encode(name,"utf-8")+"&address="+URLEncoder.encode("详细信息："+ nearbyInf(str0),"utf-8")+"&query="+URLEncoder.encode(query,"utf-8")+"&location_x="+json00+"&location_y="+json01;
			  //str+="<a href='http://xiaowangzi.touclick.com/weixin/indexx.jsp?length=1"+str00+"'>查看地图</a>\n";
			  urlo+=str00;
			  dm[i+1]=new Data4Item(name+"\n"+teltphone+"地址："+address+"("+distance+")","","http://api.map.baidu.com/staticimage?center="+json01+","+json00+"&width=300&height=600&zoom=11&markers="+json01+","+json00+"&bbox=minX",vari.URL+"/indexx.jsp?length=1"+str00);
            }
		 dm[0]=new Data4Item("查看所有"+query,"","http://api.map.baidu.com/staticimage?center="+str1+"&width=300&height=200&zoom=11",urlo);
		log.info("urlo="+urlo);
		 return dm;
	 }
	   public static String nearbyInf(String str) throws IOException, JDOMException{
		   String url="http://api.map.baidu.com/place/v2/detail?uid="+str+"&ak=AC39610b83a798c57469d1fc0a50cdb5&output=json&scope=2";
		   StringBuffer buffer = httpRequest1(url, "utf-8","");  
		    JSONObject jsonObject=JSONObject.fromObject(buffer.toString());
		    JSONObject json0=jsonObject.getJSONObject("result");
		    JSONObject json=json0.getJSONObject("detail_info");
		  //  log.info("json="+json);
		    String str0="";
		   if(json.containsKey("distance")) str0+="地图距离："+json.getString("distance")+" ";
		   if(json.containsKey("type")) str0+="类别："+json.getString("type")+" ";
		   if(json.containsKey("tag")) str0+="标签："+json.getString("tag")+" ";
		   if(json.containsKey("price")) str0+="价格："+json.getString("price")+" ";
		   if(json.containsKey("shop_hours"))str0+="营业时间："+json.getString("shop_hours")+" ";
		   if(json.containsKey("overall_rating")) str0+="总评："+json.getString("overall_rating")+" ";
		   if(json.containsKey("taste_rating")) str0+="口味："+json.getString("taste_rating")+" ";
		   if(json.containsKey("service_rating")) str0+="服务："+json.getString("service_rating")+" ";
		   if(json.containsKey("environment_rating")) str0+="环境："+json.getString("environment_rating")+" ";
		   if(json.containsKey("facility_rating")) str0+="星级："+json.getString("facility_rating")+" ";
		   if(json.containsKey("hygiene_rating")) str0+="卫生："+json.getString("hygiene_rating")+" ";
		   if(json.containsKey("technology_rating")) str0+="技术："+json.getString("technology_rating")+" ";
		   if(json.containsKey("image_num")) str0+="图片数："+json.getString("image_num")+" ";
		   if(json.containsKey("groupon_num")) str0+="团购数："+json.getString("groupon_num")+" ";
		   if(json.containsKey("discount_num")) str0+="优惠数："+json.getString("discount_num")+" ";
		   if(json.containsKey("comment_num")) str0+="评论数："+json.getString("comment_num")+" ";
		   if(json.containsKey("favorite_num")) str0+="收藏数："+json.getString("favorite_num")+" ";
		   if(json.containsKey("checkin_num")) str0+="签到数："+json.getString("checkin_num")+" ";
		   return str0;
	   }
	
	public static String Music(String title, String name) throws IOException,
			JDOMException {
		String menu_create_url = "http://box.zhangmen.baidu.com/x?op=12&count=1&title=song$$name$$$$ ";
		String returnurl = "";
		String musicurl;
		if(TypeOne.isEmpty(name))
			musicurl= menu_create_url.replace("song", title).replace(
				"name$$$$", "");
		else
			musicurl= menu_create_url.replace("song", title).replace(
					"name", name);
		log.info("musicurl1="+musicurl);
		StringBuffer buffer = httpRequest(musicurl, "POST", "");
		String xmlStr = buffer.toString();
		StringReader sr = new StringReader(xmlStr);
		InputSource is = new InputSource(sr);
		Document documentObject = (new SAXBuilder()).build(is);
		if (null != documentObject) {
			Element root = documentObject.getRootElement();
			 List<Element>list=root.getChildren();
			int count=Integer.parseInt(root.getChildText("count"));
			if (count!=0) { // 有这歌
				// log.info("url="+root.getChild("url"));
				for(Element t:list){
				//Element url = root.getChild("url");
				String str = t.getChildText("encode");
				if(str==null||!str.startsWith("http://"))continue;
				returnurl = str.substring(0, str.lastIndexOf("/") + 1)
						+ t.getChildText("decode");
				if(!returnurl.endsWith(".mp3")&&!returnurl.endsWith(".wma")){returnurl = "None";continue;}
				break;
				// log.info("小路径："+url.getChildText("decode"));
				// log.info("durl="+root.getChildText("durl"));
				}
			log.info("return="+returnurl);	
			}
		} else
			returnurl = "None";
		return returnurl;
	}

	public static ArrayList News(String title) throws IOException,
			JDOMException {
		ArrayList arr = new ArrayList();
		String create_url = "http://news.baidu.com/ns?word=title&tn=newsfcu&from=news&cl=2&rn=3&ct=0";
		String newsurl = create_url.replace("title", title);
		StringBuffer stringObject = httpRequest1(newsurl, "gb2312", "");
		String firstspilt[] = stringObject.toString().split("<div");
		// log.info(firstspilt[1]);
		String secondsplit[] = firstspilt[2].split("<a href=\"");
		for (int i = 1; i < 4; i++) {
			int firTar = secondsplit[i].indexOf("\" target=\"_blank\">");
			int lasTar = secondsplit[i].lastIndexOf("\" target=\"_blank\">");
			int firA = secondsplit[i].indexOf("</a>");
			ArrayList ar = new ArrayList();
			ar.add(secondsplit[i].substring(0, firTar));
			ar.add(secondsplit[i].substring(lasTar + 18, firA) + "\n");
			arr.add(ar);
		}
		return arr;
	}

	public static String Weather(String citycode) throws IOException,
			JDOMException {
		String rebacktext = "";
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		String create_url = "http://m.weather.com.cn/data/citycode.html";
		String weaurl = create_url.replace("citycode", citycode);
		buffer = httpRequest1(weaurl, "utf-8", "");
		jsonObject = JSONObject.fromObject(buffer.toString());
		JSONObject jsonObject0 = jsonObject.getJSONObject("weatherinfo");
		rebacktext = jsonObject0.getString("city") + "天气\n"
				+ jsonObject0.getString("date_y") + " "
				+ jsonObject0.getString("week") + "\n" + "今日："
				+ jsonObject0.getString("temp1") + " "
				+ jsonObject0.getString("weather1") + " "
				+ jsonObject0.getString("wind1") + "\n" + "温馨提示："
				+ jsonObject0.getString("index_d") + "\n" + "明日："
				+ jsonObject0.getString("temp1") + " "
				+ jsonObject0.getString("weather2") + " "
				+ jsonObject0.getString("wind2") + "\n" + "后天："
				+ jsonObject0.getString("temp1") + " "
				+ jsonObject0.getString("weather3") + " "
				+ jsonObject0.getString("wind3");
		return rebacktext;
	}

	public static String Translate(String word) throws IOException,
			JDOMException {
		JSONObject jsonObject = null;
		String rebacktext = "";
		boolean flag;
		StringBuffer buffer = new StringBuffer();
		String create_url = "http://fanyi.youdao.com/openapi.do?keyfrom=weixin-app&key=767258678&type=data&doctype=json&version=1.1&q=";
		// String weaurl = create_url.replace("word", word);
		if (word.matches("[\\u4E00-\\u9FA5]+")) {
			buffer = httpRequest1(
					create_url + URLEncoder.encode(word, "utf-8"), "utf-8", "");
			flag = true;
		} else {
			buffer = httpRequest1(create_url + word, "utf-8", "");
			flag = false;
		}
		// log.info("json:"+JSONObject.fromObject(buffer.toString()));
		jsonObject = JSONObject.fromObject(buffer.toString());
		log.info("jsonO="+jsonObject);
		if (!"0".equals(jsonObject.getString("errorCode")))
			return rebacktext = "找不到这个词！";
		else {
			rebacktext = "翻译" + jsonObject.getString("translation") + "\n";
			JSONObject jsonObject0 = jsonObject.getJSONObject("basic");
			if (jsonObject0.containsKey("phonetic"))
				if (flag)
					rebacktext += "读音"
							+ new String(jsonObject0.getString("phonetic")
									.getBytes(), "gbk") + "\n";
				else
					rebacktext += "音标"
							+ new String(jsonObject0.getString("phonetic")
									.getBytes(), "utf-8") + "\n";
			log.info("rebacktext="+rebacktext);
			if (jsonObject0.containsKey("explains"))
				rebacktext += "译作" + jsonObject0.getString("explains") + "\n";
			rebacktext += "网络释义" + "\n";
			// log.info("json="+jsonObject);
			if(jsonObject.containsKey("web"))
			for (int i = 0; i < jsonObject.getJSONArray("web").size(); i++) {
				String str = jsonObject.getJSONArray("web").getString(i);
				JSONObject jsonObject1 = JSONObject.fromObject(str);
				rebacktext += jsonObject1.getString("value") + " "
						+ jsonObject1.getString("key") + "\n";
			}
		}
		return rebacktext;
	}

	public static String TrainLook(String citycode1, String citycode2,
			String looktime) throws IOException, JDOMException {
		String create_url = "http://dynamic.12306.cn/otsquery/query/queryRemanentTicketAction.do?method=queryststrainall";
		String postText = "date="+new SimpleDateFormat("yyyy").format(new Date())+"-looktime&fromstation=citycode1&tostation=citycode2&starttime=00:00--24:00";
		String newText = postText.replace("looktime", looktime).replace(
				"citycode1", citycode1).replace("citycode2", citycode2);
		log.info(newText);
		StringBuffer buffer = httpRequest(create_url, "POST", newText);
		JSONObject jsonObject = JSONObject.fromObject("{a:" + buffer.toString()
				+ "}");
		// log.info(jsonObject);
		String rebackText = "[余票情况（至多显示10班次）]\n";
		for (int i = 0; i < (jsonObject.getJSONArray("a").size() > 10 ? 10
				: jsonObject.getJSONArray("a").size()); i++) {
			String str = jsonObject.getJSONArray("a").getString(i);
			JSONObject jsonObject0 = JSONObject.fromObject(str);
			rebackText += "[" + jsonObject0.getString("id") + "]始发站("
					+ jsonObject0.getString("start_time") + ")："
					+ jsonObject0.getString("start_station_name") + "  终点站("
					+ jsonObject0.getString("end_time") + ")："
					+ jsonObject0.getString("end_station_name") + "  火车编号："
					+ jsonObject0.getString("value") + "\n";

		}
		// log.info("rebackText="+rebackText);
		return rebackText;
	}

	// xml post?
	public static StringBuffer httpRequest(String requestUrl,
			String requestMethod, String outputStr) throws IOException,
			JDOMException {
		StringBuffer buffer = new StringBuffer();
		URL geturl = new URL(requestUrl);
		HttpURLConnection httpUrlConn = (HttpURLConnection) geturl
				.openConnection();
		httpUrlConn.setDoOutput(true); // 可有可无
		httpUrlConn.setDoInput(true);
		httpUrlConn.setUseCaches(false);
		// 设置请求方式（GET/POST）
		httpUrlConn.setRequestMethod(requestMethod); // 错误405
		if ("GET".equalsIgnoreCase(requestMethod))
			httpUrlConn.connect();
		// 当有数据需要提交时
		if (null != outputStr) {
			OutputStream outputStream = httpUrlConn.getOutputStream();
			outputStream.write(outputStr.getBytes("UTF-8"));
			outputStream.close();
		}
		// 将返回的输入流转换成字符串
		InputStream inputStream = httpUrlConn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		bufferedReader.close();
		inputStreamReader.close();
		// 释放资源
		inputStream.close();
		inputStream = null;
		httpUrlConn.disconnect();

		return buffer;
	}

	// 字符流（万能） get
	public static StringBuffer httpRequest1(String requestUrl, String encod,
			String outputStr) throws IOException, JDOMException {
		StringBuffer buffer = new StringBuffer();
		// URL url = new URL(requestUrl);
		URL getUrl = new URL(requestUrl);
		// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
		// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();
		// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
		// 服务器
		connection.connect();
		// 取得输入流，并使用Reader读取
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), encod));
		String lines;
		while ((lines = reader.readLine()) != null) {
			buffer.append(lines);
		}
		reader.close();
		// 断开连接
		connection.disconnect();
		return buffer;
	}

}
