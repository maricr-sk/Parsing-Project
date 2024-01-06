# Parsing-Project

Directions: 

Create a static method that takes an infix expression in string form and translates it into a postfix expression. For this project, we will use a single space as a delimiter. This delimiter will be present after each operator, operand, and parenthesis.

Sample Input:

2

A + B * C

( A + B ) * C

Sample Output:

ABC*+

AB+C*

Then, write a static method that takes a string argument and evaluates the postfix expression and prints the resulting value.

Sample Input:

3

10:2:8:*:+:3:-

5:3:+:6:2:-:*:3:5:*:+

5:3:+:6:2:/:*:3:5:*:+

Sample Output:

23.0

47.0

39.0
