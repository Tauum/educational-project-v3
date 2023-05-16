package com.example.javaspringboot.Additional.Service;


import com.example.javaspringboot.Additional.Model.ContactForm;
import com.example.javaspringboot.Additional.Repository.ContactFormRepo;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ContactFormService {

    private final ContactFormRepo contactFormRepo;

    @Autowired
    public ContactFormService(ContactFormRepo contactFormRepo) {
        this.contactFormRepo = contactFormRepo;
    }

    public ContactForm addContactForm(ContactForm ContactForm) { return contactFormRepo.save(ContactForm); }

    public List<ContactForm> findAll(){ return contactFormRepo.findAll(); }

    //query method (auto generates method in spring back-backend)
    @Transactional
    public void deleteContactForm(UUID id) {
        ContactForm found = findContactFormById(id);
        contactFormRepo.deleteContactFormById(found.getId());}

    public ContactForm findContactFormById(UUID id) {
        ContactForm find = contactFormRepo.findContactFormById(id);
        if (find != null){return find;}
        return null;
    }

    public List<ContactForm> findAllOrderByDate() {
        return contactFormRepo.findAllByOrderByGeneratedDate();
    }

    public void deleteAll() {
        contactFormRepo.deleteAll();
    }
}