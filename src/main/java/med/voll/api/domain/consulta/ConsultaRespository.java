package med.voll.api.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRespository extends JpaRepository<Consulta,Long> {
    Boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    Boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime fecha);
}
