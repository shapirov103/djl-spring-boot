package com.aws.samples.djldemoweb.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Index controller.
 */
@Controller
class IndexController {

    @RequestMapping("/")
    fun index(): String {
        return "index"
    }
}