grammar Bitspren;

program : (statement STATEMENT_SEPARATOR)+ ;

statement 
	: functionDefinition
	| function ;

functionDefinition : IDENTIFIER FUNCTION_DEFINTION_OP function ;

function : IDENTIFIER LEFT_BRACKET independentVariable RIGHT_BRACKET ;

independentVariable : IDENTIFIER;

IDENTIFIER : IDENTIFIER_START_CHAR IDENTIFIER_FOLLOWING_CHAR*;

LEFT_BRACKET : '(' ;
RIGHT_BRACKET : ')' ;
X_VAR : 'x' ;

IDENTIFIER_START_CHAR : [a-zA-Z$_] ;
IDENTIFIER_FOLLOWING_CHAR : [a-zA-Z0-9$_] ;

FUNCTION_DEFINTION_OP : '=' ;

STATEMENT_SEPARATOR 
					: NEW_LINE* 
					| ';' NEW_LINE+
					| ';' 
					| EOF ;

NEW_LINE : '\r\n' ;

WS : [ \t]+ -> skip ; // skip spaces, tabs, newlines ;

COMMENT
    :   '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip
    ;