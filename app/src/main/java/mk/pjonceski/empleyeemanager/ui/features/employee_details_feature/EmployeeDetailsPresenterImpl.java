package mk.pjonceski.empleyeemanager.ui.features.employee_details_feature;

import android.os.Bundle;
import android.support.annotation.NonNull;

import javax.annotation.Nonnull;

import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.navigation.Router;
import mk.pjonceski.empleyeemanager.ui.base_mvp.presenter.PresenterTemplate;

/**
 * Implementation for {@link EmployeeDetailsContract.Presenter}.
 */
public class EmployeeDetailsPresenterImpl
        extends PresenterTemplate<EmployeeDetailsContract.View, EmployeeDetailsContract.Interactor>
        implements EmployeeDetailsContract.Presenter {
    private Employee employee;

    public EmployeeDetailsPresenterImpl(EmployeeDetailsContract.View view, EmployeeDetailsContract.Interactor interactor) {
        super(view, interactor);
    }

    @Override
    public void subscribe() {
        super.subscribe();
        if (view != null) {
            view.populateScreenWithEmployee(employee);
        }
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
    }

    @Override
    public void onSaveState(@Nonnull Bundle outState) {
        super.onSaveState(outState);
        outState.putParcelable(Router.BundleKeys.EMPLOYEE, employee);
    }

    @Override
    public void onRestoreState(@NonNull Bundle savedState) {
        super.onRestoreState(savedState);
        employee = savedState.getParcelable(Router.BundleKeys.EMPLOYEE);
    }
}
