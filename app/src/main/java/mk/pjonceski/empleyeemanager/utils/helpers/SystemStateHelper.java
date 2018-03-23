package mk.pjonceski.empleyeemanager.utils.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import mk.pjonceski.empleyeemanager.App;

/**
 * Provides methods to get the state of the android system.
 * It get data from services such as {@link ConnectivityManager}
 */

@SuppressWarnings("WeakerAccess")
public class SystemStateHelper {
    private App app;

    SystemStateHelper(App app) {
        this.app = app;
    }

    /**
     * This method check if internet is available on the device.
     *
     * @return true if available, false otherwise.
     */
    public boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }
        return false;
    }
}
