package dev.psiconnect.dtos.requests;

import dev.psiconnect.entities.Paciente.BeneficioSocial;
import java.util.List;

/**
 * DTO para requisição de criação ou atualização dos dados de um paciente.
 *
 * Este objeto reúne informações pessoais, de contato, endereço, preferências
 * e dados sensíveis do paciente para uso no cadastro ou modificação.
 *
 * @param cpf              Número do CPF do paciente, usado como identificador único.
 * @param nome             Nome completo do paciente.
 * @param email            Endereço de e-mail para contato e login.
 * @param foto             URL ou caminho da foto do paciente.
 * @param bio              Pequena biografia ou descrição pessoal do paciente.
 * @param contato          Informações de contato adicionais, como telefone.
 * @param senha            Senha criptografada para autenticação do paciente.
 * @param beneficioSocial  Indicação do benefício social aplicado ao paciente, se houver.
 * @param endereco         Dados do endereço residencial do paciente.
 * @param prefEspecialidades Lista de IDs das especialidades preferidas pelo paciente.
 * @param prefAbordagens   Lista de IDs das abordagens preferidas pelo paciente.
 */
public record PacienteRequestDTO(
        String cpf,
        String nome,
        String email,
        String foto,
        String bio,
        String contato,
        String senha,
        BeneficioSocial beneficioSocial,
        EnderecoPacRequestDTO endereco,
        List<Long> prefEspecialidades,
        List<Long> prefAbordagens
) {}
