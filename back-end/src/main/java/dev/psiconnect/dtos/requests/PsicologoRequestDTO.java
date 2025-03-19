package dev.psiconnect.dtos.requests;

import dev.psiconnect.entities.Psicologo.ModalidadeAtendimento;

public record PsicologoRequestDTO(
        String crp,
        String nome,
        String email,
        String bio,
        String formacao,
        String contato,
        String senhaHash,
        Double valorPadraoConsulta,
        Boolean aceitaValorSocial,
        ModalidadeAtendimento modalidadeAtendimento
) {}
