package mk.pjonceski.empleyeemanager.di.data.source;


import dagger.Module;
import dagger.Provides;
import mk.pjonceski.empleyeemanager.data.source.remote.datasource.EmployeeRemoteDataSource;
import mk.pjonceski.empleyeemanager.data.source.remote.datasource.EmployeeRemoteDataSourceImpl;
import mk.pjonceski.empleyeemanager.data.source.remote.retrofit.RetrofitApi;


/**
 * Dagger module to provide instances for {@link mk.pjonceski.empleyeemanager.data.source.remote}
 */
@SuppressWarnings("unused")
@Module
public class RemoteDataSourceModule {
    @Provides
    static EmployeeRemoteDataSource provideEmployeeRemoteDataSource(RetrofitApi retrofitApi) {
        return new EmployeeRemoteDataSourceImpl(retrofitApi);
    }
}
