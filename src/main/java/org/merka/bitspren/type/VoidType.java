package org.merka.bitspren.type;

public class VoidType extends SemanticType
{
	private static VoidType instance = new VoidType();
	
	public static VoidType instance(){
		return instance;
	}
}
