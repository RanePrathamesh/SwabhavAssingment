package com.aurionpro.model.menu;

import com.aurionpro.model.food.Food;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MenuPrinter {

    private final Menu menu;

    public MenuPrinter(Menu menu) {
        this.menu = menu;
    }

    public void showMenu() {
        System.out.println("\n********** " + menu.getMenuName().toUpperCase() + " **********\n");

        List<Food> foods = menu.getFoods();
        Map<String, List<Food>> foodTypeMap = new LinkedHashMap<>();

        for (Food food : foods) {
            String type = food.getFoodType().getFoodType();
            foodTypeMap.putIfAbsent(type, new ArrayList<>());
            foodTypeMap.get(type).add(food);
        }

        for (Map.Entry<String, List<Food>> entry : foodTypeMap.entrySet()) {
            String header = formatFoodType(entry.getKey());
            System.out.println("========== " + header + " ==========");

            printFoodTable(entry.getValue());
            System.out.println();
        }
    }

    private void printFoodTable(List<Food> foodList) {
        System.out.printf("%-8s %-20s %-45s %-10s\n", "ID", "Name", "Description", "Price (₹)");
        System.out.println("---------------------------------------------------------------------------------------------");

        for (Food food : foodList) {
            System.out.printf(
                    "%-8s %-20s %-45s %-10.2f\n",
                    food.getFoodId(),
                    food.getFoodname(),
                    food.getDescription(),
                    food.getPrice()
            );
        }

        System.out.println("---------------------------------------------------------------------------------------------");
    }

    private String formatFoodType(String rawType) {
        switch (rawType.toLowerCase()) {
            case "starter":
                return "STARTERS";
            case "maincourse":
                return "MAIN COURSE";
            case "dessert":
                return "DESSERTS";
            default:
                return rawType.toUpperCase();
        }
    }
}
