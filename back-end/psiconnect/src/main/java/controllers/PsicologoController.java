package controllers;

import entities.Psicologo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repositories.PsicologoRepository;

import java.util.List;

@RestController
@RequestMapping("psicologos")
public class PsicologoController {

    @Autowired
    private PsicologoRepository psicologoRepository;

    @GetMapping
    public List getAllPsicologos() {

        List<Psicologo> psicologoList = psicologoRepository.findAll();
        return psicologoList;
    }
}
