package pubvari;

import java.util.HashMap;
import java.util.Map;

import DbXml.HelperurlArr;
import DbXml.KeywordArr;
import DbXml.OpenidwidArr;
import DbXml.PicmusrebackArr;
import DbXml.RekeywordArr;
import DbXml.VirecordArr;


public  class Variable {
	//服务器域名
	public String URL="http://1.skyofcity.applinzi.com";
	//资源路径
	   //image
	public String source_pic_url="http://skyofcity-image.stor.sinaapp.com/";
	  //video
	public String source_video_url="http://skyofcity-video.stor.sinaapp.com/";
	
	//代发邮箱
    public static String email_name="wonderfeng12@163.com";
    public static String email_pass="987654321bb";
    //收件邮箱
    public static String emial_toname="vwzf@sina.com";
		
	//微信公众账号
	public String weixin_name="天空之城";
	public static String appName="skyofcity";
	     
	       //mysql数据库
	       public String database="app_skyofcity"; 
	       public String jdbc="jdbc:mysql://w.rdc.sae.sina.com.cn";
	       public String port="3307";
	       public String username="lx2mzkywwn";
	       public String password="xhij4mzy3ij3k13z5mwylw222mjx4000yhmm1425";
	       
	       //sae信息
	       public static String accessKey="1mhurd7YWtNkqu4EI9i4";
	       public static String secretKey="7ae25aa8a4c6fff6bfee5d40771855dadc96a7c8";
	       
	       //数据库缓存
	       public static HelperurlArr dbHelperList;
	       public static KeywordArr    dbKeywordList;
	       public static OpenidwidArr dbOpenidwidList;
	       public static PicmusrebackArr dbPicmusList;
	       public static RekeywordArr dbRekeywordList;
	       public static VirecordArr   dbVirecordList;
	       public static Map mapDbList=new HashMap<String, Object>();
	   
	     //预报
	   	public static Map<String,String> map_weather_f=new HashMap<String, String>();
	   	     //天气对应
	   	     public static String[][] array_weather_sunny;
	   	//时况
	   	public static Map<String,String> map_weather_l=new HashMap<String, String>();
	   	     //风向风力
	   		public static String[] array_weather_wind=new String[]{
	   			"无持续风向","东北风","东风","东南风","南风","西南风","西风","西北风","北风","旋转风"};
	   		public static String[] array_weather_mh=new String[]{
	   			"微风","3-4级","4-5级","5-6级","6-7级","7-8级","8-9级","9-10级","10-11级","11-12级"};
	   	
	   	static{
	   	 System.setProperty ("jsse.enableSNIExtension", "false");
	   		
	   		map_weather_f.put("fa", "昼气");
	   		map_weather_f.put("fb", "夜气");
	   		map_weather_f.put("fc", "昼温");
	   		map_weather_f.put("fd", "夜温");
	   		map_weather_f.put("fe", "昼风向");
	   		map_weather_f.put("ff", "夜风向");
	   		map_weather_f.put("fg", "昼风力");
	   		map_weather_f.put("fh", "夜风力");
	   		map_weather_f.put("fi", "日出|日落");
	   		
	   		array_weather_sunny=new String[][]{
	   				{"晴","多云","阴","阵雨","雷阵雨","雷阵雨伴有冰雹","雨夹雪","小雨","中雨","大雨"},
	   				{"暴雨","大暴雨","特大暴雨","阵雪","小雪","中雪","大雪","暴雪","雾","冻雨"},
	   				{"沙尘暴","小到中雨","中到大雨","大到暴雨","暴雨到大暴雨","大暴雨到特大暴雨","小到中雪","中到大雪","大到暴雪","浮尘"},
	   				{"扬沙","强沙尘暴","","","","","","","",""},
	   				{"","","","","","","","","",""},
	   				{"","","","霾","","","","","",""},
	   				{"","","","","","","","","",""},
	   				{"","","","","","","","","",""},
	   				{"","","","","","","","","",""},
	   				{"","","","","","","","","","无"}};
	   		
	   		map_weather_l.put("l1", "当前温度");
	   		map_weather_l.put("l2", "当前湿度");
	   		map_weather_l.put("l3", "当前风力");
	   		map_weather_l.put("l4", "当前风向");
	   		map_weather_l.put("l5", "");
	   		map_weather_l.put("l6", "");
	   		map_weather_l.put("l7", "实况发布时间");


	   		
	   		
	   		
	   	}

	   	public static void setDb(){
	   		mapDbList.put("HelperurlArr", dbHelperList);
	   		mapDbList.put("KeywordArr", dbKeywordList);
	   		mapDbList.put("OpenidwidArr", dbOpenidwidList);
	   		mapDbList.put("PicmusrebackArr", dbPicmusList);
	   		mapDbList.put("RekeywordArr", dbRekeywordList);
	   		mapDbList.put("VirecordArr", dbVirecordList);
	   	}
	       

}
