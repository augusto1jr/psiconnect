import styles from './Header.module.css';

export default function Header() {
  return (
    <header className={styles.header}>
        <div className={styles.logoContainer}>
            <img src="/psiconnect-logo.png" alt="Logo PsiConnect" className={styles.logoImage}/>
            <h1 className={styles.title}>PsiConnect</h1>
        </div>
    </header>
  );
}
