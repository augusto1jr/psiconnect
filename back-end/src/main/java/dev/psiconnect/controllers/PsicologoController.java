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
import org.springframework.http.ResponseEntity;
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<PsicologoResponseDTO> getPsicologoById(@PathVariable Long id) {
        return psicologoRepository.findById(id)
                .map(psicologo -> ResponseEntity.ok(new PsicologoResponseDTO(psicologo)))
                .orElse(ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletePsicologo(@PathVariable Long id) {
        if (!psicologoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        psicologoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updatePsicologo(@PathVariable Long id, @RequestBody PsicologoRequestDTO data) {
        return psicologoRepository.findById(id)
                .map(psicologo -> {
                    psicologo.setCrp(data.crp());
                    psicologo.setNome(data.nome());
                    psicologo.setEmail(data.email());
                    psicologo.setBio(data.bio());
                    psicologo.setFormacao(data.formacao());
                    psicologo.setContato(data.contato());
                    psicologo.setSenhaHash(data.senhaHash());
                    psicologo.setValorConsulta(data.valorConsulta());
                    psicologo.setAceitaBeneficio(data.aceitaBeneficio());
                    psicologo.setModalidadeAtendimento(data.modalidadeAtendimento());

                    if (psicologo.getEndereco() != null) {
                        EnderecoPsicologo endereco = psicologo.getEndereco();
                        EnderecoPsiRequestDTO novoEndereco = data.endereco();

                        endereco.setRua(novoEndereco.rua());
                        endereco.setNumero(novoEndereco.numero());
                        endereco.setComplemento(novoEndereco.complemento());
                        endereco.setBairro(novoEndereco.bairro());
                        endereco.setCidade(novoEndereco.cidade());
                        endereco.setEstado(novoEndereco.estado());
                        endereco.setCep(novoEndereco.cep());
                        endereco.setLatitude(novoEndereco.latitude());
                        endereco.setLongitude(novoEndereco.longitude());
                    }

                    psicologo.setEspecialidades(especialidadeRepository.findAllById(data.especialidades()));
                    psicologo.setAbordagens(abordagemRepository.findAllById(data.abordagens()));

                    psicologoRepository.save(psicologo);
                    return ResponseEntity.ok(new PsicologoResponseDTO(psicologo));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}