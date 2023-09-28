import DAO.AdresDAO;
import DAO.AdresDAOPsql;
import domein.Adres;
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
        AdresDAOPsql adao = null;
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(getConnection(), adao);
        adao = new AdresDAOPsql(getConnection(), rdao);

        //testReizigerDAO(rdao);
        testAdresDAO(adao);
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
        Adres adres = new Adres(77, "9876ZZ", "14A", "Hamburgerweg", "Duckstad", sietske);
        sietske.setAdres(adres);
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

        System.out.println("[Test] ReizigerDAO.update() met nieuwe data voor reiziger met id 1");
        Reiziger r2 = new Reiziger(1, "A", null, "Koppes", java.sql.Date.valueOf("2003-01-07"));
        rdao.update(r2);
        r2 = rdao.findById(id);
        System.out.println(r2);

        System.out.println();

        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() met sietske: ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
    }

    public static void testAdresDAO(AdresDAO adao) throws SQLException {
        Reiziger testReiziger = new Reiziger(6, "A", null, "Koppes", java.sql.Date.valueOf("2003-01-07"));
        Adres testAdres = new Adres(6, "1234AA", "69", "Wegweg", "Zeewolde", testReiziger);

        System.out.println("\n---------- Test ReizigerDAO -------------");

        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres adres : adressen) System.out.println(adres);

        System.out.println();

        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adao.save(testAdres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " reizigers\n");

        System.out.print("[Test] Eerst \n");
        System.out.println(adao.findByReiziger(testReiziger));
        System.out.print("[Test] Na update testAdres  \n");
        testReiziger = new Reiziger(6, "A", null, "Naisi", java.sql.Date.valueOf("2003-02-03"));
        testAdres = new Adres(6, "9876ZZ", "69", "Nietwegweg", "Puttn", testReiziger);
        adao.update(testAdres);
        System.out.println(adao.findByReiziger(testReiziger));

        System.out.println();

        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.delete() met testAdres: ");
        adao.delete(testAdres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " reizigers\n");
    }
}
