package com.portafolio.my_portafolio_backend.controller;

import com.portafolio.my_portafolio_backend.service.IEducationService;
import com.portafolio.my_portafolio_backend.service.IExperienceService;
import com.portafolio.my_portafolio_backend.service.IPersonalInfoService;
import com.portafolio.my_portafolio_backend.service.ISkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequiredArgsConstructor
public class indexController {
    private final IPersonalInfoService personalInfoService;
    private final ISkillService skillService;
    private final IEducationService educationService;
    private final IExperienceService experienceService;

    @GetMapping("/")
    public String showIndex(Model model){//MODEL es una clase muy útil, se añade atributos, le pone un nombre, trae informacion y enviarla a la vista
        System.out.println("Mostrando la página de inicio");
        model.addAttribute("personalInfo",personalInfoService.findAll().getFirst());//me encuentre el primero
        model.addAttribute("skillList",skillService.findAll());
        model.addAttribute("educationList",educationService.findAll());
        model.addAttribute("experienceList",experienceService.findAll());

        return "index";
    }
}
