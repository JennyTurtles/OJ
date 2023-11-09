package edu.dhu.global.service;

import java.util.Set;

public interface RedisServiceI {
	
	public void del(byte [] key);
	public void del(String key);
	public void set(byte [] key,byte [] value,int liveTime);
	public void set(String key,String value,int liveTime);
	public void set(String key,String value);
	public void set(byte [] key,byte [] value);
	public String get(String key);
	public byte[] get(byte [] key);
	public Set<String> keys(String pattern);
	public boolean exists(String key);
	public String flushDB();
	public long dbSize();
	public String ping();
	public boolean lock(String key, int expire);
	public void unLock(String key);
}
