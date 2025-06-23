package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccionPaciente;
import med.voll.api.domain.direccion.DireccionPaciente;

public record DatosActualizarPaciente(@NotNull
                                      Long id,
                                      String nombre,
                                      String telefono,
                                      String documentoidentidad,
                                      DatosDireccionPaciente direccionPaciente
) {
}
