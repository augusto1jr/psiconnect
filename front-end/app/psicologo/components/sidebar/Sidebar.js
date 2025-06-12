import styles from './Sidebar.module.css';

/**
 * Componente de barra lateral do psicólogo.
 * Exibe informações do perfil e botões de navegação.
 *
 * @component
 * @param {Object} props - Propriedades do componente.
 * @param {Object} props.psicologo - Objeto contendo os dados do psicólogo (nome e foto).
 * @param {Function} props.handleHome - Função chamada ao clicar no botão "Início".
 * @param {Function} props.handleLogout - Função chamada ao clicar no botão "Sair".
 * @returns {JSX.Element} Barra lateral com informações do psicólogo e menu de navegação.
 */
export default function Sidebar({ psicologo, handleHome, handleLogout }) {
  return (
    <div className={styles.sideBar}>

        {/* Perfil */}
        <div className={styles.profileBlock}>
            <img src={psicologo.foto || '/default-avatar.jpeg'} alt="Foto de perfil" className={styles.avatar} />
            <div className={styles.profileInfo}>
                <span>{psicologo.nome}</span>
                <button className={styles.editButton}>
                    <span className="material-symbols-outlined">edit</span>
                </button>
            </div>
        </div>

        {/* Botão Home */}
        <button onClick={handleHome} className={styles.navButton}>
          <span className="material-symbols-outlined">home</span>
          <span>Início</span>
        </button>

        {/* Botão Pesquisar */}
        <button className={styles.navButton}>
          <span className="material-symbols-outlined">search</span>
          <span>Pesquisar</span>
        </button>

        {/* Botão Favoritos */}
        <button className={styles.navButton}>
          <span className="material-symbols-outlined">favorite</span>
          <span>Favoritos</span>
        </button>

        {/* Botão Agenda */}
        <button className={styles.navButton}>
          <span className="material-symbols-outlined">calendar_month</span>
          <span>Agenda</span>
        </button>

        {/* Botão Ajustes */}
        <button className={styles.navButton}>
          <span className="material-symbols-outlined">settings</span>
          <span>Ajustes</span>
        </button>

        {/* Botão Ajuda */}
        <button className={styles.navButton}>
          <span className="material-symbols-outlined">help</span>
          <span>Ajuda</span>
        </button>

        {/* Botão Sair */}
        <div className={styles.logoutBlock}>
            <button onClick={handleLogout} className={styles.logoutButton}>
                <span className="material-symbols-outlined">logout</span>Sair
            </button>
        </div>
    </div>
  );
}
