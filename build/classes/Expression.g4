/**
 * Define a grammar called Expression
 */
grammar Expression;

@header {
package com.mathapollo.equation.codegen;
}

WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines

math: pointform
  | expr
  ;

expr:  
  form0 comp form0 
  | form0
  ;

comp: '>'
  |'<'
  |'='
  |'<='
  |'>=';
  
pointform:
  expr? '(' form0 op0 form0 ')'
  ;

op0 : 
  ','
  ;

form0 : 
    form0 op1 form1 
  | form1
  ;

op1 : 
    '+'
  |'-'
  ;
  
form1: 
    form1 op2? form2
  | neg form1 
  | form2;

op2 : '*'
  |'/'
  ;

neg : '-';

form2: 
   form2 op3 form3 
  |form3
  ;
  
op3 : '^';

form3 : '(' expr ')' 
  | Value
  | Symbol '(' expr ')'
  ;
  

Value:
   Symbol
  |Const
  |Variable
  ;

Symbol:
  [A-Z][A-Z]?
  ;
  
Variable:
  [a-z][a-z]?
  ;

Const: 
  [0-9]+
  |[0-9]* '.' [0-9]*
  ;