# Mileston 4 - 'ticket platform'
Il progetto prevede la realizzazione di una backoffice per la piattaforma di gestione delle richieste di supporto al team di assistenza tecnica di un prodotto.

# Schema ER
Di seguito, lo schema ER del progetto. Qui sono rappresentate tutte le entità e le relazioni fra esse.
![schema](/src/main/resources/static/images/ER.png)

# Spring Security - Custom Login setup
Dopo il setup di **Spring Security**, il primo passo che ho voluto fare era quello di configurare una pagina custom di login con **tailwindcss**.

Sono riuscito a configurare la mia pagina di login personalizzata creando un controller "**LoginController**" su cui controllo la rotta **GET** per "/login".

Successivamente ho avuto bisogno di modificare la mia **@Configuration SecurityConfiguration** con i seguenti metodi :
```java
.formLogin()
.loginPage("/login")
.loginProcessingUrl("/login")
.defaultSuccessUrl("/", true)
```
Di seguito, i dettagli sul ruolo di ciascun metodo :
- tramite `.formLogin()` dico a SpringSecurity semplicemente dell'esistenza di un form di Login.
- tramite `loginPage("/login")` riferisco su quale rotta trovare il mio custom form login
- tramite `loginProcessingUrl("/login")` sto chedendo a Spring Security di controllare questa rotta perché è qui che manderò nel mio form la POST su cui Spring Security voglio che mi faccia l'autenticazione.
- tramite `defaultSuccessUrl()`, una volta che l'utente è autenticato lo reindirizzo alla rotta '/' ovvero la pagina principale della mia applicazione.


## Custom Login Form : Validazione
Per la validazione, ho voluto dividere in :
- Validazione lato client
- Validazione lato server

Lato client non ho fatto altro che aggiungere un file `loginValidation.js` su `static/js`che verificasse se gli input fossero vuoti o meno all'invio del form.
Se gli input non sono entrambi compilati, il form impedisce il submit.

Lato server è stato più complicato in quanto la sfida era, sulla medesima pagina di Login, inserire un errore come "Credenziali errate" nel momento in cui Spring Security restituiva il risultato della verifica.
**Thymeleaf** fortunatamente aveva una soluzione per il mio problema ovvero :
```html
    <div th:if="${param.error}" class="text-red-500">
        Credenziali errate. Per favore riprova.
    </div>
```

In questo div, sfrutto il parametro che aggiunge Spring Security all'url di login, dopo un login errato ovvero `login?error` e nel template thymeleaf, verifico la presenza del parametro.
Se esiste allora il div con "Credenziali errate" viene visualizzato correttamente nel mio form di custom login.

Per attuare questo meccanismo, ho avuto bisogno della [documentazione ufficiale](https://www.thymeleaf.org/doc/articles/springmvcaccessdata.html) di Thymeleaf sotto la sezione "Request Parameters". (Non è assolutamente farina del mio sacco, questa soluzione è stata applicata anche [QUI](https://stackoverflow.com/questions/13261794/display-error-messages-in-spring-login))

## Logout
Per quanto riguarda il logout invece, riferendomi alla [documentazione ufficiale](https://docs.spring.io/spring-security/reference/servlet/authentication/logout.html#:~:text=an%20Identity%20Provider-,Understanding%20Logout%E2%80%99s%20Architecture,exercise%20its%20default%20LogoutSuccessHandler%20which%20redirects%20to%20/login%3Flogout.,-Customizing%20Logout%20URIs) mi basta fare una POST all'endpoint `/logout`e SpringSecurity gestirà il resto.
