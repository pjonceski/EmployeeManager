package mk.pjonceski.empleyeemanager;

import android.app.Activity;
import android.app.Application;

import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import mk.pjonceski.empleyeemanager.di.DaggerAppComponent;

/**
 * The main {@link Application class}.
 * Creates the Dagger graph of dependencies.
 */
@SuppressWarnings("unused")
public class App extends Application implements HasActivityInjector {
    private Activity currentActivity;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder().application(this).build().inject(this);
        Stetho.initializeWithDefaults(this);
    }

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
