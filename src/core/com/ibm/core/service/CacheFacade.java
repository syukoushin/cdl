package com.ibm.core.service;

import net.sf.ehcache.Element;

public interface CacheFacade {
		
	public Element getElementByKey(String Key);
	
	public Element getElementByKey(String Key,String cacheName);
	
	public void setElement(String key,Object obj);
	
	public void setElement(String key,Object obj,String cacheName);
	
	public void removeElement(String key);
	
	public void removeElement(String key,String cacheName);

}
