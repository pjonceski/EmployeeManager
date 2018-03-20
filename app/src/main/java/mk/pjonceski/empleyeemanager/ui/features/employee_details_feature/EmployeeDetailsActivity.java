package mk.pjonceski.empleyeemanager.ui.features.employee_details_feature;

import javax.inject.Inject;

import mk.pjonceski.empleyeemanager.R;
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
    protected void onResume() {
        super.onResume();
    }
}
