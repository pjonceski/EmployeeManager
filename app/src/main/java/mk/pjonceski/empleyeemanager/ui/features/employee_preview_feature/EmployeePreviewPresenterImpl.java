package mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature;

import android.os.Bundle;

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

    public EmployeePreviewPresenterImpl(EmployeePreviewContract.View view,
                                        EmployeePreviewContract.Interactor interactor,
                                        Router router) {
        super(view, interactor);
        this.router = router;
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void subscribe() {
        if (view != null) {
            view.showProgress();
//            addDisposableToContainer(
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
//                    )
                    );

            loadEmployeesListDisposable = loadEmployeesList();
            addDisposableToContainer(loadEmployeesListDisposable);
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

    @Override
    public void onButtonRefreshDataClick() {
        if (loadEmployeesListDisposable != null) {
            removeDisposableFromContainer(loadEmployeesListDisposable);
            loadEmployeesListDisposable = loadEmployeesList();
            addDisposableToContainer(loadEmployeesListDisposable);
        }
    }

    /**
     * Loads list of employees to screen.
     *
     * @return disposable reference.
     */
    private Disposable loadEmployeesList() {
        return interactor.getAllEmployeesF()
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

    }
}
