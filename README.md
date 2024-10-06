# Mileston 4 - 'ticket platform'
Il progetto prevede la realizzazione di una backoffice per la piattaforma di gestione delle richieste di supporto al team di assistenza tecnica di un prodotto.

## Schema ER
Di seguito, lo schema ER del progetto. Qui sono rappresentate tutte le entità e le relazioni fra esse.
![schema](/src/main/resources/static/images/ER.png)

## Spring Security - Custom Login setup
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


### Custom Login Form : Validazione
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

### Logout
Per quanto riguarda il logout invece, riferendomi alla [documentazione ufficiale](https://docs.spring.io/spring-security/reference/servlet/authentication/logout.html#:~:text=an%20Identity%20Provider-,Understanding%20Logout%E2%80%99s%20Architecture,exercise%20its%20default%20LogoutSuccessHandler%20which%20redirects%20to%20/login%3Flogout.,-Customizing%20Logout%20URIs) mi basta fare una POST all'endpoint `/logout`e SpringSecurity gestirà il resto.

## Spring Data JPA : Trovare Operatori 'Attivi'
Nella consegna, è previsto che l'assegnazione di un ticket sia ai soli operatori "attivi".
Di seguito spiego passo passo come ho implementato questa richiesta : 

### OperatorRepository e OperatorService
Per realizzare questo, ho voluto filtrare gli operatori attivi direttamente dalla **OperatorRepository**, in particolare ho utilizzato il metodo built-in di Spring Data JPA `findByActiveTrue()` che non fa altro che eseguire una query `where x.active = true` dove 'x' in questo caso è la mia tabella **operators**.

Mi sono poi assicurato che la mia entità Opeator contenesse la variabile d'istanza : `private boolean active;`, ho quindi proceduto ad implementare il tutto su **OperatorService**.

### Vista Thymeleaf
Ora, dalla vista non devo fare altro che rendere visibili gli operatori. Ho quindi voluto implementare un select in questo modo : 

```html
<!--OPERATOR-->
    <div class="mb-4">
        <label for="operator" class="form-label">Seleziona un operatore</label>
        <select id="operator" class="form-select" th:field="*{operator}">
            <option selected value="">-- Seleziona un operatore --</option>
            <option th:each="operator : ${operators}" 
                th:value="${operator.id}" 
                th:text="${operator.username}"></option>
        </select>
    </div>
```
## Stato operatore
La consegna richiede che l'operatore loggato, sia in grado di impostare il proprio stato **'attivo'** o **'disattivo'** a piacimento.
Tuttavia, per poter impostare lo stato 'disattivo' (ed evitare pertanto che altri ticket vengano assegnati al medesimo operatore) è necessario assicurarsi che l'operatore **NON** abbia ticket con stato 'Da Fare'.

Per fare ciò ho prima predisposto un `OperatorController` che risponde alle rotte `/operator/**`.
- Il metodo personalArea() risponde alla GET su `{/id}`per la visualizzazione del form.
- Il metodo changeStatus() risponde alla POST su `{/id}` e aggiorna lo stato dell'operatore.

Nel metodo changeStatus() ho effettuato la verifica dei ticket per l'operatore in questo modo : 

```java
    @PostMapping("/{id}")
    public String changeStatus(@ModelAttribute("operator") Operator formOperator,
                               RedirectAttributes attributes){
        //Controllo il valore passato dal form, se true procedo in persistenza, altrimenti se false controllo i ticket.
        if(formOperator.isActive()){
            operatorService.update(formOperator);
            attributes.addFlashAttribute("operatorSuccess","Stato aggiornato correttamente!");
        } else {
            //Se L'operatore ha ticket :
            if (!formOperator.getTickets().isEmpty()){
                //Ciclo us ogni ticket dell'operatore e verifico se ci sono status 'Da Fare' o 'In corso'
                for(Ticket ticket : formOperator.getTickets()){
                    if(ticket.getStatus().equals("Da Fare") || ticket.getStatus().equals("In corso")){
                        attributes.addFlashAttribute("operatorAlert","Spiacente, hai ancora dei ticket da completare");
                        break;
                    }
                }
                //Se l'operatore non ha ticket :
            } else {
                operatorService.update(formOperator);
                attributes.addFlashAttribute("operatorSuccess","Stato aggiornato correttamente!");
            }
        }
        return "redirect:/operator/{id}";
    }
```

Essendo lo 'status' dell'operatore, un valore booleano, non faccio altro che verificare se lo stato impostato dal form è 'false' (quindi, l'operatore desidera disattivarsi), in tal caso effettuo una verifica sui ticket dell'operatore con un ciclo ForEach e se vengono trovati ticket con stato 'Da Fare' o 'In corso', sarà impossibile per l'operatore disattivarsi.
