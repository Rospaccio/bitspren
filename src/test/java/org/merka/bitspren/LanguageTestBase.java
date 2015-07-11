package org.merka.bitspren;

import java.io.StringReader;
import java.util.BitSet;

import org.antlr.runtime.Lexer;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

public class LanguageTestBase
{
	public <T extends ParserRuleContext> T parseProgram(String program, ANTLRErrorListener errorListener)
	{
//		CharStream inputCharStream = new ANTLRInputStream(new StringReader(program));
//		TokenSource tokenSource = new LEX(inputCharStream);
//		TokenStream inputTokenStream = new CommonTokenStream(tokenSource);
//		ArithmeticParser parser = new ArithmeticParser(inputTokenStream);
//		parser.addErrorListener(errorListener);
//		T context = parser.program();
//		return context;
		return null;
	}
	
	public static class LanguageTestBaseErrorListener implements ANTLRErrorListener {
		
		private boolean fail = false;
		
		public boolean isFail() {
			return fail;
		}

		public void setFail(boolean fail) {
			this.fail = fail;
		}

		@Override
		public void syntaxError(Recognizer<?, ?> arg0, Object arg1, int arg2,
				int arg3, String arg4, RecognitionException arg5) {
			setFail(true);
		}
		
		@Override
		public void reportContextSensitivity(Parser arg0, DFA arg1, int arg2,
				int arg3, int arg4, ATNConfigSet arg5) {
			setFail(true);			
		}
		
		@Override
		public void reportAttemptingFullContext(Parser arg0, DFA arg1, int arg2,
				int arg3, BitSet arg4, ATNConfigSet arg5) {
			setFail(true);
		}
		
		@Override
		public void reportAmbiguity(Parser arg0, DFA arg1, int arg2, int arg3,
				boolean arg4, BitSet arg5, ATNConfigSet arg6) {
			setFail(true);
		}
	}
}
