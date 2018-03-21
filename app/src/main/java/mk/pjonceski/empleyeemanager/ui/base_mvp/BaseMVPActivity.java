package mk.pjonceski.empleyeemanager.ui.base_mvp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import mk.pjonceski.empleyeemanager.App;
import mk.pjonceski.empleyeemanager.ui.base_mvp.presenter.BasePresenter;

/**
 * Activity template which is blueprint for other activities that are implementing mvp pattern.
 */
public abstract class BaseMVPActivity extends AppCompatActivity implements HasFragmentInjector, BaseView {
    /**
     * Should return the presenter for this Activity.
     *
     * @return Interface that extends from {@link BasePresenter}.
     */
    public abstract BasePresenter getBasePresenter();

    /**
     * Should return the layout xml resource id for the content of the activity.
     *
     * @return the id of the layout.
     */
    public abstract @LayoutRes
    int getLayoutId();

    /**
     * Should return the string resource for the title of the activity.
     *
     * @return the id of the string.
     */
    public abstract @StringRes
    int getScreenTitle();

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        AndroidInjection.inject(this);
        getBasePresenter().create();
        setTitle(getScreenTitle());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBasePresenter().subscribe();
        ((App) getApplicationContext()).setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getBasePresenter().unsubscribe();
        ((App) getApplicationContext()).setCurrentActivity(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getBasePresenter().destroy();
    }


    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void finishActivity() {
        this.finish();
    }
}