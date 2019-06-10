package com.ef.wallethub;

import com.ef.wallethub.domain.LogRegistry;
import com.ef.wallethub.parser.LogParser;
import com.ef.wallethub.service.LogService;
import com.ef.wallethub.utilities.Utils;

import java.util.Date;
import java.util.List;

public class Parser {

    public static void main(String[] args) throws Exception {

        LogParser logParser = new LogParser(new LogService());

        if (args.length < 4) {
            System.out.println("Number or arguments incorrect. The arguments should be startDate, duration and threshold.");
        }

        String logPath = args[0];
        if (!logPath.contains("--accesslog=")) {
            throw new Exception("accesslog parameter name incorrect (--accesslog=XXX)");
        }

        String startDate = args[1];
        if (!startDate.contains("--startDate=")) {
            throw new Exception("startDate parameter name incorrect (--startDate=XXX)");
        }
        Date _startDate = null;
        try {
            _startDate = Utils.parseDate(startDate.split("=")[1], "yyyy-MM-dd.HH:mm:ss");
        } catch (Exception ex) {
            System.out.println("startDate argument can be only with yyyy-MM-dd.HH:mm:ss format value: " + ex.getMessage());
        }

        String duration = args[2];
        if (!duration.contains("--duration=")) {
            throw new Exception("duration parameter name incorrect (--duration=XXX)");
        }
        duration = duration.split("=")[1];
        if (!(duration.equals("hourly") || duration.equals("daily"))) {
            System.out.println("Duration argument can take only 'daily' or 'hourly' values.");
        }

        String threshold = args[3];
        if (!threshold.contains("--threshold=")) {
            throw new Exception("threshold parameter name incorrect (--threshold=XXX)");
        }
        Integer _threshold = null;
        try {
            _threshold = Integer.parseInt(threshold.split("=")[1]);
        } catch (NumberFormatException ex) {
            System.out.println("threshold argument must be an integer:" + ex.getMessage());
        }

        logParser.emptyLog();
        logParser.loadAccessLog(logPath.split("=")[1], "\\|");

        List<LogRegistry> logRegistryList = logParser.getIpOfCertainRequests(_startDate, duration, _threshold);
        logParser.recordExecution(logRegistryList, logPath.split("=")[1], _startDate, duration, _threshold);

        logParser.printIps(logRegistryList);

        logParser.closeConnection();
    }


}
