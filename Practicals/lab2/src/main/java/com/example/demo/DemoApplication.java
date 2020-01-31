package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	static String[] daysOfWeek = {"Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday","Friday"};

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);

	}

	public static String getDayOfWeek(int d, int m, int y)
	{
		// Check if the month is January or February
		// If the month is January or February, convert to 13 or 14
		// and reduce the year by one (go to previous year).
		if (m <= 0 || m > 12) {
			return "Unknown";
		}

		if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
			if (d > 31 || d < 0) {
				return "Unknown";
			}
		}
		else if (m == 4 || m == 6 || m == 9 || m == 7 || m == 9 || m == 11){
			if (d > 30 || d < 0) {
				return "Unknown";
			}
		}
		else {
			if (y % 4 == 0) {
				if (d > 29 || d < 0) {
					return "Unknown";
				}
			}
			else {
				if (d > 28 || d < 0) {
					return "Unknown";
				}
			}
 		}

		if (m == 1 || m == 2) {
			m += 12;
			y--;
		}
		int h = (d + (int)((26 * (m + 1)) / 10.0) + y + (int)(y / 4.0) +
				6 * (int)(y / 100.0) + (int)(y / 400.0)) % 7;
		return daysOfWeek[h];
	}

}
