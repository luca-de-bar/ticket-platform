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
.loginProcessingUrl("/autenticazione")
.defaultSuccessUrl("/", true)
```
Di seguito, i dettagli sul ruolo di ciascun metodo :
- tramite `.formLogin()` dico a SpringSecurity semplicemente dell'esistenza di un form di Login differente da quello standard.
- tramite `loginPage("/login")` riferisco su quale rotta trovare il formLogin
- tramite `loginProcessingUrl("/autenticazione")` sto chedendo a Spring Security di controllare questa rotta perché è qui che manderò nel mio custom login la POST su cui Spring Security voglio che mi faccia l'autenticazione.
- tramite `defaultSuccessUrl()`, una volta che l'utente è autenticato lo reindirizzo alla rotta '/' ovvero la pagina principale della mia applicazione.

Per quanto riguarda il logout invece, riferendomi alla [documentazione ufficiale](https://docs.spring.io/spring-security/reference/servlet/authentication/logout.html#:~:text=an%20Identity%20Provider-,Understanding%20Logout%E2%80%99s%20Architecture,exercise%20its%20default%20LogoutSuccessHandler%20which%20redirects%20to%20/login%3Flogout.,-Customizing%20Logout%20URIs) mi basta fare una POST all'endpoint `/logout`e SpringSecurity gestirà il resto.