package domein;

public class Adres {
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private Reiziger reiziger;

    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats, Reiziger reiziger) {
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger = reiziger;
    }

    public int getId() {
        return id;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public int getReizigerId() {
        return reiziger.getReizigerId();
    }

    public String toString() {
        String tussenvoegsel = getReiziger().getTussenvoegsel() == null ? "" : " " + getReiziger().getTussenvoegsel();
        return String.format("Reiziger {#%s %s.%s %s, geb. %s, Adres {#%s %s-%s}}",
                getReizigerId(), getReiziger().getVoorletters(), tussenvoegsel, getReiziger().getAchternaam(), getReiziger().getGeboortedatum(), id, postcode, huisnummer);
    }
}
