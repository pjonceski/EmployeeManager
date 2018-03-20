package mk.pjonceski.empleyeemanager.di.features;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import mk.pjonceski.empleyeemanager.ui.features.employee_details_feature.EmployeeDetailsActivity;
import mk.pjonceski.empleyeemanager.ui.features.employee_details_feature.EmployeeDetailsContract;
import mk.pjonceski.empleyeemanager.ui.features.employee_details_feature.EmployeeDetailsInteractorImpl;
import mk.pjonceski.empleyeemanager.ui.features.employee_details_feature.EmployeeDetailsPresenterImpl;

/**
 * Dagger module to provide instances for the injected variables
 * inside {@link EmployeeDetailsActivity}.
 */
@Module
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class EmployeeDetailsModule {
    @Binds
    abstract EmployeeDetailsContract.View bindView(EmployeeDetailsActivity employeeDetailsActivity);

    @Provides
    static EmployeeDetailsContract.Interactor provideInteractor() {
        return new EmployeeDetailsInteractorImpl();
    }

    @Provides
    static EmployeeDetailsContract.Presenter providePresenter(
            EmployeeDetailsContract.View view, EmployeeDetailsContract.Interactor interactor) {
        return new EmployeeDetailsPresenterImpl(view, interactor);
    }
}
