package com.homedepot.ept.lraToDb.hibernateWork.repos;

import org.springframework.data.repository.CrudRepository;

import com.homedepot.ept.lraToDb.report.Report;

public interface ReportRepo extends CrudRepository<Report, Long> {
}
