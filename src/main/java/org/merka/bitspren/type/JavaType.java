package org.merka.bitspren.type;

public class JavaType extends SemanticType
{	
	Class<?> runtimeType;
	
	public Class<?> getRuntimeType()
	{
		return runtimeType;
	}
	
	public JavaType(Class<?> type)
	{
		this.runtimeType = type;
	}
}
