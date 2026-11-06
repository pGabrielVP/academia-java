# Index
 - [Project Structure](#Package/Project)
 - [Roadmap](#Roadmap)

# Package/Project structure

 - controle - exposes the methods to be used by the view layer
 - facade - contains the database connection and the CRUD methods
 - model - contains both the database entities and the DTOs for filling reports
 - service - contains the methods responsible for loading and filling the report template and exporting the result to PDF
 - view - contains the swing views
 - resources - contains the persistence configuration and the report templates
 - Main.java is the application entry point

# Roadmap

 1. Switching to spring boot
 3. Adding a web interface with angular
 4. Adding new report templates
 5. Improving how images are stored
