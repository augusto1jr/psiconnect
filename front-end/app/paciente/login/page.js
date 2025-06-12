'use client';
import styles from '@/styles/login.module.css';
import Link from "next/link";
import { useState } from 'react';
import { useRouter } from 'next/navigation';

/**
 * Componente de login para pacientes no sistema PsiConnect.
 * Permite que o usuário insira email e senha para autenticação.
 * Caso o login seja bem-sucedido, redireciona o paciente para a página inicial (/paciente/home).
 * Em caso de erro, exibe mensagem ao usuário.
 *
 * @component
 * @returns {JSX.Element} JSX do formulário de login.
 */
export default function LoginPaciente() {
  const router = useRouter(); // Hook para redirecionamento de páginas
  const [email, setEmail] = useState(''); // Estado para armazenar o email digitado
  const [senha, setSenha] = useState(''); // Estado para armazenar a senha digitada

  /**
   * Função que lida com o envio do formulário de login.
   * Envia uma requisição POST para a API de login de pacientes.
   *
   * @param {React.FormEvent<HTMLFormElement>} e - Evento do formulário.
   */
  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/pacientes/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, senha }),
      });

      const resposta = await response.json();

      if (response.ok) {
        // Armazena o ID e nome do paciente no localStorage
        localStorage.setItem('pacienteId', resposta.id);
        localStorage.setItem('pacienteNome', resposta.nome);

        // Redireciona para a página principal do paciente
        router.push('/paciente/home');
      } else {
        // Exibe alerta em caso de falha na autenticação
        window.alert(resposta.mensagem || 'E-mail ou senha inválidos.');
      }
    } catch (error) {
      window.alert('Erro na requisição');
      console.error('Erro na requisição:', error);
    }
  };

  return (
    <main className={styles.main}>
      <section className={styles.login}>
        {/* Imagem de fundo definida via CSS */}
        <div className={styles.img}></div>

        <div className={styles.form}>
          {/* Logo do sistema */}
          <div className={styles.logoContainer}>
            <img src="/psiconnect-logo.png" alt="Logo PsiConnect" className={styles.logoImage} />
            <h1 className={styles.title}>PsiConnect</h1>
          </div>

          {/* Formulário de login */}
          <form onSubmit={handleLogin} autoComplete="on">
            {/* Campo de Email */}
            <div className={styles.formGroup}>
              <label htmlFor="id_email">Email:</label><br />
              <input
                type="email"
                id="id_email"
                className={styles.input}
                autoComplete="email"
                value={email}
                onChange={e => setEmail(e.target.value)}
                required
                placeholder="exemplo@email.com"
              />
            </div>

            {/* Campo de Senha */}
            <div className={styles.formGroup}>
              <label htmlFor="id_senha">Senha:</label><br />
              <input
                type="password"
                id="id_senha"
                className={styles.input}
                autoComplete="current-password"
                value={senha}
                onChange={e => setSenha(e.target.value)}
                required
                placeholder="********"
              />
            </div>

            {/* Botão de Login */}
            <div className={styles.btn_login}>
              <button type="submit">Login</button>
            </div>

            {/* Link para página de cadastro */}
            <div className={styles.btn_cadastro}>
              <Link href="./cadastro">
                <button type="button">Cadastrar</button>
              </Link>
            </div>

            {/* Link para recuperar senha */}
            <div className={styles.btn_forgot}>
              <a href="#">Esqueceu a senha?</a>
            </div>
          </form>
        </div>
      </section>

      {/* Botão de voltar para a página inicial */}
      <div className={styles.btn_voltar}>
        <button onClick={() => router.push('/')}>
          <span className="material-symbols-outlined">arrow_back</span> Voltar
        </button>
      </div>
    </main>
  );
}
