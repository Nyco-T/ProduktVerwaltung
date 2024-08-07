package mercatis.com;

public class Notebook {
    private int fortlaufendenummer;
    private String titel;
    private String beschreibung;
    private int speicherplatz;
    private String prozessor;

    public Notebook(int fortlaufendenummer, String titel, String beschreibung, int speicherplatz, String prozessor) {
        this.fortlaufendenummer = fortlaufendenummer;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.speicherplatz = speicherplatz;
        this.prozessor = prozessor;

    }

}