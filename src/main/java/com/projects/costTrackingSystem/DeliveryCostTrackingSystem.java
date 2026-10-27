package com.projects.costTrackingSystem;

import java.util.*;

enum PayStatus {
    PAID,
    UNPAID
}

class Delivery {
    final long deliveryId;
    final long startTime;
    final long endTime;
    final Driver driver;
    final long cost;
    PayStatus payStatus;

    Delivery(long deliveryId, long startTime, long endTime, Driver driver) {
        this.deliveryId = deliveryId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.driver = driver;
        this.payStatus = PayStatus.UNPAID;
        this.cost = endTime - startTime; // cost = duration in minutes
    }

    @Override
    public String toString() {
        return "Delivery ID: " + deliveryId + ", Start Time: " + startTime + ", End Time: " + endTime
                + ", Driver: " + driver.driverId + ", Cost: " + cost + ", Pay Status: " + payStatus;
    }
}

class Driver {
    final String driverId;
    final List<Delivery> deliveries = new ArrayList<>();

    Driver(String driverId) {
        this.driverId = driverId;
    }

    void addDelivery(Delivery delivery) {
        deliveries.add(delivery);
    }
}

public class DeliveryCostTrackingSystem {

    private static final long WINDOW_MINUTES = 24 * 60;

    private final Map<String, Driver> driversMap = new HashMap<>(); // id -> driver
    // unpaid deliveries ordered by endTime, so payUpToTime only touches what it pays
    private final PriorityQueue<Delivery> unpaidByEndTime = new PriorityQueue<>(Comparator.comparingLong(d -> d.endTime));
    // all deliveries (paid + unpaid) bucketed by startTime, for analytics
    private final TreeMap<Long, List<Delivery>> deliveriesByStartTime = new TreeMap<>();
    private long nextDeliveryId = 1;
    private long totalCost = 0L;
    private long totalPaid = 0L;

    public DeliveryCostTrackingSystem() {}

    public void addDriver(String driverId) {
        driversMap.putIfAbsent(driverId, new Driver(driverId));
    }

    public void addDelivery(String driverId, long startTime, long endTime) {
        Driver driver = driversMap.get(driverId);
        if (driver == null) {
            throw new IllegalArgumentException("Unknown driver: " + driverId);
        }
        if (startTime > endTime) {
            throw new IllegalArgumentException("startTime must be <= endTime");
        }

        Delivery del = new Delivery(nextDeliveryId++, startTime, endTime, driver);
        driver.addDelivery(del);
        unpaidByEndTime.add(del);
        deliveriesByStartTime.computeIfAbsent(startTime, k -> new ArrayList<>()).add(del);
        totalCost += del.cost;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public void payUpToTime(long upToTime) {
        while (!unpaidByEndTime.isEmpty() && unpaidByEndTime.peek().endTime <= upToTime) {
            Delivery del = unpaidByEndTime.poll();
            del.payStatus = PayStatus.PAID;
            totalPaid += del.cost;
        }
    }

    public long getCostToBePaid() {
        return totalCost - totalPaid;
    }

    public int getMaxActiveDriversInLast24Hours(long currentTime) {
        long windowStart = currentTime - WINDOW_MINUTES;
        Set<Driver> activeDrivers = new HashSet<>();

        for (List<Delivery> bucket : deliveriesByStartTime.headMap(currentTime, true).descendingMap().values()) {
            for (Delivery del : bucket) {
                if (del.endTime >= windowStart)
                    activeDrivers.add(del.driver);
            }
            if (activeDrivers.size() == driversMap.size()) break;
        }
        return activeDrivers.size();
    }

    public static void main(String[] args) {
        DeliveryCostTrackingSystem sys = new DeliveryCostTrackingSystem();
        sys.addDriver("D1");
        sys.addDriver("D2");

        sys.addDelivery("D1", 0, 10);
        sys.addDelivery("D1", 20, 40);
        sys.addDelivery("D2", 30, 50);

        System.out.println(sys.getTotalCost());                       // 50
        sys.payUpToTime(45);
        System.out.println(sys.getCostToBePaid());                    // 20
        System.out.println(sys.getMaxActiveDriversInLast24Hours(50)); // 2
    }
}
