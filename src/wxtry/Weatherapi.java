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
 * ����Ⱥ��Android�汾�Լ��޸�,����������ҵĿ��
 * 
 * @author ��־�� * @QQ 675489941 �������@����,����һ���ܽ��
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
	 * ʹ�� HMAC-SHA1 ǩ�������Զ�encryptText����ǩ��
	 * 
	 * @param url
	 *            ��ǩ�����ַ���
	 * @param privatekey
	 *            ��Կ
	 * @return
	 * @throws Exception
	 */
	private static byte[] HmacSHA1Encrypt(String url, String privatekey)
			throws Exception {
		byte[] data = privatekey.getBytes(ENCODING);
		// ���ݸ������ֽ����鹹��һ����Կ,�ڶ�����ָ��һ����Կ�㷨������
		SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		// ����һ��ָ�� Mac �㷨 �� Mac ����
		Mac mac = Mac.getInstance(MAC_NAME);
		// �ø�����Կ��ʼ�� Mac ����
		mac.init(secretKey);
		byte[] text = url.getBytes(ENCODING);
		// ��� Mac ����
		return mac.doFinal(text);
	}

	/**
	 * ��ȡURLͨ��privatekey���ܺ����
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
	 * ��ýӿڵ�URL��ַ
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
		//ָ��
		JSONArray json11=json1.getJSONArray("i");
		for(int i=0;i<json11.size();i++){
			str=(json11.getJSONObject(i).get("i5"))+str;
		}
		str="\n��ʾ��"+str;
		//Ԥ��
		System.out.println("-------------------------");
		JSONObject json22=json2.getJSONObject("f");
		JSONArray  json222=json22.getJSONArray("f1");
		String[] day=new String[]{"����","����","����"};
		String str0="";
		for(int i=0;i<json222.size();i++){
			JSONObject j=json222.getJSONObject(i);
		   
str0+=day[i]+"���磩��"+getSunny((String) j.get("fa"))+"   "+j.get("fc")+"��   "
		+"��ҹ����"+getSunny((String)j.get("fb"))+"   "+j.get("fd")+"��   "
	+"�ճ�|���䣺"+j.get("fi")+"\n";
		
		}
		str="\n"+str0+str;
		System.out.println("-------------------------");
		//����ʱ��
		JSONObject json33=json3.getJSONObject("l");
		str=("ʵʱ�����"+json33.get("l7")+"���� �¶�"+json33.get("l1")+"��    ʪ��:"+json33.get("l2")+getWind((String) json33.get("l4"))+"   "+getMh((String)json33.get("l3"))+"\n")+str;
		return str;
	}
	
}
