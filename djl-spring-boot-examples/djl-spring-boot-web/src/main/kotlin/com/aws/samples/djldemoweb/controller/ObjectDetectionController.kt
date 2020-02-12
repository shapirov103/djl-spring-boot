package com.aws.samples.djldemoweb.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam


//@TODO delete this
@Controller
class ObjectDetectionController {

    @GetMapping("/object-detection")
    fun greeting(@RequestParam(name = "name", required = false, defaultValue = "World") name: String?, model: Model): String? {
        model.addAttribute("name", name)
        return "object-detection"
    }

    @RequestMapping("/inbox")
    fun listFiles(model: Model): String {
        model.addAttribute("files", mutableListOf("dog_car_bike.jpg", "some_other.jpg"))
        return "object-detection"
    }
}
