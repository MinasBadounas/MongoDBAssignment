package com.assignment.wellics.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.wellics.model.DailyMetricsDTO;
import com.assignment.wellics.model.Metrics;
import com.assignment.wellics.model.WeeklyMetricsDTO;
import com.assignment.wellics.service.MetricsService;
import com.assignment.wellics.tools.Tools;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

	@Autowired
	private MetricsService metricsService;

	@GetMapping(value = "", produces = "application/json")
	public List<Metrics> getMetricsPerDepartmentAndType( @RequestParam("department") String department, @RequestParam("type") String type) {

		return metricsService.findMetricsByDepartmentAndType(department, type);

	}

	@GetMapping(value = "/daily", produces = "application/json")
	public List<DailyMetricsDTO> getDailyMetricsPerDepartmentAndType(@RequestParam("department") String department,
			@RequestParam("type") String type, @RequestParam("daysBack") int daysBack) {

		List<DailyMetricsDTO> dailyMetricsList = new ArrayList<DailyMetricsDTO>();

		dailyMetricsList =metricsService.findDailyMetricsByDepartmentAndType(department, type, daysBack);
				
		if (dailyMetricsList.isEmpty()) {
			ArrayList<DailyMetricsDTO> dailyMetricsArrayList = new ArrayList<DailyMetricsDTO>();
			dailyMetricsArrayList.add(Tools.setEmptyDailyMetrics());
			return dailyMetricsArrayList;
		}
		
		return dailyMetricsList;

	}

	@GetMapping(value = "/weekly", produces = "application/json")
	public List<WeeklyMetricsDTO> getWeeklyMetricsPerDepartmentAndType(@RequestParam("department") String department,
			@RequestParam("type") String type, @RequestParam("weeksBack") int weeksBack) {

		List<WeeklyMetricsDTO> weeklyMetricsList = new ArrayList<WeeklyMetricsDTO>();

		weeklyMetricsList = metricsService.findWeeklyMetricsByDepartmentAndType(department, type, weeksBack);

		if (weeklyMetricsList.isEmpty()) {
			ArrayList<WeeklyMetricsDTO> weeklyMetricsArrayList = new ArrayList<WeeklyMetricsDTO>();
			weeklyMetricsArrayList.add(Tools.setEmptyWeeklyMetrics());
			return weeklyMetricsArrayList;
		}

		return weeklyMetricsList;

	}

}
