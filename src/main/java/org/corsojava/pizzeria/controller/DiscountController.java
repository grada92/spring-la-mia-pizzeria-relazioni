package org.corsojava.pizzeria.controller;

import org.corsojava.pizzeria.model.Discount;
import org.corsojava.pizzeria.model.Pizzeria;
import org.corsojava.pizzeria.repository.DiscountRepository;
import org.corsojava.pizzeria.repository.PizzeriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/discount")
public class DiscountController {
	
	
	@Autowired
	PizzeriaRepository pizzeriarep;
	
	@Autowired
	DiscountRepository discountrep;
	
	
	@GetMapping("/create")		//gestirà le richieste GET di tipo /discount/create?id=xxx
	public String create(
		@RequestParam(name="id", required = true) Integer id,
		Model model) throws Exception {
		
		Discount discount =new Discount();	//non esiste ancora sul DB
			
		
		Pizzeria pizzeria = pizzeriarep.getReferenceById(id);
		discount.setPizzeria(pizzeria);
		
		model.addAttribute("discount", discount);
		
		return "createD";
	}
	
	@PostMapping("/create")  	//gestirà le richieste di tipo POST di tipo /borrowings/create
	public String store(
		@Valid @ModelAttribute("discount") Discount formDiscount, 
		BindingResult bindingResult,
		Model model){
		
		if (bindingResult.hasErrors())
			return "createD";
		
		Pizzeria pizzeria =formDiscount.getPizzeria();
		discountrep.save(formDiscount);
		
		return "redirect:/pizzeria"; //genera un altro get
		
	}
	
	
}
