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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dtos.ProfesorDto;
import jakarta.validation.Valid;
import models.ProfesorModel;
import repositories.ProfesorRepository;

@Controller
@RequestMapping("/profesor")
public class ProfesorController {
    @Autowired
    ProfesorRepository repository;

    @GetMapping("/")
    public String index() {
        return "profesor/index";
    }

    @GetMapping("/profesor/inserir")
    public String inserirProfesor(Model model) {
        model.addAttribute("profesorDto", new ProfesorDto("", "", ""));
        return "profesor/inserirProf";
    }

    @PostMapping("/profesor/inserir/")
    public String inserirBD(
        @ModelAttribute @Valid ProfesorDto profesorDto, 
        BindingResult result, RedirectAttributes msg) {
        if(result.hasErrors()) {
            msg.addFlashAttribute("erroCadastrar", "Error al registrar nuevo profesor");
            return "redirect:../../profesor/inserir/";
        }    
        var profesorModel = new ProfesorModel();
        BeanUtils.copyProperties(profesorDto, profesorModel);
        repository.save(profesorModel);
        msg.addFlashAttribute("sucessoCadastrar", "Profesor registrado!");
        return "redirect:../";
    }        

    @PostMapping("/profesor/listar/")
    public ModelAndView listarProfesores(){
        ModelAndView mv = new ModelAndView("profesor/listar");
        List<ProfesorModel> lista = repository.findAll();
        mv.addObject("profesores", lista);
        return mv;
    }

    @GetMapping("/profesor/listar/")
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("profesor/listar");
        List<ProfesorModel> lista = repository.findAll();
        mv.addObject("profesores", lista);
        return mv;
    }

    @GetMapping("/profesor/editar/{id}")
    public ModelAndView editar(@PathVariable int id) {
        ModelAndView mv = new ModelAndView("profesor/editar");
        Optional<ProfesorModel> profesor = repository.findById(id);
        if (profesor.isPresent()) {
            mv.addObject("profesor", profesor.get());
        } else {
            mv.addObject("error", "Profesor no encontrado");
        }
        return mv;
    }

    @PostMapping("/profesor/editar/{id}")
    public String editarBD(
        @ModelAttribute @Valid ProfesorDto profesorDto, 
        BindingResult result, RedirectAttributes msg,
        @PathVariable int id) {
        Optional<ProfesorModel> profesor = repository.findById(id);

        if(result.hasErrors()) {
            msg.addFlashAttribute("erroEditar", "Error al editar profesor");
            return "redirect:../../profesor/listar/";
        }
        if (profesor.isPresent()) {
            var profesorModel = profesor.get();
            BeanUtils.copyProperties(profesorDto, profesorModel);
            repository.save(profesorModel);
            msg.addFlashAttribute("sucessoEditar", "Profesor editado!");
        } else {
            msg.addFlashAttribute("erroEditar", "Profesor no encontrado");
        }
        return "redirect:../../profesor/listar/";
    }
    @GetMapping("/profesor/excluir/{id}")
    public String excluir(@PathVariable int id){
        Optional<ProfesorModel> profesor = repository.findById(id);
        if(profesor.isEmpty()) {
            return "redirect:../../profesor/listar/";
        }
        repository.deleteById(id);
        return "redirect:../../profesor/listar/";
    }
}

    

