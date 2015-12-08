package com.nerbit.CryptoProxy.server.message;

public interface Request {
	/*
	 * to affirm that the operation {@code Request} can be done on storage,
	 * and find some conflict as soon as possible
	 */
	//boolean affirm();
	/*
	 * to check out if the Request were unactive for a long time,
	 * in this case, the Request should be removed;
	 * If another Request on this channel arrived when the channel's Request is not completed nor expired,
	 * then the fresh Request should be suspended or discarded 
	 */
	boolean isCompleted();
	void setCompleted();
	boolean expired();
	/*
	 * when a fresh interaction with client on this Request done, the Request get more surviving time
	 */
	void delayExpiration();
	
}
