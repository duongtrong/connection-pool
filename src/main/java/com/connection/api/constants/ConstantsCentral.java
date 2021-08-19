package com.connection.api.constants;

public enum ConstantsCentral {
  APPLICATION("application.properties"),
  RABBITMQ_PORT("rabbitmq.port"),
  RABBITMQ_QUEUE("rabbitmq.queue"),
  RABBITMQ_USERNAME("rabbitmq.username"),
  RABBITMQ_PASSWORD("rabbitmq.password"),
  RABBITMQ_HOSTNAME("rabbitmq.hostname"),
  RABBITMQ_MAX_IDLE("rabbitmq.maxIdle"),
  RABBITMQ_MAX_TOTAL("rabbitmq.maxTotal"),
  RABBITMQ_MIN_IDLE("rabbitmq.minIdle"),
  RABBITMQ_EXCHANGE("rabbitmq.exchange"),

  REDIS_KEY("redis.key"),
  REDIS_PORT("redis.port"),
  REDIS_HOSTNAME("redis.hostname"),
  REDIS_MAX_IDLE("redis.maxIdle"),
  REDIS_MAX_TOTAL("redis.maxTotal"),
  REDIS_MIN_IDLE("redis.minIdle"),
  REDIS_MIN_EVICTABLE("redis.minEvictableIdle"),
  REDIS_EXPIRE("redis.expireAt"),
  REDIS_TIME_BETWEEN_EVICTION("redis.timeBetweenEviction"),
  REDIS_MAX_WAIT("redis.maxWaitMillis"),
  REDIS_BLOCK_WHEN("redis.blockWhenExhausted"),
  REDIS_TEST_EVICTION("redis.numTestsPerEvictionRun"),
  REDIS_ON_BORROW("redis.testOnBorrow"),
  REDIS_ON_RETURN("redis.testOnReturn"),
  REDIS_WHILE_IDLE("redis.testWhileIdle"),
  REDIS_TIMEOUT("redis.timeOut"),

  ORACLE_SID("oracle.sid"),
  ORACLE_HOSTNAME("oracle.host"),
  ORACLE_PORT("oracle.port"),
  ORACLE_USERNAME("oracle.username"),
  ORACLE_PASSWORD("oracle.password"),
  ORACLE_MAX_POOL("oracle.maxPool"),
  ORACLE_MIN_IDLE("oracle.minimumIdle"),
  ORACLE_DRIVER("oracle.driver"),
  ORACLE_URL("oracle.url"),
  ORACLE_POOL_NAME("oracle.name"),
  ORACLE_MAX_LIFETIME("oracle.maxLifeTime"),
  ORACLE_CONNECTION_TIMEOUT("oracle.connectionTimeout"),
  ORACLE_IDLE_TIMEOUT("oracle.idleTimeout"),
  ORACLE_AUTO_COMMIT("oracle.autocommit"),
  ORACLE_PRE_STATEMENT("oracle.preparedCache", "cachePrepStmts"),
  ORACLE_CACHE_SIZE("oracle.cacheSize", "prepStmtCacheSize"),
  ORACLE_CACHE_LIMIT("oracle.cacheLimit", "prepStmtCacheSqlLimit"),
  ;

  private final String value;
  private String nameKey;

  ConstantsCentral(String value, String nameKey) {
    this.value = value;
    this.nameKey = nameKey;
  }

  ConstantsCentral(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public String getNameKey() {
    return nameKey;
  }
}
