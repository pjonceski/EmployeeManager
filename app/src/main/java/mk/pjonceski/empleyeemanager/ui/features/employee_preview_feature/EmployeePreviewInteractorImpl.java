package mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature;


import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource;

import static java.util.stream.Collectors.toList;

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
                .toSortedList((employee1, employee2) -> employee1.getName().compareTo(employee2.getName()));
    }
}
