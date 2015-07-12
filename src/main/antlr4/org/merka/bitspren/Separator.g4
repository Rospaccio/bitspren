grammar Separator;

program : (terminatedStatement)* lastStatement ;

lastStatement : statement EOF ;

terminatedStatement : statement STATEMENT_TERMINATOR ;

statement : 'a' ;

STATEMENT_TERMINATOR
					: EOF
					| ';' (NEW_LINE)*
					| NEW_LINE (NEW_LINE)* ;

STATEMENT_SEPARATOR 
					: NEW_LINE* 
					| ';' NEW_LINE+
					| ';' ;

NEW_LINE : '\r\n' ;

WS : [ \t]+ -> skip ; // skip spaces, tabs, newlines ;
