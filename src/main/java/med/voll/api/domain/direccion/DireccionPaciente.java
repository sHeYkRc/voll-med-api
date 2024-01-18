package med.voll.api.domain.direccion;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.paciente.DatosRegistroPaciente;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DireccionPaciente {

    private String codigopostal;
    private String provincia;
    private String urbanizacion;
    private String calle;
    private String numero;
    private String complemento;
    private String distrito;
    private String ciudad;

    public DireccionPaciente (DatosDireccionPaciente datosDireccion){
        this.codigopostal = datosDireccion.codigopostal();
        this.provincia = datosDireccion.provincia();
        this.urbanizacion = datosDireccion.urbanizacion();
        this.calle = datosDireccion.calle();
        this.numero= datosDireccion.numero();
        this.complemento= datosDireccion.complemento();
        this.distrito = datosDireccion.distrito();
        this.ciudad = datosDireccion.ciudad();

    }


    public DireccionPaciente actualizarDatosPaciente(DatosDireccionPaciente datosDireccionPaciente) {
        this.codigopostal = datosDireccionPaciente.codigopostal();
        this.provincia = datosDireccionPaciente.provincia();
        this.urbanizacion = datosDireccionPaciente.urbanizacion();
        this.calle = datosDireccionPaciente.calle();
        this.numero= datosDireccionPaciente.numero();
        this.complemento= datosDireccionPaciente.complemento();
        this.distrito = datosDireccionPaciente.distrito();
        this.ciudad = datosDireccionPaciente.ciudad();
        return this;


    }
}
