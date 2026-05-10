package common.network;

import java.io.Serial;
import java.io.Serializable;

public record User(String name, String password) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
