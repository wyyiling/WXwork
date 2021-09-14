
package WXwork.util.dbpool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class druid133 {

    private static final String url = "jdbc:oracle:thin:@192.162.8.133:1521:docare";
    private static final String username = "medsurgery";
    private static final String password = "medsurgery";
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
