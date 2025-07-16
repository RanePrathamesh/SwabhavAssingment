package com.aurionpro.model.menu;

import com.aurionpro.model.food.Dessert;
import com.aurionpro.model.food.Food;
import com.aurionpro.model.food.MainCourse;
import com.aurionpro.model.food.Starter;

public class JapaneseMenu implements IMenueType {
	private Menu menu;

	public JapaneseMenu(Menu menu) {
		this.menu =menu;
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