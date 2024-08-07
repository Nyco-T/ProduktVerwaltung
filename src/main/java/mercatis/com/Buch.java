package mercatis.com;

import java.util.Date;

public class Buch {
    private int fortlaufendenummer;
    private String titel;
    private String beschreibung;
    private String autorVorname;
    private String autorNachname;
    private String buchSprache;
    private String isbn;
    private Date erscheinungsdatum;


    public Buch(int fortlaufendenummer, String titel, String beschreibung, String autorVorname, String autorNachname, String buchSprache, String isbn, Date erscheinungsdatum)
    {
        this.fortlaufendenummer = fortlaufendenummer;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.autorVorname = autorVorname;
        this.autorNachname = autorNachname;
        this.buchSprache = buchSprache;
        this.isbn = isbn;
        this.erscheinungsdatum = erscheinungsdatum;
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
    public String getAutorVorname() {
        return autorVorname;
    }
    public void setAutorVorname(String Avorname) {
        this.autorVorname = autorVorname;
    }
    public String getAutorNachname() {
        return autorNachname;
    }
    public void setAutorNachname(String autorNachname) {
        this.autorNachname = autorNachname;
    }
    public String getBuchSprache() {
        return buchSprache;
    }
    public void setBuchSprache(String buchSprache) {
        this.buchSprache = buchSprache;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public Date getErscheinungsdatum() {
        return erscheinungsdatum;
    }
    public void setErscheinungsdatum(Date erscheinungsdatum) {
        this.erscheinungsdatum = erscheinungsdatum;
    }

    public void display() {
        System.out.println("Nummer: " + fortlaufendenummer);
        System.out.println("Titel: " + titel);
        System.out.println("Beschreibung: " + beschreibung);
        System.out.println("Autor: " + autorVorname + " " + autorNachname);
        System.out.println("Sprache: " + buchSprache);
        System.out.println("ISBN: " + isbn);
        System.out.println("Erscheinungsdatum: " + erscheinungsdatum);
    }



}



