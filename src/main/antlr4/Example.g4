grammar Example;

matchingBrackets : '(' matchingBrackets ')' ;

LEFT_BRACKET : '(' ;

RIGHT_BRACKET : ')' ;

WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines ;
