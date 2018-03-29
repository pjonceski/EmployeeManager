package mk.pjonceski.empleyeemanager.service;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.firebase.jobdispatcher.JobParameters;

import java.io.IOException;

import javax.inject.Inject;

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
 * This is job that will download avatar images for employees in background.
 */
@SuppressWarnings("WeakerAccess")
public class DownloadImagesJob extends BaseJobService {
    public static final String JOB_UNIQUE_TAG = DownloadImagesThread.class.getSimpleName();

    private final static int MIN_FAILED_DOWNLOADS_TO_RESCHEDULE = 10;

    private final int MAX_NUMBER_RETRIES_TO_DOWNLOAD_IMAGE = 1;
    @Inject
    public EmployeeLocalDataSource employeeLocalDataSource;
    @Inject
    public Helpers helpers;
    private DownloadImagesThread downloadImagesThread;

    public DownloadImagesJob() {
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        downloadImagesThread = new DownloadImagesThread(employeeLocalDataSource, helpers, job);
        downloadImagesThread.start();
        return true;
    }

    /**
     * Returns true to be rescheduled if internet is not available.
     *
     * @param job the Job Parameters.
     * @return true if no internet is available,false otherwise.
     */
    @Override
    public boolean onStopJob(JobParameters job) {
        downloadImagesThread.interrupt();
        return !helpers.getSystemStateHelper().isInternetAvailable();
    }

    private class DownloadImagesThread extends Thread {
        private EmployeeLocalDataSource employeeLocalDataSource;
        private Helpers helpers;
        private JobParameters jobParameters;


        public DownloadImagesThread(EmployeeLocalDataSource employeeLocalDataSource,
                                    Helpers helpers,
                                    JobParameters jobParameters) {
            this.employeeLocalDataSource = employeeLocalDataSource;
            this.helpers = helpers;
            this.jobParameters = jobParameters;
        }

        @Override
        public void run() {
            super.run();
            clearImagesCacheAndUpdateStatus();
            int numberOfFailedDownloads = downloadAvatarImages();
            jobFinished(jobParameters, numberOfFailedDownloads >= MIN_FAILED_DOWNLOADS_TO_RESCHEDULE);
        }

        /**
         * Empties the directory for avatar images.
         */
        private void clearImagesCacheAndUpdateStatus() {
            helpers.getFileHelper().clearAvatarsImageCache();
            employeeLocalDataSource.updateAllEmployeesAvatarStatus(EmployeeEntityContract.AvatarStatus.UNSCHEDULED);
        }

        private int downloadAvatarImages() {
            int numberOfImagesDownloadedSuccessfully = 0;
            for (Employee employee : employeeLocalDataSource.getAllUnscheduledEmployeesForDownloadingAvatarImage()) {
                if (isInterrupted()) {
                    return numberOfImagesDownloadedSuccessfully;
                }
                try {
                    Optional<Employee> employeeFromDatabase = employeeLocalDataSource.getEmployeeByIdCallable(employee.getId()).call();
                    if (!employeeFromDatabase.isEmpty() && employeeFromDatabase.get().getAvatarStatus() == EmployeeEntityContract.AvatarStatus.UNSCHEDULED) {
                        if (!downloadImageWithRetry(employee)) {
                            numberOfImagesDownloadedSuccessfully++;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return numberOfImagesDownloadedSuccessfully;
        }

        /**
         * This method tries to download image wit max number of tries {@link #MAX_NUMBER_RETRIES_TO_DOWNLOAD_IMAGE}
         */
        private boolean downloadImageWithRetry(Employee employee) {
            employeeLocalDataSource.updateEmployeeAvatarStatus(employee.getId(), EmployeeEntityContract.AvatarStatus.SCHEDULED);
            int tries = MAX_NUMBER_RETRIES_TO_DOWNLOAD_IMAGE;
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
            return success;
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
                ex.printStackTrace();
            }
            if (response != null && response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    Bitmap avatarBitmap = BitmapFactory.decodeStream(responseBody.byteStream());
                    try {
                        helpers.getFileHelper().saveBitmapIntoAvatarImageCache(avatarBitmap, employee.getId());
                        return true;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            return false;
        }
    }
}
