import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.zeromq.Connect;
import akka.zeromq.Listener;
import akka.zeromq.Subscribe;
import akka.zeromq.ZeroMQExtension;
import scala.Console;

import static scala.Console.println;

public class AkkaZMQ {

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("basicsystem");

        final ActorRef actor = system.actorOf(
            Props.create(ReceiveActor.class));
        println("Started BasicSystem");
        ZeroMQExtension.get(system).newSubSocket(new Connect("tcp://127.0.0.1:2133"), new Listener(actor), Subscribe.all());
        println("Press ENTER to exit");
        Console.readLine();
        system.shutdown();
    }
}
