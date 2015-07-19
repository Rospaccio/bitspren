package org.merka.bitspren.exception;

public class EvaluationException extends RuntimeException
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3663180280311540501L;

	public EvaluationException()
	{
		super();
	}

	public EvaluationException(String message, Throwable cause)
	{
		super(message, cause);
		
	}

	public EvaluationException(String message)
	{
		super(message);
		
	}

	public EvaluationException(Throwable cause)
	{
		super(cause);
		
	}
	
}
