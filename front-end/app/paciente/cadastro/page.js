'use client';

import styles from '@/styles/login.module.css';
import Link from "next/link";
import { useState } from 'react';
import { useRouter } from 'next/navigation';

/**
 * Tela de cadastro para novos pacientes.
 * OBS: Ainda não foram criadas as demais etapas/telas de cadastro, como:
 * Tela de informações pessoais, endereço, telefone, etc.
 * Tela de informações de saúde: preferencias de especialidades e abordagens.
 * Atualmente o cadastro obtem apenas email e senha, os demais dados serão coletados posteriormente (são mockados no momento).
 * 
 * Rota: `/paciente/cadastro`
 *
 * Ao submeter o formulário, os dados de email e senha são enviados para o back-end (`/pacientes/cadastro`),
 * e em caso de sucesso, o usuário é redirecionado para `/paciente/home`.
 *
 * @component
 * @returns {JSX.Element} Elemento da página de cadastro.
 */
export default function Cadastro() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');

  /**
   * handleCadastro – Função para lidar com o envio do formulário de cadastro.
   *
   * Realiza uma requisição POST para o endpoint de cadastro de pacientes.
   * Em caso de sucesso, armazena o ID e nome no localStorage e redireciona para a home do paciente.
   *
   * @param {React.FormEvent<HTMLFormElement>} e - Evento de envio do formulário.
   * @returns {Promise<void>}
   */
  const handleCadastro = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/pacientes/cadastro', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, senha }),
      });

      if (response.ok) {
        const resposta = await response.json();
        window.alert(resposta.mensagem || 'Cadastro realizado com sucesso!');

        localStorage.setItem('pacienteId', resposta.id);
        localStorage.setItem('pacienteNome', resposta.nome);

        router.push('/paciente/home');
      } else {
        const textoErro = await response.text();
        console.error("Erro:", textoErro);
        alert("Erro ao cadastrar: " + textoErro);
      }
    } catch (error) {
      window.alert('Erro na requisição');
      console.error('Erro na requisição:', error);
    }
  };

  return (
    <main className={styles.main}>
      <section className={styles.login}>
        <div className={styles.img}>{/* Imagem aplicada via CSS */}</div>
        <div className={styles.form}>
          <div className={styles.logoContainer}>
            <img src="/psiconnect-logo.png" alt="Logo PsiConnect" className={styles.logoImage} />
            <h1 className={styles.title}>PsiConnect</h1>
          </div>

          <form onSubmit={handleCadastro}>
            <div className={styles.formGroup}>
              <label htmlFor="email">Email:</label><br />
              <input
                type="email"
                id="email"
                name="email"
                placeholder="Digite seu email"
                className={styles.input}
                value={email}
                onChange={e => setEmail(e.target.value)}
                required
              />
            </div>

            <div className={styles.formGroup}>
              <label htmlFor="senha">Senha:</label><br />
              <input
                type="password"
                id="senha"
                name="senha"
                placeholder="Digite sua senha"
                className={styles.input}
                value={senha}
                onChange={e => setSenha(e.target.value)}
                required
              />
            </div>

            <div className={styles.btn_cadastro}>
              <button type="submit">Continuar</button>
            </div>

            <div className={styles.btn_forgot}>
              <Link href="./login">Já tem uma conta? Faça login</Link>
            </div>
          </form>
        </div>
      </section>
    </main>
  );
}
