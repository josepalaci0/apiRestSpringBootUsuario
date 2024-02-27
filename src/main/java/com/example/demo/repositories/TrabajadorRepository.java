package com.example.demo.repositories;

import com.example.demo.models.TrabajadorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Collections;



@Repository
public class TrabajadorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TrabajadorModel insertarTrabajador(TrabajadorModel trabajador) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE OR REPLACE PROCEDURE pro_insertar_trabajador(");
        sql.append("    p_cedula IN NUMBER,");
        sql.append("    p_nombre IN VARCHAR2,");
        sql.append("    p_apellido IN VARCHAR2,");
        sql.append("    p_fecha_nacimiento IN VARCHAR2,");
        sql.append("    p_estado IN VARCHAR2,");
        sql.append("    p_observacion IN VARCHAR2,");
        sql.append("    p_sexo IN VARCHAR2");
        sql.append(") AS ");
        sql.append("BEGIN ");
        sql.append("    INSERT INTO trabajador ( ");
        sql.append("        cedula, ");
        sql.append("        nombre, ");
        sql.append("        apellido, ");
        sql.append("        fecha_nacimiento, ");
        sql.append("        estado, ");
        sql.append("        observacion, ");
        sql.append("        sexo ");
        sql.append("    ) VALUES ( ");
        sql.append("        p_cedula, ");
        sql.append("        p_nombre, ");
        sql.append("        p_apellido, ");
        sql.append("        p_fecha_nacimiento, ");
        sql.append("        p_estado, ");
        sql.append("        p_observacion, ");
        sql.append("        p_sexo ");
        sql.append("    ); ");
        sql.append("    COMMIT; ");
        sql.append("    DBMS_OUTPUT.PUT_LINE('Trabajador insertado correctamente.'); ");
        sql.append("EXCEPTION ");
        sql.append("    WHEN OTHERS THEN ");
        sql.append("        DBMS_OUTPUT.PUT_LINE('Error al insertar el trabajador: ' || SQLERRM); ");
        sql.append("        ROLLBACK; ");
        sql.append("END; ");

        String sqlString = sql.toString();

        try {
            jdbcTemplate.update(sqlString);
            String procedureCall = "{ call pro_insertar_trabajador(?, ?, ?, ?, ?, ?, ?) }";
            jdbcTemplate.update(procedureCall, trabajador.getCedula(), trabajador.getNombre(), trabajador.getApellido(),
                    trabajador.getFechaNacimiento(), trabajador.getEstado(), trabajador.getObservacion(),
                    trabajador.getSexo());
            System.out.println("Trabajador insertado correctamente.");
            return trabajador; // Retorna el trabajador insertado
        } catch (Exception e) {
            System.out.println("Error al insertar el trabajador: " + e.getMessage());
            return null; // Retorna null en caso de error
        }
    }

    public TrabajadorModel actualizarTrabajador(TrabajadorModel trabajador) {

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE trabajador ");
        sql.append("SET ");
        sql.append("    cedula = ?, ");
        sql.append("    nombre = ?, ");
        sql.append("    apellido = ?, ");
        sql.append("    fecha_nacimiento = ?, ");
        sql.append("    estado = ?, ");
        sql.append("    observacion = ?, ");
        sql.append("    sexo = ? ");
        sql.append("WHERE ");
        sql.append("    id = ?");

        String sqlString = sql.toString();

        try {
            jdbcTemplate.update(sqlString, trabajador.getCedula(), trabajador.getNombre(), trabajador.getApellido(),
                    trabajador.getFechaNacimiento(), trabajador.getEstado(), trabajador.getObservacion(),
                    trabajador.getSexo(), trabajador.getId());
            System.out.println("Trabajador actualizado correctamente.");
            return trabajador; // Retorna el trabajador actualizado
        } catch (Exception e) {
            System.out.println("Error al actualizar el trabajador: " + e.getMessage());
            return null; // Retorna null en caso de error
        }
    }

    public boolean eliminarTrabajador(Long id) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE OR REPLACE PROCEDURE pro_eliminar_trabajador(");
        sql.append("    p_id IN NUMBER");
        sql.append(") AS ");
        sql.append("BEGIN ");
        sql.append("    DELETE FROM trabajador WHERE id = p_id; ");
        sql.append("    COMMIT; ");
        sql.append("    DBMS_OUTPUT.PUT_LINE('Trabajador eliminado correctamente.'); ");
        sql.append("EXCEPTION ");
        sql.append("    WHEN OTHERS THEN ");
        sql.append("        DBMS_OUTPUT.PUT_LINE('Error al eliminar el trabajador: ' || SQLERRM); ");
        sql.append("        ROLLBACK; ");
        sql.append("END; ");

        String procedureSql = sql.toString();
        String callSql = "{ call pro_eliminar_trabajador(?) }";

        try {
            jdbcTemplate.update(procedureSql); // Ejecuta el SQL para crear el procedimiento almacenado
            jdbcTemplate.update(callSql, id);
            System.out.println("Trabajador eliminado correctamente.");
            return true; // Retorna true indicando que el trabajador fue eliminado correctamente
        } catch (Exception e) {
            System.out.println("Error al eliminar el trabajador: " + e.getMessage());
            return false; // Retorna false indicando que ocurrió un error al intentar eliminar al trabajador
        }
    }
    
    public List<TrabajadorModel> listarTrabajadores() {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE OR REPLACE PROCEDURE pro_listar_trabajadores AS ");
        sql.append("BEGIN ");
        sql.append("    FOR trabajador_rec IN (SELECT * FROM trabajador) LOOP ");
        sql.append("        DBMS_OUTPUT.PUT_LINE(trabajador_rec.id || ' - ' || trabajador_rec.nombre); ");
        sql.append("    END LOOP; ");
        sql.append("END; ");

        try {
            jdbcTemplate.update(sql.toString()); // Ejecuta el SQL para crear el procedimiento almacenado
            return jdbcTemplate.query("CALL pro_listar_trabajadores()", (rs, rowNum) -> {
                TrabajadorModel trabajador = new TrabajadorModel();
                trabajador.setId(rs.getLong("id"));
                trabajador.setCedula(rs.getLong("cedula"));
                trabajador.setNombre(rs.getString("nombre"));
                trabajador.setApellido(rs.getString("apellido"));
                trabajador.setFechaNacimiento(rs.getString("fecha_nacimiento"));
                // Completa el resto de los campos según la estructura de tu tabla
                return trabajador;
            });
        } catch (Exception e) {
            System.out.println("Error al listar trabajadores: " + e.getMessage());
            return Collections.emptyList(); // Retorna una lista vacía en caso de error
        }
    }

}
