package org.merka.bitspren.type;

import java.util.List;

public class Function extends SemanticType
{
	private List<SemanticType> formalParameters;

	public List<SemanticType> getFormalParameters()
	{
		return formalParameters;
	}

	public void setFormalParameters(List<SemanticType> formalParameters)
	{
		this.formalParameters = formalParameters;
	} 
}
