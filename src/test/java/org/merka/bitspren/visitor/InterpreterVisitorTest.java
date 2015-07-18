package org.merka.bitspren.visitor;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.Test;
import org.merka.bitspren.BitsprenErrorListener;
import org.merka.bitspren.BitsprenParser;
import org.merka.bitspren.BitsprenParser.BasicFunctionContext;
import org.merka.bitspren.BitsprenParser.FunctionContext;
import org.merka.bitspren.BitsprenParser.IndependentVariableContext;
import org.merka.bitspren.BitsprenParser.ProgramContext;
import org.merka.bitspren.type.SymbolTable;
import org.merka.bitspren.util.BitsprenUtils;

public class InterpreterVisitorTest
{
	
	public static final String singleLineTestProgram = "function = x^2";

	@Test
	public void testInterpretSingleLineProgram() throws IOException
	{
		BitsprenErrorListener listener = new BitsprenErrorListener();
		StringReader reader = new StringReader(singleLineTestProgram);
		BitsprenParser parser = BitsprenUtils.defaultParser(reader, listener);
		
		ProgramContext rootNode = parser.program();
		assertFalse(listener.isFail());
		
		InterpreterVisitor interpreter = new InterpreterVisitor();
		rootNode.accept(interpreter);
		
		SymbolTable table = interpreter.getSymbolTable();
		assertNotNull(table);
		assertNotNull(table.lookup("function"));
		ParserRuleContext subtree = table.lookup("function");
		assertTrue(subtree instanceof FunctionContext);
		
		FunctionContext function = (FunctionContext)subtree;
		IndependentVariableContext variable = (IndependentVariableContext) function.polinomy().getChild(0).getChild(0).getChild(0);
		assertTrue(variable.getText().equals("x"));
		BasicFunctionContext basic = (BasicFunctionContext) function.polinomy().getChild(2).getChild(0);
		assertTrue(basic.getText().equals("2"));
	}
}
