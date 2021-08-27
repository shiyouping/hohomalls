# HoHoMalls Server

Server applications are built on top of Spring WebFlux and GraphQL to provide a non-blocking and robust running
environment.

## Docker Commands

````bash
cd hohomalls/server

# Create and start containers
docker-compose -f docker/docker-compose.yml up -d

# Stop and remove resources
docker-compose -f docker/docker-compose.yml down
````
