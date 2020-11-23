package com.assignment.wellics.tools;

import java.util.Calendar;
import java.util.Random;

import com.assignment.wellics.model.DailyMetricsDTO;
import com.assignment.wellics.model.WeeklyMetricsDTO;

public class Tools {

	public static Calendar getCalendar(int year, int month, int day) {

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(year, month, day);

		return calendar;
	}

	public static Calendar getDaysBackCalendarDate(int daysBack) {

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -daysBack);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar;
	}

	public static int getTotalWeeksInYear(int year) {
		return getCalendar(year, Calendar.DECEMBER, 31).get(Calendar.WEEK_OF_YEAR);
	}

	public static WeeklyMetricsDTO setEmptyWeeklyMetrics() {

		WeeklyMetricsDTO emptyWeeklyMetricsDTO = new WeeklyMetricsDTO();

		emptyWeeklyMetricsDTO.setweek("No Data");

		return emptyWeeklyMetricsDTO;

	}

	public static DailyMetricsDTO setEmptyDailyMetrics() {

		DailyMetricsDTO emptyDailyMetricsDTO = new DailyMetricsDTO();

		emptyDailyMetricsDTO.setDate("No Data");

		return emptyDailyMetricsDTO;

	}

	public static int randomValue(String type) {

		Random rn = new Random();

		switch (type) {
		case "Light":
			int rangeLight = 600 - 200 + 1;
			int randomNumLight = rn.nextInt(rangeLight) + 200;
			return randomNumLight;
		case "Noise":
			int rangeNoise = 150 - 10 + 1;
			int randomNumNoise = rn.nextInt(rangeNoise) + 10;
			return randomNumNoise;
		case "Humidity":
			int rangeHumidity = 100 - 0 + 1;
			int randomNumHumidity = rn.nextInt(rangeHumidity) + 0;
			return randomNumHumidity;
		}

		return 0;

	}

}
