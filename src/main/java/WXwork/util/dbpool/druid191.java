
package WXwork.util.dbpool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class druid191 {
    private static final String url = "jdbc:sqlserver://192.162.8.191;database=K6_FPCON";
    private static final String username = "kq";
    private static final String password = "sa123!@#";
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
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    }
}