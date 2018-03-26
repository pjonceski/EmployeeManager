package mk.pjonceski.empleyeemanager.data.source.local.datasource;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource;
import mk.pjonceski.empleyeemanager.data.models.Optional;

/**
 * Contract for {@link EmployeeLocalDataSourceImpl}.
 * Here are defined methods for persisting and retrieving data from local storage.
 */
public interface EmployeeLocalDataSource extends EmployeeDataSource {
    void insert(Employee employee);

    /**
     * Deletes all persisted Employees and inserts the new ones.
     *
     * @param employeesList the list of employees.
     */
    void reinsertAll(List<Employee> employeesList);

    /**
     * Return single employee for the provided id.
     * If none is found null is returned.
     *
     * @param id the id of the employee.
     * @return Single to emmit the employee.
     */
    Single<Optional<Employee>> getEmployeeById(int id);

    /**
     * Returns all employees from local database.
     *
     * @return all the employees.
     */
    List<Employee> getAllEmployeesFromSqlLite();

    /**
     * Returns all employees with avatar status {@link mk.pjonceski.empleyeemanager.data.source.local.entities.EmployeeEntityContract.AvatarStatus#UNSCHEDULED}
     *
     * @return the list of unscheduled employees.
     */
    List<Employee> getAllUnscheduledEmployeesForDownloadingAvatarImage();

    /**
     * Updates the {@link mk.pjonceski.empleyeemanager.data.source.local.entities.EmployeeEntityContract.EmployeeColumns#AVATAR_STATUS}
     * for one employee.
     * Possible states are {@link mk.pjonceski.empleyeemanager.data.source.local.entities.EmployeeEntityContract.AvatarStatus}.
     *
     * @param id           the id of the Employee.
     * @param avatarStatus the status.
     */
    void updateEmployeeAvatarStatus(int id, int avatarStatus);

    /**
     * Updates the {@link mk.pjonceski.empleyeemanager.data.source.local.entities.EmployeeEntityContract.EmployeeColumns#AVATAR_STATUS}
     * of all the employees.
     * Possible states are {@link mk.pjonceski.empleyeemanager.data.source.local.entities.EmployeeEntityContract.AvatarStatus}.
     *
     * @param avatarStatus the status.
     */
    void updateAllEmployeesAvatarStatus(int avatarStatus);

    /**
     * Returns one employee for provided id from local database.
     *
     * @return callable that return employee or null if none
     */
    Callable<Optional<Employee>> getEmployeeByIdCallable(int id);
}
