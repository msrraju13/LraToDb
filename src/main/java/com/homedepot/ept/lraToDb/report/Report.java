package com.homedepot.ept.lraToDb.report;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.homedepot.ept.lraToDb.report.entity.HttpCodes;
import com.homedepot.ept.lraToDb.report.entity.RunSummary;
import com.homedepot.ept.lraToDb.report.entity.Transactions;
import com.homedepot.ept.lraToDb.utilites.FileUtils;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Report implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "TransactionsRunId", nullable = false)
    private Set<Transactions> transactions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "HttpSummaryId", nullable = false)
    private Set<HttpCodes> httpCodes = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "runSummary", nullable = false)
    RunSummary runSummary = new RunSummary();

    public Report(String reportFileName, long id) throws IOException, ParseException {
        Document reportDoc = FileUtils.ReadFile(reportFileName);
        System.out.println("runHttpCodes");
        CreateListHttpCodes(reportDoc);
        System.out.println("runTransactions");
        CreateLisTransactions(reportDoc);
        System.out.println("runSummary");
        runSummary = new RunSummary(reportDoc);
        name = runSummary.getStartDate().toString();
        this.id = id+1;
    }

    public void CreateListHttpCodes(Document reportDoc) {
        Elements list = reportDoc.select("tr[class$=tabledata_lightrow]");
        list.addAll(reportDoc.select("tr[class$=tabledata_darkrow]"));
        for (Element s : list) {
            if (s.text().contains("HTTP")) {
                httpCodes.add(new HttpCodes(s));
            }
        }
    }

    public Report() {
    }

    public void CreateLisTransactions(Document document) {
        List<String> list = document.select("tr[class$=tabledata_lightrow]").eachText();
        list.addAll(document.select("tr[class$=tabledata_darkrow]").eachText());
        System.out.println("for");
        for (String string : list) {
            if (!string.contains("HTTP")) {
                transactions.add(new Transactions(string));
            }
        }
    }
}
