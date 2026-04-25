package com.portafolio.my_portafolio_backend.rest;

import com.portafolio.my_portafolio_backend.model.PersonalInfo;
import com.portafolio.my_portafolio_backend.service.IPersonalInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/test-personal-info")//es la ruta principal de navegacion, y los controller de aqui abajo iran con una barra, ejem=> /all

public class PersonalInfoTestController {

    private final IPersonalInfoService personalInfoService;

    public PersonalInfoTestController(IPersonalInfoService personalInfoService) {
        this.personalInfoService = personalInfoService;
    }

    @GetMapping("/all")
    public List<PersonalInfo> getAllPersonalInfo(){
        return personalInfoService.findAll();
    }

    @GetMapping("/{id}")//va en corchetes por que lo pide para ejecutar el mapping
    public PersonalInfo getPersonalInfoById(@PathVariable Long id){//hay que indicar con el @PathVariable para que sepa que es una variable en el path
        Optional<PersonalInfo>info= personalInfoService.findById(id);
        if(info.isPresent()){
            return info.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Información personal no disponible en el ID: "+id);
        }
    }


    @PostMapping
    public ResponseEntity<PersonalInfo>createPersonalInfo(@RequestBody PersonalInfo personalInfo){//con esto se envia al cuerpo de la peticion
        PersonalInfo newPersonalInfo=personalInfoService.save(personalInfo);
        return new ResponseEntity<>(newPersonalInfo,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public PersonalInfo update(@PathVariable Long id, @RequestBody PersonalInfo personalInfo){//acordarnos del path variable
        personalInfo.setId(id);
        return personalInfoService.save(personalInfo);
    }

    @DeleteMapping("/{id}")
    public void deleteBy(@PathVariable Long id){//acordarnos del path variable
        personalInfoService.deleteById(id);
    }



}
