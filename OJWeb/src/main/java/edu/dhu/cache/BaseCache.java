package edu.dhu.cache;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class BaseCache {
	// 过期时间(单位为秒);
	private int refreshPeriod;
	// 关键字前缀字符;
	private String keyPrefix;
	private static final long serialVersionUID = -4397192926052141162L;
	
	private MemcachedClientBuilder builder;
	private MemcachedClient client;

	public BaseCache(String keyPrefix, int refreshPeriod) throws FileNotFoundException {
		super();
		Properties prop = new Properties();
//		InputStream in = BaseCache.class
//				.getResourceAsStream("cache.properties");
		FileInputStream in = new FileInputStream("/Users/gongrunze/OJ2/OJWeb/src/main/java/edu/dhu/cache/cache.properties");
		try {
			prop.load(in);
			String addr = prop.getProperty("address").trim();
			this.builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(addr));  
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			this.client = this.builder.build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.keyPrefix = keyPrefix;
		this.refreshPeriod = refreshPeriod;
	}

	public BaseCache() {
	};

	// 添加被缓存的对象;
	public void put(String key, Object value) {
		if(value == null) {
			return;
		}
		try {
			this.client.set(this.keyPrefix + "_" + key, refreshPeriod , value);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 删除被缓存的对象;
	public void remove(String key) {
		try {
			this.client.delete(this.keyPrefix + "_" + key);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 删除所有被缓存的对象;
	public void removeAll(Date date) {
		try {
			this.client.flushAll();
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeAll() {
		try {
			this.client.flushAll();
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 获取被缓存的对象;
	public Object get(String key) throws Exception {
		Object result = this.client.get(this.keyPrefix + "_" + key, this.refreshPeriod);
		System.out.println(result);
		if (result == null) {
			throw new Exception();
		}
		return result;
	}
	
	public void cancelUpdate(String key) {
		return;
	}
}
