package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.direccion.DatosDireccionPaciente;

public record DatosRegistroPaciente(
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telefono,
        @Pattern(regexp = "\\d{3}\\.?\\d{3}")
        @NotBlank
        String documentoidentidad,
        @NotNull
        @Valid
        DatosDireccionPaciente direccion) {
}
