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
	//����������
	public String URL="http://1.skyofcity.applinzi.com";
	//��Դ·��
	   //image
	public String source_pic_url="http://skyofcity-image.stor.sinaapp.com/";
	  //video
	public String source_video_url="http://skyofcity-video.stor.sinaapp.com/";
	
	//��������
    public static String email_name="wonderfeng12@163.com";
    public static String email_pass="987654321bb";
    //�ռ�����
    public static String emial_toname="vwzf@sina.com";
		
	//΢�Ź����˺�
	public String weixin_name="���֮��";
	public static String appName="skyofcity";
	     
	       //mysql���ݿ�
	       public String database="app_skyofcity"; 
	       public String jdbc="jdbc:mysql://w.rdc.sae.sina.com.cn";
	       public String port="3307";
	       public String username="lx2mzkywwn";
	       public String password="xhij4mzy3ij3k13z5mwylw222mjx4000yhmm1425";
	       
	       //sae��Ϣ
	       public static String accessKey="1mhurd7YWtNkqu4EI9i4";
	       public static String secretKey="7ae25aa8a4c6fff6bfee5d40771855dadc96a7c8";
	       
	       //���ݿ⻺��
	       public static HelperurlArr dbHelperList;
	       public static KeywordArr    dbKeywordList;
	       public static OpenidwidArr dbOpenidwidList;
	       public static PicmusrebackArr dbPicmusList;
	       public static RekeywordArr dbRekeywordList;
	       public static VirecordArr   dbVirecordList;
	       public static Map mapDbList=new HashMap<String, Object>();
	   
	     //Ԥ��
	   	public static Map<String,String> map_weather_f=new HashMap<String, String>();
	   	     //������Ӧ
	   	     public static String[][] array_weather_sunny;
	   	//ʱ��
	   	public static Map<String,String> map_weather_l=new HashMap<String, String>();
	   	     //�������
	   		public static String[] array_weather_wind=new String[]{
	   			"�޳�������","������","����","���Ϸ�","�Ϸ�","���Ϸ�","����","������","����","��ת��"};
	   		public static String[] array_weather_mh=new String[]{
	   			"΢��","3-4��","4-5��","5-6��","6-7��","7-8��","8-9��","9-10��","10-11��","11-12��"};
	   	
	   	static{
	   	 System.setProperty ("jsse.enableSNIExtension", "false");
	   		
	   		map_weather_f.put("fa", "����");
	   		map_weather_f.put("fb", "ҹ��");
	   		map_weather_f.put("fc", "����");
	   		map_weather_f.put("fd", "ҹ��");
	   		map_weather_f.put("fe", "�����");
	   		map_weather_f.put("ff", "ҹ����");
	   		map_weather_f.put("fg", "�����");
	   		map_weather_f.put("fh", "ҹ����");
	   		map_weather_f.put("fi", "�ճ�|����");
	   		
	   		array_weather_sunny=new String[][]{
	   				{"��","����","��","����","������","��������б���","���ѩ","С��","����","����"},
	   				{"����","����","�ش���","��ѩ","Сѩ","��ѩ","��ѩ","��ѩ","��","����"},
	   				{"ɳ����","С������","�е�����","�󵽱���","���굽����","���굽�ش���","С����ѩ","�е���ѩ","�󵽱�ѩ","����"},
	   				{"��ɳ","ǿɳ����","","","","","","","",""},
	   				{"","","","","","","","","",""},
	   				{"","","","��","","","","","",""},
	   				{"","","","","","","","","",""},
	   				{"","","","","","","","","",""},
	   				{"","","","","","","","","",""},
	   				{"","","","","","","","","","��"}};
	   		
	   		map_weather_l.put("l1", "��ǰ�¶�");
	   		map_weather_l.put("l2", "��ǰʪ��");
	   		map_weather_l.put("l3", "��ǰ����");
	   		map_weather_l.put("l4", "��ǰ����");
	   		map_weather_l.put("l5", "");
	   		map_weather_l.put("l6", "");
	   		map_weather_l.put("l7", "ʵ������ʱ��");


	   		
	   		
	   		
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
