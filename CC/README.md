# Obatin-API
Obatin-API project is part of the Obatin application. This is an API build with the Hapi.js as web application framework, Sequelize as ORM, and node-nlp as a BOT mockup.

&nbsp;
Note : for now the bot mockup plugin has been disabled but if you want to activate it, please uncomment the "talk" plugin in the server.js file

## Features
* Build with Hapi Js framework so that code can be easily modularized
* API documentations with Swagger
* Bearer authentication
* In-Memory Cache with Memcached

## Project structure 
* **src**
  * **api** (custom plugin)
    * **authentications**
      * documentations.js (documentation and authentication need to be stored here)
      * handler.js
      * index.js
      * routes.js
    * **ML**
    * **talk**
    * **users**
  * **bot** (storing intents and model from the bot mockup)
  * **config** (db config)
  * **exceptions** (handling error exceptions)
  * **migrations** (migration files)
  * **models** (contains all of model classes)
  * **services** (service layer)
  * **tokenize** (set of functions to handle jwt tokens)
  * **utils** (This directory contains several utility programs)
  * **validator** (validation input output)
  * server.js (function that start hapi server)
  * swaggerOption.js (swagger options)
* .env (environment file)

## Installation
### Pull image from Docker HUB
  ```
  $ docker pull c2159f1689/c22-ps234-cc-api
  ```
run container, dont forget to change environment, MIGRATE=1 (optional it's for database migration), example :
  ```
  $ docker run -d --name cc-api -e NODE_ENV=production -e MIGRATE=1 -e HOST=0.0.0.0 -e PORT=5000 -e DB_HOST=172.17.0.7 -e DB_USERNAME=username -e DB_PASSWORD=secretpass -e DB_DATABASE=my_db -e DB_DIALECT=postgres -e MEMCACHIER_SERVERS=172.17.0.8 -e ACCESS_TOKEN_AGE=900 -e ACCESS_TOKEN_KEY=nmcd8sajdsa8 -e REFRESH_TOKEN_KEY=mcd9aidmacid -e ML_API=http://ml-api.com -p 5000:5000 c22-ps234-cc-api:latest
  ```
View the API documentation at
[http://localhost:5000/docs](http://localhost:5000/docs)
### Build from Dockerfile
1) Clone this repo
  ```
  $ git clone https://github.com/Ian-72/Obatin-API.git
  ```

2) Go to Obatin-API directory
  ```
  $ cd Obatin-API
  ```

3) Build docker image
  ```
  $ docker build -t c22-ps234-cc-api:latest .
  ```

5) run container, dont forget to change environment, MIGRATE=1 (optional it's for database migration), example
  ```
  $ docker run -d --name cc-api -e NODE_ENV=production -e MIGRATE=1 -e HOST=0.0.0.0 -e PORT=5000 -e DB_HOST=172.17.0.7 -e DB_USERNAME=username -e DB_PASSWORD=secretpass -e DB_DATABASE=my_db -e DB_DIALECT=postgres -e MEMCACHIER_SERVERS=172.17.0.8 -e ACCESS_TOKEN_AGE=900 -e ACCESS_TOKEN_KEY=nmcd8sajdsa8 -e REFRESH_TOKEN_KEY=mcd9aidmacid -e ML_API=http://ml-api.com -p 5000:5000 c22-ps234-cc-api:latest
  ```

6) View the API documentation at
[http://localhost:5000/docs](http://localhost:5000/docs)