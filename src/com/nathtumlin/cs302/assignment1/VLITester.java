package com.nathtumlin.cs302.assignment1;

public class VLITester {

	public static void main(String[] args) {
		VeryLargeInteger vli1 = new VeryLargeInteger(2L);
		VeryLargeInteger vli2 = new VeryLargeInteger("222222222222");
		VeryLargeInteger vli3 = new VeryLargeInteger(-13431L);
		VeryLargeInteger vli4 = new VeryLargeInteger("-13431");
		VeryLargeInteger vli5 = new VeryLargeInteger(1L);
		VeryLargeInteger vli6 = new VeryLargeInteger(0L);
		
		System.out.println(vli2 + " + " + vli1 + " = " + vli2.add(vli1));
		System.out.println(vli2 + " - " + vli1 + " = " + vli2.sub(vli1));
		System.out.println(vli2 + " * " + vli1 + " = " + vli2.mul(vli1));
		System.out.println(vli2 + " / " + vli1 + " = " + vli2.div(vli1));
		System.out.println(vli2 + " % " + vli1 + " = " + vli2.mod(vli1));
		
		System.out.println(vli3 + " + " + vli4 + " = " + vli3.add(vli4));
		System.out.println(vli3 + " - " + vli4 + " = " + vli3.sub(vli4));
		System.out.println(vli3 + " * " + vli4 + " = " + vli3.mul(vli4));
		System.out.println(vli3 + " / " + vli4 + " = " + vli3.div(vli4));
		System.out.println(vli3 + " % " + vli4 + " = " + vli3.mod(vli4));
		
		System.out.println(vli3 + " + " + vli1 + " = " + vli3.add(vli1));
		System.out.println(vli3 + " - " + vli1 + " = " + vli3.sub(vli1));
		System.out.println(vli3 + " * " + vli1 + " = " + vli3.mul(vli1));
		System.out.println(vli3 + " / " + vli1 + " = " + vli3.div(vli1));
		System.out.println(vli3 + " % " + vli1 + " = " + vli3.mod(vli1));
		
		System.out.println(vli2 + " + " + vli5 + " = " + vli2.add(vli5));
		System.out.println(vli2 + " - " + vli5 + " = " + vli2.sub(vli5));
		System.out.println(vli2 + " * " + vli5 + " = " + vli2.mul(vli5));
		System.out.println(vli2 + " / " + vli5 + " = " + vli2.div(vli5));
		System.out.println(vli2 + " % " + vli5 + " = " + vli2.mod(vli5));

		System.out.println(vli2 + " + " + vli6 + " = " + vli2.add(vli6));
		System.out.println(vli2 + " - " + vli6 + " = " + vli2.sub(vli6));
		System.out.println(vli2 + " * " + vli6 + " = " + vli2.mul(vli6));
		System.out.println(vli2 + " / " + vli6 + " = " + vli2.div(vli6));
		System.out.println(vli2 + " % " + vli6 + " = " + vli2.mod(vli6));
	}
}
