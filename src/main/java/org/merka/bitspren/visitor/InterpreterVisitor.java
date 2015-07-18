package org.merka.bitspren.visitor;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.*;
import org.merka.bitspren.BitsprenParser.*;
import org.merka.bitspren.BitsprenParser.FunctionApplicationStatementRuleContext;
import org.merka.bitspren.BitsprenParser.FunctionDefinitionStatementRuleContext;
import org.merka.bitspren.BitsprenVisitor;
import org.merka.bitspren.runtime.ExecutionOutcome;
import org.merka.bitspren.type.SymbolTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Math.*;

public class InterpreterVisitor implements BitsprenVisitor<Object>
{
	private static final Logger logger = LoggerFactory.getLogger(InterpreterVisitor.class);

	private SymbolTable symbolTable;
	
	public SymbolTable getSymbolTable()
	{
		return symbolTable;
	}

	private void setSymbolTable(SymbolTable symbolTable)
	{
		this.symbolTable = symbolTable;
	}

	public InterpreterVisitor()
	{
		this.setSymbolTable(new SymbolTable());
	}
	
	@Override
	public Object visit(ParseTree tree)
	{
		return tree.accept(this);
	}

	@Override
	public Object visitChildren(RuleNode node)
	{
		int exitCode = 0;
		int childCount = node.getChildCount();
		for (int i = 0; i < childCount; i++)
		{
			ParseTree currentChild = node.getChild(i);
			currentChild.accept(this);
		}
		return exitCode;
	}

	@Override
	public Object visitTerminal(TerminalNode node)
	{
		return 0;
	}

	@Override
	public Object visitErrorNode(ErrorNode node)
	{
		return null;
	}


	@Override
	public Object visitSingleLineProgramRule(SingleLineProgramRuleContext ctx)
	{
		return ctx.statement().accept(this);
	}

	@Override
	public Object visitMultiLineProgramRule(MultiLineProgramRuleContext ctx)
	{
		return visitChildren(ctx);
	}

	@Override
	public Object visitMultiLinePlusEOFProgramRule(MultiLinePlusEOFProgramRuleContext ctx)
	{
		return visitChildren(ctx);
	}
	
	@Override
	public Object visitTerminatedStatement(TerminatedStatementContext ctx)
	{
		return ctx.statement().accept(this);
	}

	@Override
	public Object visitLastStatement(LastStatementContext ctx)
	{
		return ctx.statement().accept(this);
	}


	@Override
	public Object visitFunctionDefinitionStatementRule(FunctionDefinitionStatementRuleContext ctx)
	{
		return ctx.functionDefinition().accept(this);
	}

	@Override
	public Object visitFunctionApplicationStatementRule(FunctionApplicationStatementRuleContext ctx)
	{
		return ctx.functionApplication().accept(this);
	}
	
	@Override
	public Object visitFunctionDefinition(FunctionDefinitionContext ctx)
	{
		String identifier = ctx.IDENTIFIER().getText();
		FunctionContext subtree = ctx.function();
		symbolTable.bind(identifier, subtree);
		return Integer.valueOf(1);
	}

	@Override
	public Object visitFunction(FunctionContext ctx)
	{
		return ctx.polinomy().accept(this);
	}

	@Override
	public Object visitSumRule(SumRuleContext ctx)
	{
		
		return null;
	}

	@Override
	public Object visitBasicFunctionRule(BasicFunctionRuleContext ctx)
	{
		
		return null;
	}

	@Override
	public Object visitExponentialRule(ExponentialRuleContext ctx)
	{
		Double base = (Double) ctx.polinomy(0).accept(this);
		Double exponent = (Double) ctx.polinomy(2).accept(this);
		return pow(base, exponent);
	}

	@Override
	public Object visitMultiplicationRule(MultiplicationRuleContext ctx)
	{
		
		return null;
	}

	@Override
	public Object visitIndependentVariableRule(IndependentVariableRuleContext ctx)
	{
		
		return null;
	}

	@Override
	public Object visitNumberLiteralRule(NumberLiteralRuleContext ctx)
	{
		
		return null;
	}

	@Override
	public Object visitJavaMethodCallRule(JavaMethodCallRuleContext ctx)
	{
		
		return null;
	}

	@Override
	public Object visitEmbeddedFunctionRule(EmbeddedFunctionRuleContext ctx)
	{
		
		return null;
	}

	@Override
	public Object visitFunctionApplicationRule(FunctionApplicationRuleContext ctx)
	{
		
		return null;
	}

	@Override
	public Object visitJavaMethodCall(JavaMethodCallContext ctx)
	{
		
		return null;
	}

	@Override
	public Object visitFunctionApplication(FunctionApplicationContext ctx)
	{
		
		return null;
	}

	@Override
	public Object visitActualParameters(ActualParametersContext ctx)
	{
		
		return null;
	}

	@Override
	public Object visitIndependentVariable(IndependentVariableContext ctx)
	{
		
		return null;
	}
	
	private void logExecutionError(RuleNode node, int exitCode)
	{
		StringBuilder tracer = new StringBuilder();
		tracer.append("Execution error: ");
		Interval sourceInterval = node.getSourceInterval();
		tracer.append(sourceInterval).append("; ");		
		logger.error(tracer.toString());
	}

}
