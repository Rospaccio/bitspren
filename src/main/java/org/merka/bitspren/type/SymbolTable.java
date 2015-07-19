package org.merka.bitspren.type;

import java.util.Hashtable;

import org.antlr.v4.runtime.ParserRuleContext;

public class SymbolTable
{
	private Hashtable<String, SymbolTableEntry> table;

	public SymbolTable()
	{
		this.table = new Hashtable<>();
	}
	
	public SymbolTableEntry lookup(String identifier)
	{
		return table.get(identifier);
	}

	public void bind(String identifier, SymbolTableEntry entry)
	{
		table.put(identifier, entry);
	}
	
	public boolean isBound(String identifier){
		return table.containsKey(identifier);
	}
	
	public SemanticType peekType(String identifier)
	{
		return table.get(identifier).getType();
	}
}
