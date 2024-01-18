package med.voll.api.controller;


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
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
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
    public ResponseEntity<Page<DatosListadoPaciente>> listadoPacientes(@PageableDefault(size = 10) Pageable paginacion){
        return ResponseEntity.ok(pacienteRepository.findByActivoTrue(paginacion).map(DatosListadoPaciente::new));
        //return ResponseEntity.ok(pacienteRepository.findAll(paginacion).map(DatosListadoPaciente::new));

    }

    @PutMapping
    @Transactional
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
    public ResponseEntity eleminarPaciente(@PathVariable Long id){
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();


       // Paciente paciente = pacienteRepository.getReferenceById(id);
        //pacienteRepository.delete(paciente); eliminacion definiva
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
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