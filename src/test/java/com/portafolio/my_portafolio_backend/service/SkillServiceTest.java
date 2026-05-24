package com.portafolio.my_portafolio_backend.service;


import com.portafolio.my_portafolio_backend.exception.Validationexception;
import com.portafolio.my_portafolio_backend.model.Skill;
import com.portafolio.my_portafolio_backend.repository.ISkillRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)//Esto es para que cada vez que se realice un test vuelva la BDD a su estado original,
                                                                             //evita conflicto entre pruebas
public class SkillServiceTest {

    @Autowired//Los test ya no son de Spring sino de SpringBootTest asi que necesita constructor vacio y los inyecta con el Autowired
    private ISkillService skillService;
    @Autowired
    private ISkillRepository skillRepository;


    @Test
    void testSaveValidSkill(){
        Skill validSkill= new Skill (null, "Java",90,"fab fa-java",1L);
        Skill saveSkill = skillService.save(validSkill);

        assertNotNull(saveSkill.getId(),"El objeto guardado debe tener un ID asignado");// se mira a nivel de servicio
        assertNotNull(skillRepository // se mira a nivel de base de datos, por que puede o no estarlo
                .findById(saveSkill.getId())
                .orElse(null),"El objeto guardado debe existir en la base de datos");

    }

    @Test
    void testSavedInvalidSkill(){
        Skill inValidSkill= new Skill (null, "",90,"fab fa-java",1L);
        assertThrows(Validationexception.class,()-> skillService.save(inValidSkill),
                "Se lanza una validationException cuando el nombre esta vacio");
    }










}
