package com.aws.samples.djlspringboot;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@TODO delete this
@Controller
public class ObjectDetectionController {

    @GetMapping("/object-detection")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "object-detection";
    }

}
