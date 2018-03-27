package mk.pjonceski.empleyeemanager.utils.helpers;

import android.content.Intent;
import android.os.Handler;


import java.sql.Time;
import java.util.concurrent.TimeUnit;

import mk.pjonceski.empleyeemanager.App;
import mk.pjonceski.empleyeemanager.data.source.remote.retrofit.RetrofitApiClient;
import mk.pjonceski.empleyeemanager.service.DownloadAvatarImagesService;

import static mk.pjonceski.empleyeemanager.data.source.remote.retrofit.RetrofitApiClient.CONNECT_TIMEOUT_SECONDS;

/**
 * This class handles operations with services starting,stopping.
 */
public class ServiceHelper {
    private App app;
    private Handler serviceScheduleHandler;

    ServiceHelper(App app) {
        this.app = app;
        serviceScheduleHandler = new Handler();
    }

    /**
     * This methods starts {@link DownloadAvatarImagesService}.
     * The starting of the service is delayed to allow the service to be canceled
     * even if it is in the middle of slow downloading image download.
     * The max time that image can be downloaded is
     * {@link RetrofitApiClient#CONNECT_TIMEOUT_SECONDS} + {@link RetrofitApiClient#READ_TIMEOUT_SECONDS}
     */
    public void startDownloadImagesService() {
        DownloadAvatarImagesService.interrupt();
        serviceScheduleHandler.removeCallbacks(startDownloadServiceRunnable);
        serviceScheduleHandler.postDelayed(startDownloadServiceRunnable,
                TimeUnit.SECONDS.toMillis(CONNECT_TIMEOUT_SECONDS)
                        + TimeUnit.SECONDS.toMillis(RetrofitApiClient.READ_TIMEOUT_SECONDS));
    }

    private final Runnable startDownloadServiceRunnable = () -> {
        Intent intentDownloadService = new Intent(app, DownloadAvatarImagesService.class);
        app.startService(intentDownloadService);
    };
}
