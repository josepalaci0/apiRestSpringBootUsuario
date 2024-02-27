package com.example.demo.services;

import com.example.demo.models.TrabajadorModel;
import com.example.demo.repositories.TrabajadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class TrabajadorService {

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    public void insertarTrabajador(TrabajadorModel trabajador) {
        trabajadorRepository.insertarTrabajador(trabajador);
    }

    public void actualizarTrabajador(TrabajadorModel trabajador) {
        trabajadorRepository.actualizarTrabajador(trabajador);
    }

    public boolean eliminarTrabajador(Long id) {
        return trabajadorRepository.eliminarTrabajador(id);
    }

    public List<TrabajadorModel> listarTrabajadores() {
        return trabajadorRepository.listarTrabajadores();
    }
}
