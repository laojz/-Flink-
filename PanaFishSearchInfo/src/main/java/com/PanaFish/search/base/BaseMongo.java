package com.PanaFish.search.base;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.PanaFish.utils.ReadProperties;

import java.util.ArrayList;
import java.util.List;

public class BaseMongo {
    protected static MongoClient mongoClient ;
		
		static {
			List<ServerAddress> addresses = new ArrayList<ServerAddress>();
			String[] addressList = ReadProperties.getKey("mongoaddr","PanaFishsearch.properties").split(",");
			String[] portList = ReadProperties.getKey("mongoport","PanaFishsearch.properties").split(",");
			for (int i = 0; i < addressList.length; i++) {
				ServerAddress address = new ServerAddress(addressList[i], Integer.parseInt(portList[i]));
				addresses.add(address);
				
			}
			mongoClient = new MongoClient(addresses);
		}
	
}
