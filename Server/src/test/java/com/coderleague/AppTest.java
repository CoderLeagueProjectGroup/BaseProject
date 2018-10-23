package com.coderleague;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coderleague.module.user.entity.User;
import com.coderleague.module.user.service.IUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = App.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class AppTest 
{
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IUserService userService;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void listUser(){
        List<User> userList = userService.list(new QueryWrapper<>());
        System.out.println(userList);
    }

    @Test
    public void testException(){
        restTemplate.exchange("/user/user/test", HttpMethod.GET,null,String.class);
    }

    @Test
    public void getUser(){
        User user = userService.getUser(1);
        System.out.println(user);
    }

    @Test
    public void addUser(){
        String salt = RandomStringUtils.randomAscii(20);
        System.out.println("盐："+salt);
        userService.save(new User().setUsername("admin").setDepartmentId(1).setRoleId(1)
            .setPassword(userService.encrypt("123456",salt)).setSalt(salt)
            .setCreateId(1).setCreateTime(LocalDateTime.now())
            .setModifyId(1).setModifyTime(LocalDateTime.now()));
    }
}
