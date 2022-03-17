import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class _03 {
    public static void main(String[] args) throws SQLException {
        Scanner scr = new Scanner(System.in);
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "159357");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", props);
        int villainId = Integer.parseInt(scr.nextLine());
        PreparedStatement villainStatement = connection.prepareStatement("SELECT name from villains WHERE id = ?");
        villainStatement.setInt(1,villainId);

        ResultSet villainSet = villainStatement.executeQuery();
        if (!villainSet.next()){
            System.out.printf("No villain with ID %d exists in the database.",villainId);
            return;
        }

        System.out.println("villain: "+ villainSet.getString("name"));

        PreparedStatement minionStatement = connection.prepareStatement("SELECT DISTINCT m.name, m.age\n" +
                "from minions as m\n" +
                "join minions_villains mv on m.id = mv.minion_id\n" +
                "where mv.villain_id = ?;\n" +
                "\n");
        minionStatement.setInt(1, villainId);

        ResultSet minionSet = minionStatement.executeQuery();

        for (int i = 1; minionSet.next(); i++) {
            String name = minionSet.getString("name");
            int age = minionSet.getInt("age");
            System.out.printf("%d. %s %d\n", i, name,age);
        }

        connection.close();
    }
}
