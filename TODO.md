1. [OK] Prima dell'inserimento nel database, controllare l'unicità dell'entità (es. email utente) per prevenire duplicazioni.
2. [OK] Utilizzare PasswordEncoder per la crittografia delle password nelle richieste POST e PUT degli utenti.
3. [OK] Dopo operazioni POST, PUT e DELETE, restituire l'entità modificata nella risposta HTTP.
4. Ridurre la duplicazione nel codice dei controller, come "successMessage".
5. Rimuovere le dipendendenze di MessageFactory e sostituirle con le eccezioni personalizzate. (Rimosso le EntityNotFoundExceptions)