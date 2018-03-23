package mk.pjonceski.empleyeemanager.data.repositories;

import java.io.IOException;
import java.util.List;

import io.reactivex.Flowable;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.source.local.datasource.EmployeeLocalDataSource;
import mk.pjonceski.empleyeemanager.data.source.remote.datasource.EmployeeRemoteDataSource;
import mk.pjonceski.empleyeemanager.utils.helpers.Helpers;
import mk.pjonceski.empleyeemanager.utils.no_instance.PublishersHelper;

/**
 * Main Employees data provider(local or remote).
 */
public class EmployeeDataSourceImpl implements EmployeeDataSource {
    private EmployeeLocalDataSource employeeLocalDataSource;
    private EmployeeRemoteDataSource employeeRemoteDataSource;
    private Helpers helpers;

    public EmployeeDataSourceImpl(EmployeeLocalDataSource employeeLocalDataSource,
                                  EmployeeRemoteDataSource employeeRemoteDataSource,
                                  Helpers helpers) {
        this.employeeLocalDataSource = employeeLocalDataSource;
        this.employeeRemoteDataSource = employeeRemoteDataSource;
        this.helpers = helpers;
    }

    @Override
    public Flowable<List<Employee>> getAllEmployees() {
        if (helpers.getSystemStateHelper().isInternetAvailable()) {
            return PublishersHelper.createFlowable(() -> {
                List<Employee> employeesList = null;
                try {
                    employeesList = employeeRemoteDataSource.getAllEmployeesCallable().call();
                } catch (IOException ex) {
                    /*IOException while retrieving from network.*/
                }
                if (employeesList == null) {
                    helpers.getSharedPrefHelper().setDataSourceIsFromLocalStorage();
                    employeesList = employeeLocalDataSource.getAllEmployeesCallable().call();
                } else {
                    helpers.getSharedPrefHelper().setDataSourceIsFromRemoteStorage();
                    employeeLocalDataSource.reinsertAll(employeesList);
                }
                return employeesList;
            });
        } else {
            helpers.getSharedPrefHelper().setDataSourceIsFromLocalStorage();
            return employeeLocalDataSource.getAllEmployees();
        }
    }
}
