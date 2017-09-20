package Hello.socket;

/**
 * Created by Alex on 2017/9/6.
 */
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class GreetingController {

    private static Thread rateThread ; //rate publisher thread
    private Thread passNumThread;
    private static SimpMessagingTemplate template;//get MessagingTemplate
    private Map<String,String> airlinesMap = new HashMap<>();
    private TreeMap<String,String> map= null;

    @Autowired
    public GreetingController(SimpMessagingTemplate template)
    {
        this.template = template;
        jsonFormat();
    }

    static
    {
//rate publisher thread, generates a new value for USD rate every 2 seconds.

//        rateThread=new Thread(){
//            public void run() {
//                System.out.println("new thread running");
//                DecimalFormat df = new DecimalFormat("#.####");
//
//                while(true)
//                {
//                    double d=123+Math.random();
//                    if(true)
//                        try {
//                        //automatically send back to topic
//                            String[] message = {"month",df.format(d),"..."};
//                            template.convertAndSend("/topic/greetings" , message);
//                            System.out.println("Number："+df.format(d));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    try {
//                        sleep(2000);
//                    } catch (InterruptedException e) {
//                    }
//                }
//            };
//        } ;
//        rateThread.start();
    }
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println("MessageMapping receive"+message);
        return "Hello, " + message + "!";
    }

    public void passengerNumThread(String... date){
        System.out.println("date = [" + date.length + "]");

        passNumThread=new Thread(){
            public void run() {
                System.out.println("passNumThread running");


                while(true)
                {
                    try {
                        //automatically send back to topic
                        for(int i=0;i<date.length;i++){
                            String message = airlinesMap.get(date[i]);
                            String m[] = {date[i],message};

                            map = new TreeMap<>();
                            //map.put("date",date[i]);
                            map.put("data",message);

                            template.convertAndSend("/topic/greetings" , map);
                            System.out.println("date: "+date[i]+" message: "+message);
                            try {
                                sleep(2000);
                            } catch (InterruptedException e) {

                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
        } ;
        passNumThread.start();
    }

    public void jsonFormat(){
        String date[] = {"201501","201502","201503","201504","201505","201506","201507","201508","201509","201510","201511","201512",
                "201601","201602","201603","201604","201605","201606","201607","201608","201609","201610","201611","201612",
                "201701","201702","201703","201704","201705"};
        System.out.println("Starting jsonFormat");
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("airlines3.json"));

            JSONObject jsonObject =  (JSONObject) obj;

            for(int i=0;i<date.length;i++){
                JSONArray jsonArray = (JSONArray) jsonObject.get(date[i]);
                Iterator<String> iterator = jsonArray.iterator();
                //System.out.println(jsonArray.toJSONString());
                airlinesMap.put(date[i],jsonArray.toJSONString());
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        passengerNumThread(date);
    }



}
