package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.Paciente;
import dev.psiconnect.entities.Especialidade;
import dev.psiconnect.entities.Abordagem;

import java.util.List;
import java.util.stream.Collectors;

public record PacienteResponseDTO(
        Long id,
        String cpf,
        String nome,
        String email,
        String foto,
        String bio,
        String contato,
        String beneficioSocial,
        EnderecoPacResponseDTO endereco,
        List<String> prefEspecialidades,
        List<String> prefAbordagens
) {
    public PacienteResponseDTO(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getCpf(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getFoto(),
                paciente.getBio(),
                paciente.getContato(),
                paciente.getBeneficioSocial().name(),
                paciente.getEndereco() != null ? new EnderecoPacResponseDTO(paciente.getEndereco()) : null,
                paciente.getPrefEspecialidades() != null ? paciente.getPrefEspecialidades().stream().map(Especialidade::getNome).collect(Collectors.toList()) : null,
                paciente.getPrefAbordagens() != null ? paciente.getPrefAbordagens().stream().map(Abordagem::getNome).collect(Collectors.toList()) : null
        );
    }
}
