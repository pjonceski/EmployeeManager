package mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature;

import android.os.Bundle;
import android.os.Handler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.navigation.Router;
import mk.pjonceski.empleyeemanager.ui.base_mvp.presenter.PresenterTemplate;
import mk.pjonceski.empleyeemanager.utils.helpers.Helpers;

/**
 * Implementation for {@link EmployeePreviewContract.Presenter}
 */
public class EmployeePreviewPresenterImpl
        extends PresenterTemplate<EmployeePreviewContract.View, EmployeePreviewContract.Interactor>
        implements EmployeePreviewContract.Presenter {
    private Router router;
    private Disposable loadEmployeesListDisposable;
    private Handler loadEmployeesHandler;
    private Runnable refreshDataRunnable;
    private Helpers helpers;

    public EmployeePreviewPresenterImpl(EmployeePreviewContract.View view,
                                        EmployeePreviewContract.Interactor interactor,
                                        Router router, Helpers helpers) {
        super(view, interactor);
        this.router = router;
        this.helpers = helpers;
        loadEmployeesHandler = new Handler();
        refreshDataRunnable = () -> {
            if (view != null) {
                view.showDataLoadingIndicator();
                EmployeePreviewPresenterImpl.this.loadEmployeesList();
            }
        };
    }

    @Override
    public void subscribe() {
        if (view != null) {
            view.showDataLoadingIndicator();
            loadEmployeesList();
        }
    }

    @Override
    public void unsubscribe() {
    }

    @Override
    public void onEmployeeChosenFromList(Employee employee) {
        if (view != null) {
            view.clearConnectivityLayoutIndicators();
        }
        Bundle employeeData = new Bundle();
        employeeData.putParcelable(Router.BundleKeys.EMPLOYEE, employee);
        router.navigateToEmployeeDetailsActivity(employeeData);
    }

    /**
     * Ensuring that it has Debouncing when clicked.
     */
    @Override
    public void onButtonRefreshDataClick() {
        loadEmployeesHandler.removeCallbacks(refreshDataRunnable);
        loadEmployeesHandler.postDelayed(refreshDataRunnable, 500);
    }

    /**
     * Loads list of employees to screen.
     */
    private void loadEmployeesList() {
        removeDisposableFromContainer(loadEmployeesListDisposable);
        loadEmployeesListDisposable = interactor.getAllEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(employeesList -> {
                    if (view != null) {
                        if (helpers.getSharedPrefHelper().isDataOffline()) {
                            view.setConnectivityIndicatorsOffline();
                        } else {
                            view.clearConnectivityLayoutIndicators();
                        }
                        view.hideDataLoadingIndicator();
                        view.populateEmployeesList(employeesList);
                    }
                }, throwable -> {
                    if (view != null) {
                        view.hideDataLoadingIndicator();
                    }
                });
        addDisposableToContainer(loadEmployeesListDisposable);
    }

    @Override
    public void connectivityChange(boolean hasInternet) {
        if (view != null) {
            if (hasInternet) {
                if (helpers.getSharedPrefHelper().isDataOffline()) {
                    view.setInternetAvailableAgainIndicator();
                }
            } else {
                view.setConnectivityIndicatorsOffline();
            }
        }
    }
}