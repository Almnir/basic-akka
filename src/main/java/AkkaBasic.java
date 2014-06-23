import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Created by alm on 22.06.2014.
 */

class BasicSystemActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof MessageClass) {
            MessageClass message = (MessageClass) o;
            if (message != null) {
                System.out.println(message.message);
            }
        } else {
            unhandled(o);
        }
    }
}

public class AkkaBasic {
    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("basicsystem");

        final ActorRef actor = system.actorOf(
            Props.create(BasicSystemActor.class), "basicactor");
        System.out.println("Started BasicSystem");
        MessageClass messageClass = new MessageClass("Hello!");
        actor.tell(messageClass, ActorRef.noSender());
    }
}
