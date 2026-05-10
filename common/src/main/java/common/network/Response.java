package common.network;
import java.io.Serial;
import java.io.Serializable;


public record Response(Result<?> result) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}


