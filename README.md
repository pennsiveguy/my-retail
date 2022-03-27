### my-retail ###
Technical Case Study of a Retail ReST API.

#### Summary ####
This application is a rudimentary REST API by which to retrieve basic product data from the myRetail company, and to update the prices of those products. The application's [Swagger page](http://pennsive.com/products/swagger-ui.html) describes how to use it. A sample JSON output can be seen at [http://pennsive.com/products/13860428](http://pennsive.com/products/13860428). It retrieves product data from an external source and price data from a MongoDB database.

#### Technology/tools ####
- Gradle
- Java 1.8
- Spring Boot
- Spring Data MongoDB
- JUnit
- Mockito
- OpenAPI (Swagger)
- Postman


#### Downloading the Source Code ####
The source code repository is hosted on [GitHub](https://github.com/pennsiveguy/my-retail). Included are:
1. The production code and configuration files
2. Unit tests with JUnit4 and Mockito
3. Controller Integration tests, to verify the correct integration and behavior of the REST Controller and Advice classes
4. MongoDB database integration tests to verify basic CRUD functionality against the database
5. Miscellaneous set-up files and a data import/load script

#### Setting Up, Testing, and Running the Application ####
* Clone the [GitHub](https://github.com/pennsiveguy/my-retail) repository into your workspace

		git clone https://github.com/pennsiveguy/my-retail.git

* Create a mongdb database with the name 'my-retail'. Import the price document data into your mongodb instance by running the import script in the project's root folder.

		sh ./import-data.sh
		
* Run all the tests from the command line, or by using your IDE's run configuration.

		gradle clean test
		
* Start the application from the command line, or by using your IDE's run configuration to run the 'MyRetailApplication.java' class.

		gradle clean bootRun
		
* The application's OpenAPI/Swagger page will appear at the following path:
		
		[scheme:hostname:port]/products/swagger-ui.html
		
* In the '/src/test/resources' folder there's a Postman collection that can be imported into Postman and run against either the local app (the 'Local' environment) as well as the hosted instance at pennsive.com (the 'Demo' environment). The environments can be imported from the included Postname environment files:

		myRetail.postman_collection.json
		Local.postman_environment.json
		Demo (external).postman_environment.json

