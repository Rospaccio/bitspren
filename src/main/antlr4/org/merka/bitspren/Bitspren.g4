grammar Bitspren;

program  		
		: statement EOF								#SingleLineProgramRule
		| (terminatedStatement)+ EOF 				#MultiLineProgramRule
		| (terminatedStatement)+ statement EOF		#MultiLinePlusEOFProgramRule ;

terminatedStatement : statement STATEMENT_TERMINATOR ;

lastStatement : statement ;

statement 
			: functionDefinition 	#FunctionDefinitionStatementRule
			| functionApplication	#FunctionApplicationStatementRule ;

functionDefinition 
					: IDENTIFIER /*formalParameters*/ FUNCTION_DEFINTION_OP function ;

function : polinomy ;

polinomy
		: <assoc=right> polinomy ('^') polinomy	#ExponentialRule
		| polinomy ('*' | '/' | '%') polinomy  	#MultiplicationRule
		| polinomy ('+' | '-') polinomy			#SumRule
		| basicFunction							#BasicFunctionRule ;

basicFunction 
				: independentVariable 								#IndependentVariableRule
				| NUMBER_LITERAL									#NumberLiteralRule
				| javaMethodCall									#JavaMethodCallRule
				| '(' function ')' 									#EmbeddedFunctionRule
				/*| unaryFunction										#UnaryFunctionRule*/
				| functionApplication 								#FunctionApplicationRule;

javaMethodCall : IDENTIFIER '.' functionApplication ;

functionApplication
					: IDENTIFIER '(' actualParameters? ')' ;

formalParameters 
					: LEFT_BRACKET IDENTIFIER (',' IDENTIFIER)* RIGHT_BRACKET 
					| LEFT_BRACKET RIGHT_BRACKET;

actualParameters : function (',' function)* ;

independentVariable : IDENTIFIER;

unaryFunction : '-' function ;

NUMBER_LITERAL : NUMBER ('.'NUMBER)?;

NUMBER : [0-9]+ ;

IDENTIFIER : IDENTIFIER_START_CHAR IDENTIFIER_FOLLOWING_CHAR*;

LEFT_BRACKET : '(' ;
RIGHT_BRACKET : ')' ;
X_VAR : 'x' ;
SIN_KEYWORD : 'sin' | 'sen' ;
COSINE_KEYWORD : 'cos' ;
EXP_KEYWORD : 'exp' | 'e' ;

IDENTIFIER_START_CHAR : [a-zA-Z$_] ;
IDENTIFIER_FOLLOWING_CHAR : [a-zA-Z0-9$_] ;

FUNCTION_DEFINTION_OP : '=' ;

STATEMENT_TERMINATOR : ';' ;
					/* : ';' ([ ]* NEW_LINE)*
					| NEW_LINE ([ ]* NEW_LINE)* ;*/

/*NEW_LINE : '\r\n' ;*/

WS : [ \r\n\t]+ -> skip ; // skip spaces, tabs, newlines ;

COMMENT
    :   '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip
    ;