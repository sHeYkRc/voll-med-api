package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas{

    public void validar (DatosAgendarConsulta datos){

        var ahora = LocalDateTime.now();
        var horaDeCosulta = datos.fecha();
        var direferenciaDe30Min = Duration.between(ahora,horaDeCosulta).toMinutes()<30;

        if (direferenciaDe30Min){
            throw new ValidationException("La consulta debe programarse con 30 minutos de anticipación");
        }

    }
}
