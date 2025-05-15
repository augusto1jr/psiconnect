package dev.psiconnect.controllers;

import dev.psiconnect.dtos.requests.PsicologoRequestDTO;
import dev.psiconnect.dtos.responses.PsicologoResponseDTO;
import dev.psiconnect.dtos.requests.EnderecoPsiRequestDTO;
import dev.psiconnect.dtos.responses.ConsultaResponseDTO;
import dev.psiconnect.dtos.responses.AvaliacaoResponseDTO;

import dev.psiconnect.entities.*;
import dev.psiconnect.repositories.*;

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

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/cadastro")
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
    @PostMapping("/login")
    public ResponseEntity<String> loginPsicologo(@RequestBody Psicologo loginData) {
        Psicologo psicologo = psicologoRepository.findByEmail(loginData.getEmail());

        if (psicologo != null && psicologo.getSenha().equals(loginData.getSenha())) {
            return ResponseEntity.ok("Login realizado com sucesso!");
        } else {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos.");
        }
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
    @GetMapping("/recomendados")
    public ResponseEntity<List<PsicologoResponseDTO>> getPsicologosRecomendados() {
        List<Psicologo> recomendados = psicologoRepository.findTopRecomendados();
        List<PsicologoResponseDTO> response = recomendados.stream()
                .map(PsicologoResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
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
                    psicologo.setSenha(data.senha());
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}/consultas")
    public ResponseEntity<List<ConsultaResponseDTO>> getConsultasByPsicologo(
            @PathVariable Long id,
            @RequestParam(required = false) Consulta.Status status
    ) {
        List<Consulta> consultas;

        if (status == null) {
            consultas = consultaRepository.findByPsicologoId(id);

        } else {
            consultas = consultaRepository.findByPsicologoIdAndStatus(id, status);
        }

        List<ConsultaResponseDTO> response = consultas.stream()
                .map(ConsultaResponseDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}/avaliacoes")
    public ResponseEntity<List<AvaliacaoResponseDTO>> getAvaliacoesByPsicologo(@PathVariable Long id) {
        if (!psicologoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        List<Avaliacao> avaliacoes = avaliacaoRepository.findByPsicologoId(id);
        List<AvaliacaoResponseDTO> response = avaliacoes.stream()
                .map(AvaliacaoResponseDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }
}