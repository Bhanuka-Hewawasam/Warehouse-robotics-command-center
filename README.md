# Autonomous Warehouse Robotics Command Center

Real-time microservices dashboard for monitoring and managing warehouse AGVs (Automated Guided Vehicles).

## Architecture (Phase 0 — this drop)

```
React Client (later) --> API Gateway (8080) --> Eureka (8761) tracks all services
                                              --> Robot Service   (built in Phase 1)
                                              --> Order Service   (built in Phase 1)
                                              --> Notification Svc(built in Phase 3)
                          RabbitMQ (5672 / mgmt UI 15672)
                          MySQL x2 (one DB per service — 3307, 3308)
```

## What's in this drop

- `eureka-server/` — Service registry. Every microservice registers here so they
  can find each other by name instead of hardcoded `localhost:port` URLs.
- `api-gateway/` — Single entry point. React will only ever talk to `localhost:8080`.
  Routes already configured for `/api/robots/**`, `/api/orders/**`, `/ws/**`.
- `docker-compose.yml` — MySQL (x2, one per service — true microservice DB isolation)
  + RabbitMQ with the management UI enabled.

## How to run this (IntelliJ)

1. **Start infrastructure first:**
   ```bash
   docker-compose up -d
   ```
   Check RabbitMQ came up: http://localhost:15672 (login: guest / guest)

2. **Open `eureka-server` in IntelliJ as a Maven project**, let it download
   dependencies, then run `EurekaServerApplication`.
   Confirm it's alive: http://localhost:8761 — you should see the Eureka dashboard
   with "No instances available" (expected, nothing registered yet).

3. **Open `api-gateway` in IntelliJ** (separate module/window), run
   `ApiGatewayApplication`. Check the Eureka dashboard again — `API-GATEWAY`
   should now appear as a registered instance.

   > If it doesn't show up within ~30 seconds, check the gateway's console log
   > for `DiscoveryClient_API-GATEWAY - registration status: 204` — that
   > confirms successful registration.

## Why this structure (for your viva / interview prep)

- **Eureka Discovery Server**: instead of hardcoding `http://localhost:8081` for
  robot-service everywhere, other services just say `lb://robot-service`. If you
  scale robot-service to 3 instances later, Eureka + the gateway load-balance
  automatically — no code changes.
- **API Gateway**: single point for CORS, auth (JWT later), and routing. Frontend
  never needs to know how many backend services exist or where they run.
- **DB-per-service**: `robot-service` and `order-service` each own their database.
  Neither service is allowed to query the other's tables directly — they only
  communicate via REST (sync) or RabbitMQ events (async). This is what makes it
  "true" microservices instead of a distributed monolith.

## Next up — Phase 1

`robot-service` and `order-service`: Entity → Repository → Service → Controller,
registered with Eureka, connected to their own MySQL database, tested via Postman.
