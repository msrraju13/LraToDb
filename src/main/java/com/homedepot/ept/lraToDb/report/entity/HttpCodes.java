package com.homedepot.ept.lraToDb.report.entity;

import lombok.Data;
import org.jsoup.nodes.Element;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
@Data
@Entity
public class HttpCodes implements Serializable {
  
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int total;
    private double perSecond;

    public HttpCodes() {
    }

    public HttpCodes(Element s) {
        this.name = s.select("td[headers^=LraHTTP Responses] span").text();
        this.total = Integer.parseInt(s.select("td[headers^=LraTotal] span").text().replaceAll(" ","").replaceAll(",", ""));
        this.perSecond = Double.parseDouble(s.select("td[headers^=LraPer second] span").text().replaceAll(" ",""));
    }
}
