package mk.pjonceski.empleyeemanager.ui.features.employee_details_feature;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import javax.inject.Inject;

import mk.pjonceski.empleyeemanager.R;
import mk.pjonceski.empleyeemanager.data.models.Employee;
import mk.pjonceski.empleyeemanager.navigation.Router;
import mk.pjonceski.empleyeemanager.ui.base_mvp.BaseMVPActivity;
import mk.pjonceski.empleyeemanager.ui.base_mvp.presenter.BasePresenter;

/**
 * This activity contains all details for one employee.
 */
public class EmployeeDetailsActivity extends BaseMVPActivity implements EmployeeDetailsContract.View {
    @Inject
    EmployeeDetailsContract.Presenter presenter;

    public BasePresenter getBasePresenter() {
        return presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.employee_details_layout;
    }

    @Override
    public int getScreenTitle() {
        return R.string.employees_details_title;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpScreen();
    }

    private void setUpScreen() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public Employee getEmployee() {
        return (Employee) getIntent().getParcelableExtra(Router.BundleKeys.EMPLOYEE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void populateScreenWithEmployee(Employee employee) {


    }
}
