package dev.psiconnect.controllers;

import dev.psiconnect.dtos.requests.PsicologoRequestDTO;
import dev.psiconnect.dtos.responses.PsicologoResponseDTO;
import dev.psiconnect.entities.Psicologo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import dev.psiconnect.repositories.PsicologoRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("psicologos")
public class PsicologoController {

    @Autowired
    private PsicologoRepository psicologoRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void savePsicologo(@RequestBody PsicologoRequestDTO data){
        Psicologo psicologoData = new Psicologo(data);
        psicologoRepository.save(psicologoData);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<PsicologoResponseDTO> getAllPsicologos() {

        List<PsicologoResponseDTO> psicologoList = psicologoRepository.findAll().stream()
                .map(PsicologoResponseDTO::new).collect(Collectors.toList());

        return psicologoList;
    }
}
