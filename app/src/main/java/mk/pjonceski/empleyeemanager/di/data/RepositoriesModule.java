package mk.pjonceski.empleyeemanager.di.data;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource;
import mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSourceImpl;
import mk.pjonceski.empleyeemanager.data.source.local.datasource.EmployeeLocalDataSource;
import mk.pjonceski.empleyeemanager.data.source.remote.datasource.EmployeeRemoteDataSourceImpl;

/**
 * Dagger Module to provide instances for {@link mk.pjonceski.empleyeemanager.data.repositories}
 */
@SuppressWarnings("unused")
@Module
public abstract class RepositoriesModule {
    @Singleton
    @Provides
    static EmployeeDataSource provideEmployeeDataSource(
            EmployeeLocalDataSource employeeLocalDataSource,
            @Named(EmployeeRemoteDataSourceImpl.INJECTION_NAME) EmployeeDataSource employeeRemoteDataSource) {
        return new EmployeeDataSourceImpl(employeeLocalDataSource, employeeRemoteDataSource);
    }
}
