package com.webmarket.integrations;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import webmarket.auth.UserDto;

@FeignClient(value = "userService", url = "${integrations.auth-service.url}")
public interface UserServiceIntegration {
    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/user/{userName}")
    UserDto findByName(@PathVariable("userName") String userName);
}
