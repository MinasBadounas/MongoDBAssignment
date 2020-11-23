package com.assignment.wellics.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.assignment.wellics.model.Metrics;

@Repository
public interface MetricsRepository extends MongoRepository<Metrics, Long> {

	@Query(value = "{ 'department' : ?0 , 'type' : ?1 }", sort = "{registrationDate: 1}}")
	public List<Metrics> findMetricsByDepartmentAndType(String department, String type);

}
