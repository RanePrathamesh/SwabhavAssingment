package com.aurionpro.model.initializer;

import com.aurionpro.model.delivery.*;
import com.aurionpro.model.food.*;
import com.aurionpro.model.menu.*;

public class AppInitializer {
    private static boolean initialized = false;
    public static boolean DEBUG = false;

    public static void initializeDefaults() {
        if (initialized) return;

      
        DeliveryRegistry.addPartner(new Swiggy());
        DeliveryRegistry.addPartner(new Zomato());

        Menu indianMenu = new Menu("Indian Cuisine");
        indianMenu.addFood(new Food("S001", "Vegetable Soup", "A hot and spicy vegetable soup.", 5, new Starter()));
        indianMenu.addFood(new Food("S002", "Samosa", "Crispy pastry filled with spiced potatoes.", 3, new Starter()));
        indianMenu.addFood(new Food("M001", "Chicken Curry", "Spicy chicken curry served with rice.", 12, new MainCourse()));
        indianMenu.addFood(new Food("M002", "Butter Chicken", "Creamy chicken curry with a rich flavor.", 14, new MainCourse()));
        indianMenu.addFood(new Food("D001", "Gulab Jamun", "Fried dough balls soaked in sweet syrup.", 4, new Dessert()));
        indianMenu.addFood(new Food("D002", "Rasgulla", "Soft spongy balls made of cottage cheese soaked in syrup.", 4, new Dessert()));

        
        Menu italianMenu = new Menu("Italian Cuisine");
        italianMenu.addFood(new Food("S003", "Bruschetta", "Grilled bread rubbed with garlic and topped with tomatoes.", 6.99, new Starter()));
        italianMenu.addFood(new Food("S004", "Caprese Salad", "Fresh mozzarella, tomatoes, and basil.", 7.99, new Starter()));
        italianMenu.addFood(new Food("M003", "Lasagna", "Layers of pasta with meat and cheese.", 14.99, new MainCourse()));
        italianMenu.addFood(new Food("M004", "Pizza Margherita", "Classic pizza with tomato and cheese.", 12.99, new MainCourse()));
        italianMenu.addFood(new Food("D003", "Tiramisu", "Coffee-flavored Italian dessert.", 5.99, new Dessert()));
        italianMenu.addFood(new Food("D004", "Panna Cotta", "Creamy dessert topped with berries.", 5.49, new Dessert()));

        
        Menu japaneseMenu = new Menu("Japanese Cuisine");
        japaneseMenu.addFood(new Food("S005", "Miso Soup", "Traditional Japanese soup with tofu.", 4, new Starter()));
        japaneseMenu.addFood(new Food("S006", "Edamame", "Steamed soybeans with sea salt.", 5, new Starter()));
        japaneseMenu.addFood(new Food("M005", "Sushi", "Rice rolls with fish and vegetables.", 12, new MainCourse()));
        japaneseMenu.addFood(new Food("M006", "Teriyaki Chicken", "Grilled chicken with teriyaki sauce.", 13, new MainCourse()));
        japaneseMenu.addFood(new Food("D005", "Matcha Ice Cream", "Green tea flavored ice cream.", 3, new Dessert()));
        japaneseMenu.addFood(new Food("D006", "Mochi", "Sweet rice cake with filling.", 4, new Dessert()));

        
        MenuRegistry.addMenuType(new IndianMenu(indianMenu));
        MenuRegistry.addMenuType(new ItalianMenu(italianMenu));  // using lambda for Italian
        MenuRegistry.addMenuType(new JapaneseMenu(japaneseMenu)); // using lambda for Japanese

        initialized = true;
    }
}
