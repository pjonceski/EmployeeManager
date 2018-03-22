package mk.pjonceski.empleyeemanager.ui.base_mvp.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import javax.annotation.Nonnull;

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
     * This method should be invoked {@link Activity#onResume(),android.app.Fragment#onResume,android.view.View#onAttachedToWindow}
     */
    void subscribe();

    /**
     * This methot should be used to save the state of activity.
     *
     * @param outState the bundle to put the data to be saved.
     */
    void onSaveState(@Nonnull Bundle outState);

    /**
     * This methot should be used to restore the state of activity.
     *
     * @param savedState bundle to get the data to be saved.
     */
    void onRestoreState(@NonNull Bundle savedState);

    /**
     * This method should be invoked {@link Activity#onPause(),android.app.Fragment#onPause,android.view.View#onDetachedFromWindow}
     */
    void unsubscribe();

    /**
     * This method should be invoked when the screen is no longer able to handles user actions.
     */
    void destroy();

}
