package com.ef.wallethub.parser;

import com.ef.wallethub.domain.LogRegistry;
import com.ef.wallethub.service.LogService;
import com.ef.wallethub.utilities.Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

public class LogParserTest {

    @Test
    public void loadAccessLogShouldCallLoadLogFile(){
        LogService logService = mock(LogService.class);

        LogParser logParser = new LogParser(logService);

        logParser.loadAccessLog("/home/access.log", "|");
        verify(logService, times(1)).loadLogFile("/home/access.log", "|");
    }

    @Test
    public void getIpOfCertainRequestsShouldCallGetLogRegistriesWithDailyDates(){
        String startDate = "2017-01-01.15:00:00";
        Date _startDate = null;
        try {
            _startDate = Utils.parseDate(startDate, "yyyy-MM-dd.HH:mm:ss");
        } catch (Exception e) {
            e.printStackTrace();
        }

        LogService logService = mock(LogService.class);

        LogParser logParser = new LogParser(logService);

        logParser.getIpOfCertainRequests(_startDate,"daily", 100);
        verify(logService, times(1)).getLogRegistries("2017-01-01 15:00:00", "2017-01-02 15:00:00","daily", 100);
    }

    @Test
    public void getIpOfCertainRequestsShouldCallGetLogRegistriesWithHourlyDates(){
        String startDate = "2017-01-01.15:00:00";
        Date _startDate = null;
        try {
            _startDate = Utils.parseDate(startDate, "yyyy-MM-dd.HH:mm:ss");
        } catch (Exception e) {
            e.printStackTrace();
        }

        LogService logService = mock(LogService.class);

        LogParser logParser = new LogParser(logService);

        logParser.getIpOfCertainRequests(_startDate,"hourly", 100);
        verify(logService, times(1)).getLogRegistries("2017-01-01 15:00:00", "2017-01-01 16:00:00","hourly", 100);
    }

    @Test
    public void recordExecutionShouldCallInsertLogExecution(){
        List<LogRegistry> logRegistryList = new ArrayList<>();
        String startDate = "2017-01-01.15:00:00";
        Date _startDate = null;
        try {
            _startDate = Utils.parseDate(startDate, "yyyy-MM-dd.HH:mm:ss");
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogService logService = mock(LogService.class);

        LogParser logParser = new LogParser(logService);

        logParser.recordExecution(logRegistryList, "/home/access.log", _startDate, "daily", 100);
        verify(logService, times(1)).insertLogExecution(logRegistryList, "/home/access.log", _startDate, "daily", 100);
    }

    @Test
    public void emptyLogShouldCallDeleteLogs(){
        LogService logService = mock(LogService.class);

        LogParser logParser = new LogParser(logService);

        logParser.emptyLog();
        verify(logService, times(1)).deleteLogs();
    }

    @Test
    public void closeConnectionShouldCloseConnection(){
        LogService logService = mock(LogService.class);

        LogParser logParser = new LogParser(logService);

        logParser.closeConnection();
        verify(logService, times(1)).closeConnection();
    }
}
