package dev.psiconnect.controllers;

import dev.psiconnect.dtos.requests.EnderecoPacRequestDTO;
import dev.psiconnect.dtos.requests.PacienteRequestDTO;
import dev.psiconnect.dtos.responses.PacienteResponseDTO;
import dev.psiconnect.dtos.responses.PsicologoResponseDTO;
import dev.psiconnect.entities.*;
import dev.psiconnect.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Controlador responsável pelos endpoints relacionados aos pacientes.
 */
@RestController
@RequestMapping("pacientes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EnderecoPacRepository enderecoPacRepository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private AbordagemRepository abordagemRepository;

    @Autowired
    private PsicologoRepository psicologoRepository;

    /**
     * Cadastro simplificado de paciente (apenas e-mail e senha, demais dados são gerados).
     */
    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity<Map<String, Object>> savePaciente(@RequestBody PacienteRequestDTO data) {
        Paciente paciente = new Paciente();
        paciente.setEmail(data.email());
        paciente.setSenha(data.senha());

        // Dados gerados automaticamente
        paciente.setNome("Paciente Teste");
        paciente.setCpf("000.000.000-" + (new Random().nextInt(900) + 100));
        paciente.setFoto("https://i.pravatar.cc/150?u=" + UUID.randomUUID());
        paciente.setBio("Teste");
        paciente.setContato("000000000");
        paciente.setBeneficioSocial(Paciente.BeneficioSocial.NENHUM);

        pacienteRepository.save(paciente);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Cadastro realizado com sucesso!");
        resposta.put("id", paciente.getId());
        resposta.put("nome", paciente.getNome());

        return ResponseEntity.ok(resposta);
    }

        /* ENDPOINT CADASTRO COMPLETO
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity<?> savePaciente(@RequestBody PacienteRequestDTO data) {
        List<Especialidade> prefEspecialidades = especialidadeRepository.findAllById(data.prefEspecialidades());
        List<Abordagem> prefAbordagens = abordagemRepository.findAllById(data.prefAbordagens());

        Paciente paciente = new Paciente(data, prefEspecialidades, prefAbordagens);

        if (data.endereco() != null) {
            EnderecoPacRequestDTO enderecoData = data.endereco();
            EnderecoPaciente endereco = new EnderecoPaciente(enderecoData);
            endereco.setPaciente(paciente);
            paciente.setEndereco(endereco);
        }

        pacienteRepository.save(paciente);

        // Retorna o JSON
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("mensagem", "Cadastro realizado com sucesso!");
        resposta.put("id", paciente.getId());
        resposta.put("nome", paciente.getNome());

        return ResponseEntity.ok(resposta);
    }
    */

    /**
     * Autenticação de paciente com e-mail e senha.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginPaciente(@RequestBody Paciente loginData) {
        Paciente paciente = pacienteRepository.findByEmail(loginData.getEmail());

        if (paciente != null && paciente.getSenha().equals(loginData.getSenha())) {
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("mensagem", "Login realizado com sucesso!");
            resposta.put("id", paciente.getId());
            resposta.put("nome", paciente.getNome());
            return ResponseEntity.ok(resposta);
        }

        Map<String, String> erro = new HashMap<>();
        erro.put("mensagem", "E-mail ou senha inválidos.");
        return ResponseEntity.status(401).body(erro);
    }

    /**
     * Lista todos os pacientes.
     */
    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> getAllPacientes() {
        List<PacienteResponseDTO> pacientes = pacienteRepository.findAll().stream()
                .map(PacienteResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pacientes);
    }

    /**
     * Busca paciente por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> getPacienteById(@PathVariable Long id) {
        return pacienteRepository.findById(id)
                .map(paciente -> ResponseEntity.ok(new PacienteResponseDTO(paciente)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove paciente por ID.
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        if (!pacienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pacienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Atualiza dados do paciente, incluindo endereço e preferências.
     */
    @PutMapping("/{id}")
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity<?> updatePaciente(@PathVariable Long id, @RequestBody PacienteRequestDTO data) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    // Atualiza dados pessoais
                    paciente.setCpf(data.cpf());
                    paciente.setNome(data.nome());
                    paciente.setEmail(data.email());
                    paciente.setBio(data.bio());
                    paciente.setContato(data.contato());
                    paciente.setSenha(data.senha());
                    paciente.setBeneficioSocial(data.beneficioSocial());

                    // Atualiza endereço, se houver
                    if (paciente.getEndereco() != null && data.endereco() != null) {
                        EnderecoPaciente endereco = paciente.getEndereco();
                        EnderecoPacRequestDTO novoEndereco = data.endereco();

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

                    // Atualiza preferências
                    paciente.setPrefEspecialidades(
                            especialidadeRepository.findAllById(data.prefEspecialidades())
                    );
                    paciente.setPrefAbordagens(
                            abordagemRepository.findAllById(data.prefAbordagens())
                    );

                    pacienteRepository.save(paciente);
                    return ResponseEntity.ok(new PacienteResponseDTO(paciente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retorna lista de psicólogos recomendados.
     */
    @GetMapping("/recomendados")
    public ResponseEntity<List<PsicologoResponseDTO>> getPsicologosRecomendados() {
        List<Psicologo> recomendados = psicologoRepository.findTopRecomendados();
        List<PsicologoResponseDTO> response = recomendados.stream()
                .map(PsicologoResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
