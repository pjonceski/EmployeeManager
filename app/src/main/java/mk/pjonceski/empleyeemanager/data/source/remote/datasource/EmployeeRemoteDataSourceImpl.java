package mk.pjonceski.empleyeemanager.data.source.remote.datasource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Single;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource;
import mk.pjonceski.empleyeemanager.data.source.remote.retrofit.RetrofitApi;
import mk.pjonceski.empleyeemanager.utils.Optional;
import mk.pjonceski.empleyeemanager.utils.PublishersHelper;
import retrofit2.Call;

/**
 * This class implements methods that provide data from cloud for the
 * contract {@link mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource}.
 */

public class EmployeeRemoteDataSourceImpl implements EmployeeDataSource {
    /**
     * With this name the instance for this class is created.
     */
    public static final String INJECTION_NAME = "EmployeeRemoteDataSource";

    private RetrofitApi retrofitApi;

    public EmployeeRemoteDataSourceImpl(RetrofitApi retrofitApi) {
        this.retrofitApi = retrofitApi;
    }

    @Override
    public Flowable<List<Employee>> getAllEmployees() {
        return PublishersHelper.createFlowable(getAllEmployeesCallable());
    }

    private Callable<List<Employee>> getAllEmployeesCallable() {
        return () -> {
            Call<List<Employee>> callEmployees = retrofitApi.getAllEmployees();
            return callEmployees.execute().body();
        };
    }
}
