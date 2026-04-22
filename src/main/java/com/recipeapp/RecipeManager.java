package com.recipeapp;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.File;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class RecipeManager {

    private static final String XML_PATH = "data/recipes.xml";
    private static final String XSL_PATH = "data/style.xsl";
    private static final String HTML_PATH = "data/index.html";

    private Document getDocument() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(XML_PATH));
    }
    private void saveDocument(Document doc) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        // Asegura que se mantenga la referencia al DTD
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "recipe.dtd");
        transformer.transform(new DOMSource(doc), new StreamResult(new File(XML_PATH)));
    }

    //Añadir receta con validación 
    public void saveRecipe(String title, String c1, String c2, String diff) throws Exception {
        if (title.isEmpty() || c1.isEmpty() || c2.isEmpty() || diff.isEmpty()) {
            throw new IllegalArgumentException("All fields are required for validation.");
        }
        Document doc = getDocument();
        Node recipesNode = doc.getElementsByTagName("recipes").item(0);

        Element newRecipe = doc.createElement("recipe");
        newRecipe.appendChild(createElement(doc, "title", title));
        newRecipe.appendChild(createElement(doc, "cuisine_type1", c1));
        newRecipe.appendChild(createElement(doc, "cuisine_type2", c2));
        newRecipe.appendChild(createElement(doc, "difficulty", diff));

        recipesNode.appendChild(newRecipe);
        saveDocument(doc);
    }

    //Insertar datos de usuario 
    public void saveUser(String name, String surname, String skill, String cuisine) throws Exception {
        Document doc = getDocument();
        Node usersNode = doc.getElementsByTagName("users").item(0);

        Element newUser = doc.createElement("user");
        newUser.appendChild(createElement(doc, "name", name));
        newUser.appendChild(createElement(doc, "surname", surname));
        newUser.appendChild(createElement(doc, "skill_level", skill));
        newUser.appendChild(createElement(doc, "preferred_cuisine", cuisine));

        usersNode.appendChild(newUser);
        saveDocument(doc);
    }

    //Recomendaciones basadas en el primer usuario 
    public void recommendBySkill() throws Exception {
        Document doc = getDocument();
        XPath xpath = XPathFactory.newInstance().newXPath();
        String skill = xpath.evaluate("/recipe_system/users/user[1]/skill_level", doc);
        String expression = "/recipe_system/recipes/recipe[difficulty='" + skill + "']";
        printResults("Recommendations by Skill (" + skill + "):", doc, xpath, expression);
    }

    public void recommendBySkillAndCuisine() throws Exception {
        Document doc = getDocument();
        XPath xpath = XPathFactory.newInstance().newXPath();
        String skill = xpath.evaluate("/recipe_system/users/user[1]/skill_level", doc);
        String cuisine = xpath.evaluate("/recipe_system/users/user[1]/preferred_cuisine", doc);
        
        String expression = "/recipe_system/recipes/recipe[difficulty='" + skill + "' and " +
                            "(cuisine_type1='" + cuisine + "' or cuisine_type2='" + cuisine + "')]";
        printResults("Advanced Recommendations (" + skill + " + " + cuisine + "):", doc, xpath, expression);
    }

    //Filtrar por tipo de cocina específico 
    public void filterByCuisine(String cuisine) throws Exception {
        Document doc = getDocument();
        XPath xpath = XPathFactory.newInstance().newXPath();
        String expression = "/recipe_system/recipes/recipe[cuisine_type1='" + cuisine + "' or cuisine_type2='" + cuisine + "']";
        printResults("Recipes for cuisine: " + cuisine, doc, xpath, expression);
    }

    //Generar vista HTML usando XSL (con colores) 
    public void generateHTMLView() throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xsl = new StreamSource(new File(XSL_PATH));
        Source xml = new StreamSource(new File(XML_PATH));
        Transformer transformer = factory.newTransformer(xsl);
        transformer.transform(xml, new StreamResult(new File(HTML_PATH)));
        System.out.println("View generated: " + HTML_PATH);
    }

    // Auxiliar para imprimir resultados en consola
    private void printResults(String header, Document doc, XPath xpath, String expression) throws Exception {
        System.out.println("\n--- " + header + " ---");
        NodeList nodes = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
        if (nodes.getLength() == 0) System.out.println("No matches found.");
        for (int i = 0; i < nodes.getLength(); i++) {
            Node recipe = nodes.item(i);
            String title = xpath.evaluate("title", recipe);
            String diff = xpath.evaluate("difficulty", recipe);
            System.out.println("Recipe: " + title + " | Level: " + diff);
        }
    }

    private Element createElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.setTextContent(value);
        return node;
    }
}