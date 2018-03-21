package mk.pjonceski.empleyeemanager.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
/**
 * Executors for the application.
 * The interaction with data is divided in different pools.
 */
public class AppExecutors {
    public static final int THREAD_COUNT = 10;
    private final Executor networkIO;

    private final Executor diskIO;

    public AppExecutors(Executor diskIO, Executor networkIO) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public static Executor createNetworkThreadExecutor() {
//        return new ThreadPoolExecutor(0, AppExecutors.THREAD_COUNT,
//                60L, TimeUnit.SECONDS,
//                new SynchronousQueue<Runnable>());
        return Executors.newCachedThreadPool();
    }
}
