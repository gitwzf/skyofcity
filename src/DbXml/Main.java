package DbXml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import pubvari.Variable;
import wxtry.Dbcon;


public class Main {
	public static void main(String[] args) throws IOException {
		InputStream f=new FileInputStream(new File("src/Dbxml/virecord.xml"));

		BufferedReader in = new BufferedReader(new InputStreamReader(f));
		   StringBuffer buffer = new StringBuffer();
		   String line = "";
		   while ((line = in.readLine()) != null){
		     buffer.append(line+"\n");
		   }
		   System.out.println(buffer.toString());
	}
	public static void read() throws UnsupportedEncodingException{
		Dbcon d=new Dbcon();
	}
	
	public static void write(){
		//helper
		ArrayList<Helper_url> arr=new ArrayList<Helper_url>();
		arr.add(new Helper_url("多点佳得乐价格有变化", "佳得乐", "sell", "[\\s\\S]+3[0-7]0[\\s\\S]+"
				, "http://gw.wx.dmall.com/ware/get?param=%7B%22skuId%22%3A%22100133585%22%2C%22venderId%22%3A%222%22%2C%22erpStoreId%22%3A%22403%22%7D&token=&source=2&tempid=C70B15E3653000026D7532C712751D3E1461508411987&_=1461509055642&callback=jsonp1"
				, "get", "oZMIwuIlpJXjizV1-jEqRk35tmQg", ""));
		Variable.dbHelperList= new HelperurlArr(arr); 
		Dbcon.WriteXmlHelper();
		//keyword
		ArrayList<Keyword> arr1=new ArrayList<Keyword>();
		Variable.dbKeywordList=new KeywordArr(arr1);
		Dbcon.WriteXmlKeyword();
		//openidwid
		ArrayList<Openid_wid> arr2=new ArrayList<Openid_wid>();
		arr2.add(new Openid_wid("oZMIwuIlpJXjizV1-jEqRk35tmQg", "vwzf@sina.com", ""));
		Variable.dbOpenidwidList=new OpenidwidArr(arr2);
		Dbcon.WriteXmlOpenidwid();
		//picmusreback
		ArrayList<Pic_mus_reback> arr3=new ArrayList<Pic_mus_reback>();
		Variable.dbPicmusList=new PicmusrebackArr(arr3);
		Dbcon.WriteXmlPicmus();
		//re_keyword
		ArrayList<Re_keyword> arr4=new ArrayList<Re_keyword>();
		arr4.add(new Re_keyword(1+"", "-1", "event", "subscribe", "1", "Subscribe", "", ""));
		arr4.add(new Re_keyword(20+"", "-1", "菜单", "a", "1", "Robot", "", ""));
		arr4.add(new Re_keyword(21+"", "-1", "菜单", "b", "1", "News", "", ""));
		arr4.add(new Re_keyword(23+"", "-1", "菜单", "c", "1", "Songs", "", ""));
		arr4.add(new Re_keyword(24+"", "-1", "菜单", "d", "1", "Weather", "", ""));
		arr4.add(new Re_keyword(26+"", "-1", "菜单", "f", "1", "Translate", "", ""));
		arr4.add(new Re_keyword(82+"", "-1", "菜单", "g", "1", "Near", "", ""));
		arr4.add(new Re_keyword(4+"", "-1", "定时任务", "helper", "1", "SecretaryHelperWork", "", ""));
		Variable.dbRekeywordList=new RekeywordArr(arr4);
		Dbcon.WriteXmlRekeyword();
		//virecord
		ArrayList<Virecord> arr5=new ArrayList<Virecord>();
		arr5.add(new Virecord(1+"", "oZMIwuIlpJXjizV1-jEqRk35tmQg", "a", 20+"", "30.211,120.149"));
		Variable.dbVirecordList=new VirecordArr(arr5);
		Dbcon.WriteXmlVirecord();
	}

}
