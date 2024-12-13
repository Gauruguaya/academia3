package dtos;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CursoDto {

    @NotBlank
    private String nombreCurso;

    @NotBlank
    private String descCurso;

    @NotNull
    private Date fechaInicioCurso;

    @NotNull
    private Date fechaFinCurso;

    @NotBlank
    private String imagenCurso;

    @NotNull
    private Integer categoriaId;

    @NotNull
    private Integer profesorId;

    // Getters and setters
    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getDescCurso() {
        return descCurso;
    }

    public void setDescCurso(String descCurso) {
        this.descCurso = descCurso;
    }

    public Date getFechaInicioCurso() {
        return fechaInicioCurso;
    }

    public void setFechaInicioCurso(Date fechaInicioCurso) {
        this.fechaInicioCurso = fechaInicioCurso;
    }

    public Date getFechaFinCurso() {
        return fechaFinCurso;
    }

    public void setFechaFinCurso(Date fechaFinCurso) {
        this.fechaFinCurso = fechaFinCurso;
    }

    public String getImagenCurso() {
        return imagenCurso;
    }

    public void setImagenCurso(String imagenCurso) {
        this.imagenCurso = imagenCurso;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Integer getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Integer profesorId) {
        this.profesorId = profesorId;
    }
}