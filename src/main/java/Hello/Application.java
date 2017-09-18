package Hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Xiaochen(Alex) on 2017/7/13.
 */
@Controller
//@RestController
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Home page";
    }

    //https://spring.io/blog/2015/06/08/cors-support-in-spring-framework
    //Global CORS configuration
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
