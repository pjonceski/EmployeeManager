package mk.pjonceski.empleyeemanager.di.data.local;

import dagger.Module;
import dagger.Provides;
import mk.pjonceski.empleyeemanager.data.source.local.AppDBHelper;
import mk.pjonceski.empleyeemanager.data.source.local.datasource.EmployeeLocalDataSource;
import mk.pjonceski.empleyeemanager.data.source.local.datasource.EmployeeLocalDataSourceImpl;
import mk.pjonceski.empleyeemanager.utils.AppExecutors;

/**
 * Dagger module to provide instances for {@link mk.pjonceski.empleyeemanager.data.source.local}
 */
@SuppressWarnings("unused")
@Module
public abstract class LocalDataSourceModule {
    @Provides
    static EmployeeLocalDataSource provideEmployeeLocalDataSource(AppDBHelper appDBHelper, AppExecutors appExecutors) {
        return new EmployeeLocalDataSourceImpl(appDBHelper, appExecutors);
    }
}