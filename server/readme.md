# Analysis & Development Project - Adria 2084 - server project

This is the **server side start-project**.

This start project provides the basic scaffolding for an openapi webserver and an example bridge class for websockets.

There is already a fully working minimal example api with all the necessary classes.

Example classes (except WebServer.java) are allowed to be modified or deleted.

## Before you start:
- Choose Zulu jdk version 17 or opendjk 17 (Configure through this through intelij)
- Make sure to clone **all** the repositories **client**, **server** & **documentation**
    - **Use the following folder structure**
        - root_folder_with_name_of_choice
            - client
            - documentation
            - server

## Local testing and quality checks
You can **run** the Sonar validator and code coverage **locally!**

There is no need to push to the server to check if you are compliant with our rules.
In the interest of sparing the server, please result to local testing as often as possible.

**If everyone pushes to test, the remote will not last.**

Use the sonarlint plugin to see any code smells.
  - In the sonarlint plugin.
  - Open the report tab
  - Click on the Analyze all project files button. (left side)


## Configuring properties
All properties for a local setup are located in **conf/config.json**.

The properties for the deployed version are located on the remote server.

Adding properties to conf/config.json are not automatically pushed to the deployed version on the remote server.

Adding new properties to the local config file is perfectly fine.

However, to apply new properties or property modifications on the server please contact mr. Blomme on MS Teams. With the following data:

  - valid config file in json format with filename config-group-XX.

Please, test the config file thoroughly on your local machine as mistakes will not be fixed every day.

## What's included
  - A very basic openapi specification
    - localhost:8080/api/quotes
  - H2 database web console
  - The setup of a vert.x and openapi (WebServer.java)
  - Minimal H2 repository class
  - A starter class for the RTC topic (RtcBridge.java)
  - Database generation scripts

## How to run the start project locally
In Intelij choose gradle task **run**.

## Location OpenApi Specification
The location of the openapi specification is defined in the file **config**.

The property is called **api.url**.

By default, the local setup will pick the openapi specification located at https://project-2.ti.howest.be/monitor/apis/group-13.

If for some reason, the api isn't available or you want to use the specification in your **local** documentation folder.
```json
"api": {
"url": "../documentation/api-spec/openapi.yaml"
}
```
 - For the to work, the folder structure must be organised as describe above.

## Local endpoints
 - H2 web client
   - localhost:9000
   - url: jdbc:h2:./db-13
   - no credentials
 - Web api
   - localhost:8080/api/quotes
 - Web client
   - launch through webstorm/phpstorm (see client-side readme)
  
## Production endpoints
 - H2 web client
   - https://project-2.ti.howest.be/2023-2024/group-13/db
   - url: jdbc:h2:/opt/2023-2024/group-13/db-13
   - username:group-13
   - password: pass-13
 - Useful information
   - Server logs
     - https://project-2.ti.howest.be/monitor/logs/group-13
   - Swagger Interface
     - https://project-2.ti.howest.be/monitor/swagger/group-13
     - Through this GUI remote & local API testing is possible!
   - Overview of all Adria API's
     - https://project-2.ti.howest.be/monitor/overview/
     - Please complete the openapi.yaml file to contribute useful information to the overview page.
 - Web client project
   - https://project-2.ti.howest.be/2023-2024/group-13/
 - Sonar
   - https://sonarqube.ti.howest.be/dashboard?id=2023.project-2%3Aadria-server-13
   - https://sonarqube.ti.howest.be/dashboard?id=2023.project-2%3Aadria-client-13
   - Sonarlint login token: squ_710779242d402f3dae5daefef3d48752b535862f

## Keep the database up to date
There is no need to manually add entries into the database.

Please use the scripts: **db-create** and **db-populate** in the resource folder.

Everytime you run the api, the database will be rebuilt to the state described in db-create and db-populate scripts.

The **db-create** script is responsible for the database structure (tables, primary keys, ...)

The **db-populate** script is responsible for populating the database with useful data.

## Adding/updating an openapi endpoint.
   1. Update the openapi specification in the documentation repo.
      2. Commit and push the results.
   2. Update the function **buildRouter** in the class **OpenApiBridge**
      1. Map the operationId (openapi id) to a new function in the class **OpenApiBridge**
      1. Create this new function in the **OpenApiBridge**
   2. (Optional) Use the Request class to get the data from the ctx parameter. 
   3. Add the wanted functionality to the controller layer and the layers below.
   4. Add a new response function in the **Response** class if needed.
   6. Write unit tests
