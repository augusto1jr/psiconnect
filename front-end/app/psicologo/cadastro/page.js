'use client';
import styles from '@/styles/login.module.css';
import customStyles from '../login/login.module.css';
import Link from "next/link";
import { useState } from 'react';
import { useRouter } from 'next/navigation';

/**
 * Componente de cadastro de psicólogo.
 * 
 * OBS: Ainda não foram criadas as demais etapas/telas de cadastro, como:
 * Tela de informações pessoais, endereço, telefone, etc.
 * Tela de informações de saúde: especialidades e abordagens.
 * Atualmente o cadastro obtem apenas email e senha, os demais dados serão coletados posteriormente (são mockados no momento).
 * 
 * Permite que um psicólogo se cadastre com email e senha. Ao cadastrar com sucesso,
 * o ID e o nome retornados pela API são salvos no `localStorage`.
 * 
 * @returns {JSX.Element} Interface de formulário para cadastro de psicólogos.
 */
export default function CadastroPsicologo() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');

  /**
   * Lida com o envio do formulário de cadastro.
   * Faz uma requisição POST para o backend e armazena os dados do psicólogo no localStorage.
   * 
   * @param {React.FormEvent<HTMLFormElement>} e Evento de envio do formulário.
   */
  const handleCadastro = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/psicologos/cadastro', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, senha }),
      });

      if (response.ok) {
        const resposta = await response.json();
        window.alert(resposta.mensagem || 'Cadastro realizado com sucesso!');

        localStorage.setItem('psicologoId', resposta.id);
        localStorage.setItem('psicologoNome', resposta.nome);

        // router.push('/psicologo/home');
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
      <section className={`${styles.login} ${customStyles.login}`}>
        <div className={customStyles.img}>{/* Imagem aplicada via CSS */}</div>
        <div className={`${styles.form} ${customStyles.form}`}>
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
