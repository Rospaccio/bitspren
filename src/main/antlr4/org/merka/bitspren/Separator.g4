grammar Separator;

program : statement EOF
		| statement STATEMENT_SEPARATOR EOF
		| (statement STATEMENT_SEPARATOR) statement EOF
		| (statement STATEMENT_SEPARATOR) EOF;

statement : 'a' ;

STATEMENT_SEPARATOR 
					: NEW_LINE* 
					| ';' NEW_LINE+
					| ';' ;

NEW_LINE : '\r\n' ;

WS : [ \t]+ -> skip ; // skip spaces, tabs, newlines ;
