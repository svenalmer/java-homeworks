package hr.fer.oprpp1.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexNumberTest {

	@Test
	final void testComplexNumber() {
		ComplexNumber n = new ComplexNumber(1.6, -3.2);
		assertEquals(1.6, n.getReal());
		assertEquals(-3.2, n.getImaginary());
	}

	@Test
	final void testFromReal() {
		ComplexNumber n = ComplexNumber.fromReal(5);
		assertEquals(5, n.getReal());
		assertEquals(0, n.getImaginary());
	}

	@Test
	final void testFromImaginary() {
		ComplexNumber n = ComplexNumber.fromImaginary(5);
		assertEquals(0, n.getReal());
		assertEquals(5, n.getImaginary());
	}

	@Test
	final void testFromMagnitudeAndAngle() {
		ComplexNumber n = ComplexNumber.fromMagnitudeAndAngle(1, Math.PI / 4);
		assertEquals(0.7071067811865476, n.getReal());
		assertEquals(0.7071067811865475, n.getImaginary());
	}

	@Test
	final void testParse() {
		ComplexNumber n = ComplexNumber.parse("+7.9-12.6i");
		ComplexNumber control = new ComplexNumber(7.9, -12.6);
		assertEquals(control.getReal(), n.getReal());
		assertEquals(control.getImaginary(), n.getImaginary());
	}
	
	@Test
	final void testParseException() {
		assertThrows(IllegalArgumentException.class, () -> ComplexNumber.parse("5.1-i9.2"));
	}

	@Test
	final void testGetReal() {
		ComplexNumber n = ComplexNumber.fromReal(5);
		assertEquals(5, n.getReal());
	}

	@Test
	final void testGetImaginary() {
		ComplexNumber n = ComplexNumber.fromImaginary(5);
		assertEquals(5, n.getImaginary());
	}

	@Test
	final void testGetMagnitude() {
		ComplexNumber n = ComplexNumber.fromMagnitudeAndAngle(1, Math.PI / 4);
		assertEquals(1, n.getMagnitude());
	}

	@Test
	final void testGetAngle() {
		ComplexNumber n = ComplexNumber.fromMagnitudeAndAngle(1, Math.PI / 4);
		assertEquals(Math.PI / 4, n.getAngle());
	}

	@Test
	final void testAdd() {
		ComplexNumber n1 = new ComplexNumber(1, -2);
		ComplexNumber n2 = new ComplexNumber(4, 6);
		
		ComplexNumber result = n1.add(n2);
		assertEquals(5, result.getReal());
		assertEquals(4, result.getImaginary());
	}

	@Test
	final void testSub() {
		ComplexNumber n1 = new ComplexNumber(1, -2);
		ComplexNumber n2 = new ComplexNumber(4, 6);
		
		ComplexNumber result = n1.sub(n2);
		assertEquals(-3, result.getReal());
		assertEquals(-8, result.getImaginary());
	}

	@Test
	final void testMul() {
		ComplexNumber n1 = new ComplexNumber(1, -2);
		ComplexNumber n2 = new ComplexNumber(4, 6);
		
		ComplexNumber result = n1.mul(n2);
		assertEquals(16, result.getReal());
		assertEquals(-2, result.getImaginary());
	}

	@Test
	final void testDiv() {
		ComplexNumber n1 = new ComplexNumber(1, -2);
		ComplexNumber n2 = new ComplexNumber(4, 6);
		
		ComplexNumber result = n1.div(n2);
		assertEquals(-0.1538461538461538, result.getReal());
		assertEquals(-0.2692307692307693, result.getImaginary());
	}

	@Test
	final void testPower() {
		ComplexNumber n1 = new ComplexNumber(1, -2);
		
		ComplexNumber result = n1.power(2);
		
		assertEquals(-3.0000000000000013, result.getReal());
		assertEquals(-4, result.getImaginary());
	}

	@Test
	final void testRoot() {
		ComplexNumber n1 = new ComplexNumber(1, -2);
		
		ComplexNumber[] result = n1.root(2);
		
		assertEquals(1.2720196495140692, result[1].getReal());
		assertEquals(-0.7861513777574229, result[1].getImaginary());
	}

	@Test
	final void testToString() {
		ComplexNumber n = new ComplexNumber(1, -2);
		assertEquals("1.0-2.0i", n.toString());
	}

}
