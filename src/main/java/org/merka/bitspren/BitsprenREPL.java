package org.merka.bitspren;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.antlr.v4.runtime.tree.ParseTree;
import org.merka.bitspren.BitsprenParser.ProgramContext;
import org.merka.bitspren.type.EvaluationOutcome;
import org.merka.bitspren.util.BitsprenUtils;
import org.merka.bitspren.visitor.InterpreterVisitor;

public class BitsprenREPL
{
	
	public static final String PROMPT = "|--bitspren-->";

	public static void main(String[] args)
	{
		try
		{
			System.out.println("Bitspren REPL starting..." + System.lineSeparator());
			//recognizementREPL();
			evaluationREPL();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Impossible to continue due to unexpected error");
		}
	}

	public static void evaluationREPL() throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		InterpreterVisitor interpreter = new InterpreterVisitor();
		boolean looping = true;
		
		do
		{
			System.out.print(System.lineSeparator() + PROMPT);
			String line = reader.readLine();
			looping = !line.equals("quit");
			ParseTree parseTree = BitsprenUtils.parse(line);
			EvaluationOutcome outcome = interpreter.visit(parseTree);
			System.out.println(outcome.getValue() + "[" + outcome.getType() + "]");
		}
		while(looping);
	}
	
	public static void recognizementREPL() throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		boolean looping = true;
		do
		{
			System.out.print(System.lineSeparator() + PROMPT);
			String line = reader.readLine();
			looping = !line.equals("quit");
			BitsprenErrorListener errorListener = new BitsprenErrorListener(); 
			BitsprenParser parser = BitsprenUtils.defaultParser(new StringReader(line), errorListener);
			@SuppressWarnings("unused")
			ProgramContext context = parser.program();
			if(!errorListener.isFail())
			{
				System.out.println("OK!");
			}
			else
			{
				System.out.println("Not cool man, not cool...");
			}
			
		} while (looping);
	}
}
