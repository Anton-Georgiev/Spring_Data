
import java.sql.*;
import java.util.Properties;

public class _02 {
    public static void main(String[] args) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "159357");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", props);

        PreparedStatement statement = connection.prepareStatement("SELECT name, COUNT(mv.minion_id) as `count`\n" +
                "from villains as v\n" +
                "         join minions_villains as mv on v.id = mv.villain_id\n" +
                "GROUP BY name\n" +
                "HAVING count > 15\n" +
                "order by count desc;");

        ResultSet rs = statement.executeQuery();

        while (rs.next()){
            String vName = rs.getString("name");
            int count = rs.getInt("count");

            System.out.println(vName + " " + count);
        }

        connection.close();
    }
}
