package mercatis.com;

import java.util.Date;
public class Buch {
    private final int fortlaufendenummer;
    private final String titel;
    private final String beschreibung;
    private final String autorVorname;
    private final String autorNachname;
    private final String buchSprache;
    private final String isbn;
    private final Date erscheinungsdatum;

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



