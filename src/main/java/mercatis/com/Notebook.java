package mercatis.com;

import java.util.Date;

public class Notebook {
    private int fortlaufendenummer;
    private String titel;
    private String beschreibung;
    private int speicherplatz;
    private String prozessor;

    public Notebook(int fortlaufendenummer, String titel, String beschreibung, int speicherplatz, String prozessor)
    {
        this.fortlaufendenummer = fortlaufendenummer;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.speicherplatz = speicherplatz;
        this.prozessor = prozessor;
        String test;

    }
    public int getFortlaufendenummer() {return fortlaufendenummer;}

    public void setFortlaufendenummer(int fortlaufendenummer) {
        this.fortlaufendenummer = fortlaufendenummer;
    }
    public String getTitel() {
        return titel;
    }
    public void setTitel(String titel) {
        this.titel = titel;
    }
    public String getBeschreibung() {
        return beschreibung;
    }
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
    public int getSpeicherplatz() {return speicherplatz;}
    public void setSpeicherplatz(int speicherplatz) {
        this.speicherplatz = speicherplatz;
    }
    public String getProzessor() {
        return prozessor;
    }
    public void setProzessor(String prozessor) {
        this.prozessor = prozessor;
    }
    public void darstellen() {
        System.out.println("Nummer: " + fortlaufendenummer);
        System.out.println("Titel: " + titel);
        System.out.println("Beschreibung: " + beschreibung);
        System.out.println("Größe des Speichers: " + speicherplatz);
        System.out.println("Sprache: " + prozessor);

    }



}