package mk.pjonceski.empleyeemanager.utils;

import android.support.annotation.Nullable;

import java.util.concurrent.Callable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Contains helper methods for creating RxJava publishers:
 * {@link Flowable},
 * {@link Single}
 */
public final class PublishersHelper {
    private PublishersHelper() {

    }

    /**
     * * This method creates {@link Flowable} using the provided {@link Callable}.
     *
     * @param iCallable the input callable.
     * @param exception the exception if any.
     * @param <T>       the data to be published.
     * @return the Flowable
     */
    public static <T> Flowable<T> createFlowable(final Callable<T> iCallable, @Nullable final Exception exception) {
        return Flowable.create(emitter -> {
            if (exception == null) {
                emitter.onNext(iCallable.call());
            } else {
                emitter.onError(exception);
            }
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * This method creates {@link Single} using the provided {@link Callable}.
     *
     * @param iCallable the input callable.
     * @param exception the exception if any.
     * @param <T>       the data to be published.
     * @return the Single
     */
    public static <T> Single<T> createSingle(Callable<T> iCallable, @Nullable Exception exception) {
        return Single.create(emitter -> {
            if (exception != null) {
                emitter.onError(exception);
            } else {
                emitter.onSuccess(iCallable.call());
            }
        });
    }
}
