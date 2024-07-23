package com.example.demo.controllers;

import com.example.demo.models.TrabajadorModel;
import com.example.demo.services.TrabajadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trabajadores")
public class TrabajadorController {

    @Autowired
    private TrabajadorService trabajadorService;

    @PostMapping("/insertar")
    public ResponseEntity<String> insertarTrabajador(@RequestBody TrabajadorModel trabajador) {
        trabajadorService.insertarTrabajador(trabajador);
        return ResponseEntity.status(HttpStatus.CREATED).body("Trabajador insertado correctamente");
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarTrabajador(@RequestBody TrabajadorModel trabajador) {
        trabajadorService.actualizarTrabajador(trabajador);
        return ResponseEntity.status(HttpStatus.OK).body("Trabajador actualizado correctamente");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTrabajador(@PathVariable Long id) {
        if (trabajadorService.eliminarTrabajador(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("Trabajador con ID " + id + " eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el trabajador con ID " + id);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TrabajadorModel>> listarTrabajadores() {
        List<TrabajadorModel> trabajadores = trabajadorService.listarTrabajadores();
        if (!trabajadores.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(trabajadores);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(trabajadores);
        }
    }
}
