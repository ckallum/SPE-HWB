package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;




@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void test() {
		assertEquals("Friday", DemoApplication.getDayOfWeek(25, 10, 2019));
		assertEquals("Wednesday", DemoApplication.getDayOfWeek(1, 1, 2020));
		assertEquals("Saturday", DemoApplication.getDayOfWeek(29,2,2020));
		assertEquals("Unknown", DemoApplication.getDayOfWeek(29,2,2019));
		assertEquals("Unknown", DemoApplication.getDayOfWeek(31, 4, 2019));
		assertEquals("Unknown", DemoApplication.getDayOfWeek(32, 1, 2019));
		assertEquals("Tuesday", DemoApplication.getDayOfWeek(31, 12, 2019));
		
	}

}
