package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import models.ProfesorModel;
@Repository
public interface ProfesorRepository extends JpaRepository<ProfesorModel, Integer>{
    public List<ProfesorModel> findByNomeContainingIgnoreCase(String nombreProf);
    List<ProfesorModel> findByNombreProfContainingIgnoreCase(String nombreProf);
}
