package mk.pjonceski.empleyeemanager.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mk.pjonceski.empleyeemanager.App;
import mk.pjonceski.empleyeemanager.data.source.local.datasource.EmployeeLocalDataSource;
import mk.pjonceski.empleyeemanager.utils.AppExecutors;
import mk.pjonceski.empleyeemanager.utils.helpers.FileHelper;
import mk.pjonceski.empleyeemanager.utils.helpers.Helpers;
import mk.pjonceski.empleyeemanager.utils.helpers.PicassoHelper;
import mk.pjonceski.empleyeemanager.utils.helpers.ServiceHelper;
import mk.pjonceski.empleyeemanager.utils.helpers.SharedPrefHelper;
import mk.pjonceski.empleyeemanager.utils.helpers.SystemStateHelper;

/**
 * Dagger module that helps dagger to know our helpers in compile time.
 * All {@link mk.pjonceski.empleyeemanager.utils.helpers} injectors should be defined in this class.
 */
@SuppressWarnings({"unised", "WeakerAccess"})
@Module
public abstract class HelpersBindingModule {
    @Singleton
    @Provides
    static FileHelper provideFileHelper(App app) {
        return new FileHelper(app);
    }

    @Singleton
    @Provides
    static PicassoHelper providePicassoHelper(AppExecutors appExecutors,
                                              FileHelper fileHelper,
                                              EmployeeLocalDataSource employeeLocalDataSource) {
        return new PicassoHelper(appExecutors, fileHelper, employeeLocalDataSource);
    }

    @Singleton
    @Provides
    static ServiceHelper provideServiceHelper(App app) {
        return new ServiceHelper(app);
    }

    @Singleton
    @Provides
    static SharedPrefHelper provideSharedPrefHelper(App app) {
        return new SharedPrefHelper(app);
    }

    @Singleton
    @Provides
    static SystemStateHelper provideSystemStateHelper(App app) {
        return new SystemStateHelper(app);
    }

    @Singleton
    @Provides
    static Helpers provideHelpers(FileHelper fileHelper,
                                  PicassoHelper picassoHelper,
                                  SystemStateHelper systemStateHelper,
                                  SharedPrefHelper sharedPrefHelper,
                                  ServiceHelper serviceHelper) {
        return new Helpers(fileHelper, picassoHelper, systemStateHelper, sharedPrefHelper, serviceHelper);
    }

}
