package org.merka.bitspren.runtime;

public class ExecutionOutcome
{
	private int exitCode;
	
	public static final int OK_CODE = 0;

	public int getExitCode()
	{
		return exitCode;
	}

	private void setExitCode(int exitCode)
	{
		this.exitCode = exitCode;
	}
	
	public ExecutionOutcome(){
		this(OK_CODE);
	}
	
	public ExecutionOutcome(int exitCode){
		setExitCode(exitCode);
	}
}
