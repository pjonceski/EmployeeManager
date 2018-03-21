package mk.pjonceski.empleyeemanager.di.features;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource;
import mk.pjonceski.empleyeemanager.navigation.Router;
import mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature.EmployeePreviewActivity;
import mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature.EmployeePreviewContract;
import mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature.EmployeePreviewInteractorImpl;
import mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature.EmployeePreviewPresenterImpl;

/**
 * Dagger module to provide instances for the injected variables
 * inside {@link EmployeePreviewActivity}.
 */
@Module
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class EmployeePreviewModule {
    @Binds
    abstract EmployeePreviewContract.View bindView(EmployeePreviewActivity employeePreviewActivity);

    @Provides
    static EmployeePreviewContract.Interactor provideInteractor(EmployeeDataSource employeeDataSource) {
        return new EmployeePreviewInteractorImpl(employeeDataSource);
    }

    @Provides
    static EmployeePreviewContract.Presenter providePresenter(EmployeePreviewContract.View view,
                                                              EmployeePreviewContract.Interactor interactor,
                                                              Router router) {
        return new EmployeePreviewPresenterImpl(view, interactor, router);
    }
}
