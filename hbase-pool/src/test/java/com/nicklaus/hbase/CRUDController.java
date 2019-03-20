package com.nicklaus.hbase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nicklaus.hbase.wrapper.HBaseClientFacade;

/**
 * crud controller
 *
 * @author weishibai
 * @date 2019/03/19 4:12 PM
 */
@RestController
public class CRUDController {

    @Autowired
    private HBaseClientFacade hBaseClientFacade;

    @RequestMapping("/test/hbase")
    public void queryById() {
        System.out.println(hBaseClientFacade.connection());
    }
}
