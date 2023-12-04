package com.sqf.springcloud.controller;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sqf.springcloud.hystrix.MyHystrixCommand;
import com.sqf.springcloud.pojo.UserDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Description:
 * @Author: shaoqingfeng
 * @CREATE: 2023/11/29 10:41
 */
@RestController
@RequestMapping(value = "/web")
public class WebController {

    @Autowired
    private RestTemplate restTemplate;

    private String serviceName = "MYSC-SPRINGCLOUD-SERVICE-PROVIDER";

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String hello (String name){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://".concat(serviceName).concat("/service/hello");

        System.out.println("begin.............".concat(name).concat(":").concat(url));

        // 调用远程服务提供者的服务
        HttpEntity<String> request = new HttpEntity<>(name, headers);

        System.out.println("begin 参数信息 .............".concat(request.toString()));

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        // 获取响应结果
        HttpStatus statusCode = responseEntity.getStatusCode();
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        String responseBody = responseEntity.getBody();

        System.out.println("statusCode:".concat(statusCode.toString()));
        System.out.println("headers".concat(responseHeaders.toString()));

        System.out.println("end............................. response:".concat(responseBody));

        if(StringUtils.isBlank(responseBody))
            return null;

        return responseBody;
    }

    /**
     * 服务提供者用RequestParam接受参数,报错: HttpClientErrorException$BadRequest： 400
     * ignoreExceptions=Exception(RuntimeException运行时异常) // 忽略(异常不进入熔断方法)
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "methodName", ignoreExceptions=RuntimeException.class, commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
    public UserDTO user (@PathVariable("id") Long id){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://".concat(serviceName).concat("/service/user");

        if (null == id) {
            return null;
        }

        int i = 13/0;

        System.out.println("begin.............");

        // 调用远程服务提供者的服务
//        MultiValueMap<String,Long> params = new LinkedMultiValueMap<>();
//        params.add("id",id);
//        String jsonString = JSONObject.toJSONString(params);
//        String jsonString = JSONObject.toJSONString(responseBody);

        HttpEntity<Integer> request = new HttpEntity(id, headers);

//        ResponseEntity<String> responseEntity = restTemplate.exchange(url_1, HttpMethod.POST, request, String.class);
        ResponseEntity<UserDTO> responseEntity = restTemplate.postForEntity(url, request, UserDTO.class);

        // 获取响应结果
        HttpStatus statusCode = responseEntity.getStatusCode();
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        UserDTO responseBody = responseEntity.getBody();

        System.out.println("statusCode:".concat(statusCode.toString()));
        System.out.println("headers".concat(responseHeaders.toString()));
        System.out.println("end.............................");

        if(null == responseBody)
            return null;

        return responseEntity.getBody();
    }

    /**
     * 自定义熔断降级处理
     * @return
     */
    @RequestMapping(value = "/user/hystrix", method = RequestMethod.POST)
    public String userHystrix () throws ExecutionException, InterruptedException, TimeoutException {
        System.out.println("begin 自定义熔断降级处理 .............");

        MyHystrixCommand myHystrixCommand = new MyHystrixCommand(com.netflix.hystrix.HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("")),restTemplate);

        // 同步调用(该方法执行后,会等待远程的返回结果, 该方法才返回,然后代码继续往下执行)
//        String msg = myHystrixCommand.execute();

        // 异步调用(该方法执行后,不会马上有远程的返回结果,将来会有结果)
        Future<String> future = myHystrixCommand.queue();
        // 阻塞的方法,等待5秒,直到拿到结果或者直到超时
        String msg = future.get(5000, TimeUnit.MILLISECONDS); // 等待时间

        return msg;
    }

    /**
     * 传参
     * @param uid
     * @param uname
     * @return
     */
    @RequestMapping("/test/{id}/{name}")
    public String pathParam(@PathVariable("id")String uid, @PathVariable("name")String uname) {
        System.out.println("---------------传参----------->"+uid+":"+uname);
        return "welcome";
    }

    /**
     * 传参
     * @param uage
     * @param uname
     * @return
     */
    @RequestMapping("/requestParams")
    public String requestParams(@RequestParam("uname")String uname, @RequestParam(value="uage",required = false,defaultValue = "12")Integer uage) {
        System.out.println("****************传参*******************"+uname+":"+uage);
        return "welcome";
    }

    /**
     * 熔断服务降级处理
     * @param id
     * @param throwable
     * @return
     */
    public UserDTO methodName(Long id, Throwable throwable){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("熔断");
        userDTO.setPhone("110");
        userDTO.setAge(0);

        String msg = String.valueOf(id).concat(":服务员下班了, 触发熔断...");
        System.out.println(msg);
        System.out.println(throwable.getMessage());
        return userDTO;
    }
}
