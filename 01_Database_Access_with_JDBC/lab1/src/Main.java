import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        Scanner scr = new Scanner(System.in);

        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "159357");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/soft_uni",props);
        PreparedStatement statement =
                connection.prepareStatement("SELECT first_name, last_name FROM employees WHERE salary > ?");

        String salary = scr.nextLine();
        statement.setDouble(1, Double.parseDouble(salary));

        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            System.out.println(rs.getString("first_name") + " "+ rs.getString("last_name"));

        }

    }
}
