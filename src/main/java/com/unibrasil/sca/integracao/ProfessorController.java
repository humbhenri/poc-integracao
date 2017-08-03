/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unibrasil.sca.integracao;

import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author humbhenri
 */
@RestController
@RequestMapping("/professores")
public class ProfessorController {

    private static final Logger logger = Logger.getLogger(ProfessorController.class.getSimpleName());

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Professor> getProfessores() {
        ProfessorWS professorWSPort = new ProfessorWS_Service().getProfessorWSPort();
        return professorWSPort.getProfessors();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Professor getProfessorById(@PathVariable Integer id) {
        ProfessorWS professorWSPort = new ProfessorWS_Service().getProfessorWSPort();
        return professorWSPort.getProfessorById(id);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> addProfessor(@Valid @RequestBody Professor professor) {
        try {
            UserWS userWSPort = new UserWS_Service().getUserWSPort();
            User user = userWSPort.getUser(professor.getUsername().getUsername());
            logger.log(Level.INFO, "User recebido: username: {0}, password: {1}, enabled: {2}",
                    new Object[]{
                        professor.getUsername().getUsername(),
                        professor.getUsername().getPassword(),
                        professor.getUsername().getEnabled()
                    });
            if (user == null) {
                userWSPort.createUser(professor.getUsername());
                logger.log(Level.INFO, "User criado");
            }
            ProfessorWS professorWSPort = new ProfessorWS_Service().getProfessorWSPort();
            Professor createdProfessor = professorWSPort.createProfessor(professor);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdProfessor.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (java.lang.Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
