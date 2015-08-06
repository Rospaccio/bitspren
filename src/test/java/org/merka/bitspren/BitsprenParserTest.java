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
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.DiagnosticErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.TokenStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.Ignore;
import org.junit.Test;
import org.merka.bitspren.BitsprenParser.ProgramContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitsprenParserTest
 {
	private static Logger logger = LoggerFactory.getLogger(BitsprenParser.class);

	public static final String HARCODED_DEFAULT_PROGRAM = "def function(x) : x; def inner(param1) :  function(param1) + 1;"
			+ System.lineSeparator();

	@Test
	public void testParseHardcoded() throws IOException
	{
		CharStream charStream = new ANTLRInputStream(new StringReader(HARCODED_DEFAULT_PROGRAM));
		TokenSource tokenSource = new BitsprenLexer(charStream);
		TokenStream tokenStream = new CommonTokenStream(tokenSource);
		BitsprenParser parser = new BitsprenParser(tokenStream);
		BitsprenErrorListener listener = new BitsprenErrorListener();
		ConsoleErrorListener consoleListener = new ConsoleErrorListener();
		parser.addErrorListener(listener);
		parser.addErrorListener(consoleListener);
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
			logger.error("Parsing failed: failing file: " + file.getName());
			if (listener.getLineNumber() != 0 && listener.getMessage() != null)
			{
				logger.error("line: " + listener.getLineNumber() + "; position: "
						+ listener.getCharacterPosition() + "; " + listener.getMessage());
			}
		}
		assertFalse(listener.isFail());
	}

}
