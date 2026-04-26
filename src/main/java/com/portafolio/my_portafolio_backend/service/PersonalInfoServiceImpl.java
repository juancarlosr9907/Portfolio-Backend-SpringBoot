package com.portafolio.my_portafolio_backend.service;

import com.portafolio.my_portafolio_backend.exception.Validationexception;
import com.portafolio.my_portafolio_backend.model.PersonalInfo;
import com.portafolio.my_portafolio_backend.repository.IPersonalInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class PersonalInfoServiceImpl implements IPersonalInfoService{

    private final IPersonalInfoRepository personalInfoRepository;
    private final Validator validator;



    @Override
    @Transactional//le dice a spring que el metodo debe ser ejecutada como una sola unidad, si se completa sin erroes commit si no rollback
    public PersonalInfo save(PersonalInfo personalInfo) {
        BindingResult result = new BeanPropertyBindingResult(personalInfo, "personalInfo");
        validator.validate(personalInfo,result);
        if(result.hasErrors()){
            throw new Validationexception(result);
        }
        return personalInfoRepository.save(personalInfo);
    }

    @Override
    @Transactional(readOnly = true)//para operaciones que sean solo de lectura como los select, no modifican datos
    public Optional<PersonalInfo> findById(Long id) {
        return personalInfoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonalInfo> findAll() {
        return personalInfoRepository.findAll();
    }

    @Override
    @Transactional//transaccion,se pone en cada metodo que modifique en la base de datos, insert, update, delete...
    public void deleteById(Long id) {
        personalInfoRepository.deleteById(id);
    }
}
