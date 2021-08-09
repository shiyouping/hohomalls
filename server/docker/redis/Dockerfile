FROM redis:6.2.5-alpine3.14
RUN mkdir /opt/redis
WORKDIR /opt/redis
COPY docker/redis/redis.conf ./redis.conf
VOLUME /hohomalls-redis
CMD redis-server ./redis.conf
