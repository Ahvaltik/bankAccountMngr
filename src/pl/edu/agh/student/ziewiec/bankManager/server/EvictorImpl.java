package pl.edu.agh.student.ziewiec.bankManager.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import Ice.Current;
import Ice.Identity;
import Ice.LocalObjectHolder;
import Ice.Object;
import Ice.ServantLocator;
import Ice.UserException;

public class EvictorImpl implements ServantLocator {
	private class EvictorImplMapEntry {
		Ice.Object servant;
        java.lang.Object cookie;
        java.util.Iterator<Identity> queuePos;
        int useCount;
	}

	private int size;
	private java.util.Map<Identity,EvictorImplMapEntry> evictorMap;
	private LinkedList<Ice.Identity> identityQueue;
	
	public EvictorImpl(){
		size = 2;
		evictorMap = new HashMap<Identity,EvictorImplMapEntry>();
		identityQueue = new LinkedList<Ice.Identity>();
	}

	@Override
	public Object locate(Current curr, LocalObjectHolder cookie)
			throws UserException {
		EvictorImplMapEntry entry;
		if((entry = evictorMap.get(curr.id)) != null){
			entry.queuePos.remove();
		} else {
			entry = new EvictorImplMapEntry();
			LocalObjectHolder cookieHolder = new LocalObjectHolder();
			entry.servant = new AccountImpl(curr.id.name);
			entry.cookie = cookieHolder.value;
			evictorMap.put(curr.id, entry);
		}
		entry.useCount++;
		identityQueue.addFirst(curr.id);
		entry.queuePos = identityQueue.iterator();
		entry.queuePos.next();
		
		cookie.value = entry;
		
		return entry.servant;
	}

	@Override
	public void finished(Current curr, Object servant, java.lang.Object cookie)
			throws UserException {
		EvictorImplMapEntry entry = (EvictorImplMapEntry) cookie;
		entry.useCount--;
		evict();
	}

	@Override
	public void deactivate(String category) {
		size = 0;
		evict();
	}

	private void evict() {
		Iterator<Identity> ii = identityQueue.iterator();
		int identitiesToEvict = evictorMap.size() - size;
		for(int i = 0; i < identitiesToEvict; i++){
			Identity id = ii.next();
			EvictorImplMapEntry entry = evictorMap.get(id);
			if(entry.useCount == 0){
				entry.queuePos.remove();
				evictorMap.remove(id);
			}
		}
	}

}
