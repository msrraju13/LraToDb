package com.homedepot.ept.lraToDb.hibernateWork.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.homedepot.ept.lraToDb.hibernateWork.service.ReportService;

@org.springframework.stereotype.Controller
public class Controller {

    private static final Logger log = LogManager.getLogger(Controller.class);
    
    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file", required = false) MultipartFile file1) throws IOException, ParseException {
        log.info("TEST");
        try {
            String name = null;
            log.info("!file1.isEmpty() = " + !file1.isEmpty());
            if (!file1.isEmpty()) {
                log.info("TEST11");
                byte[] bytes = file1.getBytes();
                name = file1.getOriginalFilename();
                //String rootPath = "c:\\tmp1\\";  //try also "C:\path\"
                String rootPath = "/Users/sxm8022/Downloads/";  //try also "C:\path\"
                File dir = new File(rootPath + File.separator + "loadFiles");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                log.info("TEST12");
                File uploadedFile = new File(dir.getAbsolutePath() + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                stream.write(bytes);
                stream.flush();
                stream.close();
                log.info("TEST13");
                log.info("uploaded: " + uploadedFile.getAbsolutePath());
                reportService.saveReport(uploadedFile.getAbsolutePath());
                log.info("Saved data in DB");

            }
        } catch (Exception e) {
            log.info("TEST2");
            log.error(e.getMessage(), e.getCause());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"Status\":\"Failure\",\"Description\":\"" + e.getMessage() +"\"}");
        }
		return ResponseEntity.status(HttpStatus.OK).body("{\"Status\":\"Success\",\"Description\":\"Uploaded succesfully\"}");
    }
}