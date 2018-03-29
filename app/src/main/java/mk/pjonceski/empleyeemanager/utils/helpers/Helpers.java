package mk.pjonceski.empleyeemanager.utils.helpers;
/**
 * This class provides instances of all helper classes.
 */
public class Helpers {
    private final FileHelper fileHelper;
    private final PicassoHelper picassoHelper;
    private final SystemStateHelper systemStateHelper;
    private final SharedPrefHelper sharedPrefHelper;
    private final ServiceHelper serviceHelper;

    public Helpers(FileHelper fileHelper,
                   PicassoHelper picassoHelper,
                   SystemStateHelper systemStateHelper,
                   SharedPrefHelper sharedPrefHelper,
                   ServiceHelper serviceHelper) {
        this.fileHelper = fileHelper;
        this.picassoHelper = picassoHelper;
        this.systemStateHelper = systemStateHelper;
        this.sharedPrefHelper = sharedPrefHelper;
        this.serviceHelper = serviceHelper;
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

    public ServiceHelper getServiceHelper() {
        return serviceHelper;
    }
}
