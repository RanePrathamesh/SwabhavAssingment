package com.aurionpro.model.order;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private static List<Order> allOrders = new ArrayList<>();

    public static int getNextOrderId() {
        return allOrders.size() + 1;
    }

    public static void addOrder(Order order) {
        allOrders.add(order);
    }

    public static List<Order> getAllOrders() {
        return allOrders;
    }
}
