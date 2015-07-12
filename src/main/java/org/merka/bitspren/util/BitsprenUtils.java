package org.merka.bitspren.util;

import java.io.IOException;
import java.io.Reader;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.TokenStream;
import org.merka.bitspren.BitsprenLexer;
import org.merka.bitspren.BitsprenParser;

public class BitsprenUtils
{
	public static BitsprenParser defaultParser(Reader reader) throws IOException
	{
		CharStream charStream = new ANTLRInputStream(reader);
		TokenSource tokenSource = new BitsprenLexer(charStream);
		TokenStream tokenStream = new CommonTokenStream(tokenSource);
		BitsprenParser parser = new BitsprenParser(tokenStream);
//		LanguageTestErrorListener listener = new LanguageTestErrorListener();
//		parser.addErrorListener(listener);
		return parser;
	}
}
