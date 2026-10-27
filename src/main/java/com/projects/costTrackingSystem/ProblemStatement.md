# Problem Statement

Link: https://enginebogie.com/public/question/build-a-delivery-cost-tracking-system/1619?srsltid=AYCX9vJDu8OI0vgJJBLeEk6tV1R7S9HJWJuZ0mp2XXDNcQpWtcZgZT1U


### 1619. Build a Delivery Cost Tracking System  
    You need to implement a delivery cost tracking system for a logistics platform. 
    The system tracks driver deliveries, costs, and analytics based on delivery time windows 
    The implementation will be done in three parts, each building on the previous one.


#### Part 1: Cost Calculation
##### Operations
    1. add_driver(driverId)
        - Registers a new driver to the system.
        - Each driver is uniquely identified by a string/integer driverId.
    2. add_delivery(driverId, startTime, endTime)
        - Adds a delivery for a driver with given start and end time.
Each delivery has a cost equal to the duration in minutes.
A driver can have multiple deliveries.
Times are integers in minutes (Unix time or relative timestamp).
get_total_cost()

Returns the total cost across all drivers and deliveries added so far.
Part 2: Payment Tracking
Extend your implementation to support partial payments.

Additional Operations
pay_up_to_time(upToTime)

Marks all deliveries that ended before or at upToTime as paid.
Paid deliveries are excluded from future cost calculations.
get_cost_to_be_paid()

Returns the total cost of unpaid deliveries only.
Part 3: Analytics
Add analytics support for monitoring delivery load.

Additional Operation
get_max_active_drivers_in_last_24_hours(currentTime)
Given a currentTime, return the maximum number of unique drivers that were active in any 24-hour window ending at currentTime.
A driver is considered active in the window if they had any delivery that overlaps the 24-hour period [currentTime - 1440, currentTime].
Constraints
All timestamps are integers in minutes.
Delivery times are well-formed: startTime <= endTime.
There can be up to 10⁵ drivers and 10⁶ deliveries.
All functions should aim for O(log n) or O(1) where possible.
Example
add_driver("D1");
add_driver("D2");

add_delivery("D1", 0, 10);  // +10 cost
add_delivery("D1", 20, 40); // +20 cost
add_delivery("D2", 30, 50); // +20 cost

get_total_cost();           // Output: 50

pay_up_to_time(45);         // Marks D1's 10-min and 20-min delivery as paid
get_cost_to_be_paid();      // Output: 20 (only D2's delivery is unpaid)

get_max_active_drivers_in_last_24_hours(50); // Output: 2 (D1 and D2 had deliveries in [26, 50])