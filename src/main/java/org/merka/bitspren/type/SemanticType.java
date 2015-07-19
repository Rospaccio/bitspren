package org.merka.bitspren.type;


public abstract class SemanticType
{	
	//private static Map<Class<?>, ParseTree> instancesPool = new HashMap<Class<?>, ParseTree>();
	
	private String name;
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
//	public static synchronized <T extends SemanticType> T instance(T type)
//	{
//		SemanticType pooledObject = instancesPool.get(type); 
//		if(pooledObject == null){
//			pooledObject = Class<;
//		}
//		return (T) pooledObject;
//	}
}
