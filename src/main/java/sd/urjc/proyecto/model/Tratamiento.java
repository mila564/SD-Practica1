package sd.urjc.proyecto.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Tratamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idTratamiento;
    //private long idCultivo;
    @ManyToOne
    private Cultivo cultivo;
    //private long idProducto;
    @ManyToOne
    private Producto producto;
    private String numLoteProducto;
    private LocalDate inicioTratamiento;
    private LocalDate finPlazoReentrada;
    private LocalDate finPlazoRecoleccion;

    public Tratamiento() {

    }

    public Tratamiento(
            Cultivo cultivo,
            Producto producto,
            String numLoteProducto,
            LocalDate inicioTratamiento,
            LocalDate finPlazoReentrada,
            LocalDate finPlazoRecoleccion
    ) {
        this.cultivo = cultivo;
        this.producto = producto;
        this.numLoteProducto = numLoteProducto;
        this.inicioTratamiento = inicioTratamiento;
        this.finPlazoReentrada = finPlazoReentrada;
        this.finPlazoRecoleccion = finPlazoRecoleccion;
    }

    public long getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(long idTratamiento) {
        this.idTratamiento = idTratamiento;
    }


    public Cultivo getCultivo() {
        return cultivo;
    }

    public void setCultivo(Cultivo cultivo) {
        this.cultivo = cultivo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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

    public LocalDate getFinPlazoReentrada() {
        return finPlazoReentrada;
    }

    public void setFinPlazoReentrada(LocalDate finPlazoReentrada) {
        this.finPlazoReentrada = finPlazoReentrada;
    }

    public LocalDate getFinPlazoRecoleccion() {
        return finPlazoRecoleccion;
    }

    public void setFinPlazoRecoleccion(LocalDate finPlazoRecoleccion) {
        this.finPlazoRecoleccion = finPlazoRecoleccion;
    }

    public String toString() {
    	return "ID Tratamiento: "+this.idTratamiento+"\n"
    			+ "ID cultivo:" + this.cultivo + "\n"
    			+ "ID producto:" + this.producto + "\n"
    			+ "Numero de lote del producto:" + this.numLoteProducto + "\n"
    			+ "Fecha de comienzo de tratamiento:" + this.inicioTratamiento + "\n"
    			+ "Fecha de fin de plazo de seguridad:" +  this.finPlazoReentrada + "\n"
    			+ "Fecha de fin de plazo de no recoleccion:" + this.finPlazoRecoleccion + "\n";
    }
}
