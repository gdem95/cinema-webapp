package com.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinema.model.User;
import com.cinema.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;
    
    // Mostra la schermata di login.
    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "<i class=\"bi bi-exclamation-triangle-fill\"></i> Le credenziali inserite non sono valide.");
        }
        return "login";
    }
    
    // Mostra la pagina di registrazione.
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register"; 
    }
    
    // Salva le informazioni del nuovo utente.
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user,
                               @RequestParam("confirmPassword") String confirm,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        
        if (!isValidEmail(user.getEmail())) {
            model.addAttribute("error", "<i class=\"bi bi-exclamation-triangle-fill\"></i> Indirizzo email non valido.");
            return "register";
        }
        
        if (!user.getPassword().equals(confirm)) {
            model.addAttribute("error", "<i class=\"bi bi-exclamation-triangle-fill\"></i> Le password inserite non corrispondono.");
            return "register";
        }
        
        if (!isStrong(user.getPassword())) {
            model.addAttribute("error", "<i class=\"bi bi-exclamation-triangle-fill\"></i> La password scelta non soddisfa i requisiti di sicurezza.");
            return "register";
        }
        try {
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("msg", "<i class=\"bi bi-info-circle-fill\"></i> <strong>Registrazione effettuata!</strong> Adesso puoi accedere con le tue credenziali.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage()); 
            return "register";
        }
    }

    // Verifica i requisiti di sicurezza della password.
    private boolean isStrong(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[^A-Za-z0-9].*");
    }

    // Verifica il formato dell'email.
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$");
    }
    
}
