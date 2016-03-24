package com.nathtumlin.cs302.assignment1;

public class VeryLargeInteger {
	VeryLargeInteger(long num) {
		bitString = "";
		boolean isNegative = num < 0;

		if (isNegative) {
			num *= -1;
		}

		// converts the number to binary and stores it in a string
		while (num > 0) {
			bitString = (num % 2) + bitString;
			num /= 2;
		}

		bitString = "0" + bitString;

		if (isNegative) {
			bitString = twosCompliment(bitString);
		}
	}

	VeryLargeInteger(String num) {
		bitString = "";
		boolean isNegative = num.charAt(0) == '-';

		if (isNegative) {
			num = num.substring(1);
		}

		// divides the number in the string by 2 and sets the string to the
		// result.
		while (!num.isEmpty()) {
			// converts number to binary and stores in bitString
			bitString = (Character
					.getNumericValue(num.charAt(num.length() - 1)) % 2)
					+ bitString;

			String nextNum = "";
			int remainder = 0;

			for (Character c : num.toCharArray()) {
				int carry = remainder;
				// carry the 0.5 resulting from dividing an odd number by 2
				remainder = ((Character.getNumericValue(c) % 2) * 5);

				nextNum = nextNum + (Character.getNumericValue(c) / 2 + carry);
			}

			num = nextNum.replaceAll("^0+", "");
		}

		bitString = "0" + bitString;

		if (isNegative) {
			bitString = twosCompliment(bitString);
		}
	}

	// A way to create a new VLI from a bitstring. Additional argument to change
	// method signature to be different from base 10 string constructor
	// Used internally by operations to return the result as a new VLI
	private VeryLargeInteger(String bits, int methodHeader) {
		bitString = new String(bits);
	}

	VeryLargeInteger add(VeryLargeInteger other) {
		// Width must be one longer than longest VLI in case of carry
		int width = Math.max(bitString.length(), other.bitString.length()) + 1;

		String operand1 = setWidth(bitString, width);
		String operand2 = setWidth(other.bitString, width);
		String result = "";
		boolean carry = false;

		for (int i = width - 1; i >= 0; i--) {
			if (carry) {
				if (operand1.charAt(i) == '1' && operand2.charAt(i) == '1') {
					carry = true;
					result = "1" + result;
				} else if (!(operand1.charAt(i) == '1')
						&& !(operand2.charAt(i) == '1')) {
					carry = false;
					result = "1" + result;
				} else {
					carry = true;
					result = "0" + result;
				}
			} else {
				if (operand1.charAt(i) == '1' && operand2.charAt(i) == '1') {
					carry = true;
					result = "0" + result;
				} else if (!(operand1.charAt(i) == '1')
						&& !(operand2.charAt(i) == '1')) {
					carry = false;
					result = "0" + result;
				} else {
					carry = false;
					result = "1" + result;
				}
			}
		}

		return new VeryLargeInteger(result, 0);
	}

	public VeryLargeInteger sub(VeryLargeInteger other) {
		VeryLargeInteger negated = new VeryLargeInteger(
				twosCompliment(other.bitString), 0); // negates subtrahend and
														// adds to minuend
		return add(negated);
	}

	public VeryLargeInteger mul(VeryLargeInteger other) {
		VeryLargeInteger result = new VeryLargeInteger(0L);

		// double length for two's complement multiplication
		int width = 2 * (Math.max(bitString.length(), other.bitString.length()));

		String multiplicand = setWidth(bitString, width);
		String multiplier = setWidth(other.bitString, width);

		for (int i = width - 1; i >= 0; i--) {
			// if current digit in multiplier is a 1, shift and add multiplicand
			if (multiplier.charAt(i) == '1') {
				result = result.add(new VeryLargeInteger(lshift(multiplicand,
						width - (i + 1)), 0));
			}
		}

		// only need last width digits of bitString of result
		return result;
	}

	public VeryLargeInteger div(VeryLargeInteger other) {
		String dividend = bitString;
		String divisor = other.bitString;

		// returns 0 when attempting to divide by 0
		if (divisor.matches("^0+$")) {
			return new VeryLargeInteger(0L);
		}

		// Do division with positive numbers and adjust at end.
		boolean isNegative = false;

		if (bitString.charAt(0) == '1') {
			isNegative ^= true; // xor because negative/negative results in a
								// positive value
			dividend = twosCompliment(bitString);
		}

		if (other.bitString.charAt(0) == '1') {
			isNegative ^= true;
			divisor = twosCompliment(other.bitString);
		}

		int width = 2 * (Math.max(dividend.length(), divisor.length()));

		dividend = setWidth(dividend, width);
		divisor = lshift(setWidth(divisor, width / 2), width / 2);

		String quotient = "";

		VeryLargeInteger remainder = new VeryLargeInteger(dividend, 0);

		for (int i = 0; i <= width / 2; i++) {
			remainder = remainder.sub(new VeryLargeInteger(divisor, 0));

			// check if remainder is now negative, undo and shift 0 into
			// quotient if so
			// if not, leave it and shift 1 into quotient
			if (remainder.bitString.charAt(0) == '1') {
				remainder = remainder.add(new VeryLargeInteger(divisor, 0));
				quotient = quotient + "0";
			} else {
				quotient = quotient + "1";
			}

			// shift divisor right and add "0" to maintain width

			divisor = "0" + rshift(divisor, 1);
		}

		// fix negatives
		if (isNegative) {
			quotient = twosCompliment(quotient);
		}

		return new VeryLargeInteger(quotient, 0);
	}

	// modulo is same as division, but remainder is returned instead of quotient
	public VeryLargeInteger mod(VeryLargeInteger other) {
		String dividend = bitString;
		String divisor = other.bitString;

		// returns 0 when attempting to divide by 0
		if (divisor.matches("^0+$")) {
			return new VeryLargeInteger(0L);
		}

		// Do division with positive numbers and adjust at end.
		boolean isNegative = false;

		if (bitString.charAt(0) == '1') {
			isNegative ^= true; // xor because negative/negative results in a
								// positive value
			dividend = twosCompliment(bitString);
		}

		if (other.bitString.charAt(0) == '1') {
			isNegative ^= true;
			divisor = twosCompliment(other.bitString);
		}

		int width = 2 * (Math.max(dividend.length(), divisor.length()));

		dividend = setWidth(dividend, width);
		divisor = lshift(setWidth(divisor, width / 2), width / 2);

		String quotient = "";

		VeryLargeInteger remainder = new VeryLargeInteger(dividend, 0);

		for (int i = 0; i <= width / 2; i++) {
			remainder = remainder.sub(new VeryLargeInteger(divisor, 0));

			// check if remainder is now negative, undo and shift 0 into
			// quotient if so
			// if not, leave it and shift 1 into quotient
			if (remainder.bitString.charAt(0) == '1') {
				remainder = remainder.add(new VeryLargeInteger(divisor, 0));
				quotient = quotient + "0";
			} else {
				quotient = quotient + "1";
			}

			// shift divisor right and add "0" to maintain width

			divisor = "0" + rshift(divisor, 1);
		}

		// fix negatives
		if (isNegative) {
			quotient = twosCompliment(quotient);
		}

		return remainder;
	}

	public String toString() {
		String ret = "", temp;
		boolean isNeg = bitString.charAt(0) == '1';

		if (isNeg) { // makes numbers positive before converting to base 10
			temp = twosCompliment(bitString);
		} else {
			temp = new String(bitString);
		}

		// converts form base 2 to base 10
		while (!temp.isEmpty()) {
			int digit = 0;
			String next = "";

			for (Character bit : temp.toCharArray()) {
				digit = digit * 2 + (bit == '1' ? 1 : 0);

				if (digit > 9) {
					next += "1";
					digit -= 10;
				} else {
					next += "0";
				}
			}

			ret = digit + ret;

			temp = next.replaceAll("^0+", "");
		}

		if (isNeg) {
			ret = "-" + ret;
		}

		return ret;
	}

	private static String twosCompliment(String num) {
		String inverse = "", result = "";
		
		// returns 0 when attempting to negate 0
		if (num.matches("^0+$")) {
			return num;
		}

		for (Character c : num.toCharArray()) {
			if (c == '1')
				inverse += "0";
			else
				inverse += "1";
		}

		boolean added = false;

		for (int i = inverse.length() - 1; i >= 0; i--) {
			if (!added) {
				if (inverse.charAt(i) == '0') {
					result = "1" + result;
					added = true;
				} else {
					result = "0" + result;
				}
			} else {
				result = inverse.charAt(i) + result;
			}
		}

		if (!added) {
			result = "1" + result;
		}

		return result;
	}

	private String setWidth(String bits, int width) {
		// width of the number can be changed without changing value by
		// prepending 0's or 1's if it is positive or negative
		while (bits.length() < width)
			bits = bits.charAt(0) + bits;

		return bits;
	}

	private String lshift(String bits, int ammount) {
		for (int i = 0; i < ammount; i++) {
			bits += "0";
		}

		return bits;
	}

	private String rshift(String bits, int ammount) {
		return bits.substring(0, bits.length() - ammount);
	}

	private String bitString;
}