package dev.psiconnect.dtos.requests;

import dev.psiconnect.entities.Paciente.BeneficioSocial;
import java.util.List;

public record PacienteRequestDTO(
        String cpf,
        String nome,
        String email,
        String foto,
        String bio,
        String contato,
        String senhaHash,
        BeneficioSocial beneficioSocial,
        EnderecoPacRequestDTO endereco,
        List<Long> prefEspecialidades,
        List<Long> prefAbordagens
) {}
