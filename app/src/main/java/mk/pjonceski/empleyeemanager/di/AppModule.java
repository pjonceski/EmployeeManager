package mk.pjonceski.empleyeemanager.di;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mk.pjonceski.empleyeemanager.App;
import mk.pjonceski.empleyeemanager.data.AppCredentials;
import mk.pjonceski.empleyeemanager.data.net.retrofit.RetrofitApi;
import mk.pjonceski.empleyeemanager.data.net.retrofit.RetrofitApiClient;
import mk.pjonceski.empleyeemanager.navigation.Router;
import mk.pjonceski.empleyeemanager.navigation.RouterImpl;
import mk.pjonceski.empleyeemanager.utils.AppExecutors;

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
    static AppExecutors provideAppExecutors() {
        return new AppExecutors(Executors.newCachedThreadPool());
    }

    @Singleton
    @Provides
    static RetrofitApi provideApiClient(App app) {
        AppCredentials appCredentials=new AppCredentials("http://hiring.rewardgateway.net/",
                "medium",
                "medium");
        return RetrofitApiClient.createClient(appCredentials.getBaseUrl(),
                appCredentials.getBaseAuthUsername(),
                appCredentials.getBaseAuthPassword());
    }

}
