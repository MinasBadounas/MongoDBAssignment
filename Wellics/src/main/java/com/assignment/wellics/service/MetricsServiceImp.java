package com.assignment.wellics.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.assignment.wellics.model.DailyMetricsDTO;
import com.assignment.wellics.model.Metrics;
import com.assignment.wellics.model.WeeklyMetricsDTO;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import com.assignment.wellics.repository.MetricsRepository;
import com.assignment.wellics.tools.Tools;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
public class MetricsServiceImp implements MetricsService {

	@Autowired
	private MetricsRepository metricsRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Metrics> findMetricsByDepartmentAndType(String department, String type) {

		List<Metrics> metricsListByDepartmentAndType = metricsRepository.findMetricsByDepartmentAndType(department,
				type);

		return metricsListByDepartmentAndType;
	}

	@Override
	public List<DailyMetricsDTO> findDailyMetricsByDepartmentAndType(String department, String type, int daysBack) {

		Calendar exactDate = Tools.getDaysBackCalendarDate(daysBack);

		MatchOperation matchOperation = Aggregation
				.match(new Criteria("department").is(department).andOperator(Criteria.where("type").is(type)));
		MatchOperation matchDateOperation = Aggregation.match(new Criteria("registrationDate").gt(exactDate.getTime()));

		ProjectionOperation projectToDateModel = Aggregation.project().and("registrationDate")
				.dateAsFormattedString("%d-%m-%Y").as("date").and("registrationDate").as("registrationDate")
				.and("value").as("value");

		GroupOperation groupOperation = group("date").addToSet("date").as("date").min("registrationDate")
				.as("registrationDate").avg("value").as("average").min("value").as("minimum").max("value")
				.as("maximum");

		ProjectionOperation projectToTotalModel = Aggregation.project().and("date").as("date").and("average")
				.substring(0, 6).as("average").and("minimum").as("minimum").and("maximum").as("maximum")
				.and("registrationDate").as("registrationDate");

		SortOperation sortBy = Aggregation.sort(Sort.by(Order.asc("registrationDate")));

		Aggregation aggregation = newAggregation(matchOperation, matchDateOperation, projectToDateModel,
				groupOperation, projectToTotalModel, sortBy);

		return mongoTemplate.aggregate(aggregation, "metrics", DailyMetricsDTO.class).getMappedResults();
	}

	@Override
	public List<WeeklyMetricsDTO> findWeeklyMetricsByDepartmentAndType(String department, String type, int weeksBack) {

		Calendar exactDate = Calendar.getInstance();

		MatchOperation matchOperation = Aggregation
				.match(new Criteria("department").is(department).andOperator(Criteria.where("type").is(type)));

		ProjectionOperation projectToDateModel = Aggregation.project().and("registrationDate").extractWeek().as("week")
				.and("value").as("value").and("registrationDate").as("date").and("registrationDate").extractYear()
				.as("year");

		MatchOperation matchDateOperation = null;

		List<Criteria> orCriteriaList = new ArrayList<Criteria>();
		int weeksOfYear = exactDate.get(Calendar.WEEK_OF_YEAR);
		int year = exactDate.get(Calendar.YEAR);
		int previousYear = 0;
		int newWeeksOfYear = 0;

		while (weeksOfYear - weeksBack < 0 || previousYear == 0) {

			Criteria criteria = new Criteria();

			criteria.orOperator(Criteria.where("year").gte(year).and("week").gte(weeksOfYear - weeksBack));

			orCriteriaList.add(criteria);

			previousYear++;
			year--;
			exactDate.set(Calendar.YEAR - previousYear, 1, 1);
			newWeeksOfYear = exactDate.get(Calendar.WEEK_OF_YEAR);
			weeksBack = newWeeksOfYear - (weeksOfYear - weeksBack);

		}

		Criteria matchDateCriteria = new Criteria()
				.orOperator(orCriteriaList.toArray(new Criteria[orCriteriaList.size()]));
		matchDateOperation = Aggregation.match(matchDateCriteria);

		GroupOperation groupOperation = group("year", "week").addToSet("week").as("week").avg("value").as("average")
				.min("value").as("minimum").max("value").as("maximum").min("date").as("dateFrom").max("date")
				.as("dateTo").addToSet("year").as("year");

		ProjectionOperation projectToTotalModel = Aggregation.project().and("week").as("week").and("average")
				.substring(0, 6).as("average").and("minimum").as("minimum").and("maximum").as("maximum").and("dateFrom")
				.as("dateFrom").and("dateTo").as("dateTo").and("year").as("year");

		SortOperation sortBy = Aggregation.sort(Sort.by(Order.asc("dateFrom")));

		Aggregation aggregation = newAggregation(matchOperation, projectToDateModel, matchDateOperation, groupOperation,
				projectToTotalModel, sortBy);

		return mongoTemplate.aggregate(aggregation, "metrics", WeeklyMetricsDTO.class).getMappedResults();
	}

}
