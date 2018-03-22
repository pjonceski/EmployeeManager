package mk.pjonceski.empleyeemanager.data.source.local.datasource;

import java.util.List;

import io.reactivex.Single;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource;
import mk.pjonceski.empleyeemanager.utils.Optional;

/**
 * Contract for {@link EmployeeLocalDataSourceImpl}.
 * Here are defined methods for persisting and retrieving data from local storage.
 */
public interface EmployeeLocalDataSource extends EmployeeDataSource {
    void insert(Employee employee);

    /**
     * Deletes all persisted Employees and inserts the new ones.
     *
     * @param employeesList the list of employees
     */
    void reinsertAll(List<Employee> employeesList);

    /**
     * Return single employee for the provided id.
     * If none is found null is returned.
     *
     * @param id the id of the imployee
     * @return Single to emmit the employee
     */
    Single<Optional<Employee>> getEmployeeById(int id);

}
