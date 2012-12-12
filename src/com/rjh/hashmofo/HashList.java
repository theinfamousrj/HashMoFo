package com.rjh.hashmofo;

import java.util.HashMap;

public class HashList {

	HashMap<String, String> theMap;
	
	public HashList() {
		theMap = new HashMap();
	}
	
	public void addItem(String key, String value) {
		this.theMap.put(key, value);
	}
	
	public boolean checkExistence(String key) {
		if(this.theMap.containsKey(key)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getItemValue(String key) {
		if(checkExistence(key)) {
			return this.theMap.get(key);
		} else {
			return null;
		}
	}
	
}
