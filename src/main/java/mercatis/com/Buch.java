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


    public Buch(int fortlaufendenummer, String titel, String beschreibung, String autorVorname, String autorNachname, String buchSprache, String isbn, Date erscheinungsdatum) {
        this.fortlaufendenummer = fortlaufendenummer;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.autorVorname = autorVorname;
        this.autorNachname = autorNachname;
        this.buchSprache = buchSprache;
        this.isbn = isbn;
        this.erscheinungsdatum = erscheinungsdatum;
    }
}



