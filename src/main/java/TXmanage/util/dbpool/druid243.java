
package TXmanage.util.dbpool;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class druid243 {
    private static final String url = "jdbc:mysql://192.162.8.243:3306/wxwork?useSSL=false&serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF8&allowPublicKeyRetrieval=true";
    private static final String username = "root";
    private static final String password = "5784814@3!@#";
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
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    }
}