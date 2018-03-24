package mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature;

import java.util.List;

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
        /**
         * Show indicator on ui that data is loading.
         */
        void showDataLoadingIndicator();

        /**
         * Hide indicator that on ui for loading data.
         */
        void hideDataLoadingIndicator();

        /**
         * Set the data employeeList to the adapter.
         *
         * @param employeeList the list to be loaded.
         */
        void populateEmployeesList(List<Employee> employeeList);

        /**
         * Set status of the screen that no internet is available.
         */
        void setConnectivityIndicatorsOffline();

        /**
         * Hides all the connectivity status indicators.
         */
        void clearConnectivityLayoutIndicators();

        /**
         * Set indicator that internet is available again.
         */
        void setInternetAvailableAgainIndicator();
    }

    /**
     * Defined all methods for the presenter.
     */
    interface Presenter extends BasePresenter {
        /**
         * When employee is chosen(click) from the list of employees.
         *
         * @param employee the chosen employee.
         */
        void onEmployeeChosenFromList(Employee employee);

        /**
         * When button to refresh data is clicked.
         */
        void onButtonRefreshDataClick();

        /**
         * This method is called when internet connectivity is changed.
         *
         * @param hasInternet is true if internet is available,false otherwise.
         */
        void connectivityChange(boolean hasInternet);
    }

    /**
     * Defined all methods for the interactor.
     */
    interface Interactor extends BaseInteractor {
        /**
         * Emits the list of employees.
         *
         * @return observable which emmit the list of employees.
         */
        Single<List<Employee>> getAllEmployees();
    }

    interface OnRowItemClickListener {
        /**
         * When item is clicked from the list.
         *
         * @param employee employee for the clicked row.
         */
        void itemClicked(Employee employee);

    }
}
