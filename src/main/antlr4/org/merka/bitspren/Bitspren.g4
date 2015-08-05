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
			
functionDefinition : DEF '(' formalParameterList ')' DEF_OPERATOR functionBody ;

formalParameterList : IDENTIFIER (',' IDENTIFIER)* ;

functionBody : polinomy ;

polinomy : <assoc=right> polinomy ('^') polinomy	#ExponentialRule
		 | polinomy ('*' | '/' | '%') polinomy  	#MultiplicationRule
         | polinomy ('+' | '-') polinomy			#SumRule
		 | basicFunction							#BasicFunctionRule ;

basicFunction   : unaryFunction										#UnaryFunctionRule
				| variable			 								#VariableRule
				| NUMBER_LITERAL									#NumberLiteralRule
				| javaMethodCall									#JavaMethodCallRule
				| '(' polinomy ')' 									#EmbeddedFunctionRule
				| functionApplication 								#FunctionApplicationRule;

javaMethodCall : IDENTIFIER '.' functionApplication ;

functionApplication : IDENTIFIER '(' actualParameters? ')' ;

actualParameters : polinomy (',' polinomy)* ;

variable : IDENTIFIER ;

NUMBER_LITERAL : NUMBER ('.'NUMBER)?;

unaryFunction : '-' polinomy ;

NUMBER : [0-9]+ ;

IDENTIFIER : IDENTIFIER_START_CHAR    IDENTIFIER_FOLLOWING_CHAR*;

/* KEYWORDS and symbols*/
DEF : 'def' ;
DEF_OPERATOR : ':' ;
COMPONENTS_SEPARATOR : ',' ;
/* KEYWORDS and symbols - end */

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