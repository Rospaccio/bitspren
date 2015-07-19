package org.merka.bitspren.visitor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import org.merka.bitspren.BitsprenErrorListener;
import org.merka.bitspren.BitsprenParser;
import org.merka.bitspren.BitsprenParser.BasicFunctionContext;
import org.merka.bitspren.BitsprenParser.FunctionContext;
import org.merka.bitspren.BitsprenParser.IndependentVariableContext;
import org.merka.bitspren.BitsprenParser.ProgramContext;
import org.merka.bitspren.type.EvaluationOutcome;
import org.merka.bitspren.type.SymbolTable;
import org.merka.bitspren.type.UndefinedType;
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
		EvaluationOutcome result = interpreter.visit(rootNode);
		
		assertNotNull(result);
		assertTrue(result.getType() instanceof UndefinedType);
		
		SymbolTable table = interpreter.getSymbolTable();
		assertNotNull(table);
		assertNotNull(table.lookup("function"));
		ParseTree subtree = table.lookup("function").getParseTree();
		assertTrue(subtree instanceof FunctionContext);
		
		FunctionContext function = (FunctionContext)subtree;
		IndependentVariableContext variable = (IndependentVariableContext) function.polinomy().getChild(0).getChild(0).getChild(0);
		assertTrue(variable.getText().equals("x"));
		BasicFunctionContext basic = (BasicFunctionContext) function.polinomy().getChild(2).getChild(0);
		assertTrue(basic.getText().equals("2"));
	}
	
	@Test
	public void TestEval() throws IOException
	{
		EvaluationOutcome outcome = BitsprenUtils.eval("function = 3 + x; x = 2; function(x);");
		assertNotNull(outcome);
		assertNotNull(outcome.getValue());
		assertFalse(outcome.getType() == UndefinedType.instance());
		assertTrue(outcome.getValue() instanceof Double);
		assertEquals(5.0, (double)outcome.getValue(), 0.00000001);
	}
}
