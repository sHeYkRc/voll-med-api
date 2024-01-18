package med.voll.api.domain.direccion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosDireccionPaciente(
                                     @NotBlank
                                     String urbanizacion,
                                     @NotBlank
                                     @Pattern(regexp = "\\d{5}")// indica que el codigo postal debe tener solo 5 digitos
                                     String codigopostal,

                                     @NotBlank
                                     String provincia,
                                     @NotBlank
                                     String calle,
                                     @NotBlank
                                     String distrito,
                                     @NotBlank
                                     String ciudad,
                                     @NotBlank
                                     String numero,
                                     @NotBlank
                                     String complemento

                                     ) {
}
