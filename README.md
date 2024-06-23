# Verwaltung zur Bewirtschaftung von Feldern

## Übersicht

Dieses Projekt ist eine Android-App, die verschiedene Funktionen wie Benutzerregistrierung, Anmeldung und die Verwaltung von Benutzerauswahlen bietet. 
Die App speichert Daten in einer SQLite-Datenbank und ermöglicht es Benutzern, ihre Aktionen zu verfolgen und zu verwalten.
Genauer sollen Landwirte damit ihre Bewirtschaftungen auf ausgewählten Feldern zu bestimmten Zeiten speichern können. Dementsprechend
können dort platzierte Windräder für die notwendige Dauer abgeschalten werden.

## Funktionen

- **Benutzerregistrierung und -anmeldung**: Benutzer können sich registrieren und anmelden. Die Anmeldedaten werden in einer SQLite-Datenbank gespeichert.
- **Datenverwaltung**: Benutzer können Aktionen auswählen, die Auswahl speichern, gespeicherten Daten anzeigen und ihre Einträge löschen.
- **Admin-Funktionalität**: Ein Admin-Benutzer kann alle gespeicherten Daten verwalten.

## Architektur

Die App ist in mehrere Fragmente unterteilt:

- **MainFragment**: Hauptfragment der App, das die verschiedenen Fragmente verwaltet.
- **ActionFragment**: Ermöglicht Definition eines Eintrages spezifisch Tag, Uhrzeit, Dauer und Bewirtschaftungsart.
- **SelectionFragment**: Ermöglicht Benutzern die Auswahl zwischen Neuanlegung einer Bewirtschaftung, Übersicht aller Bewirtschaftungen und Logout.
- **LoginFragment**: Ermöglicht die Benutzeranmeldung und -registrierung.
- **MapFragment**: Ermöglicht die Auswahl des entsprechenden Feldes.
- **SummaryFragment**: Zeigt eine Zusammenfassung der ausgewählten Aktion und ermöglicht das Speichern in der Datenbank.
- **OverviewFragment**: Zeigt eine Übersicht aller gespeicherten Daten.
- **DatabaseHelper**: Hilfsklasse für den Datenbankzugriff.

## Einrichtung

### Voraussetzungen

- Android Studio Iguana | 2023.2.1 Patch 2
- Ein Android-Gerät oder Emulator zum Testen

### Installation

1. Klonen Sie das Repository:
    ```bash
    git clone https://github.com/STARCHIPX/vogel
    ```

2. Öffnen Sie das Projekt in Android Studio:
    ```bash
    File -> Open -> Wählen Sie das geklonte Repository
    ```

3. Führen Sie das Projekt auf einem Emulator oder Gerät aus:
    ```bash
    Run -> Run 'app'
    ```

## Verwendete Technologien

- **Java**: Programmiersprache
- **SQLite**: Lokale Datenbank für die Speicherung von Benutzerdaten und Aktionen
- **Android SDK**: Sammlung von Tools und Bibliotheken zur Entwicklung von Android-Anwendungen

## Code-Struktur

- `MainActivity.java`: Hauptaktivität der App, die die Fragmente verwaltet.
- `DatabaseHelper.java`: Hilfsklasse für den Datenbankzugriff.
- `LoginFragment.java`: Fragment für die Benutzeranmeldung und -registrierung.
- `SelectionFragment.java`: Fragment für die Auswahl von Aktionen.
- `SummaryFragment.java`: Fragment zur Anzeige und Speicherung der Zusammenfassung der ausgewählten Aktion.
- `OverviewFragment.java`: Fragment zur Anzeige der gespeicherten Daten.


## Probleme und Lösungen

- **Unterschiedliche Gradle-Versionen trotz gleicher Installation**: Version "8.3.2" festgelegt.
- **Fehlende Datei**: com.android.tools.idea.explainer.IssueExplainer zeigt fehlende Datei auf. Neustart, Invalidate Caches und Neustart. Überprüfung der Plugin-Integrität.
                      Aktualisierung Android Studio. Tatsächlicher Fehler waren Abostrophe in der strings.xml Datei.


## Beitragende

- **Lucas Dachwitz**
- **Dominik Godau**
- **Nora Havel**
