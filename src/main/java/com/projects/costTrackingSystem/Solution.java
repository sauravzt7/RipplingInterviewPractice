package com.projects.costTrackingSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * HackerRank-style runner simulation for Delivery Cost Tracking System.
 *
 * Supported Commands:
 * - ADD_DRIVER <driverId>
 * - ADD_DELIVERY <driverId> <startTime> <endTime>
 * - GET_TOTAL_COST
 * - PAY_UP_TO_TIME <upToTime>
 * - GET_COST_TO_BE_PAID
 * - GET_MAX_ACTIVE_DRIVERS <currentTime>
 */
public class Solution {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("demo")) {
            runSampleSimulation();
            return;
        }

        System.out.println("=== Delivery Cost Tracking System Simulation ===");
        System.out.println("Running sample simulation from problem statement:\n");
        runSampleSimulation();
        System.out.println("\nYou can also provide standard input (enter commands line by line, EOF to end).");
    }

    public static void runSampleSimulation() {
        String sampleInput = """
                ADD_DRIVER D1
                ADD_DRIVER D2
                ADD_DELIVERY D1 0 10
                ADD_DELIVERY D1 20 40
                ADD_DELIVERY D2 30 50
                GET_TOTAL_COST
                PAY_UP_TO_TIME 45
                GET_COST_TO_BE_PAID
                GET_MAX_ACTIVE_DRIVERS 50
                """;

        System.out.println("Input Operations:");
        System.out.println(sampleInput);
        System.out.println("Output:");
        processInput(new BufferedReader(new StringReader(sampleInput)));
    }

    public static void processInput(BufferedReader reader) {
        DeliveryCostTrackingSystem system = new DeliveryCostTrackingSystem();

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#") || line.startsWith("//")) {
                    continue;
                }

                String[] parts = line.split("\\s+");
                String command = parts[0].toUpperCase();

                switch (command) {
                    case "ADD_DRIVER" -> {
                        String driverId = parts[1];
                        system.addDriver(driverId);
                    }
                    case "ADD_DELIVERY" -> {
                        String driverId = parts[1];
                        long startTime = Long.parseLong(parts[2]);
                        long endTime = Long.parseLong(parts[3]);
                        system.addDelivery(driverId, startTime, endTime);
                    }
                    case "GET_TOTAL_COST" -> {
                        System.out.println(system.getTotalCost());
                    }
                    case "PAY_UP_TO_TIME" -> {
                        long upToTime = Long.parseLong(parts[1]);
                        system.payUpToTime(upToTime);
                    }
                    case "GET_COST_TO_BE_PAID" -> {
                        System.out.println(system.getCostToBePaid());
                    }
                    case "GET_MAX_ACTIVE_DRIVERS" -> {
                        long currentTime = Long.parseLong(parts[1]);
                        System.out.println(system.getMaxActiveDriversInLast24Hours(currentTime));
                    }
                    default -> System.err.println("Unknown command: " + command);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runInteractive() {
        processInput(new BufferedReader(new InputStreamReader(System.in)));
    }
}
