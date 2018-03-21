package mk.pjonceski.empleyeemanager.di.data.source;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource;
import mk.pjonceski.empleyeemanager.data.source.local.datasource.EmployeeLocalDataSourceImpl;
import mk.pjonceski.empleyeemanager.data.source.remote.datasource.EmployeeRemoteDataSourceImpl;
import mk.pjonceski.empleyeemanager.data.source.remote.retrofit.RetrofitApi;


/**
 * Dagger module to provide instances for {@link mk.pjonceski.empleyeemanager.data.source.remote}
 */
@SuppressWarnings("unused")
@Module
public class RemoteDataSourceModule {
    @Provides
    @Named(EmployeeRemoteDataSourceImpl.INJECTION_NAME)
    static EmployeeDataSource provideEmployeeRemoteDataSource(RetrofitApi retrofitApi) {
        return new EmployeeRemoteDataSourceImpl(retrofitApi);
    }
}
