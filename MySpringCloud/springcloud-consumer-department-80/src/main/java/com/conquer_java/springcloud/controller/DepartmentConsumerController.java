package com.conquer_java.springcloud.controller;

import com.conquer_java.springcloud.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 【RestTemplate】：
 * ===！！！===采用RESTful风格提供访问HTTP远程服务的便捷方法！===！！！===
 *
 * 1) 消费者无需并且不应引用Service服务，那么消费者如何使用服务呢？
 * 2) 可以通过RestTemplate类（Ctrl+Shift+Alt+N查找）发送RESTful风格的请求RequestURL
 * 3) xxxxTemplate提供API直接调用即可。若要使用，必须将其注册到Spring中（本身构造器上没有@Bean注解，只能手动注册到Spring的Bean容器中！）。
 * 4) RESTful调用形式——完全解耦：
 *   a) 无需Service，只需POJO/Entity实体类；
 *   b) 无需RPC远程引用reference；
 *   c) 消费者随便请求，提供者鉴别服务（消费者可以发起错误请求，服务方无法提供正常结果而已）；
 * public <T> T getForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException
 * 或者：public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException
 * 或者：public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables) throws RestClientException
 * 或者：public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException
 * 或者：public <T> T postForObject(String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException
 * 或者：public <T> T postForObject(String url, @Nullable Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException
 * 或者：public <T> ResponseEntity<T> postForEntity(String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException
 * 或者：public <T> ResponseEntity<T> postForEntity(String url, @Nullable Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException
 */
@RestController
public class DepartmentConsumerController {

    private static final String REST_URL_PREFIX = "http://www.dept-provider.com:8001";

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/consumer/dept/add")
    public boolean add(Department dept) {
        return restTemplate.postForObject(REST_URL_PREFIX + "/dept/add", dept, Boolean.class);
    }

    @RequestMapping("/consumer/dept/get/{id}")
    public Department get(@PathVariable("id") long id) {
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/get/" + id, Department.class);
    }

    @RequestMapping("/consumer/dept/list")
    public List<Department> list() {
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/list", List.class);
    }

    @RequestMapping("/consumer/dept/delete/{id}")
    public void delete(@PathVariable("id") long id) {
        restTemplate.delete(REST_URL_PREFIX + "/dept/delete/" + id);
    }
}
