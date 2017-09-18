package Hello.controller;

import Hello.Tools.DataProcessing;
import Hello.bean.Comment;
import Hello.bean.User;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Xiaochen(Alex) on 2017/7/10.
 */
@RestController
//@Controller
@RequestMapping(value = "/hello")
public class HelloController {

    @GetMapping(value = "/1")
    public Map printHello() {

        Map<String, Object> map = new HashMap<>();
        map.put("spring", "summer");
        map.put("number", new int[] {11,22,33});
        return map;
    }

    @RequestMapping(value = "/2", method = RequestMethod.GET)
    public Map<String,User> printHello2() {



        User p1 = new User("Alex",23,"student","02102834278");
        User p2 = new User("Richard",18,"student","1238");
        User p3 = new User("Emma",30,"student","4567");

        Map<String,User> map = new HashMap<>();
        map.put("1",p1);
        map.put("2",p2);
        map.put("3",p3);

        return map;


    }


    @RequestMapping(value = "/3")
    public HashMap<String, Object> login(@RequestParam String name, @RequestParam String password ) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", "hello world");
        map.put("name", name);
        map.put("password",password);
        return map;
    }

    @RequestMapping(value = "/31")
    public String[] test() {
        String[] toppings = {"Cheese", "Pepperoni", "Black Olives"};
        return toppings;
    }

    @RequestMapping(value = "/32")
    public String[] test2() {
        DataProcessing DP = new DataProcessing("newfile.csv");
        return DP.selectColumns();
    }

    @RequestMapping(value = "/33")
    public String[] test3() {
        DataProcessing DP = new DataProcessing("newfile.csv");

        return DP.selectColumns();
    }


    @RequestMapping(value = "/4/{name}/{age}")
    public User getUser(@PathVariable String name, @PathVariable int age) {
        User user = new User(name,age,"student","123456");
        user.setDate(new Date());
        return user;
    }

    @RequestMapping(value = "/5", method = RequestMethod.POST)
    public HashMap postMethod(@RequestParam String name, @RequestParam String passwd){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", name);
        map.put("password",passwd);
        return map;
    }

    @GetMapping(value = "/6")
    public List<Comment> getJson() {

        Comment c1=new Comment("Alex" , "Great place to eat and shop" ,"10 mins ago");
        Comment c2=new Comment("Richard" , "Great place to eat and shop" ,"5 mins ago");
        Comment c3=new Comment("Jenny" , "Great place to eat and shop" ,"1 mins ago");

        ArrayList<Comment> list = new ArrayList<>();
        list.add(c1);
        list.add(c2);
        list.add(c3);
        return list;
    }

    @GetMapping(value = "/update")
    public String updateComment(@RequestParam String author, @RequestParam String text) {
        System.out.println("author = [" + author + "], text = [" + text + "]");
        Comment c=new Comment(author,text,new Date().toString());

        return "success";
    }

    @PostMapping(value = "/upload")
    public String uploadFile(@RequestParam String name, @RequestParam String code) throws IOException {
        code = code.substring(code.indexOf(',')+1);
        System.out.println("name = [" + name + "], code = [" + code + "]");
        byte[] file = Base64.getDecoder().decode(new String(code).getBytes("UTF-8"));

        String fileName="newfile.csv";

        FileOutputStream stream = new FileOutputStream(fileName);
        try {
            stream.write(file);
        } finally {
            stream.close();
        }
        return "uploadFile success";
    }
    //encode example
    @GetMapping(value = "/update111")
    public String upload(){
        String aa="UHJvY2VzcyB5b3UgYXJlIGZvbGxvd2luZyANCglBZ2lsZSBkZXZlbG9wbWVudA0KR29pbmcgYXMgcGxhbm5lZD8gDQoJV2UgaGF2ZSBhIHNjaGVkdWxlDQoJVGhpcyBhcHBsaWNhdGlvbiBpcyBkZW1hbmRpbmcgZm9yIAlpbnRlcm5hdGlvbmFsIHN0dWRlbnQNCg0KV2hhdCBjb3VsZCBiZSBpbXByb3ZlZD8gDQoJVGVhbXdvcmsNCglEZXNpZ24gDQoJQXBwbGljYXRpb24NCkhvdyB3ZWxsIGlzIGl0IHdvcmtpbmcgZm9yIHlvdXIgZ3JvdXA/IA0KCVdlIGFyZSBhIGdvb2QgdGVhbS4NCglFdmVyeW9uZSBiZSByZXNwb25zaWJsZSBmb3Igb25lIHBhcnQgYW5kIAl0cnkgdGhlaXIgYmVzdC4NCkNoYWxsZW5nZXMgZmFjZWQNCglQcm9qZWN0DQoJVGVjaG5pY2FsDQoJDQo=";
        try {
            byte[] name = Base64.getEncoder().encode("hello World".getBytes());
            System.out.println(name.toString()+"hello64");
            byte[] decodedString = Base64.getDecoder().decode(new String(aa).getBytes("UTF-8"));
            System.out.println(new String(decodedString));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "success";
    }
}
