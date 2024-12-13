package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import models.CategoriaModel;
@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, Integer>{
    public List<CategoriaModel> findByNomeContainingIgnoreCase(String nombreCat);
}