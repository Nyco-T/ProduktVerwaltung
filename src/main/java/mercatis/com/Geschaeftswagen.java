package mercatis.com;

public class Geschaeftswagen {
    private int fortlaufendenummer;
    private String titel;
    private String beschreibung;
    private int kmStand;
    private String parkplatz;

    public Geschaeftswagen(int fortlaufendenummer, String titel, String beschreibung, int kmStand , String parkplatz)
    {
        this.fortlaufendenummer = fortlaufendenummer;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.kmStand = kmStand;
        this.parkplatz = parkplatz;

    }

}