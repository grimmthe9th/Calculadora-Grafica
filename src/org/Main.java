package org;

import bsh.EvalError;
import bsh.Interpreter;


public class Main {
	
	public static void main(String[] args) {
		VentanaCalculadora calculadora = new VentanaCalculadora();
		calculadora.setVisible(true);
	
	Interpreter interpreter = new Interpreter();
	

	try {
		interpreter.eval("result = 2+2*3/1.2");
		System.out.println(interpreter.get("result").toString());
	} catch (EvalError e) {
		// TODO: handle exception
		e.printStackTrace();
	}
}




}




