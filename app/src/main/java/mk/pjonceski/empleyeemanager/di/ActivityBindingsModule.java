package mk.pjonceski.empleyeemanager.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import mk.pjonceski.empleyeemanager.di.features.EmployeeDetailsModule;
import mk.pjonceski.empleyeemanager.di.features.EmployeePreviewModule;
import mk.pjonceski.empleyeemanager.di.scopes.PerActivity;
import mk.pjonceski.empleyeemanager.ui.features.employee_details_feature.EmployeeDetailsActivity;
import mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature.EmployeePreviewActivity;

/**
 * Dagger module that helps dagger to know our activities in compile time.
 * All activities injectors should be defined in this class.
 */
@Module
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class ActivityBindingsModule {
    @PerActivity
    @ContributesAndroidInjector(modules = {EmployeePreviewModule.class})
    abstract EmployeePreviewActivity provideEmployeePreviewAndroidInject();

    @PerActivity
    @ContributesAndroidInjector(modules = {EmployeeDetailsModule.class})
    abstract EmployeeDetailsActivity provideEmployeeDetailsAndroidInject();
}
