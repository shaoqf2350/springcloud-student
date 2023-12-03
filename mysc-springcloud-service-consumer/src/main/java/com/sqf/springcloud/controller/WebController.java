package com.sqf.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @HystrixCommand(fallbackMethod = "error")
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String hello (String name){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);// APPLICATION_JSON // APPLICATION_FORM_URLENCODED

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
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
    public UserDTO user (@PathVariable("id") Long id){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = "http://".concat(serviceName).concat("/service/user");

        if (null == id) {
            return null;
        }

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

    @RequestMapping("/web/order")
    public String order (String name){

        // 业务处理;

        // 请求 URL
        String url = "";

        // 调用远程服务提供者的服务
        HttpEntity<String> request = new HttpEntity(name);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        // 获取响应结果
        HttpStatus statusCode = response.getStatusCode();
        HttpHeaders headers = response.getHeaders();
        String body = response.getBody();

        System.out.println("statusCode:".concat(statusCode.toString()));
        System.out.println("headers".concat(headers.toString()));

        return body;
    }

    public String error(String name){ return name.concat(":老板跑路了, 服务员离职了, 触发熔断保护..."); }
}
