package med.voll.api.domain.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import med.voll.api.domain.direccion.DatosDireccionPaciente;

public record DatosRespuestaPaciente(Long id,
                                     String nombre,
                                     String email,
                                     String telefono,
                                     String documentoIdentidad,
                                     DatosDireccionPaciente direccion) {
}
