# project-stock-track
This project is generated with Java JDK 21 and Spring Boot 3. The project configuration is implemented in the dependency `lib-base-backend`, and the main configuration is in `lib-parent-project`.

## Project Structure
	- project.stock.track
		- app.beans         -> Entities and POJOs definition
		- app.repostitory   -> Database repositories implementation
		- app.utils         -> Utility functions implementation
		- app.vo            -> Definition of catalog entities, static data, and URIs
		- config            -> Project Configuration
		- modules           -> Controllers and business logic implementation
		- services          -> Services Implementation to consume external APIs

# lib-base-backend

## Project Structure
	- lib.base.backend.modules.web	-> Implementation of web configuration, enabled with the `@WebConfiguration` annotation
		- RestExceptionHandler	-> Global management of exceptions. Error HTTP 500 for internal erros and 422 for logic business
		- SpringFoxConfig 		-> Configuration for Swagger REST controller. URL: `<project_url>/swagger-ui/index.html`
		- PropertiesConfig		-> Definition of properties files: `application_default.properties` (in lib-base-backend) and `application.properties` (main project)
	
	- lib.base.backend.modules.security.jwt -> mplementation of JWT security, enabled with the `@JwtConfiguration` annotation
	- lib.base.backend.modules.catalog	-> Implementation of catalog definition, enabled with the `@CatalogConfiguration` annotation
	- lib.base.backend.web.config.beans
		- DataBaseBeans		-> Configuration of database entity managers for scope and transactions
		- UtilBeans			-> Configuration of base utilities

## How to implement modules in your project

### `Implement Catalog Module.`
	- Create a class to implement the `CatalogDefinition` interface.
	- Define Map of catalog lists
	- Override the `getCatalogsDefinition` method and return the defined Map of catalog lists.
	
Example: 

```java
public class CatalogStockTrackDefinition implements CatalogDefinition {

	@SuppressWarnings("rawtypes")
	Map<String, Class> catalogs = new LinkedHashMap<>();
	
	public CatalogStockTrackDefinition() {
		
		catalogs.put("sector", CatalogSectorEntity.class);
		catalogs.put("broker", CatalogBrokerEntity.class);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Class> getCatalogsDefinition() {
		return catalogs;
	}
}
```

# lib-parent-project-web

## Project Structure
	- Implementation of common depencences
	- Profile definition
		- generate_jpa_metamodel -> Generate JPA model based on entities defined in the main project
		- default               -> Default profile, which takes properties defined in the resource path and loads the main class defined in the `prop.main.class` property in the properties file
		- dev                   -> Development profile, which takes properties defined in `resources/config_properties/dev/` path and loads the main class defined in the `prop.main.class` property in the properties file
		- prod                  -> Production profile, which takes properties defined in `resources/config_properties/prod/` path and loads the main class defined in the `prop.main.class` property in the properties file