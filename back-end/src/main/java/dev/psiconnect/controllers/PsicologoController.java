package dev.psiconnect.controllers;

import dev.psiconnect.dtos.requests.PsicologoRequestDTO;
import dev.psiconnect.dtos.requests.EnderecoPsiRequestDTO;
import dev.psiconnect.dtos.responses.PsicologoResponseDTO;
import dev.psiconnect.entities.Abordagem;
import dev.psiconnect.entities.EnderecoPsicologo;
import dev.psiconnect.entities.Psicologo;
import dev.psiconnect.entities.Especialidade;
import dev.psiconnect.repositories.EnderecoPsiRepository;
import dev.psiconnect.repositories.PsicologoRepository;
import dev.psiconnect.repositories.EspecialidadeRepository;
import dev.psiconnect.repositories.AbordagemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private AbordagemRepository abordagemRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    @Transactional
    public void savePsicologo(@RequestBody PsicologoRequestDTO data) {
        List<Especialidade> especialidades = especialidadeRepository.findAllById(data.especialidades());
        List<Abordagem> abordagens = abordagemRepository.findAllById(data.abordagens());

        Psicologo psicologo = new Psicologo(data, especialidades, abordagens);

        if (data.endereco() != null) {
            EnderecoPsiRequestDTO enderecoData = data.endereco();
            EnderecoPsicologo endereco = new EnderecoPsicologo(enderecoData);
            endereco.setPsicologo(psicologo);
            psicologo.setEndereco(endereco);
        }

        psicologoRepository.save(psicologo);
    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<PsicologoResponseDTO> getAllPsicologos() {
        return psicologoRepository.findAll().stream()
                .map(PsicologoResponseDTO::new)
                .collect(Collectors.toList());
    }
}
