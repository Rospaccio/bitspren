package org.merka.bitspren.type;

public class UndefinedType extends SemanticType
{
	private static final UndefinedType instance = new UndefinedType();
	public static UndefinedType instance(){
		return instance;
	}
}
