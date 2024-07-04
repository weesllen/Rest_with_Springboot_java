package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.Math.SimpleMath;

import br.com.well.Converts.MathConvertNumeric;
import br.com.well.exceptions.UnsupportedMathOperationException;

@RestController
public class MathController {

	
	//	private static final AtomicLong counter = new AtomicLong();
		private SimpleMath math = new SimpleMath();
		
		@GetMapping("/calculate/{operation}/{numberOne}/{numberTwo}")
		public Double calculate(
		@PathVariable(value = "operation") String operation,
		@PathVariable(value = "numberOne") String numberOne,
		@PathVariable(value = "numberTwo") String numberTwo)
		
		throws Exception{
			if(!MathConvertNumeric.isNumeric(numberOne) || !MathConvertNumeric.isNumeric(numberTwo)) {
				throw new UnsupportedMathOperationException("Please set a numeric value !");
			}
			
			Double num1 = MathConvertNumeric.convertToDouble(numberOne);
			Double num2 = MathConvertNumeric.convertToDouble(numberTwo);
			
				switch(operation) {
				case "add":
					return math.add(num1, num2);
					
				case "subtract":
					return math.subtract(num1, num2);
					
				case "multiply":
					return math.multiply(num1, num2);
					
				case "divide":
					return math.divide(num1, num2);
					
				case "mean":
					return math.mean(num1, num2);
					
				default:
					throw new UnsupportedMathOperationException("Unsupported operation " + operation);
					}
		}
				
				@GetMapping("/sqrt/{number}")
				public double sqrt(
						@PathVariable(value = "number")String number)
					throws Exception{
						
					if(!MathConvertNumeric.isNumeric(number)) {
						throw new UnsupportedMathOperationException("Please set a numeric value!!!");
					}
						return Math.sqrt(MathConvertNumeric.convertToDouble(number));
						
					}
			
		}
			
		
		
				
		
		
