FROM redis:6.2.5-alpine3.14
COPY docker/redis/redis.conf /usr/local/etc/redis/redis.conf
CMD [ "redis-server", "/usr/local/etc/redis/redis.conf" ]
VOLUME ["/hohomalls-redis"]