package mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature;

import javax.inject.Inject;

import mk.pjonceski.empleyeemanager.R;
import mk.pjonceski.empleyeemanager.ui.base_mvp.BaseMVPActivity;
import mk.pjonceski.empleyeemanager.ui.base_mvp.presenter.BasePresenter;

/**
 * In this activity list of all employees will be shown.
 * User can see employee details with click on one item of the list.
 * On item click navigates to {@link mk.pjonceski.empleyeemanager.ui.features.employee_details_feature.EmployeeDetailsActivity}.
 */
public class EmployeePreviewActivity extends BaseMVPActivity implements EmployeePreviewContract.View {
    @Inject
    EmployeePreviewContract.Presenter presenter;

    public BasePresenter getBasePresenter() {
        return presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.employee_preview_layout;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
