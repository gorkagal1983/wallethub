package com.ef.wallethub.parser;

import com.ef.wallethub.domain.LogRegistry;
import com.ef.wallethub.service.LogService;
import com.ef.wallethub.utilities.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogParser {

    private LogService logService;
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public LogParser(LogService logService) {
        this.logService = logService;
    }

    /**
     * ç
     * @param path
     * @param delimiter
     */
    public void loadAccessLog(String path, String delimiter) {
        logService.loadLogFile(path, delimiter);
    }

    /**
     *
     * @param startDate
     * @param duration
     * @param threshold
     * @return
     */
    public List<LogRegistry> getIpOfCertainRequests(Date startDate, String duration, Integer threshold) {
        Date endDate = null;
        List<LogRegistry> logRegistryList = new ArrayList<>();

        if (duration.equals("daily")) {
            endDate = Utils.addDays(startDate, 1);
        } else if (duration.equals("hourly")) {
            endDate = Utils.addHours(startDate, 1);
        }

        try {
            String _startDate = Utils.formatDate(startDate, dateFormat);
            String _endDate = Utils.formatDate(endDate, dateFormat);
            logRegistryList = logService.getLogRegistries(_startDate, _endDate, duration, threshold);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return logRegistryList;
    }

    /**
     *
     * @param logRegistryList
     * @param accessPath
     * @param startDate
     * @param duration
     * @param threshold
     */
    public void recordExecution(List<LogRegistry> logRegistryList, String accessPath, Date startDate, String duration, Integer threshold) {
        logService.insertLogExecution(logRegistryList, accessPath, startDate, duration, threshold);
    }

    /**
     *
     * @param logRegistryList
     */
    public void printIps(List<LogRegistry> logRegistryList) {
        logRegistryList.forEach(elem -> System.out.println(elem.getIp()));
    }

    /**
     *
     */
    public void closeConnection() {
        logService.closeConnection();
    }

    /**
     *
     */
    public void emptyLog() {
        logService.deleteLogs();
    }

}
