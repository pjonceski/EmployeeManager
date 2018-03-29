package mk.pjonceski.empleyeemanager.utils.helpers;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import mk.pjonceski.empleyeemanager.App;

/**
 * Handles writing and retrieving files from internal storage.
 */
@SuppressWarnings("WeakerAccess")
public final class FileHelper {
    /**
     * The directory with purpose as local cache for avatar images.
     */
    private final static String AVATARS_CACHE_DIRECTORY_NAME = "avatars_directory";
    /**
     * The avatar image extension.
     */
    private final static String AVATAR_IMAGE_EXTENSION = ".png";

    private ContextWrapper contextWrapper;

    public FileHelper(App app) {
        this.contextWrapper = new ContextWrapper(app);
    }

    /**
     * Deletes files inside the directory {@link #AVATARS_CACHE_DIRECTORY_NAME}.
     */
    public void clearAvatarsImageCache() {
        File dir = contextWrapper.getDir(AVATARS_CACHE_DIRECTORY_NAME, Context.MODE_PRIVATE);
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                file.delete();
            }
        }
    }

    /**
     * This method returns the file for the image with provided name.
     * The images are from directory {@link #AVATARS_CACHE_DIRECTORY_NAME}.
     *
     * @param employeeId the id of the employee.
     */
    @Nullable
    public File getImageFromAvatarsImageCache(int employeeId) {
        File directory = contextWrapper.getDir(AVATARS_CACHE_DIRECTORY_NAME, Context.MODE_PRIVATE);
        File myImageFile = new File(directory, String.valueOf(employeeId) + AVATAR_IMAGE_EXTENSION);
        return myImageFile.exists() ? myImageFile : null;
    }

    /**
     * This method saves image into local storage.
     * The directory with name {@link #AVATARS_CACHE_DIRECTORY_NAME} serves as cache for
     * avatar images for employees.
     *
     * @param imageBitmap the bitmap to be saved.
     * @param id          the image will be saved with this employee id.
     * @throws IOException the exception that happened during persisting file.
     */
    public void saveBitmapIntoAvatarImageCache(Bitmap imageBitmap, int id) throws IOException {
        final File directory = contextWrapper.getDir(AVATARS_CACHE_DIRECTORY_NAME, Context.MODE_PRIVATE);
        final File myImageFile = new File(directory, String.valueOf(id) + AVATAR_IMAGE_EXTENSION); // Create image file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myImageFile);
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
