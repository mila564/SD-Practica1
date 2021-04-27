package sd.urjc.proyecto.repository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sd.urjc.proyecto.model.Cultivo;
import sd.urjc.proyecto.model.Producto;
import sd.urjc.proyecto.model.Tratamiento;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    Optional<Tratamiento> findById(long aLong);
    @Transactional
    void deleteByProducto(Producto producto);
    @Transactional
    void deleteByCultivo(Cultivo cultivo);	
    List<Tratamiento> findAllByFinPlazoReentradaGreaterThanEqualOrFinPlazoRecoleccionGreaterThanEqualOrderByFinPlazoReentradaAsc(LocalDate finPlazoReentrada, LocalDate finPlazoRecoleccion);
    List<Tratamiento> findAllByFinPlazoReentradaGreaterThanEqualOrFinPlazoRecoleccionGreaterThanEqualOrderByFinPlazoRecoleccionAsc(LocalDate finPlazoReentrada, LocalDate finPlazoRecoleccion);
    List<Tratamiento> findAllByFinPlazoReentradaGreaterThanEqualOrFinPlazoRecoleccionGreaterThanEqualOrderByCultivoNombreAsc(LocalDate finPlazoReentrada, LocalDate finPlazoRecoleccion);
    List<Tratamiento> findAllByFinPlazoReentradaGreaterThanEqualOrFinPlazoRecoleccionGreaterThanEqual(LocalDate finPlazoReentrada, LocalDate finPlazoRecoleccion);
	List<Tratamiento> findByOrderByFinPlazoReentradaAsc();
	List<Tratamiento> findByOrderByFinPlazoRecoleccionAsc();
	List<Tratamiento> findByOrderByCultivoNombreAsc();
}
