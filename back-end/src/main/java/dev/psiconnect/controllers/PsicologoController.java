package dev.psiconnect.controllers;

import dev.psiconnect.dtos.requests.PsicologoRequestDTO;
import dev.psiconnect.dtos.responses.PsicologoResponseDTO;
import dev.psiconnect.dtos.requests.EnderecoPsiRequestDTO;
import dev.psiconnect.dtos.responses.ConsultaResponseDTO;
import dev.psiconnect.dtos.responses.AvaliacaoResponseDTO;

import dev.psiconnect.entities.*;
import dev.psiconnect.repositories.*;

import dev.psiconnect.services.PsicologoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Controlador responsável pelos endpoints relacionados aos psicólogos.
 */
@RestController
@RequestMapping("psicologos")
public class PsicologoController {

    @Autowired
    private PsicologoService psicologoService;

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

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    /**
     * Cadastra um psicólogo com dados genéricos para teste.
     *
     * @param data DTO com e-mail e senha.
     * @return ResponseEntity com mensagem e ID do psicólogo.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity<?> savePsicologo(@RequestBody PsicologoRequestDTO data) {
        Psicologo psicologo = new Psicologo();
        psicologo.setEmail(data.email());

        // Criptografa a senha
        psicologo.setSenha(passwordEncoder.encode(data.senha()));

        psicologo.setNome("Psicólogo Teste");
        psicologo.setCrp("00/00000-" + (new Random().nextInt(900) + 100));
        psicologo.setFoto("https://i.pravatar.cc/150?u=" + UUID.randomUUID());
        psicologo.setBio("Teste");
        psicologo.setContato("000000000");
        psicologo.setValorConsulta(100.0);
        psicologo.setAceitaBeneficio(false);
        psicologo.setModalidadeAtendimento(Psicologo.ModalidadeAtendimento.REMOTO);

        psicologoService.salvar(psicologo);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Cadastro realizado com sucesso!");
        resposta.put("id", psicologo.getId());
        resposta.put("nome", psicologo.getNome());

        return ResponseEntity.ok(resposta);
    }

    /**
     * Realiza login de um psicólogo.
     *
     * @param loginData Objeto com e-mail e senha.
     * @return ResponseEntity com dados ou erro 401.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<?> loginPsicologo(@RequestBody Psicologo loginData) {
        Psicologo psicologo = psicologoService.buscarPorEmail(loginData.getEmail());

        if (psicologo == null) {
            return ResponseEntity.status(401).body(Map.of("mensagem", "E-mail ou senha inválidos."));
        }

        System.out.println("Senha recebida: " + loginData.getSenha());
        System.out.println("Hash salvo no banco: " + psicologo.getSenha());

        if (!passwordEncoder.matches(loginData.getSenha(), psicologo.getSenha())) {
            return ResponseEntity.status(401).body(Map.of("mensagem", "E-mail ou senha inválidos."));
        }

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Login realizado com sucesso!");
        resposta.put("id", psicologo.getId());
        resposta.put("nome", psicologo.getNome());

        return ResponseEntity.ok(resposta);
    }


    /**
     * Retorna todos os psicólogos cadastrados.
     *
     * @return ResponseEntity com lista de PsicologoResponseDTO.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<PsicologoResponseDTO>> getAllPsicologos() {
        List<PsicologoResponseDTO> lista = psicologoService.buscarTodos()
                .stream()
                .map(PsicologoResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    /**
     * Retorna um psicólogo por ID.
     *
     * @param id ID do psicólogo.
     * @return ResponseEntity com PsicologoResponseDTO ou 404.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<PsicologoResponseDTO> getPsicologoById(@PathVariable Long id) {
        return psicologoService.buscarPorId(id)
                .map(psicologo -> ResponseEntity.ok(new PsicologoResponseDTO(psicologo)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove um psicólogo pelo ID.
     *
     * @param id ID do psicólogo.
     * @return ResponseEntity 204 ou 404.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletePsicologo(@PathVariable Long id) {
        if (!psicologoService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }

        psicologoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza os dados de um psicólogo.
     *
     * @param id   ID do psicólogo.
     * @param data DTO com os dados atualizados.
     * @return ResponseEntity com PsicologoResponseDTO atualizado ou 404.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updatePsicologo(@PathVariable Long id, @RequestBody PsicologoRequestDTO data) {
        return psicologoService.buscarPorId(id)
                .map(psicologo -> {
                    psicologo.setCrp(data.crp());
                    psicologo.setNome(data.nome());
                    psicologo.setEmail(data.email());
                    psicologo.setBio(data.bio());
                    psicologo.setFormacao(data.formacao());
                    psicologo.setContato(data.contato());
                    psicologo.setSenha(passwordEncoder.encode(data.senha()));
                    psicologo.setValorConsulta(data.valorConsulta());
                    psicologo.setAceitaBeneficio(data.aceitaBeneficio());
                    psicologo.setModalidadeAtendimento(data.modalidadeAtendimento());

                    if (psicologo.getEndereco() != null && data.endereco() != null) {
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

                    psicologoService.salvar(psicologo);
                    return ResponseEntity.ok(new PsicologoResponseDTO(psicologo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retorna as consultas de um psicólogo, filtrando opcionalmente por status.
     *
     * @param id     ID do psicólogo.
     * @param status (Opcional) Status da consulta.
     * @return ResponseEntity com lista de ConsultaResponseDTO.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}/consultas")
    public ResponseEntity<List<ConsultaResponseDTO>> getConsultasByPsicologo(
            @PathVariable Long id,
            @RequestParam(required = false) Consulta.Status status
    ) {
        List<Consulta> consultas = (status == null)
                ? consultaRepository.findByPsicologoId(id)
                : consultaRepository.findByPsicologoIdAndStatus(id, status);

        List<ConsultaResponseDTO> response = consultas.stream()
                .map(ConsultaResponseDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    /**
     * Retorna as avaliações de um psicólogo.
     *
     * @param id ID do psicólogo.
     * @return ResponseEntity com lista de AvaliacaoResponseDTO ou 404.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}/avaliacoes")
    public ResponseEntity<List<AvaliacaoResponseDTO>> getAvaliacoesByPsicologo(@PathVariable Long id) {
        if (!psicologoService.existePorId(id)) {
            return ResponseEntity.notFound().build();
        }

        List<Avaliacao> avaliacoes = avaliacaoRepository.findByPsicologoId(id);
        List<AvaliacaoResponseDTO> response = avaliacoes.stream()
                .map(AvaliacaoResponseDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }
}
