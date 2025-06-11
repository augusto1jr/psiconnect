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

/**
 * Controlador responsável pelos endpoints relacionados aos psicólogos.
 */
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
        psicologo.setSenha(data.senha());

        psicologo.setNome("Psicólogo Teste");
        psicologo.setCrp("00/00000-" + (new Random().nextInt(900) + 100));
        psicologo.setFoto("https://i.pravatar.cc/150?u=" + UUID.randomUUID());
        psicologo.setBio("Teste");
        psicologo.setContato("000000000");
        psicologo.setValorConsulta(100.0);
        psicologo.setAceitaBeneficio(false);
        psicologo.setModalidadeAtendimento(Psicologo.ModalidadeAtendimento.REMOTO);

        psicologoRepository.save(psicologo);

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

    /**
     * Retorna todos os psicólogos cadastrados.
     *
     * @return ResponseEntity com lista de PsicologoResponseDTO.
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<PsicologoResponseDTO>> getAllPsicologos() {
        List<PsicologoResponseDTO> lista = psicologoRepository.findAll().stream()
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
        return psicologoRepository.findById(id)
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
        if (!psicologoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        psicologoRepository.deleteById(id);
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

                    psicologoRepository.save(psicologo);
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
