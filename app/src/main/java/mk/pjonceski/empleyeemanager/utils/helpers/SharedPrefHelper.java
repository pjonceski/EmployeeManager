package mk.pjonceski.empleyeemanager.utils.helpers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import mk.pjonceski.empleyeemanager.App;

/**
 * This class contains methods for storing and retriving data from SharedPreferences .
 */
public class SharedPrefHelper {
    /**
     * Used to return status if the data retrieved is local or from remote sources.
     */
    private static final class DataSourceType {
        static final int LOCAL = 0;
        static final int REMOTE = 1;
    }

    /**
     * Key for storing the data source type.
     */
    private final static String DATA_SOURCE_KEY = "data_source_type_key";
    private SharedPreferences sharedPreferences;

    SharedPrefHelper(App app) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(app);
    }

    /**
     * Writes to shared preferences for key {@link DataSourceType} value {@link DataSourceType#LOCAL}.
     * Write the data instantly(blocking).
     */
    public void setDataSourceIsFromLocalStorage() {
        writeToSharedPreferences(DATA_SOURCE_KEY, DataSourceType.LOCAL);
    }

    /**
     * Writes to shared preferences for key {@link DataSourceType} value {@link DataSourceType#REMOTE}.
     * Write the data instantly(blocking).
     */
    public void setDataSourceIsFromRemoteStorage() {
        writeToSharedPreferences(DATA_SOURCE_KEY, DataSourceType.REMOTE);
    }

    /**
     * Returns if data retrieved from repositories is from local storage(no network).
     *
     * @return true if local, false if from network
     */
    public boolean isDataOffline() {
        return sharedPreferences.getInt(DATA_SOURCE_KEY, DataSourceType.REMOTE) == DataSourceType.LOCAL;
    }

    /**
     * Writes to shared preferences instantly(blocking).
     *
     * @param key   the key for the value.
     * @param value the value to be writen.
     */
    private void writeToSharedPreferences(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }


}
