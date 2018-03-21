package mk.pjonceski.empleyeemanager.data.repositories;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.utils.Optional;

/**
 * In this class are defined methods that publish different types of data for employees.
 */

public interface EmployeeDataSource {

    Flowable<List<Employee>> getAllEmployees();

}
