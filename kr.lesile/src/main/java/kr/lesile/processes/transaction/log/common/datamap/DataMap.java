package kr.lesile.processes.transaction.log.common.datamap;

import java.util.HashMap;
import java.util.Set;

public class DataMap<K,V>
{
	private HashMap<K,V> dataMap = null;
	
	
	
	
	
	public DataMap()
	{
		this.dataMap = new HashMap<K,V>();
	}
	
	
	
	
	
	public void put( K key, V value )
	{
		synchronized( this.dataMap )
		{
			this.dataMap.put(key, value);
		}
	}
	
	
	
	public V get( K key )
	{
		synchronized( this.dataMap )
		{
			return this.dataMap.get(key);
		}
	}
	
	
	
	public Set<K> keySet()
	{
		return this.dataMap.keySet();
	}
	
	
}
