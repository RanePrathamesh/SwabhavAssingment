package com.aurionpro.model.delivery;

public class DeliveryAssigner {

    public static IDeliveryPartner assignRandomPartner() {
        IDeliveryPartner partner = DeliveryRegistry.getRandomPartner();

        
        if (partner != null) {
            return partner;
        } else {
            return new IDeliveryPartner() {
                @Override
                public String getPartnerName() {
                    return "No Partners Available";
                }
            };
        }

    }
}
