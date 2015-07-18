package org.merka.bitspren.type;

import java.util.Hashtable;

import org.antlr.v4.runtime.ParserRuleContext;

public class SymbolTable
{
	private Hashtable<String, ParserRuleContext> table;

	public SymbolTable()
	{
		this.table = new Hashtable<>();
	}
	
	public ParserRuleContext lookup(String identifier)
	{
		return table.get(identifier);
	}

	public ParserRuleContext bind(String identifier, ParserRuleContext subtree)
	{
		return table.put(identifier, subtree);
	}
}
