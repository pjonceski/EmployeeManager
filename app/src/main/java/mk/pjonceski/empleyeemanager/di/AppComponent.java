package mk.pjonceski.empleyeemanager.di;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import mk.pjonceski.empleyeemanager.App;
import mk.pjonceski.empleyeemanager.di.data.RepositoriesModule;
import mk.pjonceski.empleyeemanager.di.data.local.LocalDataSourceModule;

/**
 * The main AppComponent class for Dagger.
 * This is the only component.
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class,
        AppModule.class,
        ActivityBindingsModule.class,
        RepositoriesModule.class,
        LocalDataSourceModule.class
})
public interface AppComponent extends AndroidInjector<App> {
    @Override
    void inject(App instance);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App app);

        AppComponent build();
    }
}
