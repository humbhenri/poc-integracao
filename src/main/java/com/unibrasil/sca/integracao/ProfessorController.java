/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibrasil.sca.integracao;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author humbhenri
 */
@RestController
@RequestMapping("/professores")
public class ProfessorController {
    
    private final Logger logger = LoggerFactory.getLogger(ProfessorController.class);

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public List<Professor> getProfessores() {
        ProfessorWS professorWSPort = new ProfessorWS_Service().getProfessorWSPort();
        return professorWSPort.getProfessors();
    }
}
