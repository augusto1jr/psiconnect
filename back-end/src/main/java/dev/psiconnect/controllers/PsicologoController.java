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

import java.util.*;
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

    /* ENDPOINT CADASTRO SIMPLIFICADO */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity<?> savePsicologo(@RequestBody PsicologoRequestDTO data) {
        Psicologo psicologo = new Psicologo();
        psicologo.setEmail(data.email());
        psicologo.setSenha(data.senha());

        // Valores genéricos obrigatórios
        psicologo.setNome("Psicólogo Teste");
        psicologo.setCrp("00/00000-" + (new Random().nextInt(900) + 100)); // gera um CRP aleatório básico
        psicologo.setFoto("https://i.pravatar.cc/150?u=" + UUID.randomUUID());
        psicologo.setBio("Teste");
        psicologo.setContato("000000000");
        psicologo.setValorConsulta(100.0);
        psicologo.setAceitaBeneficio(false);
        psicologo.setModalidadeAtendimento(Psicologo.ModalidadeAtendimento.REMOTO); // Enum padrão

        psicologoRepository.save(psicologo);

        // Retorna o JSON
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Cadastro realizado com sucesso!");
        resposta.put("id", psicologo.getId());
        resposta.put("nome", psicologo.getNome());

        return ResponseEntity.ok(resposta);
    }

    /* ENDPOINT CADASTRO COMPLETO
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity<?> savePsicologo(@RequestBody PsicologoRequestDTO data) {
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

        // Resposta padrão
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Cadastro realizado com sucesso!");
        resposta.put("id", psicologo.getId());
        resposta.put("nome", psicologo.getNome());

        return ResponseEntity.ok(resposta);
    }
    */

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<?> loginPsicologo(@RequestBody Psicologo loginData) {
        Psicologo psicologo = psicologoRepository.findByEmail(loginData.getEmail());

        if (psicologo != null && psicologo.getSenha().equals(loginData.getSenha())) {
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("mensagem", "Login realizado com sucesso!");
            resposta.put("id", psicologo.getId());
            resposta.put("nome", psicologo.getNome());

            return ResponseEntity.ok(resposta);
        } else {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", "E-mail ou senha inválidos.");
            return ResponseEntity.status(401).body(erro);
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