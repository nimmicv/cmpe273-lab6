package edu.sjsu.cmpe.cache.client;


import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        CacheServiceInterface cache1 = new DistributedCacheService(
                "http://localhost:3000");
        CacheServiceInterface cache2 = new DistributedCacheService(
                "http://localhost:3001");
        CacheServiceInterface cache3 = new DistributedCacheService(
                "http://localhost:3002");
        
        List<CacheServiceInterface> servers = Lists.newArrayList(cache1,cache2,cache3);
        int x= 1;
        char c ='a';
        for(int i=1;i<=10;i++)
        {
        	int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(i)), servers.size());
        	servers.get(bucket).put(i, Character.toString(c));
        	
          System.out.println("put("+i +"=>"+ c+")");
          c++;
        }
        for(int i=1;i<=10;i++)
        {
        	int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(i)), servers.size());
        	//System.out.println(servers.get(bucket).get(i));
        	System.out.println("get("+i +") =>"+ servers.get(bucket).get(i));
        }
        

        
    }

}
