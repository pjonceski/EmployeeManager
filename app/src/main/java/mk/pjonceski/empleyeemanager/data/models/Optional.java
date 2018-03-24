package mk.pjonceski.empleyeemanager.data.models;

import android.support.annotation.Nullable;

import java.util.NoSuchElementException;

/**
 * This class is used to wrap variables that may be null.
 * By calling {@link Optional#isEmpty()} you can check if it is null.
 *
 * @param <M> the variable that is wrapped.
 */
@SuppressWarnings({"unused","WeakerAccess"})
public class Optional<M> {

    private final M optional;

    public Optional(@Nullable M optional) {
        this.optional = optional;
    }

    public boolean isEmpty() {
        return this.optional == null;
    }

    public M get() {
        if (optional == null) {
            throw new NoSuchElementException("No value present");
        }
        return optional;
    }
}