'use client';
import styles from '@/styles/login.module.css';
import customStyles from '../login/login.module.css';
import Link from "next/link";
import { useState } from 'react';
import { useRouter } from 'next/navigation';

/**
 * Componente de login para psicólogos.
 * Permite autenticar o usuário psicólogo via email e senha, redirecionando para a home ao sucesso.
 *
 * @component
 * @returns {JSX.Element} Página de login para psicólogos.
 */
export default function LoginPsicologo() {
  const router = useRouter();

  /**
   * Estado que armazena o e-mail informado no campo de login.
   * @type {[string, Function]}
   */
  const [email, setEmail] = useState('');

  /**
   * Estado que armazena a senha informada no campo de login.
   * @type {[string, Function]}
   */
  const [senha, setSenha] = useState('');

  /**
   * Manipula o envio do formulário de login.
   * Realiza a requisição à API para autenticar o psicólogo.
   * Salva o ID e nome no `localStorage` e redireciona em caso de sucesso.
   *
   * @async
   * @function
   * @param {React.FormEvent} e - Evento de envio do formulário.
   */
  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/psicologos/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, senha }),
      });

      const resposta = await response.json();

      if (response.ok) {
        localStorage.setItem('psicologoId', resposta.id);
        localStorage.setItem('psicologoNome', resposta.nome);
        router.push('/psicologo/home');
      } else {
        window.alert(resposta.mensagem || 'E-mail ou senha inválidos.');
      }
    } catch (error) {
      window.alert('Erro na requisição');
      console.error('Erro na requisição:', error);
    }
  };

  return (
    <main className={styles.main}>
      <section className={`${styles.login} ${customStyles.login}`}>
        {/* Imagem lateral aplicada via CSS */}
        <div className={customStyles.img}></div>

        <div className={`${styles.form} ${customStyles.form}`}>
          {/* Logo e título do sistema */}
          <div className={styles.logoContainer}>
            <img src="/psiconnect-logo.png" alt="Logo PsiConnect" className={styles.logoImage} />
            <h1 className={styles.title}>PsiConnect</h1>
          </div>

          {/* Formulário de login */}
          <form onSubmit={handleLogin} autoComplete="on">
            {/* Campo de e-mail */}
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

            {/* Campo de senha */}
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

            {/* Botão para login */}
            <div className={styles.btn_login}>
              <button type="submit">Login</button>
            </div>

            {/* Botão de cadastro para novos usuários */}
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

      {/* Botão para voltar à tela inicial */}
      <div className={styles.btn_voltar}>
        <button onClick={() => router.push('/')}>
          <span className="material-symbols-outlined">arrow_back</span> Voltar
        </button>
      </div>
    </main>
  );
}
