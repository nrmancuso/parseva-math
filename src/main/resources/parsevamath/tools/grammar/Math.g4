grammar Math;

@header{
 package parsevamath.tools.grammar;
}

compilationUnit
    :   expr EOF
    ;

expr
    :   '(' expr ')'                         # parensExpr
    |   op=( OP_ADD | OP_SUB ) expr                    # unaryExpr
    |   left=expr op=( OP_MUL | OP_DIV ) right=expr    # infixExpr
    |   left=expr op=( OP_ADD | OP_SUB ) right=expr    # infixExpr
    |   func=ID '(' expr ')'                 # funcExpr
    |   value=NUM                            # numberExpr
    ;

OP_ADD: '+';
OP_SUB: '-';
OP_MUL: '*';
OP_DIV: '/';

NUM :   [0-9]+ ('.' [0-9]+)? ([eE] [+-]? [0-9]+)?;
ID  :   [a-zA-Z]+;
WS  :   [ \t\r\n] -> channel(HIDDEN);