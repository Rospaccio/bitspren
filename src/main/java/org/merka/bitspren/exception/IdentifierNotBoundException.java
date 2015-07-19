package org.merka.bitspren.exception;

public class IdentifierNotBoundException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8831330748980679295L;
	private String identifier;
	
	public String getIdentifier(){
		return identifier;
	}
	public IdentifierNotBoundException(String identifier){
		this.identifier = identifier;
	}
	
}
