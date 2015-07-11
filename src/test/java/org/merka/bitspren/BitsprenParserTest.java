package org.merka.bitspren;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.TokenStream;
import org.junit.Test;
import org.merka.bitspren.BitsprenParser.FunctionContext;
import org.merka.bitspren.BitsprenParser.ProgramContext;

public class BitsprenParserTest
{
	public static final String HARCODED_DEFAULT_PROGRAM = "testFunction(x)";
	
	@Test
	public void testParseHardcoded() throws IOException
	{
		CharStream charStream = new ANTLRInputStream(new StringReader(HARCODED_DEFAULT_PROGRAM));
		TokenSource tokenSource = new BitsprenLexer(charStream);
		TokenStream tokenStream = new CommonTokenStream(tokenSource);
		BitsprenParser parser = new BitsprenParser(tokenStream);
		LanguageTestErrorListener listener = new LanguageTestErrorListener();
		parser.addErrorListener(listener);
		ProgramContext rootNode = parser.program();
		assertNotNull(rootNode);
		assertFalse(listener.isFail());
	}

}
