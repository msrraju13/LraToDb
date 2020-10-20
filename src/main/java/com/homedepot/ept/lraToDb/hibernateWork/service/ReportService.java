package com.homedepot.ept.lraToDb.hibernateWork.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homedepot.ept.lraToDb.hibernateWork.repos.ReportRepo;
import com.homedepot.ept.lraToDb.report.Report;

import java.io.IOException;
import java.text.ParseException;

@Service
public class ReportService {
    @Autowired
    private ReportRepo reportRepo;

    public ReportService() {
    }

    public void saveReport(String absolutePath) throws IOException, ParseException {
        long id=reportRepo.count();
        Report report = new Report(absolutePath,id);
        System.out.println("end create report");
        reportRepo.save(report);
    }

    public Iterable<Report> getListReports() throws IOException, ParseException {

        return reportRepo.findAll();
    }

    public long countReports() throws IOException, ParseException {

        return reportRepo.count();
    }
}
