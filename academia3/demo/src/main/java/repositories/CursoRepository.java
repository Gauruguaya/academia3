package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import models.CursoModel;
@Repository
public interface CursoRepository extends JpaRepository<CursoModel, Integer>{
    List<CursoModel> findByNombreCursoContainingIgnoreCase(String nombreCurso);
    @Query("SELECT c FROM CursoModel c WHERE c.nombre LIKE %:keyword%")
    List<CursoModel> searchByKeyword(@Param("keyword") String keyword);
    @Query("SELECT c FROM CursoModel c WHERE " +
       "LOWER(c.nombreCurso) LIKE LOWER(CONCAT('%', :keyword1, '%')) OR " +
       "LOWER(c.nombreCurso) LIKE LOWER(CONCAT('%', :keyword2, '%'))")
    List<CursoModel> searchByMultipleKeywords(@Param("keyword1") String keyword1, @Param("keyword2") String keyword2);
    List<CursoModel> findByCategoriaId(Integer categoriaId);
    List<CursoModel> findByProfesorId(Integer profesorId);
}



