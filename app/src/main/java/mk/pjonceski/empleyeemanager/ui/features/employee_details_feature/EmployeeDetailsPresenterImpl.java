package mk.pjonceski.empleyeemanager.ui.features.employee_details_feature;

import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.ui.base_mvp.presenter.PresenterTemplate;

/**
 * Implementation for {@link EmployeeDetailsContract.Presenter}.
 */
public class EmployeeDetailsPresenterImpl
        extends PresenterTemplate<EmployeeDetailsContract.View, EmployeeDetailsContract.Interactor>
        implements EmployeeDetailsContract.Presenter {
    public EmployeeDetailsPresenterImpl(EmployeeDetailsContract.View view, EmployeeDetailsContract.Interactor interactor) {
        super(view, interactor);
    }

    @Override
    public void create() {
        super.create();
        if (view != null) {
            Employee employee = view.getEmployee();

        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void subscribe() {
        super.subscribe();
        if (view != null) {
            Employee employee = view.getEmployee();
        }
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();
    }
}
