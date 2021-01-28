grammar Math;

@header{
 package parsevamath.tools.grammar;
}

compilationUnit
    :   expr EOF
    ;

expr
    :   lparen=LPAREN expr rparen=RPAREN                         # parensExpr
    |   op=( OP_ADD | OP_SUB ) expr                    # unaryExpr
    |   left=expr op=( OP_MUL | OP_DIV ) right=expr    # infixExpr
    |   left=expr op=( OP_ADD | OP_SUB ) right=expr    # infixExpr
    |   func=ID '(' expr (COMMA expr)* ')'                 # funcExpr
    |   value=NUM                            # numberExpr
    |   constant                            #constExpr
    |   expr fact=OP_FACT                        #factorialExpr
    ;

constant
    :   ( 'e' | 'E' )
    |   ( 'pi' | 'PI' )
    ;

OP_ADD: '+';
OP_SUB: '-';
OP_MUL: '*';
OP_DIV: '/';
OP_FACT: '!';
LPAREN: '(';
RPAREN: ')';

NUM :   [0-9]+ ('.' [0-9]+)? ([eE] [+-]? [0-9]+)?;
ID  :   [a-zA-Z]+;
WS  :   [ \t\r\n] -> channel(HIDDEN);
COMMA : ',';
