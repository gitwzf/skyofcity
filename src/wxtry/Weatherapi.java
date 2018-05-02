package wxtry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import pubvari.Variable;

/**
 * 根据群里Android版本稍加修改,请勿用于商业目的
 * 
 * @author 任志鹏 * @QQ 675489941 简单问题可@本人,但不一定能解决
 * 
 */
public class Weatherapi {
	Logger log=Logger.getLogger("logfile");
	private static final String MAC_NAME = "HmacSHA1";
	private static final String ENCODING = "UTF-8";
	private static final String appid = "8cc02d26bea11944";
	private static final String private_key = "5b1107_SmartWeatherAPI_27e75d7";
	private static final String url_header = "http://open.weather.com.cn/data/?";

	/**
	 * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
	 * 
	 * @param url
	 *            被签名的字符串
	 * @param privatekey
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	private static byte[] HmacSHA1Encrypt(String url, String privatekey)
			throws Exception {
		byte[] data = privatekey.getBytes(ENCODING);
		// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		// 生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance(MAC_NAME);
		// 用给定密钥初始化 Mac 对象
		mac.init(secretKey);
		byte[] text = url.getBytes(ENCODING);
		// 完成 Mac 操作
		return mac.doFinal(text);
	}

	/**
	 * 获取URL通过privatekey加密后的码
	 * 
	 * @param url
	 * @param privatekey
	 * @return
	 * @throws Exception
	 */
	private static String getKey(String url, String privatekey)
			throws Exception {
		byte[] key_bytes = HmacSHA1Encrypt(url, privatekey);
		String base64encoderStr = new String(Base64.encodeBase64(key_bytes,
				false), ENCODING);
		return URLEncoder.encode(base64encoderStr, ENCODING);
	}

	/**
	 * 获得接口的URL地址
	 * 
	 * @param areaid
	 * @param type
	 * @param date
	 * @return
	 * @throws Exception
	 */
	private  String getInterfaceURL(String areaid, String type,
			String date) {
		String keyurl = url_header + "areaid=" + areaid + "&type=" + type
				+ "&date=" + date + "&appid=";
		String key = null;
		try {
			key = getKey(keyurl + appid, private_key);
		} catch (Exception e) {
			
			log.error("getInterfaceURL error:",e);
		}
		String appid6 = appid.substring(0, 6);

		return keyurl + appid6 + "&key=" + key;
	}
    public static String returnUrl(String s){
    	String content = null;
		try {
			URL u = new URL(s);
			HttpURLConnection uConnection = (HttpURLConnection) u
					.openConnection();
			try {
				uConnection.connect();
				InputStream is = uConnection.getInputStream();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is,"utf-8"));
				StringBuilder sb = new StringBuilder();
				String lines;
				while ((lines = br.readLine()) != null) {
					sb.append(lines);
				}
				 content = new String(sb);
				System.out.println(content);
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("connect failed");
			}

		} catch (IOException e) {
			System.out.println("build failed");
			e.printStackTrace();
		}
		return content;
    }
    public static String getSunny(String key){
    	if(key==null||key.equals(""))return "";
    	int a,b;
    	a=Integer.parseInt(key)/10;
    	b=Integer.parseInt(key)%10;
    	return Variable.array_weather_sunny[a][b];
    }
    public static String getWind(String key){
    	if(key==null||key.equals(""))return "";
    	return Variable.array_weather_wind[Integer.parseInt(key)];
    	
    }
    public static String getMh(String key){
    	if(key==null||key.equals(""))return "";
    	return Variable.array_weather_mh[Integer.parseInt(key)];
    	
    }
    
    
	public String Weather(String code){
		String str="";
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String todate = dateFormat.format(date);//index  forecast3d  observe
		String jstr1=returnUrl(getInterfaceURL(code, "index", todate));
		String jstr2=returnUrl(getInterfaceURL(code, "forecast3d", todate));
		String jstr3=returnUrl(getInterfaceURL(code, "observe", todate));
		
		JSONObject json1=JSONObject.fromObject(jstr1);
		JSONObject json2=JSONObject.fromObject(jstr2);
		JSONObject json3=JSONObject.fromObject(jstr3);
		//指数
		JSONArray json11=json1.getJSONArray("i");
		for(int i=0;i<json11.size();i++){
			str=(json11.getJSONObject(i).get("i5"))+str;
		}
		str="\n提示："+str;
		//预报
		System.out.println("-------------------------");
		JSONObject json22=json2.getJSONObject("f");
		JSONArray  json222=json22.getJSONArray("f1");
		String[] day=new String[]{"今天","明天","后天"};
		String str0="";
		for(int i=0;i<json222.size();i++){
			JSONObject j=json222.getJSONObject(i);
		   
str0+=day[i]+"（昼）："+getSunny((String) j.get("fa"))+"   "+j.get("fc")+"℃   "
		+"（夜）："+getSunny((String)j.get("fb"))+"   "+j.get("fd")+"℃   "
	+"日出|日落："+j.get("fi")+"\n";
		
		}
		str="\n"+str0+str;
		System.out.println("-------------------------");
		//天气时况
		JSONObject json33=json3.getJSONObject("l");
		str=("实时情况（"+json33.get("l7")+"）： 温度"+json33.get("l1")+"℃    湿度:"+json33.get("l2")+getWind((String) json33.get("l4"))+"   "+getMh((String)json33.get("l3"))+"\n")+str;
		return str;
	}
	
}
