package org.merka.bitspren;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.Collection;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.TokenStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.Test;
import org.merka.bitspren.BitsprenParser.FunctionContext;
import org.merka.bitspren.BitsprenParser.ProgramContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitsprenParserTest
{
	private static Logger			logger						= LoggerFactory
																		.getLogger(BitsprenParser.class);

	public static final String		HARCODED_DEFAULT_PROGRAM	= "_f = x"; /*
+ "function = 2*x                                      "
+ "function = 2 + 3 + x + clientY;                     "
+ "sum = x + sum(x - 1);                               "
+ "sum = x ^ (f(12 - x * 2) + x ^ 2) - 13              "
+ "testFunction = 3 + 2 + 1 - 5 - 4 / 2 % 3 - (1 + 2 );"
+ "d(89)                                               "
+ "$d = d(x(x + 1))                                    "
+ "                                                    "
+ "                                                    "
+ "fibonacci = fibonacci(x - 1) + fibonacci(x - 2);    "
+ "decrease = x - 1;                                   "
+ "decrease(2);                                        "
+ "decrease(x(s(1)));                                  "
+ "                                                    "
+ "function = f(s, r)                                  "
+ "                                                    "
+ "quit();                                             ";*/

	public static final String[]	validStrings				= { "testFunction = x",
			"f = x;" + System.lineSeparator(), "f = g(d(x));", "f = x+3*2-4",
			"f = x + 12 * g(3 + x)"							};

	@Test
	public void testParseHardcoded() throws IOException
	{
		CharStream charStream = new ANTLRInputStream(new StringReader(HARCODED_DEFAULT_PROGRAM));
		TokenSource tokenSource = new BitsprenLexer(charStream);
		TokenStream tokenStream = new CommonTokenStream(tokenSource);
		BitsprenParser parser = new BitsprenParser(tokenStream);
		BitsprenErrorListener listener = new BitsprenErrorListener();
		parser.addErrorListener(listener);
		ProgramContext rootNode = parser.program();
		assertNotNull(rootNode);
		assertFalse(HARCODED_DEFAULT_PROGRAM, listener.isFail());
	}

	@Test
	public void TestFromFile() throws URISyntaxException, IOException
	{
		File sourceDir = new File(getClass().getResource("/valid-programs").toURI());
		Collection<File> files = FileUtils.listFiles(sourceDir, TrueFileFilter.INSTANCE, null);
		for (File file : files)
		{
			assertProgramIsValid(file);
		}
	}

	
	
	private void assertProgramIsValid(File file) throws IOException
	{
		CharStream charStream = new ANTLRInputStream(new FileInputStream(file));
		TokenSource tokenSource = new BitsprenLexer(charStream);
		TokenStream tokenStream = new CommonTokenStream(tokenSource);
		BitsprenParser parser = new BitsprenParser(tokenStream);
		BitsprenErrorListener listener = new BitsprenErrorListener();
		parser.addErrorListener(listener);
		ProgramContext rootNode = parser.program();
		assertNotNull(rootNode);
		if (listener.isFail())
		{
			logger.error("Parse failed");
			if (listener.getLineNumber() != 0 && listener.getMessage() != null)
			{
				logger.error("line: " + listener.getLineNumber() + "; position: "
						+ listener.getCharacterPosition() + "; " + listener.getMessage());
			}
		}
		assertFalse(listener.isFail());
	}

}
