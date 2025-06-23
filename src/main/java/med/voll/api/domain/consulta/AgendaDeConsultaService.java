package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.ValidadorCancelamientoDeConsulta;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {
    @Autowired
    private ConsultaRespository consultaRespository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    List<ValidadorDeConsultas> validadores;

    @Autowired
    List<ValidadorCancelamientoDeConsulta> validadoresCancelamiento;

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datos){

        if(!pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("Este ID del paciente no ha sido encontrado");
        }

        if(datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("El ID del medico no ha sido encontrado");
        }

        validadores.forEach(v->v.validar(datos));

        Paciente paciente = pacienteRepository.findById(datos.idPaciente()).get();

        Medico medico = selecionarMedico(datos);

        if (medico == null){
            throw new ValidacionDeIntegridad("lo sentimos por el momento no constamos con medicos disponibles con esta especialidad");
        }

        Consulta consulta = new Consulta(medico,paciente,datos.fecha());
        consultaRespository.save(consulta);
        return new DatosDetalleConsulta(consulta);

    }

    private Medico selecionarMedico(DatosAgendarConsulta datos) {
        if (datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad() == null){
            throw new ValidacionDeIntegridad("se debe selecionar una especialidad para el medico");
        }

        return  medicoRepository.selecionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }


    public void cancelar(DatosCancelamientoConsulta datos) {

        if(!consultaRespository.existsById(datos.idConsulta())){
            throw new ValidacionDeIntegridad("El id de tu consulta no existe verificalo ");

        }

        validadoresCancelamiento.forEach(v -> v.validar(datos));

        var consulta = consultaRespository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
        //consultaRespository.deleteById(consulta.getId()); elimina la consulta no la "cancela"

    }

}
