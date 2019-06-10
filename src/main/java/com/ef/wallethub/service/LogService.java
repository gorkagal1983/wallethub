package com.ef.wallethub.service;

import com.ef.wallethub.database.WalletHubConnection;
import com.ef.wallethub.domain.LogRegistry;
import com.ef.wallethub.utilities.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class LogService {

    private WalletHubConnection walletHubConnection;


    public LogService() {
        this.walletHubConnection = WalletHubConnection.getConnection();
    }

    /**
     * @param filePath
     * @param delimiter
     */
    public void loadLogFile(String filePath, String delimiter) {
        StringBuffer loadData = new StringBuffer(" load data infile '" + filePath + "' into table wallethub.log fields terminated by '" + delimiter + "' lines terminated by '\\n' (access_date,ip,request,status,user_agent) SET log_id=UUID_TO_BIN(UUID());");

        try {
            Statement stmt = this.walletHubConnection.getConn().createStatement();

            stmt.execute(loadData.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param startDate
     * @param endDate
     * @param duration
     * @param threshold
     * @return
     */
    public List<LogRegistry> getLogRegistries(String startDate, String endDate, String duration, Integer threshold) {

        List<LogRegistry> logRegistries = new ArrayList<>();
        StringBuffer select = new StringBuffer("select tmp.ip from (select count(*) as 'times', ip from wallethub.log where access_date > ");
        select.append(" '" + startDate + "' and access_date < '" + endDate + "'");
        select.append(" group by ip) as tmp where tmp.times > " + threshold);

        try {
            Statement stmt = this.walletHubConnection.getConn().createStatement();

            ResultSet resultSet = stmt.executeQuery(select.toString());
            while (resultSet.next()) {
                LogRegistry logRegistry = new LogRegistry();
                logRegistry.setIp(resultSet.getString(1));
                logRegistries.add(logRegistry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logRegistries;
    }

    public void insertLogExecution(List<LogRegistry> logRegistryList, String accessPath, Date startDate, String duration, Integer threshold) {

        try {
            UUID uuid = UUID.randomUUID();
            String executionDate = Utils.formatDate(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss");
            String _startDate = Utils.formatDate(startDate, "yyyy-MM-dd HH:mm:ss");

            StringBuffer insert = new StringBuffer("insert into wallethub.log_execution (execution_id, execution_date, ip, access_log_path, start_date, duration, threshold) ");
            insert.append(" values (UUID_TO_BIN('" + uuid + "'),'" + executionDate + "',?,'" + accessPath + "','" + _startDate + "','" + duration + "'," + threshold + ")");

            PreparedStatement preparedStatement = this.walletHubConnection.getConn().prepareStatement(insert.toString());

            logRegistryList.forEach(elem -> {
                try {
                    preparedStatement.setString(1, elem.getIp());
                    preparedStatement.addBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            preparedStatement.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void deleteLogs() {
        try {
            Statement stmt = this.walletHubConnection.getConn().createStatement();

            stmt.execute("delete from wallethub.log");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void closeConnection() {
        this.walletHubConnection.close();
    }


}
