package space.irsi7.exceptions;

import java.io.IOException;
import java.io.UncheckedIOException;

public class IllegalInitialDataException extends UncheckedIOException {

    public IllegalInitialDataException(IOException cause) {
        super(cause);
    }
}

