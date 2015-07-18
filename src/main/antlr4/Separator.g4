grammar Separator;

program 
		: statement EOF
		| (terminatedStatement)+ EOF 
		| (terminatedStatement)+ statement EOF;

// lastStatement : statement ;

terminatedStatement : statement STATEMENT_TERMINATOR ;

statement : 'a' ;

STATEMENT_TERMINATOR
					: ';' (NEW_LINE)*
					| NEW_LINE (NEW_LINE)* ;

NEW_LINE : '\r\n' ;

WS : [ \t]+ -> skip ; // skip spaces, tabs, newlines ;
