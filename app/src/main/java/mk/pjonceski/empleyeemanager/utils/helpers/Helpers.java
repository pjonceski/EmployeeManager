package mk.pjonceski.empleyeemanager.utils.helpers;

import android.content.ContextWrapper;

import mk.pjonceski.empleyeemanager.App;
import mk.pjonceski.empleyeemanager.utils.AppExecutors;

/**
 * This class provides instances of all helper classes.
 */
public class Helpers {
    private final FileHelper fileHelper;
    private final PicassoHelper picassoHelper;
    private final SystemStateHelper systemStateHelper;
    private final SharedPrefHelper sharedPrefHelper;

    public Helpers(App app, AppExecutors appExecutors) {
        this.fileHelper = new FileHelper(new ContextWrapper(app));
        this.picassoHelper = new PicassoHelper(appExecutors, fileHelper);
        this.systemStateHelper = new SystemStateHelper(app);
        this.sharedPrefHelper = new SharedPrefHelper(app);
    }

    public FileHelper getFileHelper() {
        return fileHelper;
    }

    public PicassoHelper getPicassoHelper() {
        return picassoHelper;
    }

    public SystemStateHelper getSystemStateHelper() {
        return systemStateHelper;
    }

    public SharedPrefHelper getSharedPrefHelper() {
        return sharedPrefHelper;
    }
}
