package org.merka.bitspren.type;

public class EvaluationOutcome
{
	private Object value;
	private SemanticType type;
	public Object getValue()
	{
		return value;
	}
	public SemanticType getType()
	{
		return type;
	}
	public EvaluationOutcome(Object value, SemanticType type)
	{
		super();
		this.value = value;
		this.type = type;
	}

	
}
