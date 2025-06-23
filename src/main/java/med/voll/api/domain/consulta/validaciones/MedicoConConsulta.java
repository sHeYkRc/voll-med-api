package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRespository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas{

    @Autowired
    ConsultaRespository respository;
    public void validar(DatosAgendarConsulta datos){

        if (datos == null){
            return;
        }
        var medicoConConsulta = respository.existsByMedicoIdAndData(datos.idMedico(), datos.fecha());

        if (medicoConConsulta){
            throw new ValidationException("Este medico ya tiene una colnsulta asignada intenta con otro");
        }
    }
}
