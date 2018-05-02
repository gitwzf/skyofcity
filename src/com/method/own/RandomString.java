package com.method.own;

import java.util.ArrayList;
/**
 * ����
 * ��������ַ���
 * */
public class RandomString {
	/**@param length ���ص�����ַ�����
	 * @param number  �Ƿ��������
	 *@param lowercase �Ƿ����СдӢ����ĸ
	 *@param  uppercase �Ƿ������дӢ����ĸ
	 * @param  specialchar �Ƿ���������ַ�
	 *@param   exclude   ���ų����ַ�
	 *@return ����ַ���
	 * */
	public String getRandomString(int length,boolean number,boolean lowercase,boolean uppercase,boolean specialchar,String exclude){
		if(length==0)return "";
		ArrayList<Character> array=new ArrayList<Character>();
		if(!specialchar){//�����ַ�
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
