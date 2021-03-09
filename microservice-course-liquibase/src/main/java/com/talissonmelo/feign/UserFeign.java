package com.talissonmelo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("user-service")
public interface UserFeign {

    @PostMapping(value = "/users/names")
    List<String> getUserNames(@RequestBody List<Long> usersIdList);
}
