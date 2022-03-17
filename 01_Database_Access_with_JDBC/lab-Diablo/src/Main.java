import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        Scanner scr = new Scanner(System.in);
        props.setProperty("user", "root");
        props.setProperty("password", "159357");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/diablo", props);

        PreparedStatement statement = connection.prepareStatement("SELECT user_name, first_name, last_name, COUNT(ug.id) AS `count` from `users` \n" +
                "  JOIN `users_games` AS ug \n" +
                " ON users.id = ug.user_id\n" +
                " WHERE user_name = ?\n" +
                " GROUP BY ug.user_id;");

        String username = scr.nextLine();
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {

            System.out.println(resultSet.getString("user_name") + " "
                    + resultSet.getString("first_name") + " " + resultSet.getString("last_name") + " " + resultSet.getInt("count"));
        } else System.out.println("no such user!");
    }
}
