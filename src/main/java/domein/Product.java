package domein;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int productNummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private List<Integer> ovChipkaartNummers = new ArrayList<>();

    public Product(int productNummer, String naam, String beschrijving, double prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public int getProductNummer() {
        return productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public List<Integer> getOVChipkaartNummers() {
        return ovChipkaartNummers;
    }

    public void addOVChipkaartNummer(int ovChipkaartNummer) {
        ovChipkaartNummers.add(ovChipkaartNummer);
    }

    public String toString() {
        return String.format("#%s, %s, %s, €%s", productNummer, naam, beschrijving, prijs);
    }
}
