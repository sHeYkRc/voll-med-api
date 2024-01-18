package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {
    @Autowired
    private ConsultaRespository cosultaRespository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;


    public void agendar(DatosAgendarConsulta datos){

        if(pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("Este ID del paciente no ha sido encontrado");
        }

        if(datos.idMedico() != null && medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("El ID del medico no ha sido encontrado");
        }

        Paciente paciente = pacienteRepository.findById(datos.idPaciente()).get();
        Medico medico = selecionarMedico(datos);


        Consulta consulta = new Consulta(null, medico,paciente,datos.fecha());
        cosultaRespository.save(consulta);

    }

    private Medico selecionarMedico(DatosAgendarConsulta datos) {
        if (datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad() == null){
            throw new ValidacionDeIntegridad("se debe selecionar una especialidad para el medico");
        }
        return  medicoRepository.selecionarMedicoConEspecialidad(datos.especialidad(),datos.fecha());
    }
}
