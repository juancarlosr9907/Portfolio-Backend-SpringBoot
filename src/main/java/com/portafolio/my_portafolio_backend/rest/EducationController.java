package com.portafolio.my_portafolio_backend.rest;


import com.portafolio.my_portafolio_backend.model.Education;
import com.portafolio.my_portafolio_backend.service.IEducationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/education")
public class EducationController {
    private final IEducationService educationService;

    public EducationController(IEducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping
    public List<Education> getAllEducation(){
        return educationService.findAll();
    }

    @GetMapping("/{id}")
    public Education getEducationById(@PathVariable Long id){
        Optional<Education>info=educationService.findById(id);
        if(info.isPresent()){
            return info.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Informacion no disponible en el ID "+ id);
        }
    }
    @PostMapping
    public Education save(@RequestBody Education education){
        return educationService.save(education);
    }
    @PutMapping("/{id}")
    public Education update(@PathVariable Long id, @RequestBody Education education){
        education.setId(id);//se setea para asegurarnos que es el id correcto y evitar problemas como un Null o id incorrecto
        return educationService.save(education);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        educationService.deleteById(id);
    }
}
