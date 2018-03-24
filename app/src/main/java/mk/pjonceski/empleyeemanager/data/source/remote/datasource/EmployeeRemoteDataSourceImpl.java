package mk.pjonceski.empleyeemanager.data.source.remote.datasource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.data.source.remote.retrofit.RetrofitApi;
import mk.pjonceski.empleyeemanager.utils.static_utils.PublishersHelper;
import retrofit2.Call;

/**
 * This class implements methods that provide data from cloud.
 * It provides data for the contract {@link mk.pjonceski.empleyeemanager.data.repositories.EmployeeDataSource}.
 */

public class EmployeeRemoteDataSourceImpl implements EmployeeRemoteDataSource {
    private RetrofitApi retrofitApi;
    /**
     * If set to true data from {@link #fakeEmployeeData} will be used.
     * If set to false data from {@link RetrofitApi#getAllEmployees()} will be used.
     * For Testing purposes set this to true when remote data source is unavailable.
     */
    private boolean useFakeData = false;
    /**
     * Fake remote data.
     */
    private Employee[] fakeEmployeeData = new Employee[]{
            new Employee("Petar Joncheski",
                    "<div>Petar Joncheski Bioraphy</br>Works at infobirosdfsadfsdfsfsdfsdfsdffsafasdfasdfsfsfs</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>" +
                            "<div>sfasdfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff</div>"
                    ,
                    "Info Biro",
                    "Senior Android Developer",
                    "https://www.allbusiness.com/asset/2016/05/app-developer-300x235.jpg"),
            new Employee("Lacar Panchevski",
                    "Lacar Panchevski Bioraphy</br>Works at Reward Gateway",
                    "Reward Gateway",
                    "Senior Android Developer",
                    "http://cdn.wccftech.com/wp-content/uploads/2016/06/app-developer.jpg"),
            new Employee("Petar Joncheski",
                    "Petar Joncheski Bioraphy</br>Works at infobiro",
                    "Info Biro",
                    "Senior Android Developer",
                    "https://www.allbusiness.com/asset/2016/05/app-developer-300x235.jpg"),
            new Employee("Lacar Panchevski",
                    "Lacar Panchevski Bioraphy</br>Works at Reward Gateway",
                    "Reward Gateway",
                    "Senior Android Developer",
                    "http://cdn.wccftech.com/wp-content/uploads/2016/06/app-developer.jpg"),
            new Employee("Petar Joncheski",
                    "Petar Joncheski Bioraphy</br>Works at infobiro",
                    "Info Biro",
                    "Senior Android Developer",
                    "https://www.allbusiness.com/asset/2016/05/app-developer-300x235.jpg"),
            new Employee("Lacar Panchevski",
                    "Lacar Panchevski Bioraphy</br>Works at Reward Gateway",
                    "Reward Gateway",
                    "Senior Android Developer",
                    "http://cdn.wccftech.com/wp-content/uploads/2016/06/app-developer.jpg"),
            new Employee("Petar Joncheski",
                    "Petar Joncheski Bioraphy</br>Works at infobiro",
                    "Info Biro",
                    "Senior Android Developer",
                    "https://www.allbusiness.com/asset/2016/05/app-developer-300x235.jpg"),
            new Employee("Lacar Panchevski",
                    "Lacar Panchevski Bioraphy</br>Works at Reward Gateway",
                    "Reward Gateway",
                    "Senior Android Developer",
                    "http://cdn.wccftech.com/wp-content/uploads/2016/06/app-developer.jpg"),
            new Employee("Petar Joncheski",
                    "Petar Joncheski Bioraphy</br>Works at infobiro",
                    "Info Biro",
                    "Senior Android Developer",
                    "https://www.allbusiness.com/asset/2016/05/app-developer-300x235.jpg"),
            new Employee("Lacar Panchevski",
                    "Lacar Panchevski Bioraphy</br>Works at Reward Gateway",
                    "Reward Gateway",
                    "Senior Android Developer",
                    "http://cdn.wccftech.com/wp-content/uploads/2016/06/app-developer.jpg"),
            new Employee("Petar Joncheski",
                    "Petar Joncheski Bioraphy</br>Works at infobiro",
                    "Info Biro",
                    "Senior Android Developer",
                    "https://www.allbusiness.com/asset/2016/05/app-developer-300x235.jpg"),
            new Employee("Lacar Panchevski",
                    "Lacar Panchevski Bioraphy</br>Works at Reward Gateway",
                    "Reward Gateway",
                    "Senior Android Developer",
                    "http://cdn.wccftech.com/wp-content/uploads/2016/06/app-developer.jpg"),
            new Employee("Petar Joncheski",
                    "Petar Joncheski Bioraphy</br>Works at infobiro",
                    "Info Biro",
                    "Senior Android Developer",
                    "https://www.allbusiness.com/asset/2016/05/app-developer-300x235.jpg"),
            new Employee("Lacar Panchevski",
                    "Lacar Panchevski Bioraphy</br>Works at Reward Gateway",
                    "Reward Gateway",
                    "Senior Android Developer",
                    "http://cdn.wccftech.com/wp-content/uploads/2016/06/app-developer.jpg"),
            new Employee("Petar Joncheski",
                    "Petar Joncheski Bioraphy</br>Works at infobiro",
                    "Info Biro",
                    "Senior Android Developer",
                    "https://www.allbusiness.com/asset/2016/05/app-developer-300x235.jpg"),
            new Employee("Lacar Panchevski",
                    "Lacar Panchevski Bioraphy</br>Works at Reward Gateway",
                    "Reward Gateway",
                    "Senior Android Developer",
                    "http://cdn.wccftech.com/wp-content/uploads/2016/06/app-developer.jpg"),
            new Employee("Petar Joncheski",
                    "Petar Joncheski Bioraphy</br>Works at infobiro",
                    "Info Biro",
                    "Senior Android Developer",
                    "https://www.allbusiness.com/asset/2016/05/app-developer-300x235.jpg"),
            new Employee("Lacar Panchevski",
                    "Lacar Panchevski Bioraphy</br>Works at Reward Gateway",
                    "Reward Gateway",
                    "Senior Android Developer",
                    "http://cdn.wccftech.com/wp-content/uploads/2016/06/app-developer.jpg"),
            new Employee("Petar Joncheski",
                    "Petar Joncheski Bioraphy</br>Works at infobiro",
                    "Info Biro",
                    "Senior Android Developer",
                    "https://www.allbusiness.com/asset/2016/05/app-developer-300x235.jpg"),
            new Employee("Lacar Panchevski",
                    "Lacar Panchevski Bioraphy</br>Works at Reward Gateway",
                    "Reward Gateway",
                    "Senior Android Developer",
                    "http://cdn.wccftech.com/wp-content/uploads/2016/06/app-developer.jpg"),
            new Employee("Petar Joncheski",
                    "Petar Joncheski Bioraphy</br>Works at infobiro",
                    "Info Biro",
                    "Senior Android Developer",
                    "https://www.allbusiness.com/asset/2016/05/app-developer-300x235.jpg"),
            new Employee("Lacar Panchevski",
                    "Lacar Panchevski Bioraphy</br>Works at Reward Gateway",
                    "Reward Gateway",
                    "Senior Android Developer",
                    "http://cdn.wccftech.com/wp-content/uploads/2016/06/app-developer.jpg")

    };

    public EmployeeRemoteDataSourceImpl(RetrofitApi retrofitApi) {
        this.retrofitApi = retrofitApi;
    }

    @Override
    public Flowable<List<Employee>> getAllEmployees() {
        return PublishersHelper.createFlowable(getAllEmployeesCallable());
    }

    /**
     * Creates callable that returns the list of employees.
     *
     * @return callable for the provided list.
     */
    private Callable<List<Employee>> getAllEmployeesCallable() {
        return this::getAllEmployeesFromRestApi;
    }

    @Override
    public List<Employee> getAllEmployeesFromRestApi() throws IOException {
        if (!useFakeData) {
            Call<List<Employee>> callEmployees = retrofitApi.getAllEmployees();
            return callEmployees.execute().body();
        } else {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
            }
            return Arrays.asList(fakeEmployeeData);
        }
    }

}
