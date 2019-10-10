package com.fh.service.system.redis;

import com.fh.util.Logger;
import com.fh.util.ProtobuffSerializationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

@Service("redisService")
public class RedisService {
	protected Logger log = Logger.getLogger(this.getClass());
	@Autowired
    private ShardedJedisPool shardedJedisPool;  
      
    /** 
     * @param key 
     * @param value 
     * @return 
     * 设置单个值 
     */  
    public String set(String key,Object value){  
        String result = null;  
        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
        if(shardedJedis == null){  
            return result;  
        }  
        try {  
            result = shardedJedis.set(key,new String(ProtobuffSerializationUtil.serialize(value),"ISO-8859-1"));  
        } catch (Exception e) {  
            log.error(e.getMessage(),e);  
        } finally{  
            shardedJedis.close();  
        }  
        return result;  
    }  
    /** 
     * @param key 
     * @param value 
     * @return 
     * 设置List 
     */  
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    public String setList(String key,List value){  
        String result = null;  
        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
        if(shardedJedis == null){  
            return result;  
        }  
        try {  
            result = shardedJedis.set(key,new String(ProtobuffSerializationUtil.serializeList(value),"ISO-8859-1"));  
        } catch (Exception e) {  
            log.error(e.getMessage(),e);  
        } finally{  
            shardedJedis.close();  
        }  
        return result;  
    }     
    /** 
     * @param key 
     * @param value 
     * @return 
     * 设置单个值和有效时间 
     */  
    public String set(String key,Object value,int seconds){  
        String result = null;  
        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
        if(shardedJedis == null){  
            return result;  
        }  
        try {  
            result = shardedJedis.set(key,new String(ProtobuffSerializationUtil.serialize(value),"ISO-8859-1"));  
            expire(key, seconds);  
        } catch (Exception e) {  
            log.error(e.getMessage(),e);  
        } finally{  
            shardedJedis.close();  
        }  
        return result;  
    }     
    /** 
     * @param key 
     * @param value 
     * @return 
     * 设置List和有效时间 
     */  
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    public String setList(String key,List value,int seconds){  
        String result = null;  
        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
        if(shardedJedis == null){  
            return result;  
        }  
        try {  
            result = shardedJedis.set(key,new String(ProtobuffSerializationUtil.serializeList(value),"ISO-8859-1"));  
            expire(key, seconds);  
        } catch (Exception e) {  
            log.error(e.getMessage(),e);  
        } finally{  
            shardedJedis.close();  
        }  
        return result;  
    }     
    /** 
     * @param key 
     * @param value 
     * @return 
     * 获取单个值 
     */  
    public <T> T get(String key,Class<T> clazz){  
        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
        if(shardedJedis == null){  
            return null;  
        }  
        try {  
            String resultStr = shardedJedis.get(key);  
            if(StringUtils.isEmpty(resultStr))  
                return null;  
            return ProtobuffSerializationUtil.deserialize(resultStr.getBytes("ISO-8859-1"), clazz);  
        } catch (Exception e) {  
            log.error(e.getMessage(),e);  
            e.printStackTrace();  
        } finally{  
            shardedJedis.close();  
        }  
        return null;  
    }     
    /** 
     * @param key 
     * @param value 
     * @return 
     * 获取List 
     */  
    public <T> List<T> getList(String key,Class<T> clazz){  
        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
        if(shardedJedis == null){  
            return null;  
        }  
        try {  
            String resultStr = shardedJedis.get(key);  
            if(StringUtils.isEmpty(resultStr))  
                return null;  
            return ProtobuffSerializationUtil.deserializeList(resultStr.getBytes("ISO-8859-1"), clazz);  
        } catch (Exception e) {  
            log.error(e.getMessage(),e);  
            e.printStackTrace();  
        } finally{  
            shardedJedis.close();  
        }  
        return null;  
    }     
    /** 
     * @param key 
     * @param value 
     * @return 
     * 判断key是否存在 
     */  
    public Boolean exists(String key){  
        Boolean result = false;  
        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
        if(shardedJedis == null){  
            return result;  
        }  
        try {  
            result = shardedJedis.exists(key);  
        } catch (Exception e) {  
            log.error(e.getMessage(),e);  
        } finally{  
            shardedJedis.close();  
        }  
        return result;  
    }  
    /** 
     * @param key 
     * @param value 
     * @return 
     * 设置key的过期时间段 
     */  
    public Long expire(String key,int seconds){  
        Long result = null;  
        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
        if(shardedJedis == null){  
            return result;  
        }  
        try {  
            result = shardedJedis.expire(key, seconds);  
        } catch (Exception e) {  
            log.error(e.getMessage(),e);  
        } finally{  
            shardedJedis.close();  
        }  
        return result;  
    }     
    /** 
     * @param key 
     * @param value 
     * @return 
     * 设置key的过期时间点 
     */  
    public Long expire(String key,long unixTime){  
        Long result = null;  
        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
        if(shardedJedis == null){  
            return result;  
        }  
        try {  
            result = shardedJedis.expireAt(key, unixTime);  
        } catch (Exception e) {  
            log.error(e.getMessage(),e);  
        } finally{  
            shardedJedis.close();  
        }  
        return result;  
    }
    
    /**
     * 删除
     * @param key
     */
    public void del(String key){
    	ShardedJedis shardedJedis = shardedJedisPool.getResource();  
    	try {
			if(key != null){
				 shardedJedis.del(key);
			}
		} catch (Exception e) {
			  log.error(e.getMessage(),e);  
		} finally{  
            shardedJedis.close();  
        }  
    }
    
    
    /** 
     * @param key 
     * @param value 
     * @return 
     * 设置LIST
     */  
    public Long lPush(String key,Object value){  
        Long result = null;  
        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
        if(shardedJedis == null){  
            return result;  
        }  
        try {  
            result = shardedJedis.lpush(key,new String(ProtobuffSerializationUtil.serialize(value),"ISO-8859-1"));  
        } catch (Exception e) {  
            log.error(e.getMessage(),e);  
        } finally{  
            shardedJedis.close();  
        }  
        return result;  
    }
    
    /** 
     * @param key 
     * @param value 
     * @return 
     * 删除list第一个元素
     */  
    public void blpop(String key){  
        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
        try {  
            shardedJedis.blpop(key);  
        } catch (Exception e) {  
            log.error(e.getMessage(),e);  
        } finally{  
            shardedJedis.close();  
        }  
    }
    
    /** 
     * @param key 
     * @param value 
     * @return 
     * 获取list长度
     */  
    public Long llen(String key){  
    	Long len = null;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();  
        try {  
        	len = shardedJedis.llen(key);
        } catch (Exception e) {  
            log.error(e.getMessage(),e);  
        } finally{  
            shardedJedis.close();  
        }  
        return len;
    }
    
    /** 
     * @param key 
     * @param start 开始下标
     * * @param end 结束下标
     * @return 
     * 获取list列表
     */  
    public  <T> List<T> lrange(String key,Long start,Long end,Class<T> clas){  
    	List<String> len = null;
    	List<T> list = null;
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        try {  
        	len = shardedJedis.lrange(key, start, end);
        	if(len!=null){
        		int size = len.size();
        		if(size>0){
        			list = new ArrayList<T>();
        			for (int i = 0; i < size; i++) {
        				String str = len.get(i);
        				list.add(ProtobuffSerializationUtil.deserialize(str.getBytes("ISO-8859-1"), clas));  
        			}
        		}
        	}
        } catch (Exception e) {  
            log.error(e.getMessage(),e);  
        } finally{  
            shardedJedis.close();  
        }  
        return list;
    }
}
