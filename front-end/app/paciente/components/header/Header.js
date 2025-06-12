import styles from './Header.module.css';

/**
 * Componente de cabeçalho do site/aplicativo.
 * Exibe o logo e o título "PsiConnect" no topo da página.
 *
 * @component
 * @returns {JSX.Element} Cabeçalho da aplicação com logo e título.
 */
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
