package org.merka.bitspren.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.merka.bitspren.BitsprenErrorListener;
import org.merka.bitspren.BitsprenLexer;
import org.merka.bitspren.BitsprenParser;
import org.merka.bitspren.type.EvaluationOutcome;
import org.merka.bitspren.type.UndefinedType;

public class BitsprenUtils
{
	public static BitsprenParser defaultParser(Reader reader) throws IOException
	{
		return defaultParser(reader, null);
	}

	public static BitsprenParser defaultParser(Reader reader, BitsprenErrorListener errorListener)
			throws IOException
	{
		CharStream charStream = new ANTLRInputStream(reader);
		TokenSource tokenSource = new BitsprenLexer(charStream);
		TokenStream tokenStream = new CommonTokenStream(tokenSource);
		BitsprenParser parser = new BitsprenParser(tokenStream);
		if (errorListener == null)
		{
			errorListener = new BitsprenErrorListener();
		}
		parser.addErrorListener(errorListener);
		return parser;
	}

	public static ParseTree parse(String input) throws IOException
	{
		BitsprenParser parser = defaultParser(new StringReader(input));
		return parser.program();
	}

//	public static EvaluationOutcome eval(String input) throws IOException
//	{
//		InterpreterVisitor visitor = new InterpreterVisitor();
//		EvaluationOutcome outcome = visitor.visit(parse(input));
//		return outcome;
//	}

	/**
	 * Tells if the type of a binary operation between {@code leftOperand} and
	 * {@code rightOperand} is {@link UndefinedType}
	 * 
	 * @param leftOperand
	 *            Left operand of the operation.
	 * @param rightOperand
	 *            Right operand of the operation.
	 * @return true, if one or both operand are of type {@link UndefinedType},
	 *         false otherwise.
	 */
	public static boolean isBinaryOperationUndefined(EvaluationOutcome leftOperand,
			EvaluationOutcome rightOperand)
	{
		return leftOperand.getType() == UndefinedType.instance()
				|| rightOperand.getType() == UndefinedType.instance();
	}

//	public static Number binaryOperationPreservingType(String operand,
//			EvaluationOutcome leftOpernad, EvaluationOutcome rightOperand)
//	{
//		if (leftOpernad.getValue() == null)
//		{
//			throw new NullPointerException("leftOperand");
//		}
//		if (rightOperand == null)
//		{
//			throw new NullPointerException("rightOperand");
//		}
//		if (StringUtils.isEmpty(operand))
//		{
//			throw new IllegalArgumentException("operand");
//		}
//
//		if (leftOpernad.getValue() instanceof Integer && rightOperand.getValue() instanceof Integer)
//		{
//
//		}
//		else
//		{
//			
//		}
//	}

	public static Number add(Integer leftOperand, Integer rightOperand)
	{
		return Integer.sum(leftOperand, rightOperand);
	}
	
	public static Number add(Double leftOperand, Double rightOperand)
	{
		return Double.sum(leftOperand, rightOperand);
	}

	public static Number multiply(Integer leftOperand, Integer rightOperand)
	{
		return leftOperand * rightOperand;
	}
	
	public static Number multiply(Double leftOperand, Double rightOperand)
	{
		return leftOperand * rightOperand;
	}
}
