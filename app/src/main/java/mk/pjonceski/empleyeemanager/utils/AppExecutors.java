package mk.pjonceski.empleyeemanager.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

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
    private final Executor mainExecutor;


    public AppExecutors(Executor networkIO, Executor diskIO, Executor mainExecutor) {
        this.networkIO = networkIO;
        this.diskIO = diskIO;
        this.mainExecutor = mainExecutor;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public Executor getMainExecutor() {
        return mainExecutor;
    }

    public static Executor createNetworkThreadExecutor() {
//        return new ThreadPoolExecutor(0, AppExecutors.THREAD_COUNT,
//                60L, TimeUnit.SECONDS,
//                new SynchronousQueue<Runnable>());
        return Executors.newCachedThreadPool();
    }

    /**
     * Executor that runs tasks on the main Main Thread.
     */
    public static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
