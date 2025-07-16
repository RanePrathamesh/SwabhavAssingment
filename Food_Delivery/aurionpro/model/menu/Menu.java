package com.aurionpro.model.menu;

import java.util.ArrayList;
import java.util.List;

import com.aurionpro.model.food.Food;

public class Menu {

	private String menuName;
	private List<Food> foods;

	public String getMenuName() {
		return menuName;
		
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public List<Food> getFoods() {
		return foods;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}

	public Menu() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Menu(String menuName) {
		super();
		this.menuName = menuName;
		this.foods = new ArrayList<Food>();
	}

	public void addFood(Food food) {
		foods.add(food);
	}
	
	

}

