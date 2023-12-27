# Spring Boot Book Catalog Application

## Descrizione
Questo progetto è un'applicazione Spring Boot che fornisce API REST per gestire un catalogo di libri, autori ed editori. L'applicazione si interfaccia con un database MySQL per salvare e recuperare dati, gestendo relazioni complesse tra le varie entità.

## Struttura delle Tabelle e Relazioni
- **Libro:** Ogni libro ha attributi come `id`, `titolo`, `annoDiPubblicazione`. Ogni libro è associato a un autore e a un editore.
- **Autore:** Ha attributi come `id`, `nome`, `cognome`. Un autore può avere più libri.
- **Editore:** Ha attributi come `id`, `nome`. Un editore può pubblicare più libri.

## Requisiti Specifici

### Configurazione del Progetto
- Progetto creato utilizzando Spring Initializr.
- Dipendenze appropriate incluse per Spring Boot.

### Creazione dei Modelli di Dati
- Classi `Libro`, `Autore`, `Editore` definite con annotazioni JPA.
- Relazioni tra entità stabilite (es. `@ManyToOne`, `@OneToMany`).

### Configurazione del Database
- Database MySQL utilizzato e configurato.
- Tabelle e relazioni create automaticamente all'avvio dell'applicazione.

### Creazione dei Repository JPA
- Repository implementati per `Libro`, `Autore`, `Editore`.

### Sviluppo dei Controller REST
- Controller REST con endpoint per operazioni CRUD su libri, autori ed editori.

### Gestione delle Eccezioni e Validazione
- Gestione delle eccezioni implementata.
- Validazione degli input per gli endpoint.

---

### Configurazione
Modifica il file `application.properties` per configurare la connessione al database MySQL.
