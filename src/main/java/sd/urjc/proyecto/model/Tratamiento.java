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
    private long idTratamiento;
    private long idCultivo;
    private long idProducto;
    private String numLoteProducto;
    private LocalDate inicioTratamiento;
    private LocalDate finPlazoSeguridad;
    private LocalDate finPlazoNoRecoleccion;

    public Tratamiento() {

    }

    public Tratamiento(long idCultivo,
                       long idProducto,
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

    public long getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(long idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public long getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(long idCultivo) {
        this.idCultivo = idCultivo;
    }

    public long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(long idProducto) {
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
    public String toString() {
    	return "ID Tratamiento: "+this.idTratamiento+"\n"
    			+ "ID cultivo:" + this.idCultivo + "\n"
    			+ "ID producto:" + this.idProducto + "\n"
    			+ "Numero de lote del producto:" + this.numLoteProducto + "\n"
    			+ "Fecha de comienzo de tratamiento:" + this.inicioTratamiento + "\n"
    			+ "Fecha de fin de plazo de seguridad:" +  this.finPlazoSeguridad + "\n"
    			+ "Fecha de fin de plazo de no recoleccion:" + this.finPlazoNoRecoleccion + "\n";
    }
}
