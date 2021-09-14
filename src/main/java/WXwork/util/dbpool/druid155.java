
package WXwork.util.dbpool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class druid155 {
    private static final String url = "jdbc:sqlserver://192.162.8.155;database=NCDBZS";

    private static final String username = "xxk";
    private static final String password = "888168@czyy";
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