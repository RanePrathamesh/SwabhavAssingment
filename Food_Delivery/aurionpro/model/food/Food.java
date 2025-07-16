package com.aurionpro.model.food;

public class Food {
	private String foodId;
	private String foodName;
	private String description;
	private double price;
	private IFoodType foodType;
	public Food(String foodId, String foodname, String description, double price, IFoodType foodType) {
		super();
		this.foodId = foodId;
		this.foodName = foodname;
		this.description = description;
		this.price = price;
		this.foodType = foodType;
	}
	public Food() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Food [foodId=" + foodId + ", foodname=" + foodName + ", description=" + description + ", price=" + price
				+ ", foodType=" + foodType + "]";
	}
	public IFoodType getFoodType() {
		return foodType;
	}
	public void setFoodType(IFoodType foodType) {
		this.foodType = foodType;
	}
	public String  getFoodId() {
		return foodId;
	}
	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}
	public String getFoodname() {
		return foodName;
	}
	public void setFoodname(String foodname) {
		this.foodName = foodname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
