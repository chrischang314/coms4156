package com.tabsnotspaces.match;

import com.tabsnotspaces.match.Consumer;
import com.tabsnotspaces.match.ServiceProvider;

import java.util.Comparator;
import java.util.List;

class ServiceProviderComparator implements Comparator<ServiceProvider> {

    private Consumer c;

    ServiceProviderComparator(Consumer c) {
        this.c = c;
    }

    // Calculates distance between two lat/long coordinate pairs
    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double t = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
                    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(t));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            return (dist);
        }
    }

    // Sort service providers first by distance from consumer, then by rating
    @Override
    public int compare(ServiceProvider o1, ServiceProvider o2) {
        List<Double> consumerLocation = c.getLocation();
        double dist1 = distance(consumerLocation.get(0), consumerLocation.get(1), o1.getLocation().get(0), o1.getLocation().get(1));
        double dist2 = distance(consumerLocation.get(0), consumerLocation.get(1), o2.getLocation().get(0), o2.getLocation().get(1));
        int value1 = Double.compare(dist1, dist2);
        if (value1 == 0) {
            return Long.compare(o2.getAvgRating(), o1.getAvgRating());
        }
        return value1;
    }
}