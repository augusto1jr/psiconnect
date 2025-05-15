package dev.psiconnect.dtos.requests;

import dev.psiconnect.entities.Psicologo.ModalidadeAtendimento;
import java.util.List;

public record PsicologoRequestDTO(
        String crp,
        String nome,
        String email,
        String foto,
        String bio,
        String formacao,
        String contato,
        String senha,
        Double valorConsulta,
        Boolean aceitaBeneficio,
        ModalidadeAtendimento modalidadeAtendimento,
        EnderecoPsiRequestDTO endereco,
        List<Long> especialidades,
        List<Long> abordagens
) {}
