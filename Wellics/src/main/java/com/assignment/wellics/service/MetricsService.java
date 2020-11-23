package com.assignment.wellics.service;

import java.util.List;

import com.assignment.wellics.model.DailyMetricsDTO;
import com.assignment.wellics.model.Metrics;
import com.assignment.wellics.model.WeeklyMetricsDTO;

public interface MetricsService {

	public List<Metrics> findMetricsByDepartmentAndType(String department, String type);

	public List<DailyMetricsDTO> findDailyMetricsByDepartmentAndType(String department, String type, int daysBack);

	public List<WeeklyMetricsDTO> findWeeklyMetricsByDepartmentAndType(String department, String type, int weeksBack);
}
