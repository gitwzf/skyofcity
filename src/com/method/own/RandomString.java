package com.method.own;

import java.util.ArrayList;
/**
 * 功能
 * 返回随机字符串
 * */
public class RandomString {
	/**@param length 返回的随机字符长度
	 * @param number  是否包含数字
	 *@param lowercase 是否包含小写英文字母
	 *@param  uppercase 是否包含大写英文字母
	 * @param  specialchar 是否包含特殊字符
	 *@param   exclude   需排除的字符
	 *@return 随机字符串
	 * */
	public String getRandomString(int length,boolean number,boolean lowercase,boolean uppercase,boolean specialchar,String exclude){
		if(length==0)return "";
		ArrayList<Character> array=new ArrayList<Character>();
		if(!specialchar){//特殊字符
			for(int i=48;i<=57;i++)
				array.add((char)i);
			for(int i=97;i<=122;i++)
				array.add((char)i);
			for(int i=65;i<=90;i++)
				array.add((char)i);
		}else
		for(int i=33;i<=126;i++)
			array.add((char)i);
		
		if(!number){
			for(int i=48;i<=57;i++)
				array.remove((Character)(char)i);
		}
		if(!lowercase){
			for(int i=97;i<=122;i++)
				array.remove((Character)(char)i);
		}
		if(!uppercase){
			for(int i=65;i<=90;i++)
				array.remove((Character)(char)i);
		}
		
		if(exclude!=null)
			for(int i=0;i<exclude.length();i++)
				array.remove((Character)exclude.charAt(i));
		
		
		StringBuffer str=new StringBuffer();
		for(int i=0;i<length;i++){
			int m=(int)(Math.random()*array.size());
			str.append(array.get(m));
		}
		return str.toString();
	}

}
