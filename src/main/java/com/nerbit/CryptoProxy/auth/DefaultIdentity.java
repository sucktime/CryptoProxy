package com.nerbit.CryptoProxy.auth;

public class DefaultIdentity extends Identity{
	
	public static final Identity instance = new DefaultIdentity();

	private DefaultIdentity() {
		
		super("jgf", "123456");
		
	}
	
}
