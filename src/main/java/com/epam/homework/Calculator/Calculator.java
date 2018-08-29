package com.epam.homework.Calculator;

import java.text.*;
import java.util.regex.*;
import java.util.*;

public class Calculator {

	private static final char openParenthesis = '(';
	private static final char closeParenthesis = ')';
	private static final char delimiter = '.';

	public static final Map<Character, Integer> OPERATORS;
	public static final String outputFormat = "#.####";

	static{
		OPERATORS = new HashMap<>();
		OPERATORS.put('+', 1);
		OPERATORS.put('-', 1);
		OPERATORS.put('*', 2);
		OPERATORS.put('/', 2);
	}

	/**
	* Evaluate statement represented as string.
	*
	* @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
	*                  parentheses, operations signs '+', '-', '*', '/'<br>
	*                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
	* @return string value containing result of evaluation or null if statement is invalid
	*/
	public String evaluate(String statement) {

		if(statement == null){
			return null;
		}
		statement = statement.replaceAll(" ", "");
		int length = statement.length();

		if(length == 0){
			return null;
		}

		String pattern = "(^[*/].*)|(.*[-+*/.][-+*/.].*)|(.*[^0-9-+*/().].*)|(.*[-+*/]$)";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(statement);

		if(m.find()){
			return null;
		}

		List<String> polishResult = new ArrayList<>();
		Stack<Character> stack = new Stack<>();
		Stack<Double> numbersStore = new Stack<>();
		double result = 0;
		double number1 = 0;
		double number2 = 0;


		for(int i = 0; i < length; i++){
			char ch = statement.charAt(i);

			if(ch == openParenthesis){										//opened Parenthesis
				stack.push(openParenthesis);
			} else if(ch == closeParenthesis){								// closed Parenthesis

				try{
					char temp = stack.peek();

					while(temp != openParenthesis){
						temp = stack.pop();
						if(temp == openParenthesis){
							continue;
						}
						polishResult.add(Character.toString(temp));
					}

				}catch(EmptyStackException ex){
					return null;
				}

			} else{												//parse numbers and operators

				if(OPERATORS.containsKey(ch)){					//work with operators
					if(stack.isEmpty()){
						stack.push(ch);
					}else{
						char stackOperand = stack.peek();
						if(stackOperand == openParenthesis){
							stack.push(ch);
						}else{
							if(OPERATORS.get(stackOperand) < OPERATORS.get(ch)){
								stack.push(ch);
							}else{
								stackOperand = stack.pop();
								polishResult.add(Character.toString(stackOperand));
								stack.push(ch);
							}
						}
					}
				} else if(Character.isDigit(ch) || ch == delimiter){	//work with numbers
					StringBuilder sb = new StringBuilder();

					while(i < length && (Character.isDigit(statement.charAt(i)) || statement.charAt(i) == delimiter)){
						sb.append(statement.charAt(i++));
					}

					--i;

					try{

						Double.parseDouble(sb.toString());		//check format of number
					} catch(NumberFormatException ex){
						return null;
					}

					polishResult.add(sb.toString());

				} else {										//unparseable character
					return null;
				}
			}

		}

		while(!stack.isEmpty()){
			char ch = stack.pop();
			if(ch == openParenthesis){
				return null;
			}
			polishResult.add(Character.toString(ch));
		}

		stack.clear();

		for(String s : polishResult){
			try{
				switch(s){

				case "+":
					number2 = numbersStore.pop();
					number1 = numbersStore.pop();
					result = number1 + number2;
					numbersStore.push(result);
					break;

				case "-":
					number2 = numbersStore.pop();
					number1 = numbersStore.pop();
					result = number1 - number2;
					numbersStore.push(result);
					break;

				case "*":
					number2 = numbersStore.pop();
					number1 = numbersStore.pop();
					result = number1 * number2;
					numbersStore.push(result);
					break;

				case "/":
					number2 = numbersStore.pop();
					number1 = numbersStore.pop();

					result = number1 / number2;
					if(result == Double.POSITIVE_INFINITY){

						return null;
					}
					numbersStore.push(result);
					break;

				default:
					numbersStore.push(Double.parseDouble(s));
					break;
				}
			}catch (EmptyStackException ex){
				return null;
			}
		}

		DecimalFormat decimalFormat = new DecimalFormat(outputFormat, DecimalFormatSymbols.getInstance(Locale.ENGLISH));

		return decimalFormat.format(numbersStore.pop());
	}

}