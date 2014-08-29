package pl.edu.agh.student.ziewiec.bankManager.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import Ice.Current;
import Ice.Identity;
import Ice.LocalObjectHolder;
import Ice.Object;
import Ice.ServantLocator;
import Ice.UserException;

public class EvictorImpl implements ServantLocator {
	private int size;
	
	public EvictorImpl(){
		size = 2;
	}

	@Override
	public Object locate(Current curr, LocalObjectHolder cookie)
			throws UserException {
		return null;
	}

	@Override
	public void finished(Current curr, Object servant, java.lang.Object cookie)
			throws UserException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate(String category) {
		// TODO Auto-generated method stub

	}

}
