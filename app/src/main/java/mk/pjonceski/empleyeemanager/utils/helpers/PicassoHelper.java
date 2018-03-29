package mk.pjonceski.empleyeemanager.utils.helpers;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import mk.pjonceski.empleyeemanager.data.source.local.datasource.EmployeeLocalDataSource;
import mk.pjonceski.empleyeemanager.data.source.local.entities.EmployeeEntityContract;
import mk.pjonceski.empleyeemanager.utils.AppExecutors;

/**
 * This class provides helper methods for using the picasso library.
 */

public final class PicassoHelper {

    private AppExecutors appExecutors;
    private FileHelper fileHelper;
    private EmployeeLocalDataSource employeeLocalDataSource;

    public PicassoHelper(AppExecutors appExecutors, FileHelper fileHelper, EmployeeLocalDataSource employeeLocalDataSource) {
        this.appExecutors = appExecutors;
        this.fileHelper = fileHelper;
        this.employeeLocalDataSource = employeeLocalDataSource;
    }

    public interface ImageLoadingListener {
        /**
         * Successfully downloaded image.
         *
         * @param bitmapImage the bitmap of the image.
         */
        void onSuccess(Bitmap bitmapImage);

        /**
         * If exception is thrown while saving image to local storage.
         *
         * @param ex the exception.
         */
        void onError(IOException ex);

        /**
         * If bitmap cannot be loaded.
         *
         * @param ex            the exception.
         * @param errorDrawable the bitmap that needed to be shown in case of error loading.
         */
        void onBitmapFailed(Exception ex, Drawable errorDrawable);
    }

    /**
     * Creates {@link com.squareup.picasso.Target} that is used for downloading images.
     * Inside the target the received bitmap is persisted to internal storage.
     *
     * @param employeeID           the id of the employee.
     * @param fromFile             true if the loading is from file, false otherwise.
     * @param imageLoadingListener the listener that provides callbacks statuses.
     * @return the target.
     */
    public Target createPicassoImageTargetForEmployee(final int employeeID,
                                                      boolean fromFile,
                                                      final ImageLoadingListener imageLoadingListener) {
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                if (!fromFile) {
                    appExecutors.getDiskIO().execute(() -> {
                        try {
                            fileHelper.saveBitmapIntoAvatarImageCache(bitmap, employeeID);
                        } catch (IOException e) {
                            appExecutors.getMainExecutor().execute(() -> imageLoadingListener.onError(e));
                        }
                    });
                }
                if (imageLoadingListener != null) {
                    imageLoadingListener.onSuccess(bitmap);
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                employeeLocalDataSource.updateEmployeeAvatarStatus(employeeID, EmployeeEntityContract.AvatarStatus.UNSCHEDULED);
                if (imageLoadingListener != null) {
                    imageLoadingListener.onBitmapFailed(e, errorDrawable);
                }
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                employeeLocalDataSource.updateEmployeeAvatarStatus(employeeID, EmployeeEntityContract.AvatarStatus.SCHEDULED);
            }
        };
    }
}
