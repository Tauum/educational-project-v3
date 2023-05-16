package com.example.javaspringboot.Additional.Controller;


import com.example.javaspringboot.Additional.Model.ContactForm;
import com.example.javaspringboot.Additional.Service.ContactFormService;
import com.example.javaspringboot.Additional.Service.FeedbackService;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin( originPatterns = "*", maxAge = 3600, allowCredentials = "true")

@RestController
@RequestMapping("/ContactForms")
public class ContactFormController {

    private final ContactFormService contactFormService;

    public ContactFormController(ContactFormService contactFormService) {
        this.contactFormService = contactFormService;
    }

    @GetMapping
    public ResponseEntity<List<ContactForm>> getAllContactForm()
    {
        List<ContactForm> contactForms = contactFormService.findAllOrderByDate();
        return new ResponseEntity<>(contactForms, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ContactForm> addContactForm(@RequestBody ContactForm contactForm)
    {
        ContactForm newContactForm = contactFormService.addContactForm(contactForm);
        return new ResponseEntity<>(newContactForm, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteContactForm(@PathVariable("id") UUID id)
    {
        contactFormService.deleteContactForm(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteContactForm()
    {
        contactFormService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

