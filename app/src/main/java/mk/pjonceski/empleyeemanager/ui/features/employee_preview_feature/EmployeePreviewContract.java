package mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature;

import mk.pjonceski.empleyeemanager.ui.base_mvp.BaseInteractor;
import mk.pjonceski.empleyeemanager.ui.base_mvp.BaseView;
import mk.pjonceski.empleyeemanager.ui.base_mvp.presenter.BasePresenter;

/**
 * Defined all contracts that need to be implemented for
 * {@link mk.pjonceski.empleyeemanager.ui.features.employee_preview_feature} feature.
 */
public interface EmployeePreviewContract {
    /**
     * Defined all methods for interaction with the view.
     */
    interface View extends BaseView {
    }

    /**
     * Defined all methods for the presenter.
     */
    interface Presenter extends BasePresenter{
    }

    /**
     * Defined all methods for the interactor.
     */
    interface Interactor extends BaseInteractor {
    }
}
