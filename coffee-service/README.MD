# Setup projekta

## Postavljanje i pokretanje PostgeSQL s Dockerom
Više o tome ovdje
https://docs.docker.com/samples/library/postgres/

`docker run --name coffee-postgres -p 5432:5432 -e POSTGRES_PASSWORD=secr3t -e POSTGRES_USER=armin -e POSTGRES_DB=coffeuniverse -d postgres`

## Postavljanje i pokretanje Elasticsearch za development

Trenutno se koristi ES kao servis unutar Springa
U slučaju da se pokaže potreba moguće stvoriti docker container s naredbom sljedećeg oblika:
`docker run --name zato-es -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e "xpack.security.enabled=false" docker.elastic.co/elasticsearch/elasticsearch:5.6.10`

# Generalne informacije

Ime baze: coffeuniverse
korisnik/lozinka : armin/secr3t

## Pokretanje aplikacije:

### Backend 
- Source Packages -> com.example.search.coffee -> Desna tipka "Run file" ili 
- U rootu projekta iz terminala pokrenuti naredbu: ./mvnw (Mac OS X/Linux) ili mvnw (Windows)

Aplikacija će biti dostupna na http://localhost:8080

### Frontend
otići u root frontend projekta i pokrenuti iz terminala -> yarn start ( ili npm start )

# Generalne preporuke

	Frontend: Visual Studio Code - odlična potpora za angular 6 + TS

	Backend : Netbeans - Za Maven projekt, DBeaver

# Prvi korisnik u sustavu administrator

Prvi korisnik je stvoren u data.sql i po defaultu je aplikacija konfigurirana tako da se stvore pri svakom pokretanju sve tablice koje nisu stvorene 

Sign-in prvog korisnika se može provjeriti sa naredbom

`curl -X POST http://localhost:8080/api/users/sign-in?rememberMe=true -H "Content-Type:application/json" -d "{\"username\":\"admin\", \"password\":\"admin\"}"`
 
dobije se odgovor:

`{"username":"admin","token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNTQ1NDA1MzkzLCJleHAiOjE1NDYwMTAxOTN9.Xomvm6iP776gc3y7sF1RISRZLt7xNX4j-gTd4GR8UpE"}`

Sada je moguće pitati zaštićeni url s tokenom iz odgovora sa curl na sljedeći način:

`curl -X GET http://localhost:8080/api/users/current -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNTQ1NDA1MzkzLCJleHAiOjE1NDYwMTAxOTN9.Xomvm6iP776gc3y7sF1RISRZLt7xNX4j-gTd4GR8UpE"`

dobije se odgovor:

`{"username":"admin","authorities":[{"authority":"ROLE_ADMIN"}]}`
