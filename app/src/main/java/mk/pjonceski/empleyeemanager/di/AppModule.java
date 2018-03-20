package mk.pjonceski.empleyeemanager.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mk.pjonceski.empleyeemanager.App;
import mk.pjonceski.empleyeemanager.navigation.Router;
import mk.pjonceski.empleyeemanager.navigation.RouterImpl;

/**
 * Dagger module to provide instances for global application variables .
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@Module
public abstract class AppModule {
    @Singleton
    @Provides
    static Router provideRouter(App app) {
        return new RouterImpl(app);
    }

}
