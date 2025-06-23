package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosCancelamientoConsulta;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;
    @PostMapping
    @Transactional
    @Operation(
            summary = "registra una consulta en la base de datos",
            description = "",
            tags = {"consulta", "post"})
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos)throws ValidacionDeIntegridad {

        var respounse = service.agendar(datos);

        return ResponseEntity.ok(respounse);

    }

    @DeleteMapping
    @Transactional
    public ResponseEntity Cancelar(@RequestBody @Valid DatosCancelamientoConsulta datos) {

        service.cancelar(datos);

        return ResponseEntity.noContent().build();
    }
}
