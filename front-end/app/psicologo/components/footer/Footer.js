import styles from './Footer.module.css';

/**
 * Componente de rodapé do site/aplicativo.
 * Exibe o nome da aplicação e o símbolo de copyright.
 *
 * @component
 * @returns {JSX.Element} Rodapé da página com o nome "PsiConnect".
 */
export default function Footer() {
  return (
    <footer className={styles.footer}>
        <p>PsiConnect &copy;</p>
    </footer>
  );
}
