package mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature;

import android.util.Log;

import java.util.Comparator;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource;

/**
 * Implementation for {@link EmployeePreviewContract.Interactor}
 */
public class EmployeePreviewInteractorImpl implements EmployeePreviewContract.Interactor {
    private EmployeeDataSource employeeDataSource;

    public EmployeePreviewInteractorImpl(EmployeeDataSource employeeDataSource) {
        this.employeeDataSource = employeeDataSource;
    }

    @Override
    public Single<List<Employee>> getAllEmployees() {
        return employeeDataSource.getAllEmployees()
                .flatMap(Flowable::fromIterable)
                .doOnNext(tasks -> {
                    Log.d("test", tasks.getName());
                }).toList();
    }

    @Override
    public Flowable<List<Employee>> getAllEmployeesF() {
        return employeeDataSource.getAllEmployees();
//                .flatMap(Flowable::fromIterable)
//                .sorted((employee1, employee2) -> employee1.getName().compareTo(employee2.getName()))
//                .toSortedList((employee1,employee2)->employee1.getName().compareTo(employee2.getName()))
//                .toFlowable();
    }
}
