package com.projects.costTrackingSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryCostTrackingSystemTest {

    private DeliveryCostTrackingSystem system;

    @BeforeEach
    void setUp() {
        system = new DeliveryCostTrackingSystem();
    }

    @Test
    @DisplayName("Sample scenario from Problem Statement")
    void testSampleScenario() {
        system.addDriver("D1");
        system.addDriver("D2");

        system.addDelivery("D1", 0, 10);   // cost = 10
        system.addDelivery("D1", 20, 40);  // cost = 20
        system.addDelivery("D2", 30, 50);  // cost = 20

        assertEquals(50L, system.getTotalCost());

        system.payUpToTime(45);
        assertEquals(20L, system.getCostToBePaid());

        assertEquals(2, system.getMaxActiveDriversInLast24Hours(50));
    }

    @Nested
    @DisplayName("Part 1: Cost Calculation Tests")
    class Part1Tests {

        @Test
        @DisplayName("Total cost with multiple drivers and deliveries")
        void testTotalCost() {
            system.addDriver("driverA");
            system.addDriver("driverB");

            system.addDelivery("driverA", 100, 150); // 50
            system.addDelivery("driverA", 200, 260); // 60
            system.addDelivery("driverB", 50, 80);   // 30

            assertEquals(140L, system.getTotalCost());
        }

        @Test
        @DisplayName("Total cost starts at 0 for empty system")
        void testEmptySystemCost() {
            assertEquals(0L, system.getTotalCost());
        }

        @Test
        @DisplayName("Delivery with zero duration has zero cost")
        void testZeroDurationDelivery() {
            system.addDelivery("D1", 100, 100);
            assertEquals(0L, system.getTotalCost());
        }
    }

    @Nested
    @DisplayName("Part 2: Payment Tracking Tests")
    class Part2Tests {

        @Test
        @DisplayName("Partial payments with multiple time cutoffs")
        void testPartialPayments() {
            system.addDelivery("D1", 0, 10);   // end: 10, cost: 10
            system.addDelivery("D2", 5, 25);   // end: 25, cost: 20
            system.addDelivery("D1", 20, 50);  // end: 50, cost: 30
            system.addDelivery("D3", 60, 100); // end: 100, cost: 40

            assertEquals(100L, system.getTotalCost());
            assertEquals(100L, system.getCostToBePaid());

            // Pay deliveries ending <= 20 (only D1's 0-10)
            system.payUpToTime(20);
            assertEquals(100L, system.getTotalCost());
            assertEquals(90L, system.getCostToBePaid());

            // Pay deliveries ending <= 50 (D2's 5-25 and D1's 20-50)
            system.payUpToTime(50);
            assertEquals(100L, system.getTotalCost());
            assertEquals(40L, system.getCostToBePaid());

            // Pay all remaining (<= 150)
            system.payUpToTime(150);
            assertEquals(0L, system.getCostToBePaid());
        }

        @Test
        @DisplayName("Payment when upToTime does not match any delivery end time")
        void testPaymentNoMatch() {
            system.addDelivery("D1", 10, 20); // end: 20, cost: 10
            system.payUpToTime(15);
            assertEquals(10L, system.getCostToBePaid());
        }
    }

    @Nested
    @DisplayName("Part 3: Analytics - Max Active Drivers in 24 Hours Tests")
    class Part3Tests {

        @Test
        @DisplayName("Window overlap check: [currentTime - 1440, currentTime]")
        void test24HourWindowOverlap() {
            long currentTime = 2000L;
            long windowStart = currentTime - 1440L; // 560

            // D1: before window entirely [0, 500] -> not active
            system.addDelivery("D1", 0, 500);

            // D2: touches window start [500, 560] -> active (overlaps at 560)
            system.addDelivery("D2", 500, 560);

            // D3: strictly inside window [1000, 1200] -> active
            system.addDelivery("D3", 1000, 1200);

            // D4: starts before, ends after currentTime [500, 2500] -> active
            system.addDelivery("D4", 500, 2500);

            // D5: starts inside, ends after currentTime [1900, 2200] -> active
            system.addDelivery("D5", 1900, 2200);

            // D6: strictly after currentTime [2001, 2100] -> not active
            system.addDelivery("D6", 2001, 2100);

            // Active drivers: D2, D3, D4, D5 (4 unique drivers)
            assertEquals(4, system.getMaxActiveDriversInLast24Hours(currentTime));
        }

        @Test
        @DisplayName("Same driver with multiple deliveries counted once")
        void testUniqueDriverCount() {
            long currentTime = 1500L;

            // D1 has 3 active deliveries in window
            system.addDelivery("D1", 100, 200);
            system.addDelivery("D1", 300, 400);
            system.addDelivery("D1", 500, 600);

            // D2 has 1 active delivery
            system.addDelivery("D2", 700, 800);

            assertEquals(2, system.getMaxActiveDriversInLast24Hours(currentTime));
        }

        @Test
        @DisplayName("Active drivers when no deliveries in window")
        void testNoActiveDrivers() {
            system.addDelivery("D1", 0, 100);
            assertEquals(0, system.getMaxActiveDriversInLast24Hours(5000));
        }
    }
}
