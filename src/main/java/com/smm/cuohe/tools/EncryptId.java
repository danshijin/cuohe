package com.smm.cuohe.tools;

public class EncryptId {
	
	private int multiKey = 31;
	
	private int addKey = 17;
	
	public String Encrytor(int id) {
		return firstToLast(Integer.toString(id * multiKey + addKey));
	}


	public int Decryptor(String enId) {
		return (Integer.parseInt(lastToFirst(enId)) - addKey) / multiKey;
	}
	
	private String firstToLast(String str){
		
		return str.substring(1) + str.charAt(0);
	}
	
	private String lastToFirst(String str){
		return str.charAt(str.length() - 1) +  str.substring(0, str.length() - 1);
	}
	public static void main(String[] args) {
		EncryptId ee = new EncryptId();
		
		System.out.println(ee.Encrytor(7));
		
	}
	
}