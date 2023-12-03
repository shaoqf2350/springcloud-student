package com.sqf.springcloud.hystrix;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * @Description: 自定义Hystrix请求的服务异常熔断处理
 * @Author: shaoqingfeng
 * @CREATE: 2023/12/3 20:41
 */
public class MyHystrixCommand extends HystrixCommand<String> {

    private RestTemplate restTemplate;

    private static final String serviceName = "MYSC-SPRINGCLOUD-SERVICE-PROVIDER";

    public MyHystrixCommand(Setter setter, RestTemplate restTemplate){
        super(setter);
        this.restTemplate = restTemplate;
    }

    @Override
    protected String run() throws Exception{
        // 调用远程服务
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "http://".concat(serviceName).concat("/service/hello");

        HttpEntity<String> request = new HttpEntity<>("孙悟空", headers);

        return restTemplate.exchange(url, HttpMethod.POST, request, String.class).getBody();
    }

    /**
     * 当远程服务超时,异常不可用等情况时,会触发该熔断降级方法
     * @return
     */
    @Override
    public String getFallback(){

        return "自定义熔断处理: 服务员下班了,请明天再来......";
    }
}
