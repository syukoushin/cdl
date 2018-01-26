package com.ibm.core.service.impl;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.ibm.core.service.CacheFacade;

public class CacheFacadeImpl implements CacheFacade {
	
	private static CacheFacadeImpl instance=null;
	
	private static CacheManager cacheManager=null;
	
	private static String DEFAULT_CACHE="BaseCatch";
	
	public static CacheFacadeImpl getInstance(){
//		System.out.println("------CacheFacadeImpl   getInstance    begin---------");
		if(instance==null){
			instance=new CacheFacadeImpl();
			instance.setCacheManager(CacheManager.create());
//			System.out.println("------init  CacheFacadeImpl---------");
		}
		return instance;
	}
	
	public Element getElementByKey(String Key, String cacheName) {
		Cache cache=cacheManager.getCache(cacheName);
		if(cache.isKeyInCache(Key)){
			return cache.get(Key);
		}
		return null;
	}

	public Element getElementByKey(String key) {
//		System.out.println("------getElementByKey    begin---------");
		Cache cache=cacheManager.getCache(DEFAULT_CACHE);
		if(cache.isKeyInCache(key)){
//			System.out.println("------chache  have the key  ---------"+key);
			return cache.get(key);
		}
//		System.out.println("------chache did not   have the key  ---------"+key);
		return null;
	}

	public void removeElement(String key, String cacheName) {
	}

	public void removeElement(String key) {
//		System.out.println("------removeElement    begin---------");
		Cache cache=cacheManager.getCache(DEFAULT_CACHE);
		if(cache.isKeyInCache(key)){
//			System.out.println("------removeElement the key  ---------"+key);
			cache.remove(key);
		}
//		System.out.println("------removeElement  end---------");
	}



	public void setElement(String key, Object obj, String cacheName) {
		
		Cache cache=cacheManager.getCache(cacheName);
		Element element = new Element(key, obj);
		cache.put(element);
	}



	public void setElement(String key, Object obj) {
//		System.out.println("------setElement    begin---------");
		Cache cache=cacheManager.getCache(DEFAULT_CACHE);
		Element element = new Element(key, obj);
		cache.put(element);
//		System.out.println("------setElement    end---------");
	}



	public static void setCacheManager(CacheManager cacheManager) {
		CacheFacadeImpl.cacheManager = cacheManager;
	}
	
	

}
