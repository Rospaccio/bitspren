package org.merka.bitspren;

import java.util.BitSet;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitsprenErrorListener implements ANTLRErrorListener
{
	private static final Logger logger = LoggerFactory.getLogger(BitsprenErrorListener.class);
	private boolean	fail	= false;

	public boolean isFail()
	{
		return fail;
	}

	public void setFail(boolean fail)
	{
		this.fail = fail;
	}

	@Override
	public void syntaxError(Recognizer<?, ?> arg0, Object arg1, int arg2, int arg3, String arg4,
			RecognitionException arg5)
	{
		setFail(true);
	}

	@Override
	public void reportContextSensitivity(Parser arg0, DFA arg1, int arg2, int arg3, int arg4,
			ATNConfigSet arg5)
	{
		setFail(true);
	}

	@Override
	public void reportAttemptingFullContext(Parser arg0, DFA arg1, int arg2, int arg3, BitSet arg4,
			ATNConfigSet arg5)
	{
		logger.debug(arg1.toString());
		logger.debug("" + arg2);
		logger.debug("" + arg3);
		setFail(true);
	}

	@Override
	public void reportAmbiguity(Parser arg0, DFA arg1, int arg2, int arg3, boolean arg4,
			BitSet arg5, ATNConfigSet arg6)
	{
		logger.debug(arg6.getAlts().toString());
		setFail(true);
	}
}
