import styles from './AvaliacoesPsicologo.module.css';

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
