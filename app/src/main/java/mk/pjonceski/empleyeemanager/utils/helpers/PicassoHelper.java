package mk.pjonceski.empleyeemanager.utils.helpers;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import mk.pjonceski.empleyeemanager.utils.AppExecutors;

/**
 * This class provides helper method for using the picasso library.
 */

public final class PicassoHelper {

    private AppExecutors appExecutors;
    private FileHelper fileHelper;

    public PicassoHelper(AppExecutors appExecutors, FileHelper fileHelper) {
        this.appExecutors = appExecutors;
        this.fileHelper = fileHelper;
    }

    public interface ImageLoadingListener {
        void onSuccess(Bitmap bitmapImage);

        void onError(IOException ex);

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


  /*  public Target createPicassoImageTarget(final Context context,
                                           final String imageName) {
        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(FileHelper.AVATARS_CACHE_DIRECTORY_NAME, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                new Thread(new Runnable() {
                appExecutors.getDiskIO().execute(() -> {
                    Log.d("TAG", "Saving image.Name=" + imageName + " , imageDir=" + FileHelper.AVATARS_CACHE_DIRECTORY_NAME);
                    boolean success = true;
                    final File myImageFile = new File(directory, imageName); // Create image file
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(myImageFile);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.flush();
                    } catch (IOException e) {
                        success = false;
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (!Thread.interrupted()) {
                            final boolean successLocal = success;
                        }
                    }
                });
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
    }*/
}
