# Robot Service

Owns all robot state: position, battery level, status, assigned order.
Registers with Eureka as `robot-service` and is reachable through the API Gateway.

## Prerequisites

Before running this, make sure these are already running:
1. `docker-compose up -d` (from the `warehouse-robotics` root folder) — MySQL must be up
2. `eureka-server` — running on port 8761
3. `api-gateway` — running on port 8080 (optional for direct testing, required for gateway routing)

## How to run (IntelliJ)

1. `File → New → Module from Existing Sources` (or just open `robot-service` folder
   as its own window) and let Maven download dependencies
2. Run `RobotServiceApplication`
3. Console should show: `Started RobotServiceApplication` and Tomcat on port `8081`
4. Check Eureka dashboard (http://localhost:8761) — `ROBOT-SERVICE` should now be listed

## Database

On first run, Hibernate auto-creates the `robots` table inside the `robot_db`
database (MySQL container on port 3307). No manual SQL needed — `ddl-auto: update`
handles it.

## Testing the API (Postman or curl)

Both of these work — direct-to-service, or through the gateway:
- Direct: `http://localhost:8081/api/robots`
- Via Gateway: `http://localhost:8080/api/robots`

### Create a robot
```
POST /api/robots
Content-Type: application/json

{
  "name": "Robot-01",
  "xPos": 0,
  "yPos": 0,
  "batteryLevel": 100
}
```

### Get all robots
```
GET /api/robots
```

### Get all IDLE robots
```
GET /api/robots?status=IDLE
```

### Get one robot
```
GET /api/robots/1
```

### Update position + battery (simulates the movement engine ticking)
```
PATCH /api/robots/1/position?xPos=5&yPos=3&batteryLevel=87
```

### Change status (e.g. force into CHARGING)
```
PATCH /api/robots/1/status?status=CHARGING
```

### Assign an order to a robot
```
PATCH /api/robots/1/assign-order?orderId=42
```

### Clear a robot's order (used during reassignment)
```
PATCH /api/robots/1/clear-order
```

### Delete a robot
```
DELETE /api/robots/1
```

## What's next (Phase 1 continued)

`order-service` — same layered structure (Entity/Repository/Service/Controller),
its own MySQL database (`order_db`, port 3308), registers with Eureka the same way.
