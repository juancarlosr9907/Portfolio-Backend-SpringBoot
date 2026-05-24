package com.portafolio.my_portafolio_backend.service;


import com.portafolio.my_portafolio_backend.exception.Validationexception;
import com.portafolio.my_portafolio_backend.model.Skill;
import com.portafolio.my_portafolio_backend.repository.ISkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)//extiende de mockito, es un framework que simila el comportamiento del repositorio, en vez de usar el repositorio usa un obejto falso
                                    //simula ya actua identico, se usan para testear la logica de negocio y nada mas
public class SkillServiceImplTest {

    @Mock
    private ISkillRepository skillRepository;

    @Mock
    private Validator validator;

    @InjectMocks//esto inyecta o crea una instancia de la clase servicio e implemente el MOCK del repositorio que esta arriba
    private SkillServiceImpl skillService;

    @Test
    void testFindAllReturnsListOfSkills(){
        //Arrange
        List<Skill> mockSkills = Arrays.asList(new Skill(), new Skill());
        when(skillRepository.finAll()).thenReturn(mockSkills);//aca le decimos que el finAll no lo busque en la base de datos si no en la lista que creamos arriba
        //Accion
        List<Skill>skills=skillService.findAll();
        //Assert
        assertNotNull(skills);
        assertEquals(2, skills.size());//primero mira la cantidad de elementos, en este caso 2
        verify(skillRepository,times(1)).finAll();//verificacion de mockito que dice que este repositorio fue llamado una vez
    }

    @Test
    void testFindByIdReturnsSkillWhenFound(){
        Long id = 1L;
        Skill skillMock= new Skill();
        when(skillRepository.findById(id)).thenReturn(Optional.of(skillMock));

        Optional<Skill>skillOptional = skillService.findById(id);

        assertTrue(skillOptional.isPresent());
        assertEquals(skillMock, skillOptional.get());
        verify(skillRepository,times(1)).findById(id);

    }


    @Test
    void testSaveSkillThrowsExceptionWhenInvalid(){
        Skill invalidSkill= new Skill();
        doAnswer(invocationOnMock -> {//expresion lamda que simula un error
            BindingResult result = invocationOnMock.getArgument(1);
            result.rejectValue("name", "NoyBlanck","El nombre no puede estar vacio");
            return null;//esto es por que el doAnswer necesita retornal algo si no da error, entonces se retorna un null
        }).when(validator).validate(any(Skill.class),any(BindingResult.class));

        assertThrows(Validationexception.class, () -> skillService.save(invalidSkill),
                "Debe lanzarse uyna ValidationException si el objeto no es valido");

        verify(skillRepository,never()).save(any(Skill.class));
    }

    @Test
    void testSaveSkillSavesValidSkill(){
        //preparacion
        Skill validSkill = new Skill(null,"Java",90,"fab fa-java",1L);//similamos objeto creado correctamente
        when(skillRepository.save(any(Skill.class))).thenReturn(validSkill);//simulamos exito a la base de datos
        doNothing().when(validator).validate(any(Skill.class),any(BindingResult.class));//aca indicamos que cuando el servicio llame no haga nada para que la prueba siga sin errores

        //accion
        Skill savedSkill= skillService.save(validSkill);

        //verificacion
        assertNotNull(savedSkill);
        verify(skillRepository,times(1)).save(validSkill);

    }





}
