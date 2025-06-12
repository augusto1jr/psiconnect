'use client';

import styles from './page.module.css';
import Link from 'next/link';

/**
 * Componente da página inicial da aplicação (`/`), exibindo duas seções principais:
 * uma para pacientes e outra para psicólogos. Cada seção possui uma mensagem motivacional
 * e um botão que redireciona para a respectiva página de login.
 *
 * A logo da PsiConnect é estilizada via CSS no elemento central.
 *
 * @component
 * @returns {JSX.Element} Página inicial com opções de entrada para pacientes e psicólogos.
 */
export default function Home() {
  return (
    <main className={styles.homeContainer}>
      {/* Seção para Pacientes */}
      <section className={`${styles.homeSection} ${styles.paciente}`}>
        <p>Cuidar da sua saúde mental é investir em qualidade de vida!</p>
        <Link href="/paciente/login">
          <button type="button" className={styles.loginButton}>Seja um Paciente</button>
        </Link>
      </section>

      {/* Logo da PsiConnect (imagem inserida via CSS) */}
      <div className={styles.homeLogo}>
        {/* Imagem inserida via CSS */}
      </div>

      {/* Seção para Psicólogos */}
      <section className={`${styles.homeSection} ${styles.psicologo}`}>
        <p>Ao trabalhar conosco você consegue atender novos pacientes de onde estiver!</p>
        <Link href="/psicologo/login">
          <button type="button" className={styles.loginButton}>Seja um Psicólogo</button>
        </Link>
      </section>
    </main>
  );
}
