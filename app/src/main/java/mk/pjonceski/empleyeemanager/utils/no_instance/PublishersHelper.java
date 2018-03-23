package mk.pjonceski.empleyeemanager.utils.no_instance;

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
     * @param <T>       the data to be published.
     * @return the Flowable that provides the data.
     */
    public static <T> Flowable<T> createFlowable(final Callable<T> iCallable) {
        return Flowable.create(emitter -> {
            emitter.onNext(iCallable.call());
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * This method creates {@link Single} using the provided {@link Callable}.
     *
     * @param iCallable the input callable.
     * @param <T>       the data to be published.
     * @return the Single that provides the data.
     */
    public static <T> Single<T> createSingle(Callable<T> iCallable) {
        return Single.create(emitter -> {
            emitter.onSuccess(iCallable.call());
        });
    }

    /**
     * This method creates {@link Flowable} using the provided {@link T} type parameter.
     *
     * @param objectToBeStreamed the object to be published.
     * @param <T>                the type of the object to be published.
     * @return flowable that provides the input object.
     */
    public static <T> Flowable<T> createFlowable(@Nullable T objectToBeStreamed, @Nullable Exception ex) {
        return Flowable.create(emitter -> {
            if (ex != null) {
                emitter.onError(ex);
            } else {
                emitter.onNext(objectToBeStreamed);
            }
        }, BackpressureStrategy.BUFFER);
    }
}
