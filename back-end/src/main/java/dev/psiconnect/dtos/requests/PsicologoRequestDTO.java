package dev.psiconnect.dtos.requests;

import dev.psiconnect.entities.Psicologo.ModalidadeAtendimento;
import java.util.List;

/**
 * DTO para requisição de criação ou atualização dos dados de um psicólogo.
 *
 * Contém informações profissionais, de contato, endereço, preferências e credenciais
 * necessárias para cadastrar ou modificar um psicólogo no sistema.
 *
 * @param crp                 Número do registro profissional do psicólogo (CRP).
 * @param nome                Nome completo do psicólogo.
 * @param email               Endereço de e-mail para contato e autenticação.
 * @param foto                URL ou caminho da foto do psicólogo.
 * @param bio                 Breve descrição ou biografia do psicólogo.
 * @param formacao            Formação acadêmica ou especializações.
 * @param contato             Informações adicionais de contato (telefone, etc).
 * @param senha               Senha criptografada para autenticação.
 * @param valorConsulta       Valor cobrado pelo psicólogo por consulta.
 * @param aceitaBeneficio     Indica se o psicólogo aceita pacientes com benefício social.
 * @param modalidadeAtendimento Modalidade do atendimento oferecido (ex: remoto, presencial).
 * @param endereco            Dados do endereço profissional ou residencial do psicólogo.
 * @param especialidades      Lista de IDs das especialidades do psicólogo.
 * @param abordagens          Lista de IDs das abordagens terapêuticas do psicólogo.
 */
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
