package mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.ui.base_mvp.BaseInteractor;
import mk.pjonceski.empleyeemanager.ui.base_mvp.BaseView;
import mk.pjonceski.empleyeemanager.ui.base_mvp.presenter.BasePresenter;

/**
 * Defined all contracts that need to be implemented for
 * {@link mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature} feature.
 */
public interface EmployeePreviewContract {
    /**
     * Defined all methods for interaction with the view.
     */
    interface View extends BaseView {
        void showProgress();

        void hideProgress();

        void populateEmployeesList(List<Employee> employeeList);

        void setOfflineIndicator();

        void setOnlineIndicator();
    }

    /**
     * Defined all methods for the presenter.
     */
    interface Presenter extends BasePresenter {
        void onEmployeeChosenFromList(Employee employee);
        void onButtonRefreshDataClick();
    }

    /**
     * Defined all methods for the interactor.
     */
    interface Interactor extends BaseInteractor {
        Single<List<Employee>> getAllEmployees();

        Flowable<List<Employee>> getAllEmployeesF();
    }

    interface OnRowItemClickListener {
        void itemClicked(Employee employee);

    }
}
