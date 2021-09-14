package WXwork.util.dbpool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class druid9145 {
    private static final String url = "jdbc:oracle:thin:@//192.162.9.145:1521/hisemrdb";
    private static final String username = "core_his50";
    private static final String password = "CZYY@his+2017";
    private static final DruidDataSource dataSource = new DruidDataSource();
    private static DruidPooledConnection druidPooledConnection;

    public static Connection getConnection() {
        try {
            druidPooledConnection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return druidPooledConnection;
    }

    static {
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    }
}

