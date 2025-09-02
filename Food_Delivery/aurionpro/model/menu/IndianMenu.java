package com.aurionpro.model.menu;

import com.aurionpro.model.food.*;

public class IndianMenu implements IMenueType {

    private Menu menu;

    public IndianMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void showFullMenu() {
        new MenuPrinter(menu).showMenu();
    }

    @Override
    public Menu getMenu() {
        return menu;
    }
}
