package trung.util.cache.redis;

public interface IRedisLock {
    boolean lock() throws InterruptedException;
    void release();
}
