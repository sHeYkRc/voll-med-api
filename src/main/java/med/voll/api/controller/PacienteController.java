package med.voll.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccionPaciente;
import med.voll.api.domain.direccion.Direccion;
import med.voll.api.domain.direccion.DireccionPaciente;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Resgista a un paciente nuevo ")
    public ResponseEntity registrarPaciente(@RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente,
                                            UriComponentsBuilder uriComponentsBuilder) {

        Paciente paciente = pacienteRepository.save(new Paciente(datosRegistroPaciente));
        DatosRespuestaPaciente datosRespuestaPaciente = new DatosRespuestaPaciente(paciente.getId(),paciente.getNombre(),
                paciente.getEmail(), paciente.getTelefono(), paciente.getDocumentoidentidad(),
                new DatosDireccionPaciente(paciente.getDireccionPaciente().getUrbanizacion(),
                        paciente.getDireccionPaciente().getCodigopostal(),
                        paciente.getDireccionPaciente().getProvincia(),paciente.getDireccionPaciente().getCalle(),
                        paciente.getDireccionPaciente().getDistrito(),paciente.getDireccionPaciente().getCiudad(),
                        paciente.getDireccionPaciente().getNumero(), paciente.getDireccionPaciente().getComplemento()));
        URI url = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaPaciente);
    }

    @GetMapping//me quedo duda sobre la creacion de querry revisar el repository
    @Operation(summary = "Obtiene el listado de los pacientes activos en base de datos")
    public ResponseEntity<Page<DatosListadoPaciente>> listadoPacientes(@PageableDefault(size = 10) Pageable paginacion){
        return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).map(DatosListadoPaciente::new));
        //return ResponseEntity.ok(pacienteRepository.findAll(paginacion).map(DatosListadoPaciente::new));

    }

    @PutMapping
    @Transactional
    @Operation(summary = "Actializa los datos de un paciente")
    public ResponseEntity actualizarPaciente(@RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente){
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatosPacientes(datosActualizarPaciente);
        pacienteRepository.save(paciente);
        return ResponseEntity.ok(new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(), paciente.getTelefono(),paciente.getDocumentoidentidad(),
                new DatosDireccionPaciente(paciente.getDireccionPaciente().getUrbanizacion(),
                        paciente.getDireccionPaciente().getCodigopostal(),paciente.getDireccionPaciente().getProvincia(),
                        paciente.getDireccionPaciente().getCalle(),paciente.getDireccionPaciente().getDistrito(),
                        paciente.getDireccionPaciente().getCiudad(),paciente.getDireccionPaciente().getNumero(),
                        paciente.getDireccionPaciente().getComplemento())));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "desactiva a un paciente mediante el id")
    public ResponseEntity eleminarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();


       // Paciente paciente = pacienteRepository.getReferenceById(id);
        //pacienteRepository.delete(paciente); eliminacion definiva
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "obtiene los datos de un paciente")
    public ResponseEntity<DatosRespuestaPaciente> retornarDatosPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        var datosPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(),paciente.getTelefono(), paciente.getDocumentoidentidad(),
                new DatosDireccionPaciente(paciente.getDireccionPaciente().getUrbanizacion(),
                        paciente.getDireccionPaciente().getCodigopostal(),
                        paciente.getDireccionPaciente().getProvincia(), paciente.getDireccionPaciente().getCalle(),
                        paciente.getDireccionPaciente().getDistrito(), paciente.getDireccionPaciente().getCiudad(),
                        paciente.getDireccionPaciente().getNumero(),paciente.getDireccionPaciente().getComplemento()));
        return ResponseEntity.ok(datosPaciente);

    }

}