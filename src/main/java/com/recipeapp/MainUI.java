package com.recipeapp;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MainUI extends JFrame {
    private RecipeManager manager = new RecipeManager();

    public MainUI() {
        // Configuración de la ventana
        setTitle("Recipe Recommender System");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1, 10, 10));

        // Creación de botones
        JButton btnView = new JButton("1. View All Recipes (XSL to HTML)");
        JButton btnAddRecipe = new JButton("2. Add New Recipe");
        JButton btnAddUser = new JButton("3. Set User Data");
        JButton btnRecommendSkill = new JButton("4. Recommend by Skill (XPath)");
        JButton btnRecommendFull = new JButton("5. Advanced Recommendation (XPath)");
        JButton btnFilterCuisine = new JButton("6. Filter by Cuisine (XPath)");

        // Añadir componentes a la interfaz
        add(new JLabel("Semantic Web Recipe Manager", JLabel.CENTER));
        add(btnView);
        add(btnAddRecipe);
        add(btnAddUser);
        add(btnRecommendSkill);
        add(btnRecommendFull);
        add(btnFilterCuisine);


        // 1. Generar HTML y abrirlo
        btnView.addActionListener(e -> {
            try {
                manager.generateHTMLView();
                JOptionPane.showMessageDialog(this, "HTML generated in data/index.html. Please open it manually.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error generating view: " + ex.getMessage());
            }
        });

        // 2. Formulario para añadir receta 
        btnAddRecipe.addActionListener(e -> {
            JTextField title = new JTextField();
            JTextField c1 = new JTextField("Italian");
            JTextField c2 = new JTextField("European");
            String[] diffs = {"Beginner", "Intermediate", "Advanced"};
            JComboBox<String> diffBox = new JComboBox<>(diffs);

            Object[] message = {
                "Title:", title,
                "Cuisine 1:", c1,
                "Cuisine 2:", c2,
                "Difficulty:", diffBox
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Add Recipe", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    manager.saveRecipe(title.getText(), c1.getText(), c2.getText(), (String)diffBox.getSelectedItem());
                    JOptionPane.showMessageDialog(this, "Recipe saved to XML!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Validation Error: " + ex.getMessage());
                }
            }
        });

        // 3. Formulario para datos de usuario 
        btnAddUser.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter User Name:");
            String cuisine = JOptionPane.showInputDialog("Preferred Cuisine:");
            if (name != null && cuisine != null) {
                try {
                    manager.saveUser(name, "User", "Intermediate", cuisine);
                    JOptionPane.showMessageDialog(this, "User data updated!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // 4. Recomendación por nivel 
        btnRecommendSkill.addActionListener(e -> {
            try {
                manager.recommendBySkill();
                JOptionPane.showMessageDialog(this, "Check the terminal/console for recommendations!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // 5. Recomendación avanzada 
        btnRecommendFull.addActionListener(e -> {
            try {
                manager.recommendBySkillAndCuisine();
                JOptionPane.showMessageDialog(this, "Check the terminal/console for recommendations!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // 6. Filtrar por cocina 
        btnFilterCuisine.addActionListener(e -> {
            String cuisine = JOptionPane.showInputDialog("Enter cuisine type (e.g. Italian):");
            if (cuisine != null) {
                try {
                    manager.filterByCuisine(cuisine);
                    JOptionPane.showMessageDialog(this, "Filtered results in terminal!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainUI();
    }
}