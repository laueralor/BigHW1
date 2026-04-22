package com.recipeapp;

import org.w3c.dom.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.util.Random;

public class Scraper {
    public static void main(String[] args) {
        try {
            // Conexión a la web de la BBC 
            Document doc = Jsoup.connect("https://www.bbcgoodfood.com/recipes/collection/budget-autumn").get();
            Elements titles = doc.select("h2"); // Selector típico para títulos en esa web

            String[] cuisines = {"Italian", "Asian", "Mexican", "French", "Indian"};
            String[] difficulties = {"Beginner", "Intermediate", "Advanced"};
            Random rand = new Random();

            StringBuilder xml = new StringBuilder();
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xml.append("<!DOCTYPE recipe_system SYSTEM \"recipe.dtd\">\n");
            xml.append("<recipe_system>\n<users>\n<user>\n<name>Laura</name>\n<surname>Eraso</surname>\n");
            xml.append("<skill_level>Intermediate</skill_level>\n<preferred_cuisine>Italian</preferred_cuisine>\n");
            xml.append("</user>\n</users>\n<recipes>\n");

            // Solo necesitamos 20 recetas 
            for (int i = 0; i < Math.min(20, titles.size()); i++) {
                String cleanTitle = titles.get(i).text().replace("&", "&amp;");
                xml.append("  <recipe>\n");
                xml.append("    <title>").append(cleanTitle).append("</title>\n");
                xml.append("    <cuisine_type1>").append(cuisines[rand.nextInt(cuisines.length)]).append("</cuisine_type1>\n");
                xml.append("    <cuisine_type2>").append(cuisines[rand.nextInt(cuisines.length)]).append("</cuisine_type2>\n");
                xml.append("    <difficulty>").append(difficulties[rand.nextInt(difficulties.length)]).append("</difficulty>\n");
                xml.append("  </recipe>\n");
            }

            xml.append("</recipes>\n</recipe_system>");

            // Sobrescribir el archivo recipes.xml con los datos reales 
            FileWriter writer = new FileWriter("data/recipes.xml");
            writer.write(xml.toString());
            writer.close();
            System.out.println("XML with 20 recipes generated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}