package mk.pjonceski.empleyeemanager.ui.base_mvp.presenter;

import android.app.Activity;

/**
 * The base interface for Presenters.
 * Here we can define common functionalities.
 */

public interface BasePresenter {
    /**
     * This method should be invoked when the screen is ready to be populated with data.
     */
    void create();
    /**
     * This method should be invoked when the screen is no longer able to handles user actions.
     */
    void destroy();
    /**
     * This method should be invoked {@link Activity#onResume(),android.app.Fragment#onResume,android.view.View#onAttachedToWindow}
     */
    void subscribe();
    /**
     * This method should be invoked {@link Activity#onPause(),android.app.Fragment#onPause,android.view.View#onDetachedFromWindow}
     */
    void unsubscribe();
}
