grammar Bitspren;

program  		
		: statement EOF
		| (terminatedStatement)+ EOF 
		| (terminatedStatement)+ statement EOF;

terminatedStatement : statement STATEMENT_TERMINATOR ;

lastStatement : statement ;

statement 
			: functionDefinition 
			| functionApplication;

functionDefinition 
					: IDENTIFIER FUNCTION_DEFINTION_OP function ;

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
					: ';' (NEW_LINE)*
					| NEW_LINE+ ;

NEW_LINE : '\r\n' ;

WS : [ \t]+ -> skip ; // skip spaces, tabs, newlines ;

COMMENT
    :   '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip
    ;