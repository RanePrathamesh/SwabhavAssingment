package com.aurionpro.model.menu;



public class ItalianMenu implements IMenueType {

    private Menu menu;

    public ItalianMenu(Menu menu) {
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
