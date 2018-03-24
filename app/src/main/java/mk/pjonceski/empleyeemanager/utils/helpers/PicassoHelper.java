package mk.pjonceski.empleyeemanager.utils.helpers;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import mk.pjonceski.empleyeemanager.utils.AppExecutors;

/**
 * This class provides helper methods for using the picasso library.
 */

public final class PicassoHelper {

    private AppExecutors appExecutors;
    private FileHelper fileHelper;

    PicassoHelper(AppExecutors appExecutors, FileHelper fileHelper) {
        this.appExecutors = appExecutors;
        this.fileHelper = fileHelper;
    }

    public interface ImageLoadingListener {
        /**
         * Successfully downloaded image.
         *
         * @param bitmapImage the bitmap of the image.
         */
        void onSuccess(Bitmap bitmapImage);

        /**
         * If exception is thrown.
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

    public Target createPicassoImageTarget(final String imageName,
                                           final ImageLoadingListener imageLoadingListener) {
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                appExecutors.getDiskIO().execute(() -> {
                    boolean success = true;
                    try {
                        fileHelper.saveImageIntoAvatarImageCache(bitmap, imageName);
                    } catch (IOException e) {

                        if (!Thread.interrupted()) {
                            if (imageLoadingListener != null) {
                                imageLoadingListener.onError(e);
                            }
                        }
                        success = false;
                    } finally {
                        if (!Thread.interrupted()) {
                            final boolean successLocal = success;
                            appExecutors.getMainExecutor().execute(() -> {
                                if (successLocal && imageLoadingListener != null) {
                                    imageLoadingListener.onSuccess(bitmap);
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                if (imageLoadingListener != null) {
                    imageLoadingListener.onBitmapFailed(e, errorDrawable);
                }
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
    }
}
