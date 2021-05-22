package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
	public static void main(String[] args) {
		String expression = args[0];
		String[] splitExp = expression.split(" ");
		
		ObjectStack stack = new ObjectStack();
		for(String s : splitExp) {
			try {
				int i = Integer.parseInt(s);
				stack.push(Integer.valueOf(i));
			} catch (Exception e){
				int o2 = (Integer)stack.pop();
				int o1 = (Integer)stack.pop();
				
				int result;
				
				if(s.equals("-")) result = o1 - o2;
				else if(s.equals("+")) result = o1 + o2;
				else if(s.equals("/")) result = o1 / o2;
				else if(s.equals("*")) result = o1 * o2;
				else if(s.equals("%")) result = o1 % o2;
				else {
					throw new IllegalArgumentException("Invalid expression!");
				}
				
				stack.push(result);
			}
		}
		
		if (stack.size() != 1) {
			System.err.println("There are more than 1 element in stack after performing the whole operation! Invalid expression given!");
		} else {
			System.out.println(stack.pop());
		}
		
	}
}
