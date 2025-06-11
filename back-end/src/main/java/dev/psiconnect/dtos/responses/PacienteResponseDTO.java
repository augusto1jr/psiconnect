package dev.psiconnect.dtos.responses;

import dev.psiconnect.entities.Paciente;
import dev.psiconnect.entities.Especialidade;
import dev.psiconnect.entities.Abordagem;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO de resposta que representa os dados de um paciente no sistema PsiConnect.
 *
 * Inclui informações pessoais, contato, benefícios sociais, endereço e preferências
 * de especialidades e abordagens psicológicas.
 *
 * @param id                 Identificador único do paciente.
 * @param cpf                Cadastro de Pessoa Física (CPF) do paciente.
 * @param nome               Nome completo do paciente.
 * @param email              Endereço de email do paciente.
 * @param foto               URL ou referência da foto do paciente.
 * @param bio                Biografia ou descrição pessoal do paciente.
 * @param contato            Informações de contato do paciente (telefone, etc).
 * @param beneficioSocial    Tipo de benefício social que o paciente possui (se houver).
 * @param endereco           Endereço residencial do paciente, representado por DTO específico.
 * @param prefEspecialidades  Lista dos nomes das especialidades psicológicas preferidas pelo paciente.
 * @param prefAbordagens      Lista dos nomes das abordagens psicológicas preferidas pelo paciente.
 */
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
    /**
     * Construtor que cria um DTO a partir da entidade Paciente.
     *
     * Mapeia os campos diretamente e converte as listas de especialidades e abordagens
     * em listas de nomes.
     *
     * @param paciente Entidade Paciente para conversão em DTO.
     */
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
