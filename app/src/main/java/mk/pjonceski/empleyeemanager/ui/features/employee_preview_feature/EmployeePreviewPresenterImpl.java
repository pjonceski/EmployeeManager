package mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature;

import android.os.Bundle;
import android.os.Handler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.navigation.Router;
import mk.pjonceski.empleyeemanager.ui.base_mvp.presenter.PresenterTemplate;

/**
 * Implementation for {@link EmployeePreviewContract.Presenter}
 */
public class EmployeePreviewPresenterImpl
        extends PresenterTemplate<EmployeePreviewContract.View, EmployeePreviewContract.Interactor>
        implements EmployeePreviewContract.Presenter {
    private Router router;
    private Disposable loadEmployeesListDisposable;

    private Handler mainHandler;

    public EmployeePreviewPresenterImpl(EmployeePreviewContract.View view,
                                        EmployeePreviewContract.Interactor interactor,
                                        Router router) {
        super(view, interactor);
        this.router = router;
        mainHandler = new Handler();
    }


    @Override
    public void subscribe() {
        if (view != null) {
            view.showProgress();
            loadEmployeesList();
            view.setOfflineIndicator();
       /*     addDisposableToContainer(
            interactor.getAllEmployees()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(employees -> {
                                if (view != null) {
                                    view.hideProgress();
                                    view.populateEmployeesList(employees);
                                }
                            }, throwable -> {
                                if (view != null) {
                                    view.hideProgress();
                                }
                            }
                    )
                    );*/

        }

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void onEmployeeChosenFromList(Employee employee) {
        Bundle employeeData = new Bundle();
        employeeData.putParcelable(Router.BundleKeys.EMPLOYEE, employee);
        router.navigateToEmployeeDetailsActivity(employeeData);
    }

    /**
     * Ensuring that it has Debouncing when clicked.
     */
    @Override
    public void onButtonRefreshDataClick() {
        mainHandler.removeCallbacks(null);

        mainHandler.postDelayed(() -> {
            if (view != null) {
                view.showProgress();
                loadEmployeesList();
            }
        }, 1000);
    }

    /**
     * Loads list of employees to screen.
     */
    private void loadEmployeesList() {
        removeDisposableFromContainer(loadEmployeesListDisposable);
        loadEmployeesListDisposable = interactor.getAllEmployeesF()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(employees -> {
                            if (view != null) {
                                view.hideProgress();
                                view.populateEmployeesList(employees);
                            }
                        },
                        throwable -> {
                            if (view != null) {
                                view.hideProgress();
                            }
                        });
        addDisposableToContainer(loadEmployeesListDisposable);
    }
}
