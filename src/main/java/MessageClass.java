import java.io.Serializable;

/**
 * Created by alm on 22.06.2014.
 */
public class MessageClass implements Serializable {
    public final String message;

    public MessageClass(String message) {
        this.message = message;
    }
}
