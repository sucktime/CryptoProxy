package com.nerbit.CryptoProxy.server.message;

import java.nio.channels.SocketChannel;

import com.nerbit.CryptoProxy.auth.DefaultIdentity;

public class AuthRequest extends AbstractRequest{
	private String[] parameters;
	
	public AuthRequest(SocketChannel ch, String name, String password) {
		super(ch, RequestType.OP_AUTH);
		this.parameters = new String[]{name, password};
	}

	@Override
	public boolean affirm() {
		
		return true;
	}

	public boolean auth(){
		return parameters[0].equals(DefaultIdentity.instance.getId()) &&
				parameters[1].equals(DefaultIdentity.instance.getPassword());
	}
	
}
