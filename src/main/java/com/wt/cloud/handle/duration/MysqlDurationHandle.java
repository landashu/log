package com.wt.cloud.handle.duration;

import com.giant.cloud.connection.MysqlConnection;
import com.giant.cloud.handle.execute.MysqlExecuteHandle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.ResultSet;
import java.time.LocalDate;

/**
 * @author big uncle
 * @date 2021/1/26 10:49
 * @module
 **/
public class MysqlDurationHandle extends MysqlExecuteHandle {

    private static final Log log = LogFactory.getLog(MysqlDurationHandle.class);
    private MysqlConnection mysqlConnection;

    public MysqlDurationHandle(MysqlConnection mysqlConnection) {
        super(mysqlConnection);
        this.mysqlConnection = mysqlConnection;
    }

    public void newTable(){
        while(true){
            try{
                Thread.sleep(60*1000);
                mysqlConnection.initlogTable();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void timedClear(){
        while(true){
            try{
                // 每分钟执行一次
                Thread.sleep(60*1000);
                mysqlConnection.execute((con) ->{
                    try {
                        LocalDate localDate = LocalDate.now().plusDays(-mysqlConnection.getLogEntity().getDuration());
                        String sql = "SELECT log_table FROM `log_cluster` WHERE start_time <= '" + localDate.toString() + "'";
                        con.execute(sql);
                        ResultSet rs = con.getResultSet();
                        if(rs == null){
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        while(rs.next()){
                            String logTable = rs.getString("log_table");
                            if(logTable.startsWith(mysqlConnection.getMysqlEntity().getTable())){
                                sb.append("`"+logTable+"`,");
                            }
                        }
                        if(sb.length()<1){
                            return;
                        }
                        sb = sb.deleteCharAt(sb.length() - 1);
                        String delsq = "DELETE FROM `log_cluster` where log_table IN ("+sb.toString().replaceAll("`","'")+")";
                        con.execute(delsq);
                        con.execute("drop table "+sb.toString()+";");
                    }catch(Exception e){
                        log.error(e.getMessage());
                        e.printStackTrace();
                    }
                });
            }catch(Exception e){
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void execute(){
        super.exec();
    }

}
