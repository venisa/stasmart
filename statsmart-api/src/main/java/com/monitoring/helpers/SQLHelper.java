package com.monitoring.helpers;

import com.monitoring.config.SystemConfig;
import com.monitoring.models.CPU;
import com.monitoring.models.CPUFields;
import com.monitoring.models.Host;
import com.monitoring.models.Memory;
import com.monitoring.models.MemoryFields;
import com.monitoring.models.Metric;
import com.monitoring.models.Statistics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class containing methods to execute SQL queries to collect performance metrics from the database.
 */
public class SQLHelper {

    private static final String CPU_TABLE = SystemConfig.getStringProperty("cpu_table");
    private static final String MEMORY_TABLE = SystemConfig.getStringProperty("memory_table");
    private static final String HOST_TABLE = SystemConfig.getStringProperty("host_table");

    private final Connection connection;

    public SQLHelper(Connection connection) {
        this.connection = connection;
    }

    public List<Host> getHosts() {

        List<Host> hosts = new ArrayList<>();
        String sql = "SELECT * FROM " + HOST_TABLE;

        Statement statement;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            
            while(resultSet.next()) {
                hosts.add(new Host(resultSet.getString("ip"), resultSet.getString("name")));    
            }
            
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO add logging
        }    
        
        return hosts;
    }

    public Statistics getAverage(String hostId) {

        String cpuQuery = "SELECT host_name, cpu, AVG(per_usr) AS per_usr, AVG(per_nice) AS per_nice, AVG(per_sys) " +
                "AS per_sys, AVG(per_io_wait) AS per_io_wait FROM " + CPU_TABLE + " GROUP BY host_name, cpu HAVING host_name=?";

        String memoryQuery = "SELECT host_name, AVG(total) AS total, AVG(used) AS used, AVG(free) AS free " +
                "FROM " + MEMORY_TABLE + " GROUP BY host_name HAVING HOST_NAME=?";

        return getStatistics(cpuQuery, memoryQuery, hostId);
    }

    public Statistics getLatest(String hostId) {

        String memoryQuery = "SELECT total, used, free, host_name FROM " + MEMORY_TABLE + " WHERE host_name=? ORDER BY dateTime desc LIMIT 1;";

        String cpuQuery = "SELECT host_name, cpu, per_usr, per_nice, per_sys, per_io_wait FROM " + CPU_TABLE + " WHERE " +
                "host_name =? ORDER BY dateTime desc LIMIT 1;";

        return getStatistics(cpuQuery, memoryQuery, hostId);
    }

    public Statistics getSummary() {

        String cpuQuery = "SELECT host_name, cpu, AVG(per_usr) AS per_usr, AVG(per_nice) AS per_nice, AVG(per_sys) AS " +
                "per_sys, AVG(per_io_wait) AS per_io_wait FROM " + CPU_TABLE + " GROUP BY host_name, cpu";

        String memoryQuery = "SELECT host_name, AVG(total) AS total, AVG(used) AS used, AVG(free) AS free FROM "
                + MEMORY_TABLE + " GROUP BY host_name;";

        return getStatistics(cpuQuery, memoryQuery);
    }


    public Statistics getStatistics(String cpuQuery, String memoryQuery) {
        List<Metric> metrics = new ArrayList<>();

        metrics.addAll(executeCPUQuery(cpuQuery));
        metrics.addAll(executeMemoryQuery(memoryQuery));

        return new Statistics(metrics);
    }

    public Statistics getStatistics(String cpuQuery, String memoryQuery, String hostId) {
        List<Metric> metrics = new ArrayList<>();

        metrics.addAll(executeCPUQuery(cpuQuery, hostId));
        metrics.addAll(executeMemoryQuery(memoryQuery, hostId));

        return new Statistics(metrics);
    }

    //TODO refactor
    public List<CPU> executeCPUQuery(String sql) {
        Statement statement;
        List<CPU> cpuMetrics = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            cpuMetrics = getCPUMetrics(resultSet);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO add logging
        }

        return cpuMetrics;
    }

    public List<CPU> executeCPUQuery(String sql, String hostId) {
        List<CPU> cpuMetrics = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, hostId);
            ResultSet resultSet = preparedStatement.executeQuery();

            cpuMetrics = getCPUMetrics(resultSet);

            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cpuMetrics;
    }

    public List<CPU> getCPUMetrics(ResultSet resultSet) {
        List<CPU> cpuMetrics = new ArrayList<>();
        try {
            while(resultSet.next()) {
                cpuMetrics.add( new CPU(
                                new CPUFields(
                                        resultSet.getString("cpu"),
                                        resultSet.getDouble("per_usr"),
                                        resultSet.getDouble("per_nice"),
                                        resultSet.getDouble("per_sys"),
                                        resultSet.getDouble("per_io_wait")
                                ),
                                resultSet.getString("host_name")
                        )
                );

            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("resultset is null");
            //TODO add logging
        }
        return cpuMetrics;
    }

    public List<Memory> executeMemoryQuery(String sql) {
        Statement statement;
        List<Memory> memoryMetrics = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            memoryMetrics = getMemoryMetrics(resultSet);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO add logging
        }

        return memoryMetrics;
    }

    public List<Memory> executeMemoryQuery(String sql, String hostId) {
        List<Memory> memoryMetrics = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, hostId);

            ResultSet resultSet = preparedStatement.executeQuery();

            memoryMetrics = getMemoryMetrics(resultSet);

            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memoryMetrics;
    }

    public List<Memory> getMemoryMetrics(ResultSet resultSet) {
        List<Memory> memoryMetrics = new ArrayList<>();

        try {
            while(resultSet.next()) {
                memoryMetrics.add(
                        new Memory(
                                new MemoryFields(
                                        resultSet.getDouble("total"),
                                        resultSet.getDouble("used"),
                                        resultSet.getDouble("free")

                                ),
                                resultSet.getString("host_name")
                        )
                );

            }
            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memoryMetrics;
    }
}
