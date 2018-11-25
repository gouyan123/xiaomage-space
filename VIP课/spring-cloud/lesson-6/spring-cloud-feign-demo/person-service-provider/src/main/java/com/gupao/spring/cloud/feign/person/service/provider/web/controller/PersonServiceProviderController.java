package com.gupao.spring.cloud.feign.person.service.provider.web.controller;

import com.gupao.spring.cloud.feign.api.domain.Person;
import com.gupao.spring.cloud.feign.api.service.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link PersonService} 提供者控制器（可选实现{@link PersonService}）
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2017/11/5
 */
@RestController
public class PersonServiceProviderController {

    private final Map<Long, Person> persons = new ConcurrentHashMap<>();

    /**
     * 保存
     *
     * @param person {@link Person}
     * @return 如果成功，<code>true</code>
     */
    @PostMapping(value = "/person/save")
    public boolean savePerson(@RequestBody Person person) {
        return persons.put(person.getId(), person) == null;
    }

    /**
     * 查找所有的服务
     *
     * @return
     */
    @GetMapping(value = "/person/find/all")
    public Collection<Person> findAllPersons() {
        return persons.values();
    }


}
