package com.example.lab6.repository;

import com.example.lab6.pojo.Wizard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WizardService {
    @Autowired
    private WizardRepository repository;

    public WizardService(WizardRepository repository) {
        this.repository = repository;
    }
    public List<Wizard> getWizards(){
        return repository.findAll();
    }
    public Wizard addWizard(Wizard w){
        return repository.insert(w);
    }
    public Wizard updateWizard(Wizard w){
        return repository.save(w);
    }
    public boolean deleteWizard(Wizard w){
        try{
            repository.delete(w);
            return true;
        } catch(Exception e){
            return false;
        }
    }
    public Wizard getById(String id){
        return repository.getWizardById(id);
    }
}
