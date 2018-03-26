package mk.pjonceski.empleyeemanager.utils.helpers;

import android.content.Intent;

import mk.pjonceski.empleyeemanager.App;
import mk.pjonceski.empleyeemanager.service.DownloadAvatarImagesService;

/**
 * This class handles operations with services starting,stopping.
 */
public class ServiceHelper {
    private App app;

    ServiceHelper(App app) {
        this.app = app;
    }

    /**
     * This methods starts {@link DownloadAvatarImagesService}
     */
    public void startDownloadImagesService() {
        Intent intentDownloadService = new Intent(app, DownloadAvatarImagesService.class);
        app.startService(intentDownloadService);
    }
}
