package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")

public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Registra un nuevo medico en la base de datos")
        public ResponseEntity resgistrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                             UriComponentsBuilder uriComponentsBuilder) {
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
               medico.getEmail(), medico.getTelefono(), medico.getEspecialidad().toString(),
               new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                       medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                       medico.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }

    @GetMapping
    @Operation(summary = "Obtiene el listado de los medicos en base de datos activos")
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 10) Pageable paginacion){
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
        //return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Actualiza los datos de un medico existente")
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){

        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        medicoRepository.save(medico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getEspecialidad().toString(),
               new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                       medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                       medico.getDireccion().getComplemento())));

    }
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "desaciva a un medico de la base de datos")
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    @Operation(summary = "se obtiene los datos de un medico mediante el id")
    public ResponseEntity<DatosRespuestaMedico> retornarDatosMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedicos = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedicos);
    }

    //public void eliminarMedico(@PathVariable Long id){
      //  Medico medico = medicoRepository.getReferenceById(id);
        //medicoRepository.delete(medico);
    //}
}
