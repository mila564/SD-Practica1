package sd.urjc.proyecto.model;

import java.util.List;
import java.time.LocalDate;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cultivo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String nombre; //especie
	private String variedad;
	private String zona;
	private LocalDate fechaPlantacion;
	@OneToMany
	private List<Tratamiento> tratamientos;

	
	
	public Cultivo() {
		
	}
   
	public Cultivo(String nombre, String variedad, String zona, LocalDate fechaPlantacion, List<Tratamiento> tratamientos) {
		this.nombre = nombre;
		this.variedad = variedad;
		this.zona = zona;
		this.fechaPlantacion = fechaPlantacion;
		this.tratamientos = tratamientos;
		
	}
	public Cultivo(String nombre, String variedad, String zona, LocalDate fechaPlantacion) {
		this.nombre = nombre;
		this.variedad = variedad;
		this.zona = zona;
		this.fechaPlantacion = fechaPlantacion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getVariedad() {
		return variedad;
	}

	public void setVariedad(String variedad) {
		this.variedad = variedad;
	}
	
	
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	
	public LocalDate getFechaPlantacion() {
		return fechaPlantacion;
	}

	public void setFechaPlantacion(LocalDate fechaPlantacion) {
		this.fechaPlantacion = fechaPlantacion;
	}
	
	public List<Tratamiento> getTratamientos() {
		return tratamientos;
	}

	public void setTratamientos(List<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
	}
   
}
