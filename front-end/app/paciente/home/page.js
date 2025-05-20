'use client';
import Link from 'next/link';
import styles from '../page.module.css';

export default function Home() {
  return (
    <main className={styles.main}>
        <header className={styles.header}>
            <div className={styles.logoContainer}>
                <img src="/psiconnect-logo.png" alt="Logo PsiConnect" className={styles.logoImage}/>
                <h1 className={styles.title}>PsiConnect</h1>
            </div>
        </header>

        <div className={styles.mainContent}>
            <h1 className={styles.title}>PsiConnect</h1>
        </div>
        
        <footer className={styles.footer}>
            <p>PsiConnect &copy;</p>
        </footer>
    </main>
  );
}
