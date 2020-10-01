package com.conquer_java.framework.redis;

/**
 * Redis一级缓存使用场景：
 * 1) 必须SQL语句(包括参数)相同；
 * 2) 必须会话相同；
 * 3) 必须方法相同；
 * 4) 必须命名空间namespace相同；
 * 5) 不能在执行SELECT查询操作前执行ClearCache操作；
 * 6) 不能执行INSERT | UPDATE | DELETE操作；
 */
public class DemoRedisL1Cache {
}
