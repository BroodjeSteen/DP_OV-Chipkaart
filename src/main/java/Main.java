import domein.Reiziger;

import DAO.ReizigerDAO;
import DAO.ReizigerDAOPsql;

import java.sql.*;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws SQLException, ParseException {
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(getConnection());
        testReizigerDAO(rdao);
    }

    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            String url = "jdbc:postgresql://localhost/ovchip";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "Aartje12");
            Connection conn = DriverManager.getConnection(url, props);
            connection = conn;
        }
        return connection;
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException, ParseException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        System.out.println("[Test] ReizigerDAO.findById() met id = 1 geeft de volgende reiziger:");
        int id = 1;
        Reiziger r1 = rdao.findById(id);
        System.out.println(r1);
        System.out.println();


        System.out.println("[Test] ReizigerDAO.findByGbdatum() met geboortedatum = 2002-12-03 geeft de volgende reiziger(s):");
        String datum = "2002-12-03";
        List<Reiziger> reizigerList = rdao.findByGbdatum(datum);
        for (Reiziger r : reizigerList) {
            System.out.println(r);
        }

        System.out.println();

        System.out.println("[Test] ReizigerDAO.update() met nieuwe date voor reiziger met id 1");
        Reiziger r2 = new Reiziger(1, "L", null, "Koppes", java.sql.Date.valueOf("2003-01-07"));
        rdao.update(r2);
        r2 = rdao.findById(id);
        System.out.println(r2);

        System.out.println();

        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() met sietske: ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
    }
}
