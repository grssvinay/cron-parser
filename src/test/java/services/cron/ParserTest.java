package services.cron;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

class ParserTest {

	@Test
	void test() throws Exception {
		Map<String, String> response = Parser.parse("*/15 0 1,15 * 1-5 /user/bin/find");
		assertEquals("0 15 30 45", response.get("minute"));
		assertEquals("0", response.get("hour"));
		assertEquals("1 15", response.get("day of month"));
		assertEquals("1 2 3 4 5 6 7 8 9 10 11 12", response.get("month"));
		assertEquals("1 2 3 4 5", response.get("day of week"));
		assertEquals("/user/bin/find", response.get("command"));
	}

	@Test
	void testRangeWithComma() throws Exception {
		Map<String, String> response = Parser.parse("2-10/15 0/7 1,13-15 * SUN /user/bin/find");
		assertEquals("2", response.get("minute"));
		assertEquals("0 7 14 21", response.get("hour"));
		assertEquals("1 13 14 15", response.get("day of month"));
		assertEquals("1 2 3 4 5 6 7 8 9 10 11 12", response.get("month"));
		assertEquals("0", response.get("day of week"));
		assertEquals("/user/bin/find", response.get("command"));
	}

	@Test
	void testRangeWithDayOfWeekText() throws Exception {
		Map<String, String> response = Parser.parse("2-10/15 0/7 1,13-15 * SUN-5/2 /user/bin/find");
		assertEquals("2", response.get("minute"));
		assertEquals("0 7 14 21", response.get("hour"));
		assertEquals("1 13 14 15", response.get("day of month"));
		assertEquals("1 2 3 4 5 6 7 8 9 10 11 12", response.get("month"));
		assertEquals("0 2 4", response.get("day of week"));
		assertEquals("/user/bin/find", response.get("command"));
	}

	@Test
	void testErrorWithDoubleAsterisk() throws Exception {
		Exception e = assertThrows(NumberFormatException.class,
				() -> Parser.parse("2-10/15 0/7 1,13-15 ** SUN /user/bin/find"));
		assertEquals("For input string: \"**\"", e.getMessage());
	}

	@Test
	void testErrorWithExceedRange() throws Exception {
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> Parser.parse("2-10/15 0/7 1,13-15 * 1-9 /user/bin/find"));
		assertEquals("1-9", e.getMessage());
	}

	@Test
	void testErrorWithSpecialCharStartingPattern() throws Exception {
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> Parser.parse("2-10/15 0/7 1,-13-15 * 1-9 /user/bin/find"));
		assertEquals("-13-15", e.getMessage());
	}
}
