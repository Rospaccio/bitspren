package org.merka.bitspren.type;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

public class SymbolTableEntry
{
	private ParseTree parseTree;
	private SemanticType type;
	private Object value;
	
	public Object getValue()
	{
		return value;
	}

	public ParseTree getParseTree()
	{
		return parseTree;
	}

	public SemanticType getType()
	{
		return type;
	}

	public SymbolTableEntry(ParserRuleContext parseTree, SemanticType type){
		this(parseTree, type, null);
	}
	
	public SymbolTableEntry(ParserRuleContext parseTree, SemanticType type, Object value){
		this.parseTree = parseTree;
		this.type = type;
		this.value = value;
	}
}
