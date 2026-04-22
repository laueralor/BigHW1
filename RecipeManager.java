import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.File;

public class RecipeManager {
    public void getRecommendations() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = factory.newDocumentBuilder().parse(new File("recipes.xml"));
        XPath xpath = XPathFactory.newInstance().newXPath();

        String userSkill = xpath.evaluate("/recipe_system/users/user[1]/skill_level", doc);
        
        String expression = "/recipe_system/recipes/recipe[difficulty='" + userSkill + "']";
        NodeList nodes = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println("Recommended: " + 
                xpath.evaluate("title", nodes.item(i)));
        }
    }
}