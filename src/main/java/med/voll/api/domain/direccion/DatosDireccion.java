package med.voll.api.domain.direccion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Medico;

public record DatosDireccion(@NotBlank
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
