package Hello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;

import javax.websocket.*;
import javax.websocket.server.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Alex on 2017/9/6.
 */
//@RestController
//@Controller
//@RequestMapping(value = "/socket")

//@ServerEndpoint("/socket")
public class WebSocketController {


    //queue holds the list of connected clients
    private static Queue<Session> queue = new ConcurrentLinkedQueue<Session>();
    private static Thread rateThread ; //rate publisher thread

//    //@MessageMapping("/add" )
//    //@SendTo("/topic/showResult")
//    public String addNum(String input) throws Exception {
//        Thread.sleep(2000);
//        String result=input;
//        return result;
//    }
//    @RequestMapping("/start")
//    public String start() {
//        return "start";
//    }

    static
    {
//rate publisher thread, generates a new value for USD rate every 2 seconds.
        rateThread=new Thread(){
            public void run() {
                DecimalFormat df = new DecimalFormat("#.####");
                while(true)
                {
                    double d=2+Math.random();
                    if(queue!=null)
                        sendAll("USD Rate: "+df.format(d));
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                    }
                }
            };
        } ;
        rateThread.start();
    }
    @OnMessage
    public void onMessage(Session session, String msg) {
//provided for completeness, in out scenario clients don't send any msg.
        try {
            System.out.println("received msg "+msg+" from "+session.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnOpen
    public void open(Session session) {
        queue.add(session);
        System.out.println("New session opened: "+session.getId());
    }
    @OnError
    public void error(Session session, Throwable t) {
        queue.remove(session);
        System.err.println("Error on session "+session.getId());
    }
    @OnClose
    public void closedConnection(Session session) {
        queue.remove(session);
        System.out.println("session closed: "+session.getId());
    }
    private static void sendAll(String msg) {
        try {
   /* Send the new rate to all open WebSocket sessions */
            ArrayList<Session > closedSessions= new ArrayList<>();
            for (Session session : queue) {
                if(!session.isOpen())
                {
                    System.err.println("Closed session: "+session.getId());
                    closedSessions.add(session);
                }
                else
                {
                    session.getBasicRemote().sendText(msg);
                }
            }
            queue.removeAll(closedSessions);
            System.out.println("Sending "+msg+" to "+queue.size()+" clients");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
