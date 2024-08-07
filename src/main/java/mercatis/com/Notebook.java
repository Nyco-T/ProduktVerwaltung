package mercatis.com;

public
class Notebook {
    private final int fortlaufendenummer;
    private final String titel;
    private final String beschreibung;
    private final int speicherplatz;
    private final String prozessor;
    
    public Notebook(int fortlaufendenummer, String titel, String beschreibung, int speicherplatz, String prozessor) {
        this.fortlaufendenummer    = fortlaufendenummer;
        this.titel                 = titel;
        this.beschreibung          = beschreibung;
        this.speicherplatz         = speicherplatz;
        this.prozessor             = prozessor;

    }
}