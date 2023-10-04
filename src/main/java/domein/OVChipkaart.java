package domein;

import java.sql.Date;

public class OVChipkaart {
    private int kaartNummer;
    private Date geldigTot;
    private int klasse;
    private double saldo;
    private int reizigerId;

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

    public String toString() {
        return String.format("#%s, geldig tot: %s, klasse: %s, saldo: €%s", kaartNummer, geldigTot, klasse, saldo);
    }
}
