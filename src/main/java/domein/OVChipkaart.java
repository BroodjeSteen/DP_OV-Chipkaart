package domein;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaart {
    private int kaartNummer;
    private Date geldigTot;
    private int klasse;
    private double saldo;
    private int reizigerId;
    private List<Product> producten = new ArrayList<>();

    public OVChipkaart(int kaartNummer, Date geldigTot, int klasse, double saldo, int reizigerId) {
        this.kaartNummer = kaartNummer;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reizigerId = reizigerId;
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getReizigerId() {
        return reizigerId;
    }

    public List<Product> getProducten() {
        return producten;
    }

    public void addProduct(Product product) {
        producten.add(product);
        product.addOVChipkaartNummer(this.kaartNummer);
    }

    public String toString() {
        return String.format("#%s, geldig tot: %s, klasse: %s, saldo: €%s, Producten: {%s}", kaartNummer, geldigTot, klasse, saldo, producten);
    }
}
