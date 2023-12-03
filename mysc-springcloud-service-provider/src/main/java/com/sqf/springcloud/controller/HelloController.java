package com.sqf.springcloud.controller;

import com.sqf.springcloud.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: shaoqingfeng
 * @CREATE: 2023/11/29 10:21
 */
@RestController // 等价于@Controller+@ResponseBody
@RequestMapping(value = "/service")
public class HelloController {

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String hello(@RequestBody String name){
        System.out.println("provider hello 服务者...");

        // 业务处理;
//        Map mapObj = JSONObject.parseObject(name, Map.class);
        String response = "OK ..................................... ";
        if (StringUtils.isNotBlank(name)) {
            System.out.println(response.concat(name));
        } else {
            name = "--";
            System.out.println("jsonSring is null!");
        }
        return "Hello,".concat(name).concat(",Spring Cloud. provider 1");
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User user(@RequestBody Long id){
        String msg = "user provider 1 ...";
        System.out.println(msg);

        if (null == id) {
            System.out.println(msg.concat("ID 为空!"));
            return null;
        } else {
            User user = new User();
            user.setId(19811010L);
            user.setName("孙悟空");
            user.setAge(41);
            user.setPhone("13075412350");

            if(user.getId().compareTo(id) == 0) {
                return user;
            }

            System.out.println(msg.concat(" 错误的ID!"));

            return user;
        }
    }

    //http网页请求测试,RequestParam获取参数
    @RequestMapping(value = "/helloDTO", method = RequestMethod.POST)
    public String helloDTO(@RequestParam("name") String name){
        System.out.println("mysc-springcloud-service-provider helloDTO 服务者...".concat(name));
        if (StringUtils.isBlank(name)){
            name = " name is null !";
        }
        return "Hello,".concat(name).concat(",Spring Cloud.");
    }
}
