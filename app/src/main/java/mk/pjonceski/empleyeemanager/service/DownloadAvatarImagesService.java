package mk.pjonceski.empleyeemanager.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasServiceInjector;
import mk.pjonceski.empleyeemanager.R;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.models.Optional;
import mk.pjonceski.empleyeemanager.data.source.local.datasource.EmployeeLocalDataSource;
import mk.pjonceski.empleyeemanager.data.source.local.entities.EmployeeEntityContract;
import mk.pjonceski.empleyeemanager.utils.helpers.Helpers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * This services is used to download avatar for employees in background.
 */

public class DownloadAvatarImagesService extends IntentService implements HasServiceInjector {
    private static volatile boolean shouldRunning = true;
    private final int DOWNLOAD_IMAGES_NOTIFICATION_ID = 101;
    @Inject
    DispatchingAndroidInjector<Service> serviceDispatchingAndroidInjector;

    @Inject
    Helpers helpers;

    @Inject
    EmployeeLocalDataSource employeeLocalDataSource;

    private final int maxNumberOfTriesToDownloadImage = 1;

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceDispatchingAndroidInjector;
    }

    public DownloadAvatarImagesService() {
        super(DownloadAvatarImagesService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidInjection.inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        shouldRunning = true;
        startForeground(DOWNLOAD_IMAGES_NOTIFICATION_ID, getForegroundNotification());
        clearImagesCacheAndUpdateStatus();
        downloadAvatarImages();
    }

    /**
     * This tells the service that it should stop running current operations.
     */
    public synchronized static void interrupt() {
        DownloadAvatarImagesService.shouldRunning = false;
    }

    /**
     * Empties the directory for avatar images.
     */
    private void clearImagesCacheAndUpdateStatus() {
        helpers.getFileHelper().clearAvatarsImageCache();
        employeeLocalDataSource.updateAllEmployeesAvatarStatus(EmployeeEntityContract.AvatarStatus.UNSCHEDULED);
    }

    private void downloadAvatarImages() {
        //TODO iterate with while while unscheduled employee exists.
        for (Employee employee : employeeLocalDataSource.getAllUnscheduledEmployeesForDownloadingAvatarImage()) {
            if (!shouldRunning) {
                shouldRunning = true;
                return;
            }
            try {
                Optional<Employee> employeeFromDatabase = employeeLocalDataSource.getEmployeeByIdCallable(employee.getId()).call();
                if (!employeeFromDatabase.isEmpty() && employeeFromDatabase.get().getAvatarStatus() == EmployeeEntityContract.AvatarStatus.UNSCHEDULED) {
                    downloadImageWithRetry(employee);
                }
            } catch (Exception e) {
            }

        }
    }

    /**
     * This method tries to download image wit max number of tries {@link #maxNumberOfTriesToDownloadImage}
     */
    private void downloadImageWithRetry(Employee employee) {
        employeeLocalDataSource.updateEmployeeAvatarStatus(employee.getId(), EmployeeEntityContract.AvatarStatus.SCHEDULED);
        int tries = maxNumberOfTriesToDownloadImage;
        boolean success = false;
        while (tries > 0) {
            if (downloadImage(employee)) {
                success = true;
                break;
            }
            tries--;
        }
        if (!success) {
            employeeLocalDataSource.updateEmployeeAvatarStatus(employee.getId(), EmployeeEntityContract.AvatarStatus.UNSCHEDULED);
        }
    }

    private boolean downloadImage(Employee employee) {
        final OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(employee.getAvatar())
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException ex) {
        }
        if (response != null && response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                Bitmap avatarBitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                try {
                    helpers.getFileHelper().saveImageIntoAvatarImageCache(avatarBitmap, String.valueOf(employee.getId()));
                    return true;
                } catch (IOException ex) {
                }
            }
        }
        return false;
    }

    private Notification getForegroundNotification() {
        return new NotificationCompat.Builder(this, String.valueOf(DOWNLOAD_IMAGES_NOTIFICATION_ID))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.downloading_images))
                .setContentText(getString(R.string.downloading_images))
                .setProgress(0, 0, true)
                .build();
    }
}
