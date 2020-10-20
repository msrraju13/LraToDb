package com.homedepot.ept.lraToDb.report.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
@Data
@Entity
public class Transactions implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    double minimum;
    double average;
    double maximum;
    double percent90;

    public Transactions() {
    }

    double stdDeviation;
    int pass;
    int fail;
    int stop;
    String transactionName;

    public Transactions(String tmp1) {
        String[] tmp = tmp1.split(" ");
        transactionName = tmp[0];
        minimum = Double.parseDouble(tmp[1]);
        average = Double.parseDouble(tmp[2]);
        maximum = Double.parseDouble(tmp[3]);
        stdDeviation = Double.parseDouble(tmp[4]);
        percent90 = Double.parseDouble(tmp[5]);
        pass = Integer.parseInt(tmp[6].replaceAll(",", ""));
        fail = Integer.parseInt(tmp[7].replaceAll(",", ""));
        stop = Integer.parseInt(tmp[8].replaceAll(",", ""));
    }
}
