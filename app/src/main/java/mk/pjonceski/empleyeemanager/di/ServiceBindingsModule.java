package mk.pjonceski.empleyeemanager.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import mk.pjonceski.empleyeemanager.service.DownloadImagesJob;

/**
 * Dagger module that helps dagger to know our services in compile time.
 * All services injectors should be defined in this class.
 */
@Module
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class ServiceBindingsModule {
    @ContributesAndroidInjector
    abstract DownloadImagesJob provideDownloadImageJobInject();
}
