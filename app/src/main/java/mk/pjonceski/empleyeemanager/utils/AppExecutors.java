package mk.pjonceski.empleyeemanager.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Executors for the application.
 * The interaction with data is divided in different pools.
 */
public class AppExecutors {
    /**
     * This executor is used for writing to local storage(sqlLite or sharedPreferences).
     */
    private final Executor diskIO;
    /**
     * This executor is used to execute on main thread.
     */
    private final Executor mainExecutor;

    public AppExecutors(Executor diskIO, Executor mainExecutor) {
        this.diskIO = diskIO;
        this.mainExecutor = mainExecutor;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getMainExecutor() {
        return mainExecutor;
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
