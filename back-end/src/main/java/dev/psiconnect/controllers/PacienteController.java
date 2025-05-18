package dev.psiconnect.controllers;

import dev.psiconnect.dtos.requests.EnderecoPacRequestDTO;
import dev.psiconnect.dtos.requests.PacienteRequestDTO;
import dev.psiconnect.dtos.responses.PacienteResponseDTO;
import dev.psiconnect.entities.*;
import dev.psiconnect.repositories.AbordagemRepository;
import dev.psiconnect.repositories.EnderecoPacRepository;
import dev.psiconnect.repositories.EspecialidadeRepository;
import dev.psiconnect.repositories.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EnderecoPacRepository enderecoPacRepository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private AbordagemRepository abordagemRepository;

    /* ENDPOINT CADASTRO SIMPLIFICADO */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/cadastro")
    @Transactional
    public void savePaciente(@RequestBody PacienteRequestDTO data) {
        Paciente paciente = new Paciente();
        paciente.setEmail(data.email());
        paciente.setSenha(data.senha());

        // Valores genéricos obrigatórios
        paciente.setNome("Paciente Teste");
        paciente.setCpf("000.000.000-00"); // CPF mockado
        paciente.setFoto("https://via.placeholder.com/150"); // URL padrão de imagem
        paciente.setBio("Teste");
        paciente.setContato("000000000"); // Telefone fictício
        paciente.setBeneficioSocial(Paciente.BeneficioSocial.NENHUM); // Enum padrão

        pacienteRepository.save(paciente);
    }


    /* ENDPOINT CADASTRO COMPLETO
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/cadastro")
    @Transactional
    public void savePaciente(@RequestBody PacienteRequestDTO data) {
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
    }
    */

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public ResponseEntity<String> loginPaciente(@RequestBody Paciente loginData) {
        Paciente paciente = pacienteRepository.findByEmail(loginData.getEmail());

        if (paciente != null && paciente.getSenha().equals(loginData.getSenha())) {
            return ResponseEntity.ok("Login realizado com sucesso!");
        } else {
            return ResponseEntity.status(401).body("E-mail ou senha inválidos.");
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<PacienteResponseDTO> getAllPacientes() {
        return pacienteRepository.findAll().stream()
                .map(PacienteResponseDTO::new)
                .collect(Collectors.toList());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> getPacienteById(@PathVariable Long id) {
        return pacienteRepository.findById(id)
                .map(paciente -> ResponseEntity.ok(new PacienteResponseDTO(paciente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        if (!pacienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        pacienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity<?> updatePaciente(@PathVariable Long id, @RequestBody PacienteRequestDTO data) {
        return pacienteRepository.findById(id)
                .map(paciente -> {
                    paciente.setCpf(data.cpf());
                    paciente.setNome(data.nome());
                    paciente.setEmail(data.email());
                    paciente.setBio(data.bio());
                    paciente.setContato(data.contato());
                    paciente.setSenha(data.senha());
                    paciente.setBeneficioSocial(data.beneficioSocial());

                    if (paciente.getEndereco() != null) {
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

                    paciente.setPrefEspecialidades(especialidadeRepository.findAllById(data.prefEspecialidades()));
                    paciente.setPrefAbordagens(abordagemRepository.findAllById(data.prefAbordagens()));

                    pacienteRepository.save(paciente);
                    return ResponseEntity.ok(new PacienteResponseDTO(paciente));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
