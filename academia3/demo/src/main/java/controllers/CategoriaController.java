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

import dtos.CategoriaDto;
import jakarta.validation.Valid;
import models.CategoriaModel;
import repositories.CategoriaRepository;

	@Controller
	@RequestMapping("/categoria")
	public class CategoriaController {
		@Autowired
		CategoriaRepository repository;
		
		@GetMapping("/")
		public String index() {
			return "categoria/index";
		}
		
		@GetMapping("/categoria/inserir/")
		public String inserirCategoria(Model model) {
		model.addAttribute("categoriaDto", new CategoriaDto("default value"));
    	return "categoria/inserirCat";
		}

		@PostMapping("/categoria/inserir/")
		public String inserirBD(
				@ModelAttribute @Valid CategoriaDto categoriaDto, 
				BindingResult result, RedirectAttributes msg) {
			if(result.hasErrors()) {
				msg.addFlashAttribute("erroCadastrar", "Error al registrar nueva categoría");
				return "redirect:../../categoria/inserir/";}	
			var categoriaModel = new CategoriaModel();
			BeanUtils.copyProperties(categoriaDto, categoriaModel);
			repository.save(categoriaModel);
			msg.addFlashAttribute("sucessoCadastrar", "Categoría registrada!");
			return "redirect:../../categoria/listar/";
		}		
		@PostMapping("/categoria/listar/")
		public ModelAndView listarCategorias(){
			ModelAndView mv = new ModelAndView("categoria/listar");
			List<CategoriaModel> lista = repository.findAll();
			mv.addObject("categorias", lista);
			return mv;
		}
		@GetMapping("/categoria/listar/")
		public ModelAndView listar() {
			ModelAndView mv = new ModelAndView("categoria/listar");
			List<CategoriaModel> lista = repository.findAll();
			mv.addObject("categorias", lista);
			return mv;
		}
		@GetMapping("/categoria/editar/{id}")
		public ModelAndView editar(@PathVariable int id){
			ModelAndView mv = new ModelAndView("categoria/editar");
			Optional<CategoriaModel> categoria = repository.findById(id);
			mv.addObject("id", categoria.get().getIdCategoria());
			mv.addObject("descricao", categoria.get().getNombreCat());
			return mv;
		}	
		
		@PostMapping("/categoria/editar/{id}")
		public String editarBD(
				@ModelAttribute @Valid CategoriaDto categoriaDto, 
				BindingResult result, RedirectAttributes msg,
				@PathVariable int id) {
			
			Optional<CategoriaModel> categoria = repository.findById(id);

			if(result.hasErrors()) {
				msg.addFlashAttribute("erroEditar", "Error al editar categoría");
				return "redirect:../../categoria/listar/";
			}
			var categoriaModel = categoria.get();
			BeanUtils.copyProperties(categoriaDto, categoriaModel);
			repository.save(categoriaModel);
			msg.addFlashAttribute("sucessoEditar", "¡Categoria editada!");
			return "redirect:../../categoria/listar/";
		}	
		@GetMapping("/categoria/excluir/{id}")
		public String excluir(@PathVariable int id){
			Optional<CategoriaModel> categoria = repository.findById(id);
			if(categoria.isEmpty()) {
				return "redirect:../../categoria/listar/";
			}
			repository.deleteById(id);
			return "redirect:../../categoria/listar/";
		}
			
	}
