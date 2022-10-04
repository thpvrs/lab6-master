package com.example.lab6.controller;

import com.example.lab6.pojo.Wizard;
import com.example.lab6.pojo.Wizards;
import com.example.lab6.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class
WizardController {
    @Autowired
    private WizardService wizardService;

    @RequestMapping(value = "/wizards", method = RequestMethod.GET)
    public ResponseEntity<?> getWizards(){
        Wizards w = new Wizards(wizardService.getWizards());
//        List<Wizard> w = wizardService.getWizards();
        return ResponseEntity.ok(w);
    }
    @RequestMapping(value = "/addWizard", method = RequestMethod.POST)
    public ResponseEntity<?> addWizard(@RequestBody Wizard w){
        Wizard add = wizardService.addWizard(w);
        return ResponseEntity.ok(add);
    }
    @RequestMapping(value = "/updateWizard", method = RequestMethod.POST)
    public ResponseEntity<?> updateWizard(@RequestBody Wizard w){
        Wizard update = wizardService.updateWizard(w);
        return ResponseEntity.ok(update);
    }
    @RequestMapping(value = "/deleteWizard", method = RequestMethod.POST)
    public ResponseEntity<?> deleteWizard(@RequestBody String  id){
        Wizard w = wizardService.getById(id);
        boolean check = wizardService.deleteWizard(w);
        return ResponseEntity.ok(check);
    }
}


