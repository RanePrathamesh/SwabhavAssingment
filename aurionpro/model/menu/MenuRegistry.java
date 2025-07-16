package com.aurionpro.model.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuRegistry {
    private static List<IMenueType> menuTypes = new ArrayList<>();

    public static void addMenuType(IMenueType menu) {
    	boolean alreadyExists = false;
    	for (IMenueType existing : menuTypes) {
    	    if (existing.getMenu().getMenuName().equalsIgnoreCase(menu.getMenu().getMenuName())) {
    	        alreadyExists = true;
    	        break;
    	    }
    	}
    	if (!alreadyExists) {
    	    menuTypes.add(menu);
    	}

    }


    public static List<IMenueType> getMenuTypes() {
        return menuTypes;
    }

    public static void clearMenus() {
        menuTypes.clear();
    }
}
