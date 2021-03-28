package sd.urjc.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import sd.urjc.proyecto.model.Tratamiento;
import java.util.Optional;

@Service
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {

    @Override
    Optional<Tratamiento> findById(Long aLong);
}
