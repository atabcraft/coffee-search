# General information

- Database name: coffeuniverse
- Database scheme: public
- username/password : armin/secr3t

## Starting the application:

### Backend 
- Source Packages -> com.example.search.coffee -> Right click "Run file" or 
- Go to root of project and type in terminal: ./mvnw (Mac OS X/Linux) or mvnw (Windows)

Application can be reached in browser on URL http://localhost:8080

### Frontend
Go to root of project and type in terminal: -> yarn start ( or npm start )

# General recomendations

	Frontend: Visual Studio Code - Great support for angular 7 + TS

	Backend : Netbeans - For Maven project, DBeaver for DB client

# Setup of project
	
## Setup and running PostgeSQL with Docker
More about it here
https://docs.docker.com/samples/library/postgres/

command to run:

`docker run --name coffee-postgres -p 5432:5432 -e POSTGRES_PASSWORD=secr3t -e POSTGRES_USER=armin -e POSTGRES_DB=coffeuniverse -d postgres`

## Setup and running Elasticsearch for development

Elasticsearch Node
An Elasticsearch Node is the smallest unit which you can interact with in Elasticsearch. 
It is the gateway to the receiving, analyzing and storing of the data that is to be retrieved later via searching.
When you have Elasticsearch running as a stand alone application, you have a node.
The combination of one or more nodes, configured with the same cluster name, gives you a cluster.

It is also possible to have this Elasticsearch node created and have it running in the computer’s memory, but within an application, without the need to kickstart it as a standalone process.

Currently ES is being used as in-memory Spring bean inside Spring

More about this method here https://www.elastic.co/guide/en/elasticsearch/reference/6.5/docker.html


Alternatively docker instance can be created locally for local Elasticsearch instance with command like this:
`docker run --name zato-es -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e "xpack.security.enabled=false" docker.elastic.co/elasticsearch/elasticsearch:5.6.10`

## Setup of kibana ( if needed )
Login as root to docker image

`docker run -e XPACK_MONITORING_ENABLED=false -e SERVER_NAME=localhost -d --name kibana -p 5601:5601 docker.elastic.co/kibana/kibana:5.6.10`
`docker exec -it -u 0 kibana bash#kibana is the name of container`
`cd  config`

`vi kibana.yml#add xpack.security.enabled: false at the end`

...

`#Default Kibana configuration from kibana-docker`
`server.name: kibana`
`server.host: "0"`
`elasticsearch.url: http://localhost:9200`
`elasticsearch.username: elastic`
`elasticsearch.password: changeme`
`xpack.monitoring.ui.container.elasticsearch.enabled: false`
`xpack.security.enabled: false`

...

`exit`
`docker restart kibana`

# First user in system: administrator

First user is created in data.sql and by default application is configurated in such a way that all tables are created and default data is filled for demo purpose. 

Sign-in validity of first user can be checked with following command

`curl -X POST http://localhost:8080/api/users/sign-in?rememberMe=true -H "Content-Type:application/json" -d "{\"username\":\"admin\", \"password\":\"admin\"}"`
 
Response:

`{"username":"admin","token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNTQ1NDA1MzkzLCJleHAiOjE1NDYwMTAxOTN9.Xomvm6iP776gc3y7sF1RISRZLt7xNX4j-gTd4GR8UpE"}`

Now its possible to access protected URL with token from response with CURL from terminal by running command:

`curl -X GET http://localhost:8080/api/users/current -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNTQ1NDA1MzkzLCJleHAiOjE1NDYwMTAxOTN9.Xomvm6iP776gc3y7sF1RISRZLt7xNX4j-gTd4GR8UpE"`

Response:

`{"username":"admin","authorities":[{"authority":"ROLE_ADMIN"}]}`
