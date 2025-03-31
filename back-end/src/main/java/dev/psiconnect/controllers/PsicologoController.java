package dev.psiconnect.controllers;

import dev.psiconnect.dtos.requests.PsicologoRequestDTO;
import dev.psiconnect.dtos.requests.EnderecoPsiRequestDTO;
import dev.psiconnect.dtos.responses.PsicologoResponseDTO;
import dev.psiconnect.entities.EnderecoPsicologo;
import dev.psiconnect.entities.Psicologo;
import dev.psiconnect.repositories.EnderecoPsiRepository;
import dev.psiconnect.repositories.PsicologoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("psicologos")
public class PsicologoController {

    @Autowired
    private PsicologoRepository psicologoRepository;

    @Autowired
    private EnderecoPsiRepository enderecoPsiRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void savePsicologo(@RequestBody PsicologoRequestDTO data) {
        Psicologo psicologo = new Psicologo(data);
        psicologo = psicologoRepository.save(psicologo);

        if (data.endereco() != null) {
            EnderecoPsiRequestDTO enderecoData = data.endereco();
            EnderecoPsicologo endereco = new EnderecoPsicologo(enderecoData);
            endereco.setPsicologo(psicologo);
            enderecoPsiRepository.save(endereco);
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<PsicologoResponseDTO> getAllPsicologos() {
        return psicologoRepository.findAll().stream()
                .map(PsicologoResponseDTO::new)
                .collect(Collectors.toList());
    }
}
