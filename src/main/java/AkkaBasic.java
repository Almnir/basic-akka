import akka.actor.*;
import scala.Console;

import static scala.Console.println;

/**
 * Created by alm on 22.06.2014.
 */

class BasicSystemActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof MessageClass) {
            MessageClass message = (MessageClass) o;
            if (message != null) {
                println(message.message);
                getSender().tell("hohoho", getSelf());
            }
        } if (o instanceof CommandClass) {
            switch ((CommandClass) o) {
                case SEND: {
                    println("Sending to remote actor...");
                    String agentURL = "akka.tcp://agentsystem@192.168.56.0:8081/user/agent";
                    ActorSelection actor = getContext().actorSelection(agentURL);
                    MessageClass messageClass = new MessageClass("Hello!");
                    actor.tell(messageClass, getSelf());
//                    actor.tell("hello, agent!", getSelf());
                    Thread.sleep(100);
                    break;
                }
            }
        } else {
            unhandled(o);
        }
    }
}

class DefaultDeadLetterHandlerActor extends UntypedActor{

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof DeadLetter) {
            DeadLetter deadLetter = (DeadLetter) o;
            println(deadLetter.toString());
        } else {
            unhandled(o);
        }
    }
}

public class AkkaBasic {
    public static void main(String[] args) throws InterruptedException {
        final ActorSystem system = ActorSystem.create("basicsystem");

        final ActorRef actor = system.actorOf(
                Props.create(BasicSystemActor.class), "basicactor");
        println("Started ...");
        // DeadLetter catch
        final ActorRef actorDead = system.actorOf(Props.create(DefaultDeadLetterHandlerActor.class));
        system.eventStream().subscribe(actor, DeadLetter.class);
        actor.tell(CommandClass.SEND, ActorRef.noSender());
//        String agentURL = "akka.tcp://agentsystem@192.168.56.0:8081/user/agent";
//        ActorSelection actor = system.actorSelection(agentURL);
//        MessageClass messageClass = new MessageClass("Hello!");
//        println("Sending to remote actor...");
//        actor.tell(messageClass, ActorRef.noSender());
//        Thread.sleep(100);

        println("Press ENTER to exit");
        Console.readLine();
/*
        Timeout timeout = new Timeout(Duration.apply(10, SECONDS));
        MessageClass msg = new MessageClass("Hello!");
        Future<Object> future = Patterns.ask(actor, msg, timeout);
        try {
            String result = (String) Await.result(future, Duration.apply(10, SECONDS));
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/

        system.shutdown();
    }
}
