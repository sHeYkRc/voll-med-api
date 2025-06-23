package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.direccion.DatosDireccionPaciente;
import med.voll.api.domain.direccion.Direccion;
import med.voll.api.domain.direccion.DireccionPaciente;

@Getter
@EqualsAndHashCode(of="id")
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="Paciente")
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String documentoidentidad;
    private String telefono;
    private boolean activo;
    @Embedded
    private DireccionPaciente direccionPaciente;

    public Paciente (DatosRegistroPaciente datosRegistroPaciente){
        this.activo = true;
        this.nombre = datosRegistroPaciente.nombre();
        this.email = datosRegistroPaciente.email();
        this.documentoidentidad = datosRegistroPaciente.documentoidentidad();
        this.telefono = datosRegistroPaciente.telefono();
        this.direccionPaciente =new DireccionPaciente(datosRegistroPaciente.direccion());

    }


    public void actualizarDatosPacientes(DatosActualizarPaciente datosActualizarPaciente) {
        if(datosActualizarPaciente.nombre() != null){
            this.nombre = datosActualizarPaciente.nombre();
        }
        if(datosActualizarPaciente.telefono() != null){
            this.telefono = datosActualizarPaciente.telefono();
        }
        if (datosActualizarPaciente.documentoidentidad() != null){
            this.documentoidentidad = datosActualizarPaciente.documentoidentidad();
        }
        if(datosActualizarPaciente.direccionPaciente() != null){
            this.direccionPaciente = direccionPaciente.actualizarDatosPaciente(datosActualizarPaciente.direccionPaciente());
        }
    }

    public void desactivarPaciente() {
        this.activo = false;
    }
}
