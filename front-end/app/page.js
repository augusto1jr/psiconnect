'use client';
import styles from './page.module.css';
import Link from 'next/link';

export default function Home() {
  return (
    <main className={styles.homeContainer}>
      <section className={`${styles.homeSection} ${styles.paciente}`}>
        <p>Cuidar da sua saúde mental é investir em qualidade de vida!</p>
        <Link href="/paciente/login">
          <button type="button" className={styles.loginButton}>Seja um Paciente</button>
        </Link>
      </section>

      <div className={styles.homeLogo}>
        {/* Imagem inserida via CSS*/}
      </div>

      <section className={`${styles.homeSection} ${styles.psicologo}`}>
        <p>Ao trabalhar conosco você consegue atender novos pacientes de onde estiver!</p>
        <Link href="/psicologo/login">
          <button type="button" className={styles.loginButton}>Seja um Psicólogo</button>
        </Link>
      </section>
    </main>
  );
}
