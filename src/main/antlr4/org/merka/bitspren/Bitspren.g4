grammar Bitspren;

program  		
		: (terminatedStatement)+ ;

terminatedStatement : statement STATEMENT_TERMINATOR ;

lastStatement : statement ;

statement 
	: functionDefinition ;

functionDefinition 
					: IDENTIFIER FUNCTION_DEFINTION_OP function 			#PlainFunction
					/*| IDENTIFIER FUNCTION_DEFINTION_OP functionApplication 	#CompositeFuction */;

function : polinomy ;

polinomy
		: polinomy ('*' | '/' | '%') polinomy  
		| polinomy ('+' | '-') polinomy
		| basicFunction;

basicFunction 
				: independentVariable 
				| NUMBER_LITERAL
				| '(' function ')' 
				| functionApplication ;

functionApplication
					: IDENTIFIER '(' function ')'
					| IDENTIFIER '(' functionApplication ')' ;

independentVariable : IDENTIFIER;

NUMBER_LITERAL : NUMBER ('.'NUMBER)?;

NUMBER : [0-9]+ ;

IDENTIFIER : IDENTIFIER_START_CHAR IDENTIFIER_FOLLOWING_CHAR*;

LEFT_BRACKET : '(' ;
RIGHT_BRACKET : ')' ;
X_VAR : 'x' ;

IDENTIFIER_START_CHAR : [a-zA-Z$_] ;
IDENTIFIER_FOLLOWING_CHAR : [a-zA-Z0-9$_] ;

FUNCTION_DEFINTION_OP : '=' ;

STATEMENT_TERMINATOR
					: ';' ;
					
// NEW_LINE : '\r\n' ;

WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines ;

COMMENT
    :   '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip
    ;