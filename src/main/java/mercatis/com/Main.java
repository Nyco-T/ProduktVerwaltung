package mercatis.com;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.sql.*;

public
class Main {
    private static final Scanner                       scanner              = new Scanner ( System.in );
    private static final String                        DB_URL               = "jdbc:postgresql://localhost:5432/Bibliotek";
    private static final String                        DB_USER              = "postgres";
    private static final String                        DB_PASSWORD          = "mercatis";
    private static final ArrayList < Buch >            buecherListe         = new ArrayList <> ( );
    private static final ArrayList < Notebook >        notebookListe        = new ArrayList <> ( );
    private static final ArrayList < Geschaeftswagen > geschaeftswagenListe = new ArrayList <> ( );
    private static       int                           fortlaufendenummer   = 1;
    private static final SimpleDateFormat              dateFormat           = new SimpleDateFormat ( "dd.MM.yyyy" );

    public static
    void main ( String[] args ) {
        boolean running = true;
        while ( running ) {
            displayMainMenu ( );
            int choice = getMenuChoice ( );
            switch ( choice ) {
                case 1:
                    manageBuecher ( );
                    break;
                case 2:
                    manageNotebooks ( );
                    break;
                case 3:
                    manageGeschaeftswagen ( );
                    break;
                case 4:
                    manageAllProducts ( );
                    break;
                case 5:
                    running = false;
                    System.out.println ( "Programm wird beendet. Auf Wiedersehen!" );
                    break;
                default:
                    System.out.println ( "Ungültige Auswahl. Bitte versuchen Sie es erneut." );
            }
        }
        scanner.close ( );
    }

    private static
    void displayMainMenu ( ) {
        System.out.println ( "\n--- Produktverwaltung ---" );
        System.out.println ( "1. Bücher verwalten" );
        System.out.println ( "2. Notebooks verwalten" );
        System.out.println ( "3. Geschäftswagen verwalten" );
        System.out.println ( "4. Alle Prdukte anziegen" );
        System.out.println ( "5. Beenden" );
        System.out.print ( "Wählen Sie eine Option: " );
    }

    private static
    int getMenuChoice ( ) {
        while ( !scanner.hasNextInt ( ) ) {
            System.out.println ( "Bitte geben Sie eine Zahl ein." );
            scanner.next ( );
        }
        return scanner.nextInt ( );
    }

    private static
    void manageBuecher ( ) {
        boolean running = true;
        while ( running ) {
            bookDisplayMenu ( );
            int choice = getBookMenuChoice ( );
            switch ( choice ) {
                case 1:
                    addBook ( );
                    break;
                case 2:
                    displayBooks ( );
                    break;
                case 3:
                    deleteBook ( );
                    break;
                case 4:
                    searchBooks ( );
                    break;
                case 5:
                    running = false;
                    System.out.println ( "Programm wird beendet. Auf Wiedersehen!" );
                    break;
                default:
                    System.out.println ( "Ungültige Auswahl. Bitte versuchen Sie es erneut." );
            }
        }
        scanner.close ( );
    }

    private static
    void bookDisplayMenu ( ) {
        System.out.println ( "\n--- Bücherverwaltung ---" );
        System.out.println ( "1. Buch hinzufügen" );
        System.out.println ( "2. Bücherliste anzeigen" );
        System.out.println ( "3. Buch löschen" );
        System.out.println ( "4. Buch suchen" );
        System.out.println ( "5. Beenden" );
        System.out.print ( "Wählen Sie eine Option: " );
    }

    private static
    int getBookMenuChoice ( ) {
        while ( !scanner.hasNextInt ( ) ) {
            System.out.println ( "Bitte geben Sie eine Zahl ein." );
            scanner.next ( );
        }
        return scanner.nextInt ( );
    }

    private static
    void addBook ( ) {
        scanner.nextLine ( );
        System.out.print ( "Geben Sie den Titel des Buches an: " );
        String titel = scanner.nextLine ( );
        System.out.print ( "Beschreiben Sie das Buch: " );
        String beschreibung = scanner.nextLine ( );
        System.out.print ( "Geben Sie den Vornamen des Autors an: " );
        String autorVorname = scanner.nextLine ( );
        System.out.print ( "Geben Sie den Nachnamen des Autors an: " );
        String autorNachname = scanner.nextLine ( );
        System.out.print ( "Geben Sie die Sprache in der das Buch verfasst wurde an: " );
        String buchSprache = scanner.nextLine ( );
        System.out.print ( "Geben Sie die ISBN an: " );
        String isbn              = scanner.nextLine ( );
        Date   erscheinungsdatum = null;
        while ( erscheinungsdatum == null ) {
            System.out.print ( "Wann ist das Buch erschienen? (Format: dd.MM.yyyy): " );
            String dateInput = scanner.nextLine ( );
            try {
                erscheinungsdatum = dateFormat.parse ( dateInput );
            }
            catch ( ParseException e ) {
                System.out.println ( "Ungültiges Datumsformat. Bitte versuchen Sie es erneut." );
            }
        }
        Buch buch = new Buch ( fortlaufendenummer , titel , beschreibung , autorVorname , autorNachname , buchSprache , isbn , erscheinungsdatum );
        buecherListe.add ( buch );
        try (
                Connection conn = DriverManager.getConnection ( DB_URL , DB_USER , DB_PASSWORD ) ;
                PreparedStatement pstmt = conn.prepareStatement (
                        "INSERT INTO bücher (fortlaufendenummer, titel, beschreibung, autorVorname, autorNachname, buchSprache, isbn, erscheinungsdatum) VALUES (?, ?, ?, ?, ?, ?, ?, ?)" )
        ) {
            pstmt.setInt ( 1 , fortlaufendenummer );
            pstmt.setString ( 2 , titel );
            pstmt.setString ( 3 , beschreibung );
            pstmt.setString ( 4 , autorVorname );
            pstmt.setString ( 5 , autorNachname );
            pstmt.setString ( 6 , buchSprache );
            pstmt.setString ( 7 , isbn );
            pstmt.setDate ( 8 , new java.sql.Date ( erscheinungsdatum.getTime ( ) ) );
            int affectedRows = pstmt.executeUpdate ( );
            if ( affectedRows > 0 ) {
                System.out.println ( "Buch wurde erfolgreich hinzugefügt." );
                fortlaufendenummer++;
            }
        }
        catch ( SQLException e ) {
            System.out.println ( "Fehler beim Hinzufügen des Buches: " + e.getMessage ( ) );
        }
    }

    private static
    void displayBooks ( ) {
        String query = "SELECT * FROM bücher";
        try (
                Connection conn = DriverManager.getConnection ( DB_URL , DB_USER , DB_PASSWORD ) ;
                PreparedStatement pstmt = conn.prepareStatement ( query )
        ) {
            try ( ResultSet rs = pstmt.executeQuery ( ) ) {
                if ( !rs.isBeforeFirst ( ) ) {
                    System.out.println ( "Keine Bücher gefunden." );
                }
                else {
                    System.out.println ( "\nGefundene Bücher:" );
                    while ( rs.next ( ) ) {
                        System.out.println ( "Buch " + rs.getInt ( "fortlaufendenummer" ) + ":" );
                        System.out.println ( "Titel: " + rs.getString ( "titel" ) );
                        System.out.println ( "Beschreibung: " + rs.getString ( "beschreibung" ) );
                        System.out.println ( "Autor: " + rs.getString ( "autorVorname" ) + " " + rs.getString ( "autorNachname" ) );
                        System.out.println ( "Verfassungssprache: " + rs.getString ( "buchSprache" ) );
                        System.out.println ( "ISBN: " + rs.getString ( "isbn" ) );
                        System.out.println ( "Erscheinungsdatum:" + rs.getDate ( "erscheinungsdatum" ) );
                        System.out.println ( "---------------------" );
                    }
                }
            }
        }
        catch ( SQLException e ) {
            System.out.println ( "Fehler bei der Suche: " + e.getMessage ( ) );
        }
    }

    private static
    void deleteBook ( ) {
        displayBooks ( );
        System.out.print ( "Geben Sie die Nummer des zu löschenden Buches ein: " );
        int bookNumber = getMenuChoice ( );

        try (
                Connection conn = DriverManager.getConnection ( DB_URL , DB_USER , DB_PASSWORD ) ;
                PreparedStatement pstmt = conn.prepareStatement ( "DELETE FROM bücher WHERE fortlaufendenummer = ?" )
        ) {
            pstmt.setInt ( 1 , bookNumber );
            int affectedRows = pstmt.executeUpdate ( );
            if ( affectedRows > 0 ) {
                System.out.println ( "Das Buch wurde erfolgreich gelöscht." );
            }
            else {
                System.out.println ( "Kein Buch mit dieser Nummer gefunden." );
            }
        }
        catch ( SQLException e ) {
            System.out.println ( "Fehler beim Löschen des Buches: " + e.getMessage ( ) );
        }
    }

    private static
    void searchBooks ( ) {
        scanner.nextLine ( );
        System.out.print ( "Geben Sie einen Suchbegriff ein (Titel, Autor, ISBN): " );
        String searchTerm = scanner.nextLine ( ).toLowerCase ( );
        String query      = "SELECT * FROM bücher WHERE LOWER(titel) LIKE ? OR LOWER(autorVorname) LIKE ? OR LOWER(autorNachname) LIKE ? OR isbn LIKE ?";
        try (
                Connection conn = DriverManager.getConnection ( DB_URL , DB_USER , DB_PASSWORD ) ;
                PreparedStatement pstmt = conn.prepareStatement ( query )
        ) {
            String wildcardTerm = "%" + searchTerm + "%";
            pstmt.setString ( 1 , wildcardTerm );
            pstmt.setString ( 2 , wildcardTerm );
            pstmt.setString ( 3 , wildcardTerm );
            pstmt.setString ( 4 , wildcardTerm );
            try ( ResultSet rs = pstmt.executeQuery ( ) ) {
                if ( !rs.isBeforeFirst ( ) ) {
                    System.out.println ( "Keine Bücher gefunden." );
                }
                else {
                    System.out.println ( "\nGefundene Bücher:" );
                    while ( rs.next ( ) ) {
                        System.out.println ( "Buch " + rs.getInt ( "fortlaufendenummer" ) + ":" );
                        System.out.println ( "Titel: " + rs.getString ( "titel" ) );
                        System.out.println ( "Beschreibung: " + rs.getString ( "beschreibung" ) );
                        System.out.println ( "Autor: " + rs.getString ( "autorVorname" ) + " " + rs.getString ( "autorNachname" ) );
                        System.out.println ( "Verfassungssprache: " + rs.getString ( "buchSprache" ) );
                        System.out.println ( "ISBN: " + rs.getString ( "isbn" ) );
                        System.out.println ( "Erscheinungsdatum:" + rs.getDate ( "erscheinungsdatum" ) );
                        System.out.println ( "---------------------" );
                    }
                }
            }
        }
        catch ( SQLException e ) {
            System.out.println ( "Fehler bei der Suche: " + e.getMessage ( ) );
        }
    }

    private static
    void manageNotebooks ( ) {
        boolean running = true;
        while ( running ) {
            notebookDisplayMenu ( );
            int choice = getNotebookMenuChoice ( );
            switch ( choice ) {
                case 1:
                    addNotebook ( );
                    break;
                case 2:
                    displayNotebooks ( );
                    break;
                case 3:
                    deleteNotebook ( );
                    break;
                case 4:
                    searchNotebooks ( );
                    break;
                case 5:
                    running = false;
                    System.out.println ( "Programm wird beendet. Auf Wiedersehen!" );
                    break;
                default:
                    System.out.println ( "Ungültige Auswahl. Bitte versuchen Sie es erneut." );
            }
        }
        scanner.close ( );
    }

    private static
    void notebookDisplayMenu ( ) {
        System.out.println ( "\n--- Notebookverwaltung ---" );
        System.out.println ( "1. Notebook hinzufügen" );
        System.out.println ( "2. Notebook anzeigen" );
        System.out.println ( "3. Notebook löschen" );
        System.out.println ( "4. Notebook suchen" );
        System.out.println ( "5. Beenden" );
        System.out.print ( "Wählen Sie eine Option: " );
    }

    private static
    int getNotebookMenuChoice ( ) {
        while ( !scanner.hasNextInt ( ) ) {
            System.out.println ( "Bitte geben Sie eine Zahl ein." );
            scanner.next ( );
        }
        return scanner.nextInt ( );
    }

    private static
    void addNotebook ( ) {
        scanner.nextLine ( );
        System.out.print ( "Geben Sie den Titel des Notebooks an: " );
        String titel = scanner.nextLine ( );
        System.out.print ( "Beschreiben Sie das Notebooks: " );
        String beschreibung = scanner.nextLine ( );
        System.out.print ( "Wie viel Speicherplatz hat ihr Notebook?: " );
        int speicherplatz = scanner.nextInt ( );
        scanner.nextLine ( );
        System.out.print ( "Welcher Prozessor is in Ihrem Notebook verbaut?: " );
        String   prozessor = scanner.nextLine ( );
        Notebook notebook  = new Notebook ( fortlaufendenummer , titel , beschreibung , speicherplatz , prozessor );
        notebookListe.add ( notebook );
        try (
                Connection conn = DriverManager.getConnection ( DB_URL , DB_USER , DB_PASSWORD ) ;
                PreparedStatement pstmt = conn.prepareStatement (
                        "INSERT INTO notebooks (fortlaufendenummer, titel, beschreibung, speicherplatz, prozessor) VALUES (?, ?, ?, ?, ?)" )
        ) {
            pstmt.setInt ( 1 , fortlaufendenummer );
            pstmt.setString ( 2 , titel );
            pstmt.setString ( 3 , beschreibung );
            pstmt.setInt ( 4 , speicherplatz );
            pstmt.setString ( 5 , prozessor );

            int affectedRows = pstmt.executeUpdate ( );
            if ( affectedRows > 0 ) {
                System.out.println ( "Notebook wurde erfolgreich hinzugefügt." );
                fortlaufendenummer++;
            }
        }
        catch ( SQLException e ) {
            System.out.println ( "Fehler beim Hinzufügen des Notebooks: " + e.getMessage ( ) );
        }
    }

    private static
    void displayNotebooks ( ) {
        String query = "SELECT * FROM notebooks";
        try (
                Connection conn = DriverManager.getConnection ( DB_URL , DB_USER , DB_PASSWORD ) ;
                PreparedStatement pstmt = conn.prepareStatement ( query )
        ) {

            try ( ResultSet rs = pstmt.executeQuery ( ) ) {
                if ( !rs.isBeforeFirst ( ) ) {
                    System.out.println ( "Keine Notebooks gefunden." );
                }
                else {
                    System.out.println ( "\nGefundene Notebooks:" );
                    while ( rs.next ( ) ) {
                        System.out.println ( "Notebook " + rs.getInt ( "fortlaufendenummer" ) + ":" );
                        System.out.println ( "Titel: " + rs.getString ( "titel" ) );
                        System.out.println ( "Beschreibung: " + rs.getString ( "beschreibung" ) );
                        System.out.println ( "Speicherplatz: " + rs.getInt ( "speicherplatz" ) );
                        System.out.println ( "Prozessor: " + rs.getString ( "Prozessor" ) );
                        System.out.println ( "---------------------" );
                    }
                }
            }
        }
        catch ( SQLException e ) {
            System.out.println ( "Fehler bei der Suche: " + e.getMessage ( ) );
        }
    }

    private static
    void deleteNotebook ( ) {
        displayNotebooks ( );
        System.out.print ( "Geben Sie die Nummer des zu löschenden Notebooks ein: " );
        int NotebookNumber = getNotebookMenuChoice ( );

        try (
                Connection conn = DriverManager.getConnection ( DB_URL , DB_USER , DB_PASSWORD ) ;
                PreparedStatement pstmt = conn.prepareStatement ( "DELETE FROM notebooks WHERE fortlaufendenummer = ?" )
        ) {
            pstmt.setInt ( 1 , NotebookNumber );
            int affectedRows = pstmt.executeUpdate ( );
            if ( affectedRows > 0 ) {
                System.out.println ( "Das Notebook wurde erfolgreich gelöscht." );
            }
            else {
                System.out.println ( "Kein Notebook mit dieser Nummer gefunden." );
            }
        }
        catch ( SQLException e ) {
            System.out.println ( "Fehler beim Löschen des Notebooks: " + e.getMessage ( ) );
        }
    }

    private static
    void searchNotebooks ( ) {
        scanner.nextLine ( );
        System.out.print ( "Geben Sie einen Suchbegriff ein (Titel, Beschreibung, Prozessor): " );
        String searchTerm = scanner.nextLine ( ).toLowerCase ( );
        String query      = "SELECT * FROM notebooks WHERE LOWER(titel) LIKE ? OR LOWER(beschreibung) LIKE ? OR LOWER(prozessor)LIKE ?";
        try (
                Connection conn = DriverManager.getConnection ( DB_URL , DB_USER , DB_PASSWORD ) ;
                PreparedStatement pstmt = conn.prepareStatement ( query )
        ) {
            String wildcardTerm = "%" + searchTerm + "%";
            pstmt.setString ( 1 , wildcardTerm );
            pstmt.setString ( 2 , wildcardTerm );
            pstmt.setString ( 3 , wildcardTerm );
            try ( ResultSet rs = pstmt.executeQuery ( ) ) {
                if ( !rs.isBeforeFirst ( ) ) {
                    System.out.println ( "Keine Notebooks gefunden." );
                }
                else {
                    System.out.println ( "\nGefundene Notebooks:" );
                    while ( rs.next ( ) ) {
                        System.out.println ( "Notebook " + rs.getInt ( "fortlaufendenummer" ) + ":" );
                        System.out.println ( "Titel: " + rs.getString ( "titel" ) );
                        System.out.println ( "Beschreibung: " + rs.getString ( "beschreibung" ) );
                        System.out.println ( "Speicherplatz: " + rs.getInt ( "speicherplatz" ) );
                        System.out.println ( "Prozessor: " + rs.getString ( "prozessor" ) );
                        System.out.println ( "---------------------" );
                    }
                }
            }
        }
        catch ( SQLException e ) {
            System.out.println ( "Fehler bei der Suche: " + e.getMessage ( ) );
        }
    }

    private static
    void manageGeschaeftswagen ( ) {
        boolean running = true;
        while ( running ) {
            wagenDisplayMenu ( );
            int choice = getWagenMenuChoice ( );
            switch ( choice ) {
                case 1:
                    addWagen ( );
                    break;
                case 2:
                    displayWagen ( );
                    break;
                case 3:
                    deleteWagen ( );
                    break;
                case 4:
                    searchWagen ( );
                    break;
                case 5:
                    running = false;
                    System.out.println ( "Programm wird beendet. Auf Wiedersehen!" );
                    break;
                default:
                    System.out.println ( "Ungültige Auswahl. Bitte versuchen Sie es erneut." );
            }
        }
        scanner.close ( );
    }

    private static
    void wagenDisplayMenu ( ) {
        System.out.println ( "\n--- Geschäftswagenverwaltung ---" );
        System.out.println ( "1. Auto hinzufügen" );
        System.out.println ( "2. Auto anzeigen" );
        System.out.println ( "3. Auto löschen" );
        System.out.println ( "4. Auto suchen" );
        System.out.println ( "5. Beenden" );
        System.out.print ( "Wählen Sie eine Option: " );
    }

    private static
    int getWagenMenuChoice ( ) {
        while ( !scanner.hasNextInt ( ) ) {
            System.out.println ( "Bitte geben Sie eine Zahl ein." );
            scanner.next ( );
        }
        return scanner.nextInt ( );
    }

    private static
    void addWagen ( ) {
        scanner.nextLine ( );
        System.out.print ( "Geben Sie den Namen des Autos an: " );
        String titel = scanner.nextLine ( );
        System.out.print ( "Welche Farbe hat Ihr Auto das Auto: " );
        String beschreibung = scanner.nextLine ( );
        System.out.print ( "Wie viel Kilometer ist ihr Auto gefahren?: " );
        int kmStand = scanner.nextInt ( );
        scanner.nextLine ( );
        System.out.print ( "Wo steht Ihr Auto?: " );
        String          parkplatz = scanner.nextLine ( );
        Geschaeftswagen auto      = new Geschaeftswagen ( fortlaufendenummer , titel , beschreibung , kmStand , parkplatz );
        geschaeftswagenListe.add ( auto );
        try (
                Connection conn = DriverManager.getConnection ( DB_URL , DB_USER , DB_PASSWORD ) ;
                PreparedStatement pstmt = conn.prepareStatement (
                        "INSERT INTO geschaeftswagen (fortlaufendenummer, titel, beschreibung, kmstand, parkplatz) VALUES (?, ?, ?, ?, ?)" )
        ) {
            pstmt.setInt ( 1 , fortlaufendenummer );
            pstmt.setString ( 2 , titel );
            pstmt.setString ( 3 , beschreibung );
            pstmt.setInt ( 4 , kmStand );
            pstmt.setString ( 5 , parkplatz );

            int affectedRows = pstmt.executeUpdate ( );
            if ( affectedRows > 0 ) {
                System.out.println ( "Auto wurde erfolgreich hinzugefügt." );
                fortlaufendenummer++;
            }
        }
        catch ( SQLException e ) {
            System.out.println ( "Fehler beim Hinzufügen des Autos: " + e.getMessage ( ) );
        }
    }

    private static
    void displayWagen ( ) {
        String query = "SELECT * FROM geschaeftswagen";
        try (
                Connection conn = DriverManager.getConnection ( DB_URL , DB_USER , DB_PASSWORD ) ;
                PreparedStatement pstmt = conn.prepareStatement ( query )
        ) {

            try ( ResultSet rs = pstmt.executeQuery ( ) ) {
                if ( !rs.isBeforeFirst ( ) ) {
                    System.out.println ( "Keine Autos gefunden." );
                }
                else {
                    System.out.println ( "\nGefundene Autos:" );
                    while ( rs.next ( ) ) {
                        System.out.println ( "Auto " + rs.getInt ( "fortlaufendenummer" ) + ":" );
                        System.out.println ( "Name: " + rs.getString ( "titel" ) );
                        System.out.println ( "Farbe: " + rs.getString ( "beschreibung" ) );
                        System.out.println ( "Kilometerstnad: " + rs.getInt ( "kmStand" ) );
                        System.out.println ( "Standort: " + rs.getString ( "parkplatz" ) );
                        System.out.println ( "---------------------" );
                    }
                }
            }
        }
        catch ( SQLException e ) {
            System.out.println ( "Fehler bei der Suche: " + e.getMessage ( ) );
        }
    }

    private static
    void deleteWagen ( ) {
        displayNotebooks ( );
        System.out.print ( "Geben Sie die Nummer des zu löschenden Autos ein: " );
        int AutoNummer = getWagenMenuChoice ( );

        try (
                Connection conn = DriverManager.getConnection ( DB_URL , DB_USER , DB_PASSWORD ) ;
                PreparedStatement pstmt = conn.prepareStatement ( "DELETE FROM geschaeftswagen WHERE fortlaufendenummer = ?" )
        ) {
            pstmt.setInt ( 1 , AutoNummer );
            int affectedRows = pstmt.executeUpdate ( );
            if ( affectedRows > 0 ) {
                System.out.println ( "Das Auto wurde erfolgreich gelöscht." );
            }
            else {
                System.out.println ( "Kein Auto mit dieser Nummer gefunden." );
            }
        }
        catch ( SQLException e ) {
            System.out.println ( "Fehler beim Löschen des Autos: " + e.getMessage ( ) );
        }
    }

    private static
    void searchWagen ( ) {
        scanner.nextLine ( );
        System.out.print ( "Geben Sie einen Suchbegriff ein (Titel, Farbe, Standort): " );
        String searchTerm = scanner.nextLine ( ).toLowerCase ( );

        String query = "SELECT * FROM geschaeftswagen WHERE LOWER(titel) LIKE ? OR LOWER(beschreibung) LIKE ? OR LOWER(parkplatz) LIKE ?";
        try (
                Connection conn = DriverManager.getConnection ( DB_URL , DB_USER , DB_PASSWORD ) ;
                PreparedStatement pstmt = conn.prepareStatement ( query )
        ) {
            String wildcardTerm = "%" + searchTerm + "%";
            pstmt.setString ( 1 , wildcardTerm );
            pstmt.setString ( 2 , wildcardTerm );
            pstmt.setString ( 3 , wildcardTerm );

            try ( ResultSet rs = pstmt.executeQuery ( ) ) {
                if ( !rs.isBeforeFirst ( ) ) {
                    System.out.println ( "Keine Autos gefunden." );
                }
                else {
                    System.out.println ( "\nGefundene Autos:" );
                    while ( rs.next ( ) ) {
                        System.out.println ( "Auto " + rs.getInt ( "fortlaufendenummer" ) + ":" );
                        System.out.println ( "Name: " + rs.getString ( "titel" ) );
                        System.out.println ( "Farbe: " + rs.getString ( "beschreibung" ) );
                        System.out.println ( "Kilometerstand: " + rs.getInt ( "kmStand" ) );
                        System.out.println ( "Standort: " + rs.getString ( "parkplatz" ) );
                        System.out.println ( "---------------------" );
                    }
                }
            }
        }
        catch ( SQLException e ) {
            System.out.println ( "Fehler bei der Suche: " + e.getMessage ( ) );
        }
    }

    private static
    void manageAllProducts ( ) {
        String query = "SELECT * FROM bücher";
        try (
                Connection conn = DriverManager.getConnection ( DB_URL , DB_USER , DB_PASSWORD ) ;
                PreparedStatement pstmt = conn.prepareStatement ( query )
        ) {
            try ( ResultSet rs = pstmt.executeQuery ( ) ) {
                if ( !rs.isBeforeFirst ( ) ) {
                    System.out.println ( "Keine Bücher gefunden." );
                }
                else {
                    System.out.println ( "\nGefundene Bücher:" );
                    while ( rs.next ( ) ) {
                        System.out.println ( "Buch " + rs.getInt ( "fortlaufendenummer" ) + ":" );
                        System.out.println ( "Titel: " + rs.getString ( "titel" ) );
                        System.out.println ( "Beschreibung: " + rs.getString ( "beschreibung" ) );
                        System.out.println ( "Autor: " + rs.getString ( "autorVorname" ) + " " + rs.getString ( "autorNachname" ) );
                        System.out.println ( "Verfassungssprache: " + rs.getString ( "buchSprache" ) );
                        System.out.println ( "ISBN: " + rs.getString ( "isbn" ) );
                        System.out.println ( "Erscheinungsdatum:" + rs.getDate ( "erscheinungsdatum" ) );
                        System.out.println ( "---------------------" );
                    }
                }
            }
        }
        catch ( SQLException e ) {
            throw new RuntimeException ( e );
        }
        query = "SELECT * FROM notebooks";
        try (
                Connection conn = DriverManager.getConnection ( DB_URL , DB_USER , DB_PASSWORD ) ;
                PreparedStatement pstmt = conn.prepareStatement ( query )
        ) {
            try ( ResultSet rs = pstmt.executeQuery ( ) ) {
                if ( !rs.isBeforeFirst ( ) ) {
                    System.out.println ( "Keine Notebooks gefunden." );
                }
                else {
                    System.out.println ( "\nGefundene Notebooks:" );
                    while ( rs.next ( ) ) {
                        System.out.println ( "Notebook " + rs.getInt ( "fortlaufendenummer" ) + ":" );
                        System.out.println ( "Titel: " + rs.getString ( "titel" ) );
                        System.out.println ( "Beschreibung: " + rs.getString ( "beschreibung" ) );
                        System.out.println ( "Speicherplatz: " + rs.getInt ( "speicherplatz" ) );
                        System.out.println ( "Prozessor: " + rs.getString ( "Prozessor" ) );
                        System.out.println ( "---------------------" );
                    }
                }
            }
        }
        catch ( SQLException e ) {
            throw new RuntimeException ( e );
        }
        query = "SELECT * FROM geschaeftswagen";
        try (
                Connection conn = DriverManager.getConnection ( DB_URL , DB_USER , DB_PASSWORD ) ;
                PreparedStatement pstmt = conn.prepareStatement ( query )
        ) {
            try ( ResultSet rs = pstmt.executeQuery ( ) ) {
                if ( !rs.isBeforeFirst ( ) ) {
                    System.out.println ( "Keine Autos gefunden." );
                }
                else {
                    System.out.println ( "\nGefundene Autos:" );
                    while ( rs.next ( ) ) {
                        System.out.println ( "Auto " + rs.getInt ( "fortlaufendenummer" ) + ":" );
                        System.out.println ( "Name: " + rs.getString ( "titel" ) );
                        System.out.println ( "Farbe: " + rs.getString ( "beschreibung" ) );
                        System.out.println ( "Kilometerstnad: " + rs.getInt ( "kmStand" ) );
                        System.out.println ( "Standort: " + rs.getString ( "parkplatz" ) );
                        System.out.println ( "---------------------" );
                    }
                }
            }
        }
        catch ( SQLException e ) {
            System.out.println ( "Fehler bei der Suche: " + e.getMessage ( ) );
        }
    }
}























