package com.nerbit.CryptoProxy.server.message;

import java.nio.channels.SocketChannel;

public abstract class AbstractRequest implements Request{
	public static long DEFAULT_LIVE_TIME       = 1000*60*5;//5 minutes
	public static long DEFAULT_LIVE_TIME_DELAY = 1000*60*3;
	private long expirationTime;
	
	/*
	public static final int OP_LS  = 1;
	public static final int OP_PUT = 2;
	public static final int OP_GET = 3;
	public static final int OP_DEL = 4;
	public static final int OP_MV  = 5;
	public static final int OP_AUTH = 0;
	*/
	private   final RequestType      type;
	private   final SocketChannel    channel;
	private   volatile boolean       completed;
	
	public AbstractRequest(SocketChannel ch, RequestType type){
		this.channel = ch;
		this.type = type;
		this.expirationTime = System.currentTimeMillis() + DEFAULT_LIVE_TIME;
		this.completed = false;
	}

	protected abstract boolean affirm();
	
	public final boolean expired() {
		return this.expirationTime > System.currentTimeMillis();
	}

	public final void delayExpiration() {
		this.expirationTime += DEFAULT_LIVE_TIME_DELAY;
	}

	public RequestType getType() {
		return type;
	}

	public SocketChannel getChannel() {
		return channel;
	}

	public final boolean isCompleted() {
		return completed;
	}

	public final void setCompleted() {
		this.completed = true;
	}	
	
}
