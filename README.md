# PetAdoption-API
The Pet Adoption API is a RESTful API that provides a way to manage animals available for adoption. It allows users to list animals and filter by different criteria such as name, category, status, and creation date. In addition, it provides an endpoint to initiate the indexing of animals in the database.


### Deployment
This application is deployed on the following domain in AWS: http://petadoptionapi-env.eba-zdkxpjws.us-east-1.elasticbeanstalk.com.

### Documentation
The API documentation is available at https://doc-pet-adoption-api.s3.amazonaws.com/index.html.

### Postman Collection
You can use the Postman collection to run the Pet Adoption API
[![Run in Postman](https://run.pstmn.io/button.svg)](https://god.gw.postman.com/run-collection/17951589-fb2249dd-80ad-4b27-b938-3b3a9045dfed?action=collection%2Ffork&collection-url=entityId%3D17951589-fb2249dd-80ad-4b27-b938-3b3a9045dfed%26entityType%3Dcollection%26workspaceId%3D457a6edf-3fb7-4ca7-86a1-fec6527654ee)

## Running the Project
To run the project, follow these steps:



1. Run `docker-compose up -d` to start the database.
2. Run `./gradlew flywayMigrate` to run the migrations.
3. Run `./gradlew bootrun` to start the application.

### Disclaimer

Please note that default Postgres values and external provider API keys have been used for the sake of convenience and ease of running the project. In a real-life scenario, it is highly recommended to use secure credentials and values tailored to your specific environment.
