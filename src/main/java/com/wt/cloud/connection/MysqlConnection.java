package com.wt.cloud.connection;

import com.giant.cloud.entity.IpEntity;
import com.giant.cloud.entity.LogEntity;
import com.giant.cloud.entity.MysqlEntity;
import com.giant.cloud.util.LogIpUtil;
import com.giant.cloud.util.LogSQLUtil;
import com.giant.cloud.util.LogYmlUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.function.Consumer;

/**
 * @author big uncle
 * @date 2021/1/26 11:00
 * @module
 **/
public class MysqlConnection {

    private static final Log log = LogFactory.getLog(MysqlConnection.class);
    private LogEntity LogEntity = null;
    private Connection conn = null;
    private Statement statement = null;
    private String tableName = "";

    public void init() throws SQLException {
        LogEntity = LogYmlUtil.getLogEntity();
        MysqlEntity mysqlEntity = LogEntity.getMysql().check();
        conn = DriverManager.getConnection(mysqlEntity.getUrl(),mysqlEntity.getUsername(),mysqlEntity.getPassword());
        statement = conn.createStatement();
    }

    public void execute(Consumer<Statement> consumer) throws SQLException {
        if(conn == null){
            init();
        }
        if(statement == null){
            statement = conn.createStatement();
        }
        consumer.accept(statement);
    }

    public void destroy() {
        try {
            if(statement != null) {
                statement.close();
            }
        }catch(Exception e){
        }finally {
            statement = null;
            if(conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (SQLException e) {
                    log.error(e.getMessage());
                }
            }
        }
        log.debug("mysql destroy");
    }

    /**
     * 初始化日志表
     * @author big uncle
     * @date 2021/1/26 11:16
     * @param
     * @return java.lang.String
    **/
    public void initTable() throws Exception {
        initlogCluster();
        initlogTable();
    }

    public void initlogCluster() throws Exception {
        String logCluster = LogSQLUtil.getLogClusterDDL();
        statement.execute(logCluster);
    }

    public void initlogTable() throws Exception {
        String logTable = LogSQLUtil.getLogTableDDL();
        IpEntity ipEntity = LogIpUtil.getIpAndHostName();
        String time = LocalDate.now().toString().replace("-","");
        String ip = ipEntity.getIpv4();
        tableName = LogEntity.getMysql().getTable()+"-"+ip+"-"+time;
        logTable = String.format(logTable,tableName);
        statement.execute(logTable);
        statement.execute("SELECT id FROM `log_cluster` WHERE log_table = '"+tableName+"'");
        ResultSet rs = statement.getResultSet();
        if(rs!=null && rs.next()){
            return;
        }
        String insert = "INSERT INTO `log_cluster`" +
                "(log_table,start_time,host_name,ipv4,ipv6) VALUES " +
                "('"+tableName+"',now(),'"+ipEntity.getHostName()+"','"+ipEntity.getIpv4()+"','"+ipEntity.getIpv6()+"')";
        statement.execute(insert);
    }

    public MysqlEntity getMysqlEntity() {
        return LogEntity.getMysql();
    }

    public LogEntity getLogEntity() {
        return LogEntity;
    }

    public String getTableName() {
        return tableName;
    }
}
