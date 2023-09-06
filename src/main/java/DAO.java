import java.sql.*;
import java.util.Properties;

public class DAO {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost/ovchip";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "Aartje12");
        Connection conn = DriverManager.getConnection(url, props);

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM reiziger");
        while (rs.next()) {
            String tussenvoegsel = rs.getString(3) == null ? "" : rs.getString(3);
            System.out.println(String.format("#%s: %s. %s %s (%s)", rs.getString(1), rs.getString(2), tussenvoegsel, rs.getString(4), rs.getString(5)));
        }
        rs.close();
        st.close();
    }
}
