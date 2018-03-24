package mk.pjonceski.empleyeemanager.di;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mk.pjonceski.empleyeemanager.App;
import mk.pjonceski.empleyeemanager.data.AppCredentials;
import mk.pjonceski.empleyeemanager.data.source.local.AppDBHelper;
import mk.pjonceski.empleyeemanager.data.source.remote.retrofit.RetrofitApi;
import mk.pjonceski.empleyeemanager.data.source.remote.retrofit.RetrofitApiClient;
import mk.pjonceski.empleyeemanager.navigation.Router;
import mk.pjonceski.empleyeemanager.navigation.RouterImpl;
import mk.pjonceski.empleyeemanager.utils.AppExecutors;
import mk.pjonceski.empleyeemanager.utils.helpers.Helpers;

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

    @Singleton
    @Provides
    static AppDBHelper provideAppDBHelper(App app) {
        return new AppDBHelper(app);
    }

    @Singleton
    @Provides
    static AppExecutors provideAppExecutors() {
        return new AppExecutors(Executors.newCachedThreadPool(),
                new AppExecutors.MainThreadExecutor());
    }

    @Singleton
    @Provides
    static RetrofitApi provideApiClient(App app) {
        AppCredentials appCredentials = new AppCredentials("http://hiring.rewardgateway.net/",
                "medium",
                "medium");
        return RetrofitApiClient.createClient(appCredentials.getBaseUrl(),
                appCredentials.getBaseAuthUsername(),
                appCredentials.getBaseAuthPassword());
    }

    @Singleton
    @Provides
    static Helpers provideHelpers(App app, AppExecutors appExecutors) {
        return new Helpers(app, appExecutors);
    }
}
