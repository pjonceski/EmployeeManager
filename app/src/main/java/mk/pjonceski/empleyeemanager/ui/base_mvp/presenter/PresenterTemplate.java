package mk.pjonceski.empleyeemanager.ui.base_mvp.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import javax.annotation.Nonnull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import mk.pjonceski.empleyeemanager.ui.base_mvp.BaseInteractor;
import mk.pjonceski.empleyeemanager.ui.base_mvp.BaseView;

/**
 * Presenter template which is blueprint for other presenters in order to achieve the mvp pattern.
 */
@SuppressWarnings("WeakerAccess")
public class PresenterTemplate<V extends BaseView, I extends BaseInteractor> implements BasePresenter {
    /**
     * View reference.
     */
    public V view;
    /**
     * Interactor reference.
     */
    public I interactor;
    /**
     * Container for managing {@link io.reactivex.Observer}.
     */
    private CompositeDisposable disposableContainer;

    /**
     * Constructor for injecting the View and Interactor.
     *
     * @param view       the view interface
     * @param interactor the interactor interface
     */


    public PresenterTemplate(V view, I interactor) {
        this.view = view;
        this.interactor = interactor;
        this.disposableContainer = new CompositeDisposable();
    }

    @Override
    public void create() {

    }

    @Override
    public void destroy() {
        view = null;
        disposeAllProviders();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void onSaveState(@Nonnull Bundle outState) {

    }

    @Override
    public void onRestoreState(@NonNull Bundle savedState) {

    }

    /**
     * Adds disposable to {@link #disposableContainer}
     */
    public void addDisposableToContainer(@NonNull Disposable disposable) {
        disposableContainer.add(disposable);
    }


    /**
     * Dispose providers for the corresponding disposable.
     *
     * @param disposable the disposable to be deleted.
     */
    public void removeDisposableFromContainer(@NonNull Disposable disposable) {
        if (disposableContainer != null) {
            disposableContainer.delete(disposable);
        }
    }

    /**
     * Disposes all providers added to the {@link #disposableContainer}.
     */
    public void disposeAllProviders() {
        if (disposableContainer != null) {
            disposableContainer.dispose();
        }
    }
}
