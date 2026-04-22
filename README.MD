# Semantic Web - Recipe Recommender System (Homework 1)

## Author Information
* **Name:** Laura Eraso 
 

## Project Description
This Java application facilitates recipe recommendations based on user profiles using Semantic Web technologies. It manages a database of recipes and users stored in XML format. 

## Key Features
* **Automated Data Entry:** A Scraper that extracts real recipe titles from BBC Good Food. 
* **XML Validation:** Data integrity ensured via DTD (Document Type Definition). 
* **Dynamic Recommendations:** Uses XPath to filter recipes based on user skill level and cuisine preferences. 
* **XSL Visualizer:** Generates an HTML view where recommended recipes are highlighted in yellow and others in green. 
* **CRUD Operations:** Forms to add new recipes and user data directly to the XML file. 

## Project Structure
* `/data`: Contains `recipes.xml`, `recipe.dtd`, `style.xsl`, and the generated `index.html`.
* `/src`: Java source code (Scraper, RecipeManager, and MainUI).
* `pom.xml`: Maven configuration and dependencies (Jsoup).

## How to Run
1. **Compile:** `mvn clean compile`
2. **Initialize Database:** `mvn exec:java -Dexec.mainClass="com.recipeapp.Scraper"`
3. **Launch Application:** `mvn exec:java -Dexec.mainClass="com.recipeapp.MainUI"`
