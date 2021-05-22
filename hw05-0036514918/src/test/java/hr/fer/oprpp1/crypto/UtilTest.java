package hr.fer.oprpp1.crypto;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void testNonHexString() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("hello!"));
	}
	
	@Test
	void testEmptyString() {
		assertArrayEquals(new byte[0], Util.hextobyte(""));
	}

	@Test
	void testHextobyte() {
		assertArrayEquals(new byte[] {1, -82, 34}, Util.hextobyte("01aE22"));
	}
	
	@Test
	void testbyteToHex() {
		assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
	}
}
