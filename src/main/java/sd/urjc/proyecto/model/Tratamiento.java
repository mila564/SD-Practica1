package sd.urjc.proyecto.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Tratamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTratamiento;
    private Long idCultivo;
    private Long idProducto;
    private String numLoteProducto;
    private LocalDate inicioTratamiento;
    private LocalDate finPlazoSeguridad;
    private LocalDate finPlazoNoRecoleccion;

    public Tratamiento() {

    }

    public Tratamiento(Long idCultivo,
                       Long idProducto,
                       String numLoteProducto,
                       LocalDate inicioTratamiento,
                       LocalDate finPlazoSeguridad,
                       LocalDate finPlazoNoRecoleccion) {
        this.idCultivo = idCultivo;
        this.idProducto = idProducto;
        this.numLoteProducto = numLoteProducto;
        this.inicioTratamiento = inicioTratamiento;
        this.finPlazoSeguridad = finPlazoSeguridad;
        this.finPlazoNoRecoleccion = finPlazoNoRecoleccion;
    }

    public Long getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(Long idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public Long getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(Long idCultivo) {
        this.idCultivo = idCultivo;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNumLoteProducto() {
        return numLoteProducto;
    }

    public void setNumLoteProducto(String numLoteProducto) {
        this.numLoteProducto = numLoteProducto;
    }

    public LocalDate getInicioTratamiento() {
        return inicioTratamiento;
    }

    public void setInicioTratamiento(LocalDate inicioTratamiento) {
        this.inicioTratamiento = inicioTratamiento;
    }

    public LocalDate getFinPlazoSeguridad() {
        return finPlazoSeguridad;
    }

    public void setFinPlazoSeguridad(LocalDate finPlazoSeguridad) {
        this.finPlazoSeguridad = finPlazoSeguridad;
    }

    public LocalDate getFinPlazoNoRecoleccion() {
        return finPlazoNoRecoleccion;
    }

    public void setFinPlazoNoRecoleccion(LocalDate finPlazoNoRecoleccion) {
        this.finPlazoNoRecoleccion = finPlazoNoRecoleccion;
    }
}
