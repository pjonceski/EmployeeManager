package mk.pjonceski.empleyeemanager;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.support.annotation.Nullable;

import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;
import mk.pjonceski.empleyeemanager.di.DaggerAppComponent;

/**
 * The main {@link Application class}.
 * Creates the Dagger graph of dependencies.
 */
@SuppressWarnings("unused")
public class App extends Application implements HasActivityInjector, HasServiceInjector {
    /**
     * The current active activity.
     */
    private Activity currentActivity;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Service> serviceDispatchingAndroidInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder().application(this).build().inject(this);
        Stetho.initializeWithDefaults(this);
    }

    @Nullable
    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public DispatchingAndroidInjector<Activity> getActivityDispatchingAndroidInjector() {
        return activityDispatchingAndroidInjector;
    }

    public void setActivityDispatchingAndroidInjector(DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector) {
        this.activityDispatchingAndroidInjector = activityDispatchingAndroidInjector;
    }
}
