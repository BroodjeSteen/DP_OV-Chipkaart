package domein;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    private int reizigerId;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> ovChipkaarten = new ArrayList<>();

    public Reiziger(int reizigerId, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this(reizigerId, voorletters, tussenvoegsel, achternaam, geboortedatum, null);
    }

    public Reiziger(int reizigerId, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum, Adres adres) {
        this.reizigerId = reizigerId;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.adres = adres;
    }

    public int getReizigerId() {
        return reizigerId;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getOVChipkaarten() {
        return ovChipkaarten;
    }

    public void addOVChipkaart(OVChipkaart ovChipkaart) {
        ovChipkaarten.add(ovChipkaart);
    }

    public String toString() {
        String tussenvoegsel = this.tussenvoegsel == null ? "" : " " + this.tussenvoegsel;
        return String.format("Reiziger {#%s %s.%s %s, geb. %s, Adres {%s}, OV chipkaarten {%s}}",
                reizigerId, voorletters, tussenvoegsel, achternaam, geboortedatum, adres, ovChipkaarten);
    }
}
