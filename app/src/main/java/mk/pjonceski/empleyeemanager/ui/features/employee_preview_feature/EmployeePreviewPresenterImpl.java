package mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature;

import mk.pjonceski.empleyeemanager.navigation.Router;
import mk.pjonceski.empleyeemanager.ui.base_mvp.presenter.PresenterTemplate;

/**
 * Implementation for {@link EmployeePreviewContract.Presenter}
 */
public class EmployeePreviewPresenterImpl
        extends PresenterTemplate<EmployeePreviewContract.View, EmployeePreviewContract.Interactor>
        implements EmployeePreviewContract.Presenter {
    private Router router;

    public EmployeePreviewPresenterImpl(EmployeePreviewContract.View view,
                                        EmployeePreviewContract.Interactor interactor,
                                        Router router) {
        super(view, interactor);
        this.router = router;
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
