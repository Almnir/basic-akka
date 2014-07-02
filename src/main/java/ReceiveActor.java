import akka.actor.UntypedActor;

public class ReceiveActor extends UntypedActor {
    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof String) {
            System.out.println(o);
        } else {
            unhandled(o);
        }
    }
}
