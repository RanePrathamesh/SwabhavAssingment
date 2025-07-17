package com.aurionpro.model.delivery;

import static com.aurionpro.model.initializer.AppInitializer.DEBUG;

import java.util.ArrayList;
import java.util.List;

public class DeliveryRegistry {

    private static final List<IDeliveryPartner> partners = new ArrayList<>();

    public static void addPartner(IDeliveryPartner partner) {
        boolean exists = false;

        for (IDeliveryPartner existingPartner : partners) {
            if (existingPartner.getPartnerName().equalsIgnoreCase(partner.getPartnerName())) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            partners.add(partner);
            if (DEBUG) System.out.println("Delivery partner added: " + partner.getPartnerName());
        } else {
            if (DEBUG) System.out.println("Partner already exists: " + partner.getPartnerName());
        }
    }

    public static List<IDeliveryPartner> getPartners() {
        return new ArrayList<>(partners); // Safe copy
    }

    public static IDeliveryPartner getRandomPartner() {
        if (partners.isEmpty()) return null;
        int index = (int) (Math.random() * partners.size());
        return partners.get(index);
    }
}
