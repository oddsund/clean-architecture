# clean-architecture
Repo for clean architecture workshop.
Implementasjonen er inspirert av Clean Architecture fra https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html

Strukturen i repoet i "src"-mappen er som følger:

1. **domain**  
   Entiteter, valuetypes, aggregater
2. **application**  
   Applikasjonslogikk, query og command handlere
3. **infrastructure**  
   Database, eksterne tjenester, repsitorier
4. **web**  
   Controllere, dependency injection setup ++

**Domain** er den innerste delen av arkitekturen og skal derfor ikke referere til noe annet lag.
**Application** kan bare referere til **Domain**.  
**Infrastructure** kan referere til **Application** og **Domain**.  
**Web** er ytterst og kan dermed kjenne til alle lag.

# Forutsetninger

- Maven
- JDK 17
- Editor (IntelliJ eller en annen valgfri editor)

# Kompilere og starte applikasjonen

For å kompilere: `mvn clean package`

## Kjøre applikasjonen

### Fra IntelliJ

- Åpne `CleanArchitectureWorkshopApplication` og kjøre main metoden

### Fra terminalen

- Åpne en valgfri terminal hvor Maven er tilgjengelig
- Naviger til `web` og skriv `mvn spring-boot:run` etterfulgt av enter:

```
~\clean-architecture\web> mvn spring-boot:run
```

Applikasjonen skal være tilgjengelig på `http://localhost:5233`

# Database console

Hvis du ønsker å se på innholdet i databasen er den tilgjengelig gjennom følgende web console

- http://localhost:5233/h2-console
- JDBC URL: `jdbc:h2:file:./clean-architecture-h2-db`
- Brukernavn: `sa`
- Passord. `password`

# Postman collection

`clean-architecture-postman-collection.json` er en postman collection med ressurser som representerer de ulike use casene i workshopen.

Postman-samlingen benytter variabler i postman i enkelte av ressursene. Dersom man vil sette verdi på disse variablene så kan man gjøre det ved å:
- klikk på "Environments" i Postman
- i Environment-oversikten klikk på "Globals"
- legg til ny variabel, f.eks `customerId` sett initial value til f.feks 42
- trykk "Save"
  Når man nå velger å benytte en ressurs i samlingen som krever `customerId` vil den automatisk hentes fra Globals i Postman.

# Use caser for workshopen
- (En kunde skal kunne opprettes fra et navn, legal id, legal country.)
- (En kunde skal kunne hentes vha id)
- Alle kunder skal kunne hentes ut
- En kunde skal kunne få lagt til målepunkter (id, navn, addresse, strømsone).
- En kunde skal kunne si opp et målepunkt og beholde eventuelle andre målepunkter.
- En kunde skal kunne se detaljer om alle målepunktene sine (strømsone, anleggsaddresse, et egendefinert navn f.eks. «hytta», status, type).
- En kunde skal kunne se hva forbruket har vært på et gitt målepunkt i et gitt tidsrom.
