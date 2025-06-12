import { useState } from 'react';
import styles from './AgendaPsicologo.module.css';

/**
 * Componente que exibe a agenda de um psicólogo com os dias e horários disponíveis
 * para que o paciente possa selecionar um horário e realizar o agendamento.
 *
 * @component
 * @param {Object} props - Propriedades do componente.
 * @param {Array<Object>} props.disponibilidade - Lista de objetos representando a disponibilidade do psicólogo.
 * Cada item deve conter:
 * - {string} diaSemana - Nome do dia da semana (ex: "Segunda").
 * - {string} data - Data correspondente (ex: "11/06/2025").
 * - {Array<string>} horarios - Lista de horários disponíveis nesse dia (ex: ["08:00", "09:00"]).
 *
 * @returns {JSX.Element} Componente de seleção de horário com botão de agendamento.
 */
const AgendaPsicologo = ({ disponibilidade }) => {
  const [horarioSelecionado, setHorarioSelecionado] = useState(null);

  /**
   * Define o horário selecionado pelo usuário.
   *
   * @param {string} dia - A data selecionada (formato: dd/mm/aaaa).
   * @param {string} hora - O horário selecionado (formato: hh:mm).
   */
  const handleSelecionarHorario = (dia, hora) => {
    setHorarioSelecionado({ dia, hora });
  };

  return (
    <div className={styles.agendaContainer}>
      
      <div className={styles.diasContainer}>
        {disponibilidade.map((dia, index) => (
          <div key={index} className={styles.dia}>
            <div className={styles.diaCabecalho}>
              <p className={styles.diaSemana}>{dia.diaSemana}</p>
              <p className={styles.diaData}>{dia.data}</p>
            </div>
            <div className={styles.horarios}>
              {dia.horarios.map((hora, i) => {
                const isSelecionado =
                  horarioSelecionado?.dia === dia.data &&
                  horarioSelecionado?.hora === hora;

                return (
                  <div
                    key={i}
                    className={`${styles.horario} ${
                      isSelecionado ? styles.horarioSelecionado : ''
                    }`}
                    onClick={() => handleSelecionarHorario(dia.data, hora)}
                  >
                    {hora}
                  </div>
                );
              })}
            </div>
          </div>
        ))}
      </div>

      <button
        className={styles.agendarButton}
        disabled={!horarioSelecionado}
      >
        <span className="material-symbols-outlined">calendar_month</span>
        <span>Agendar</span>
      </button>
    </div>
  );
};

export default AgendaPsicologo;
