package mercatis.com;

public class Geschaeftswagen {
    private final int fortlaufendenummer;
    private final String titel;
    private final String beschreibung;
    private final int kmStand;
    private final String parkplatz;
    public Geschaeftswagen(int fortlaufendenummer, String titel, String beschreibung, int kmStand , String parkplatz)
    {
        this.fortlaufendenummer = fortlaufendenummer;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.kmStand = kmStand;
        this.parkplatz = parkplatz;

    }
}