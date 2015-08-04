package org.merka.bitspren.visitor;

import static java.lang.Math.pow;

import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.merka.bitspren.BitsprenParser.*;
import org.merka.bitspren.BitsprenVisitor;
import org.merka.bitspren.exception.EvaluationException;
import org.merka.bitspren.exception.IdentifierNotBoundException;
import org.merka.bitspren.type.EvaluationOutcome;
import org.merka.bitspren.type.JavaType;
import org.merka.bitspren.type.SemanticType;
import org.merka.bitspren.type.SymbolTable;
import org.merka.bitspren.type.SymbolTableEntry;
import org.merka.bitspren.type.UndefinedType;
import org.merka.bitspren.type.VoidType;
import org.merka.bitspren.util.BitsprenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterpreterVisitor implements BitsprenVisitor<EvaluationOutcome>
{
	private static final Logger	logger	= LoggerFactory.getLogger(InterpreterVisitor.class);

	private SymbolTable			symbolTable;

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
	public EvaluationOutcome visit(ParseTree tree)
	{
		return tree.accept(this);
	}

	@Override
	public EvaluationOutcome visitChildren(RuleNode node)
	{
		EvaluationOutcome currentEntry = null; // node.accept(this);
		int childCount = node.getChildCount();
		int i;

		for (i = 0; i < childCount; i++)
		{
			ParseTree currentChild = node.getChild(i);
			if (currentChild instanceof TerminalNode)
			{
				break;
			}
			currentEntry = currentChild.accept(this);
		}
		return currentEntry;
	}

	@Override
	public EvaluationOutcome visitTerminal(TerminalNode node)
	{
		return null;
	}

	@Override
	public EvaluationOutcome visitErrorNode(ErrorNode node)
	{
		return null;
	}

	@Override
	public EvaluationOutcome visitSingleLineProgramRule(SingleLineProgramRuleContext ctx)
	{
		return ctx.statement().accept(this);
	}

	@Override
	public EvaluationOutcome visitMultiLineProgramRule(MultiLineProgramRuleContext ctx)
	{
		return visitChildren(ctx);
	}

	@Override
	public EvaluationOutcome visitMultiLinePlusEOFProgramRule(MultiLinePlusEOFProgramRuleContext ctx)
	{
		return visitChildren(ctx);
	}

	@Override
	public EvaluationOutcome visitTerminatedStatement(TerminatedStatementContext ctx)
	{
		return ctx.statement().accept(this);
	}

	@Override
	public EvaluationOutcome visitLastStatement(LastStatementContext ctx)
	{
		return ctx.statement().accept(this);
	}

	@Override
	public EvaluationOutcome visitFunctionDefinitionStatementRule(
			FunctionDefinitionStatementRuleContext ctx)
	{
		return ctx.functionDefinition().accept(this);
	}

	@Override
	public EvaluationOutcome visitFunctionApplicationStatementRule(
			FunctionApplicationStatementRuleContext ctx)
	{
		return ctx.functionApplication().accept(this);
	}

	@Override
	public EvaluationOutcome visitFunctionDefinition(FunctionDefinitionContext ctx)
	{
		String identifier = ctx.IDENTIFIER().getText();
		EvaluationOutcome outcome = ctx.function().accept(this);
		SymbolTableEntry entry = new SymbolTableEntry(ctx.function(), outcome.getType(),
				outcome.getValue());
		symbolTable.bind(identifier, entry);
		return outcome;
	}

	@Override
	public EvaluationOutcome visitFunction(FunctionContext ctx)
	{
		return ctx.polinomy().accept(this);
	}

	@Override
	public EvaluationOutcome visitSumRule(SumRuleContext ctx)
	{
		EvaluationOutcome leftOperand = ctx.polinomy(0).accept(this);
		EvaluationOutcome rightOperand = ctx.polinomy(1).accept(this);

		if (BitsprenUtils.isBinaryOperationUndefined(leftOperand, rightOperand))
		{
			return new EvaluationOutcome(null, UndefinedType.instance());
		}

		String operatorText = ctx.getChild(1).getText();
		Number result = null;
		if (operatorText.equals("+"))
		{
			result = (Double) leftOperand.getValue() + (Double) rightOperand.getValue();
		} 
		else
		{
			result = (Double) leftOperand.getValue() + (Double) rightOperand.getValue();
		}
		return new EvaluationOutcome(result, new JavaType(Double.class));
	}

	@Override
	public EvaluationOutcome visitBasicFunctionRule(BasicFunctionRuleContext ctx)
	{
		return ctx.basicFunction().accept(this);
	}

	@Override
	public EvaluationOutcome visitExponentialRule(ExponentialRuleContext ctx)
	{
		EvaluationOutcome base = ctx.getChild(0).accept(this);
		EvaluationOutcome exponent = ctx.getChild(2).accept(this);

		EvaluationOutcome outcome = null;
		if (base.getValue() != null && base.getType() != UndefinedType.instance()
				&& exponent.getValue() != null && exponent.getType() != UndefinedType.instance())
		{
			Double result = pow((Double) base.getValue(), (Double) exponent.getValue());
			SemanticType javaType = new JavaType(Double.class);
			outcome = new EvaluationOutcome(result, javaType);
		} 
		else
		{
			outcome = new EvaluationOutcome(null, UndefinedType.instance());
		}
		return outcome;
	}

	@Override
	public EvaluationOutcome visitMultiplicationRule(MultiplicationRuleContext ctx)
	{
		EvaluationOutcome leftOperand = ctx.polinomy(0).accept(this);
		EvaluationOutcome rightOperand = ctx.polinomy(1).accept(this);

		if (BitsprenUtils.isBinaryOperationUndefined(leftOperand, rightOperand))
		{
			return new EvaluationOutcome(null, UndefinedType.instance());
		}

		String operatorText = ctx.getChild(1).getText();
		Number result = null;
		if (operatorText.equals("*"))
		{
			result = (Double) leftOperand.getValue() * (Double) rightOperand.getValue();
		} 
		else if (operatorText.equals("%"))
		{
			result = (Double) leftOperand.getValue() % (Double) rightOperand.getValue();
		} 
		else
		{
			result = (Double) leftOperand.getValue() / (Double) rightOperand.getValue();
		}
		return new EvaluationOutcome(result, new JavaType(Double.class));
	}

	@Override
	public EvaluationOutcome visitIndependentVariableRule(IndependentVariableRuleContext ctx)
	{
		return ctx.independentVariable().accept(this);
	}

	@Override
	public EvaluationOutcome visitNumberLiteralRule(NumberLiteralRuleContext ctx)
	{
		String numberText = ctx.NUMBER_LITERAL().getText();
		EvaluationOutcome outcome = null;
		if (numberText.contains("."))
		{
			outcome = new EvaluationOutcome(Integer.parseInt(numberText), new JavaType(
					Integer.class));
		} 
		else
		{
			outcome = new EvaluationOutcome(Double.parseDouble(numberText), new JavaType(
					Double.class));
		}
		return outcome;
	}

	@Override
	public EvaluationOutcome visitJavaMethodCallRule(JavaMethodCallRuleContext ctx)
	{

		return null;
	}

	@Override
	public EvaluationOutcome visitEmbeddedFunctionRule(EmbeddedFunctionRuleContext ctx)
	{

		return null;
	}

	@Override
	public EvaluationOutcome visitFunctionApplicationRule(FunctionApplicationRuleContext ctx)
	{

		return null;
	}

	@Override
	public EvaluationOutcome visitJavaMethodCall(JavaMethodCallContext ctx)
	{

		return null;
	}

	@Override
	public EvaluationOutcome visitFunctionApplication(FunctionApplicationContext ctx)
	{
		String identifier = ctx.getChild(0).getText();
		ParseTree functionParseTree = symbolTable.lookup(identifier).getParseTree();
		if (functionParseTree == null)
		{
			throw new IdentifierNotBoundException("identifier");
		}
		return functionParseTree.accept(this);
	}

	@Override
	public EvaluationOutcome visitActualParameters(ActualParametersContext ctx)
	{

		return null;
	}

	@Override
	public EvaluationOutcome visitIndependentVariable(IndependentVariableContext ctx)
	{
		String identifier = ctx.getText();
		SymbolTableEntry entry = symbolTable.lookup(identifier);
		if (entry == null)
		{
			return new EvaluationOutcome(null, UndefinedType.instance());
		} 
		else
		{
			return entry.getParseTree().accept(this);
		}
	}

	private void logExecutionError(RuleNode node, int exitCode)
	{
		StringBuilder tracer = new StringBuilder();
		tracer.append("Execution error: ");
		Interval sourceInterval = node.getSourceInterval();
		tracer.append(sourceInterval).append("; ");
		logger.error(tracer.toString());
	}

	@Override
	public EvaluationOutcome visitFormalParameters(FormalParametersContext ctx)
	{
		return new EvaluationOutcome(null, VoidType.instance());
	}

	@Override
	public EvaluationOutcome visitUnaryFunctionRule(UnaryFunctionRuleContext ctx)
	{
		return ctx.unaryFunction().accept(this);
	}

	@Override
	public EvaluationOutcome visitUnaryFunction(UnaryFunctionContext ctx)
	{
		EvaluationOutcome outcome = ctx.function().accept(this);

		if (outcome.getType() instanceof UndefinedType)
		{
			return new EvaluationOutcome(null, UndefinedType.instance());
		}

		if (outcome.getValue() instanceof Integer)
		{
			return new EvaluationOutcome(-1 * (Integer) outcome.getValue(), new JavaType(
					Integer.class));
		} 
		else if (outcome.getValue() instanceof Double)
		{
			return new EvaluationOutcome(-1 * (Integer) outcome.getValue(), new JavaType(
					Integer.class));
		} 
		else
		{
			throw new EvaluationException("Incompatible Java types: found "
					+ outcome.getValue().getClass().getName());
		}
	}

}
