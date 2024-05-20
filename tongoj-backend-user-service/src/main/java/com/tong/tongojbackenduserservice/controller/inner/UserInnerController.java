package com.tong.tongojbackenduserservice.controller.inner;

import com.tong.tongojbackendmodel.model.entity.User;
import com.tong.tongojbackendserviceclient.service.UserFeignClient;
import com.tong.tongojbackenduserservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@RestController()
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {

    @Resource
    private UserService userService;

    @Override
    @GetMapping("/get/id")
    public User getById(@RequestParam("userId") long userId) {
        return userService.getById(userId);
    }

    @Override
    @GetMapping("/get/ids")
    public List<User> listByIds(@RequestParam("userIds") Collection<Long> ids) {
        return userService.listByIds(ids);
    }

}
