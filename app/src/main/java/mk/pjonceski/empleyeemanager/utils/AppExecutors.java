package mk.pjonceski.empleyeemanager.utils;

import java.util.concurrent.Executor;

/**
 * Executors for the application.
 * The interaction with data is divided in different pools.
 */

public class AppExecutors {
    private Executor diskIO;

    public AppExecutors(Executor diskIO) {
        this.diskIO = diskIO;
    }

    public Executor getDiskIO() {
        return diskIO;
    }
}
