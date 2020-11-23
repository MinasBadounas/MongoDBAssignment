package com.assignment.wellics.insertdata;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.assignment.wellics.model.Metrics;
import com.assignment.wellics.repository.MetricsRepository;
import com.assignment.wellics.tools.Tools;

@Component
public class DbSeeder implements CommandLineRunner {

	@Autowired
	private MetricsRepository metricsRepository;

	@Override
	public void run(String... args) throws Exception {

		DateFormat df = new SimpleDateFormat("dd/MM/yy'T'HH:mm:ss.SSSZ");

		List<String> dateList = new ArrayList<String>();
		dateList.add("06/10/2020");
		dateList.add("07/10/2020");
		dateList.add("06/10/2019");
		dateList.add("07/10/2019");
		dateList.add("06/10/2018");
		dateList.add("07/10/2018");
		dateList.add("07/05/2020");
		dateList.add("07/03/2020");

		List<String> timeList = new ArrayList<String>();
		timeList.add("T19:00:00.000+0000");
		timeList.add("T19:00:00.000+0000");
		timeList.add("T19:00:00.000+0000");
		timeList.add("T19:00:00.000+0000");
		timeList.add("T19:00:00.000+0000");
		timeList.add("T19:00:00.000+0000");
		timeList.add("T19:00:00.000+0000");
		timeList.add("T19:00:00.000+0000");

		List<Date> dateTimeList = new ArrayList<Date>();

		for (String date : dateList) {
			for (String time : timeList) {
				dateTimeList.add(df.parse(date.concat(time)));
			}
		}

		List<String> typeList = new ArrayList<String>();
		typeList.add("Light");
		typeList.add("Noise");
		typeList.add("Humidity");

		List<String> departmentList = new ArrayList<String>();
		departmentList.add("departmentA");
		departmentList.add("departmentB");
		departmentList.add("departmentC");

		long countID = 0;

		List<Metrics> importMetrics = new ArrayList<>();

		for (String department : departmentList) {
			for (String type : typeList) {
				for (Date date : dateTimeList) {
					Metrics metric = new Metrics(countID, type, department, date, Tools.randomValue(type));
					importMetrics.add(metric);
					countID++;
				}
			}
		}

		metricsRepository.deleteAll();

		System.out.println("#####Import Metrics#######" + importMetrics.toString());
		metricsRepository.saveAll(importMetrics);

	}

}
