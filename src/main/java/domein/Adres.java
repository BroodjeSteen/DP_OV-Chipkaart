package domein;

public class Adres {
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reizigerId;

    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats, int reizigerId) {
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reizigerId = reizigerId;
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

    public int getReizigerId() {
        return reizigerId;
    }

    public String toString() {
        return String.format("#%s, %s-%s", id, postcode, huisnummer);
    }
}
