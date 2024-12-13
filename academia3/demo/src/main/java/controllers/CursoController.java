package controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dtos.CursoDto;
import jakarta.validation.Valid;
import models.CursoModel;
import repositories.CategoriaRepository;
import repositories.CursoRepository;
import repositories.ProfesorRepository;

@Controller
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    @GetMapping("/curso/listar")
    public ModelAndView listarCursos() {
        ModelAndView mv = new ModelAndView("curso/listar");
        List<CursoModel> lista = cursoRepository.findAll();
        mv.addObject("cursos", lista);
        return mv;
    }

    @PostMapping("/curso/listar")
    public ModelAndView listarCursosPost() {
        ModelAndView mv = new ModelAndView("curso/listar");
        List<CursoModel> lista = cursoRepository.findAll();
        mv.addObject("cursos", lista);
        return mv;
    }

    @GetMapping("/curso/buscar")
public String buscar(@RequestParam("query") String query, Model model) {
    String[] keywords = query.split("\\s+");
    List<CursoModel> cursos;
    if (keywords.length > 1) {
        cursos = cursoRepository.searchByMultipleKeywords(keywords[0], keywords[1]);
    } else {
        cursos = cursoRepository.searchByKeyword(query);
    }
    model.addAttribute("cursos", cursos);
    return "curso/buscar";
    }

    @GetMapping("/curso/inserir")
    public String inserirCurso(Model model) {
        model.addAttribute("cursoDto", new CursoDto());
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("profesores", profesorRepository.findAll());
        return "curso/inserirCurso";
    }

    @PostMapping("/curso/inserir")
    public String inserirBD(
        @ModelAttribute @Valid CursoDto cursoDto, 
        BindingResult result, RedirectAttributes msg) {
        if(result.hasErrors()) {
            return "curso/inserir";
        }
        CursoModel cursoModel = new CursoModel();
        BeanUtils.copyProperties(cursoDto, cursoModel);
        cursoModel.setCategoria(categoriaRepository.findById(cursoDto.getCategoriaId()).orElse(null));
        cursoModel.setProfesor(profesorRepository.findById(cursoDto.getProfesorId()).orElse(null));
        cursoRepository.save(cursoModel);
        msg.addFlashAttribute("sucessoCadastrar", "Curso registrado!");
        return "redirect:../../curso/listar/";
    }

    @GetMapping("/curso/editar/{id}")
    public String editar(@PathVariable int id, Model model) {
        Optional<CursoModel> curso = cursoRepository.findById(id);
        if (curso.isPresent()) {
            model.addAttribute("cursos", curso.get());
            model.addAttribute("categorias", categoriaRepository.findAll());
            model.addAttribute("profesores", profesorRepository.findAll());
            return "curso/editar";
        } else {
            model.addAttribute("error", "Curso no encontrado");
            return "redirect:../../curso/listar/";
        }
    }

    @PostMapping("/curso/editar/{id}")
    public String editarBD(
        @PathVariable int id,
        @ModelAttribute @Valid CursoDto cursoDto, 
        BindingResult result, RedirectAttributes msg) {
        if(result.hasErrors()) {
            return "curso/editar";
        }
        Optional<CursoModel> curso = cursoRepository.findById(id);
        if (curso.isPresent()) {
            CursoModel cursoModel = curso.get();
            BeanUtils.copyProperties(cursoDto, cursoModel);
            cursoModel.setCategoria(categoriaRepository.findById(cursoDto.getCategoriaId()).orElse(null));
            cursoModel.setProfesor(profesorRepository.findById(cursoDto.getProfesorId()).orElse(null));
            cursoRepository.save(cursoModel);
            msg.addFlashAttribute("sucessoEditar", "Curso editado!");
        } else {
            msg.addFlashAttribute("erroEditar", "Curso no encontrado");
        }
        return "redirect:../../curso/listar/";
    }

    @GetMapping("/curso/excluir/{id}")
    public String excluir(@PathVariable int id, RedirectAttributes msg) {
        Optional<CursoModel> curso = cursoRepository.findById(id);
        if(curso.isEmpty()) {
            msg.addFlashAttribute("erroExcluir", "Curso no encontrado");
            return "redirect:../../curso/listar/";
        }
        cursoRepository.deleteById(id);
        msg.addFlashAttribute("sucessoExcluir", "Curso eliminado!");
        return "redirect:../../curso/listar/";
    }
}