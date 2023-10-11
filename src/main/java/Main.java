import DAO.*;
import domein.Adres;
import domein.OVChipkaart;
import domein.Product;
import domein.Reiziger;

import java.sql.*;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(getConnection());
        AdresDAOPsql adao = new AdresDAOPsql(getConnection());
        OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(getConnection());
        ProductDAO pdao = new ProductDAOPsql(getConnection());

        rdao.setOdao(odao);
        rdao.setAdao(adao);
        odao.setPdao(pdao);

        testReizigerDAO(rdao);
        //testAdresDAO(adao);
        //testOVChipkaartDAOPsql(odao);
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

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        // Initialize objects for tests
        Product p1 = new Product(7, "Dagkaart 1e klas", "Een hele dag onbeperkt reizen met de trein.", 75.80);
        Product p2 = new Product(8, "Cheat code", "Onbeperkt reizen in 1e klas", 0);

        Reiziger sietske = new Reiziger(77, "S", null, "Boers", java.sql.Date.valueOf("1981-03-14"));
        Adres adres = new Adres(77, "9876ZZ", "14A", "Hamburgerweg", "Duckstad", sietske.getReizigerId());
        OVChipkaart ov1 = new OVChipkaart(12345, java.sql.Date.valueOf("2023-12-31"), 1, 50.00, sietske.getReizigerId());
        OVChipkaart ov2 = new OVChipkaart(98765, java.sql.Date.valueOf("2024-01-01"), 1, 420.69, sietske.getReizigerId());

        ov1.addProduct(p1);
        ov1.addProduct(p2);
        ov2.addProduct(p1);

        sietske.setAdres(adres);
        sietske.addOVChipkaart(ov1);
        sietske.addOVChipkaart(ov2);

        System.out.println(p1.getOVChipkaartNummers());
        System.out.println(p2.getOVChipkaartNummers());

        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) System.out.println(r);
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database

        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        System.out.println("[Test] ReizigerDAO.findById() met id = 77 geeft de volgende reiziger:");
        Reiziger r2 = rdao.findById(77);
        System.out.println(r2);

        System.out.println();

        System.out.println("[Test] ReizigerDAO.findByGbdatum() met geboortedatum = 2002-12-03 geeft de volgende reiziger(s):");
        String datum = "2002-12-03";
        List<Reiziger> reizigerList = rdao.findByGbdatum(datum);
        for (Reiziger r : reizigerList) {
            System.out.println(r);
        }

        System.out.println();

        p2 = new Product(8, "Zeer duur", "Betaal je blut", 999999);

        // Initialze objects again with updated data
        sietske = new Reiziger(77, "A", null, "Koppes", java.sql.Date.valueOf("2003-01-07"));
        adres = new Adres(77, "1234AB", "69", "Wegweg", "Duckstad", sietske.getReizigerId());
        ov1 = new OVChipkaart(12345, java.sql.Date.valueOf("2024-05-04"), 2, 0.01, sietske.getReizigerId());
        ov2 = new OVChipkaart(98765, java.sql.Date.valueOf("2024-05-05"), 2, 999.99, sietske.getReizigerId());

        ov1.addProduct(p1);
        ov2.addProduct(p1);
        ov2.addProduct(p2);

        sietske.setAdres(adres);
        sietske.addOVChipkaart(ov1);
        sietske.addOVChipkaart(ov2);

        System.out.println("[Test] ReizigerDAO.update() met nieuwe data voor reiziger met id 77");
        rdao.update(sietske);
        System.out.println(rdao.findById(77));

        System.out.println();

        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() met sietske: ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
    }

    public static void testAdresDAO(AdresDAO adao) throws SQLException {
        Reiziger testReiziger = new Reiziger(6, "A", null, "Koppes", java.sql.Date.valueOf("2003-01-07"));
        Adres testAdres = new Adres(6, "1234AA", "69", "Wegweg", "Zeewolde", testReiziger.getReizigerId());

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
        testAdres = new Adres(6, "9876ZZ", "69", "Nietwegweg", "Puttn", testReiziger.getReizigerId());
        adao.update(testAdres);
        System.out.println(adao.findByReiziger(testReiziger));

        System.out.println();

        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.delete() met testAdres: ");
        adao.delete(testAdres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " reizigers\n");
    }

    public static void testOVChipkaartDAOPsql(OVChipkaartDAO odao) throws SQLException {
        Reiziger sietske = new Reiziger(77, "S", null, "Boers", java.sql.Date.valueOf("1981-03-14"));
        OVChipkaart ov1 = new OVChipkaart(12345, java.sql.Date.valueOf("2024-05-04"), 2, 0.01, sietske.getReizigerId());
        OVChipkaart ov2 = new OVChipkaart(98765, java.sql.Date.valueOf("2024-05-05"), 2, 999.99, sietske.getReizigerId());
        sietske.addOVChipkaart(ov1);
    }
}
