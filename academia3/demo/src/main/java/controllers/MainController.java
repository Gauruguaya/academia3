package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import models.CategoriaModel;
import models.CursoModel;
import models.ProfesorModel;
import repositories.CategoriaRepository;
import repositories.CursoRepository;
import repositories.ProfesorRepository;

@Controller
public class MainController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProfesorRepository profesorRepository;


    //@GetMapping("/")
    //public String index(Model model) {
    //    List<CategoriaModel> categorias = categoriaRepository.findAll();
    //    model.addAttribute("categorias", categorias);
    //    return "index";
    //}

    @GetMapping("/")
    public String index(Model model) {
        //return new ModelAndView("index").addObject()
        List<CategoriaModel> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        return "index";
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam("query") String query, Model model) {
        List<CursoModel> cursos = cursoRepository.findByNombreCursoContainingIgnoreCase(query);
        List<ProfesorModel> profesores = profesorRepository.findByNombreProfContainingIgnoreCase(query);
        model.addAttribute("cursos", cursos);
        model.addAttribute("profesores", profesores);
        return "buscar";
    }

    @GetMapping("/categoria/{id}")
    public String cursosPorCategoria(@PathVariable("id") Integer id, Model model) {
        List<CursoModel> cursos = cursoRepository.findByCategoriaId(id);
        model.addAttribute("cursos", cursos);
        return "categoria/categoria";
    }

    @GetMapping("/curso/{id}")
    public String detalleCurso(@PathVariable("id") Integer id, Model model) {
        CursoModel curso = cursoRepository.findById(id).orElse(null);
        model.addAttribute("cursos", curso);
        return "curso/curso";
    }

    @GetMapping("/listarCategorias")
    public String listarCategorias(Model model) {
        List<CategoriaModel> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        return "categoria/listarCat";
    }

    @GetMapping("/listarProfesores")
    public String listarProfesores(Model model) {
        List<ProfesorModel> profesores = profesorRepository.findAll();
        model.addAttribute("profesores", profesores);
        return "profesor/listarProf";
    }
   
}