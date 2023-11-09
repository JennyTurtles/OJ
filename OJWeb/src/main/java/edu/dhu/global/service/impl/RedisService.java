package edu.dhu.global.service.impl;

import edu.dhu.global.service.RedisServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.util.Set;

/**
 * 封装redis缓存服务器服务接口
 * @author fgz
 *
 * 2018-5-8 16:43:20
 */
@Service("redisService")
public class RedisService implements RedisServiceI {
	/**
     * 通过key删除（字节）
     * @param key
     */
    public void del(byte [] key){
        this.getJedis().del(key);
    }
    /**
     * 通过key删除
     * @param key
     */
    public void del(String key){
        this.getJedis().del(key);
    }

    /**
     * 添加key value 并且设置存活时间(byte)
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(byte [] key,byte [] value,int liveTime){
        this.set(key, value);
        this.getJedis().expire(key, liveTime);
    }
    /**
     * 添加key value 并且设置存活时间
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(String key,String value,int liveTime){
        this.set(key, value);
        this.getJedis().expire(key, liveTime);
    }
    /**
     * 添加key value
     * @param key
     * @param value
     */
    public void set(String key,String value){
    	try {
			this.set(key.getBytes(), value.getBytes("GB2312"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**添加key value (字节)(序列化)
     * @param key
     * @param value
     */
    public void set(byte [] key,byte [] value){
        this.getJedis().set(key, value);
    }
    /**
     * 获取redis value (String)
     * @param key
     * @return
     */
    public String get(String key){
        String value = this.getJedis().get(key);
        return value;
    }
    /**
     * 获取redis value (byte [] )(反序列化)
     * @param key
     * @return
     */
    public byte[] get(byte [] key){
        return this.getJedis().get(key);
    }

    /**
     * 通过正则匹配keys
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern){
        return this.getJedis().keys(pattern);
    }

    /**
     * 检查key是否已经存在
     * @param key
     * @return
     */
    public boolean exists(String key){
        return this.getJedis().exists(key);
    }
    /**
     * 清空redis 所有数据
     * @return
     */
    public String flushDB(){
        return this.getJedis().flushDB();
    }
    /**
     * 查看redis里有多少数据
     */
    public long dbSize(){
        return this.getJedis().dbSize();
    }
    /**
     * 检查是否连接成功
     * @return
     */
    public String ping(){
        return this.getJedis().ping();
    }

    // 操作redis客户端
    public static Jedis jedis=null;
    /**
     * 获取一个jedis 客户端
     * @return
     */
    public Jedis getJedis(){
        if(jedis == null){
        	jedis = jedisConnectionFactory.getShardInfo().createResource();
        }
        return jedis;
    }
    @Autowired
    @Qualifier("jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory;
	@Override
	public boolean lock(String key, int expire) {
		int pexpire=expire*1000;
		long value = System.currentTimeMillis() + pexpire;
        long status = this.getJedis().setnx(key, String.valueOf(value));
        this.getJedis().expire(key, expire);
        if(status == 1) {
            return true;
        }
        long oldExpireTime = Long.parseLong(this.getJedis().get(key));
        if(oldExpireTime < System.currentTimeMillis()) {
            //超时
            long newExpireTime = System.currentTimeMillis() + pexpire;
            long currentExpireTime = Long.parseLong(this.getJedis().getSet(key, String.valueOf(newExpireTime)));
            this.getJedis().expire(key, expire);
            if(currentExpireTime == oldExpireTime) {
                return true;
            }
        }
        return false;

	}
	@Override
	public void unLock(String key) {
//		long oldExpireTime = Long.parseLong(this.getJedis().get(key));
//        if(oldExpireTime > System.currentTimeMillis()) {
//        	this.getJedis().del(key);
//        }

		if(key!=null){
			this.getJedis().del(key);
		}
	}

}
