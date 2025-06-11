package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.Psicologo;
import dev.psiconnect.entities.Especialidade;
import dev.psiconnect.entities.Abordagem;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO de resposta que representa os dados de um psicólogo no sistema PsiConnect.
 *
 * Contém informações pessoais, profissionais, contato, valores de consulta,
 * modalidade de atendimento, endereço, especialidades e abordagens associadas.
 *
 * @param id                   Identificador único do psicólogo.
 * @param crp                  Número do registro profissional do psicólogo (CRP).
 * @param nome                 Nome completo do psicólogo.
 * @param email                Email de contato do psicólogo.
 * @param foto                 URL ou referência da foto do psicólogo.
 * @param bio                  Biografia ou descrição profissional do psicólogo.
 * @param formacao             Formação acadêmica do psicólogo.
 * @param contato              Informações de contato do psicólogo (telefone, etc).
 * @param valorConsulta        Valor cobrado por consulta pelo psicólogo.
 * @param aceitaBeneficio      Indica se o psicólogo aceita benefícios sociais.
 * @param modalidadeAtendimento Modalidade de atendimento oferecida (remoto, presencial, etc).
 * @param endereco             Endereço do psicólogo, encapsulado em DTO específico.
 * @param especialidades       Lista dos nomes das especialidades do psicólogo.
 * @param abordagens           Lista dos nomes das abordagens utilizadas pelo psicólogo.
 */
public record PsicologoResponseDTO(
        Long id,
        String crp,
        String nome,
        String email,
        String foto,
        String bio,
        String formacao,
        String contato,
        Double valorConsulta,
        Boolean aceitaBeneficio,
        String modalidadeAtendimento,
        EnderecoPsiResponseDTO endereco,
        List<String> especialidades,
        List<String> abordagens
) {
    /**
     * Construtor que cria um DTO a partir da entidade Psicologo.
     *
     * Mapeia os campos da entidade para o DTO, convertendo listas de entidades
     * em listas de nomes para especialidades e abordagens.
     *
     * @param psicologo Entidade Psicologo a ser convertida em DTO.
     */
    public PsicologoResponseDTO(Psicologo psicologo) {
        this(
                psicologo.getId(),
                psicologo.getCrp(),
                psicologo.getNome(),
                psicologo.getEmail(),
                psicologo.getFoto(),
                psicologo.getBio(),
                psicologo.getFormacao(),
                psicologo.getContato(),
                psicologo.getValorConsulta(),
                psicologo.getAceitaBeneficio(),
                psicologo.getModalidadeAtendimento().name(),
                psicologo.getEndereco() != null ? new EnderecoPsiResponseDTO(psicologo.getEndereco()) : null,
                psicologo.getEspecialidades() != null ? psicologo.getEspecialidades().stream().map(Especialidade::getNome).collect(Collectors.toList()) : null,
                psicologo.getAbordagens() != null ? psicologo.getAbordagens().stream().map(Abordagem::getNome).collect(Collectors.toList()) : null
        );
    }
}
