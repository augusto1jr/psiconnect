'use client';
import styles from '@/styles/login.module.css';
import customStyles from '../login/login.module.css';
import Link from "next/link";
import { useState } from 'react';
import { useRouter } from 'next/navigation';

export default function CadastroPsicologo() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');

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

        //router.push('/psicologo/home');
      } else {
        const textoErro = await response.text(); // Só é chamado uma vez
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
        <img src="/psiconnect-logo.png" alt="Logo PsiConnect" className={styles.logoImage}/>
        <h1 className={styles.title}>PsiConnect</h1></div>
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
              <button type="submit">Cadastrar</button>
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
