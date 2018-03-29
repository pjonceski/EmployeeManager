package mk.pjonceski.empleyeemanager.utils.helpers;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import mk.pjonceski.empleyeemanager.App;
import mk.pjonceski.empleyeemanager.data.source.remote.retrofit.RetrofitApiClient;
import mk.pjonceski.empleyeemanager.service.DownloadImagesJob;

/**
 * This class handles operations with services starting,stopping.
 */
public class ServiceHelper {
    private FirebaseJobDispatcher firebaseJobDispatcher;

    public ServiceHelper(App app) {
        firebaseJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(app));
    }

    /**
     * Starts {@link DownloadImagesJob} to download images in background.
     */
    public void startDownloadImagesJob() {
        cancelDownloadImagesJob();
        firebaseJobDispatcher.mustSchedule(createDownloadingImagesJob());
    }

    /**
     * Cancels job with tag {@link DownloadImagesJob#JOB_UNIQUE_TAG}.
     */
    private void cancelDownloadImagesJob() {
        firebaseJobDispatcher.cancel(DownloadImagesJob.JOB_UNIQUE_TAG);
    }

    /**
     * This method creates {@link Job} for downloading images.
     * The service runs only when network is available.
     * The job is scheduled after it is canceled, after time the image that is downloaded in the worst case.
     * The longest time to download image is {@link RetrofitApiClient#CONNECT_TIMEOUT_SECONDS}+{@link RetrofitApiClient#READ_TIMEOUT_SECONDS}
     *
     * @return the created job for downloading images.
     */
    private Job createDownloadingImagesJob() {
        int windowStart = RetrofitApiClient.CONNECT_TIMEOUT_SECONDS + RetrofitApiClient.READ_TIMEOUT_SECONDS;
        int windowEnd = windowStart + RetrofitApiClient.CONNECT_TIMEOUT_SECONDS;

        return firebaseJobDispatcher.newJobBuilder()
                .setService(DownloadImagesJob.class)
                .setTag(DownloadImagesJob.JOB_UNIQUE_TAG)
                .setTrigger(Trigger.executionWindow(windowStart, windowEnd))
                .setRecurring(false)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setReplaceCurrent(false)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build();
    }
}
