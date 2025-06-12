import styles from './AvaliacoesPsicologo.module.css';

/**
 * Componente que exibe as avaliações recebidas por um psicólogo.
 *
 * @component
 * @param {Object} props - Propriedades do componente.
 * @param {Array<Object>} props.avaliacoes - Lista de avaliações feitas ao psicólogo.
 * Cada avaliação deve conter os campos:
 * - {number} id - Identificador único da avaliação.
 * - {number} nota - Nota dada pelo paciente (1 a 5).
 * - {string} comentario - Comentário textual da avaliação.
 * - {string} nomePaciente - Nome do paciente que avaliou.
 * - {string} data - Data da avaliação.
 *
 * @returns {JSX.Element} Container com todas as avaliações ou uma mensagem informando que não há avaliações.
 */
const AvaliacoesPsicologo = ({ avaliacoes }) => {
  if (!avaliacoes || avaliacoes.length === 0) {
    return (
      <div className={styles.container}>
        <h2>Avaliações</h2>
        <p>Ainda não há avaliações.</p>
      </div>
    );
  }

  const media = (
    avaliacoes.reduce((acc, curr) => acc + curr.nota, 0) / avaliacoes.length
  ).toFixed(1);

  return (
    <div className={styles.avaliacoesContainer}>
      <h2>Minhas avaliações</h2>

      <div className={styles.header}>
        <div>
          <div className={styles.stars}>
            {'★'.repeat(5)}
          </div>
          <p>Baseado em {avaliacoes.length} opinião{avaliacoes.length > 1 ? 's' : ''}</p>
        </div>
      </div>

      <div className={styles.cardsContainer}>
        {avaliacoes.map((avaliacao) => (
          <div key={avaliacao.id} className={styles.card}>
            <div className={styles.stars}>
              {'★'.repeat(avaliacao.nota)}
            </div>
            <p className={styles.comentario}>
              {avaliacao.comentario}
            </p>
            <div className={styles.autor}>
              <strong>{avaliacao.nomePaciente}</strong>
              <span>{avaliacao.data}</span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default AvaliacoesPsicologo;
