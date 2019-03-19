package com.nicklaus.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nicklaus.db.wrapper.DBPoolFacade;

/**
 * crud controller
 *
 * @author weishibai
 * @date 2019/03/19 4:12 PM
 */
@RestController
public class CRUDController {

    @Autowired
    private DBPoolFacade poolFacade;

    @RequestMapping("/query/{id}")
    public void queryById(@PathVariable long id) {
        final NamedParameterJdbcTemplate reader = poolFacade.reader();
        final Long userId = reader.queryForObject("select id from user where id=:userId"
                , new MapSqlParameterSource("userId", id), Long.class);
        System.out.println(userId);
    }
}
