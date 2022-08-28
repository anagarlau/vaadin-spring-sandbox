package org.vaadin.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.backend.entity.Company;
import org.vaadin.example.backend.entity.Contact;
import org.vaadin.example.backend.repository.CompanyRepo;
import org.vaadin.example.backend.repository.ContactRepo;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ContactService {
    private static final Logger logger = Logger.getLogger(ContactService.class.getName());
    private ContactRepo contactRepository;
    private CompanyRepo companyRepository;

    @Autowired
    public ContactService(ContactRepo contactRepo, CompanyRepo companyRepo) {
        this.contactRepository = contactRepo;
        this.companyRepository = companyRepo;
    }

    public List<Contact> findAll(){
        return this.contactRepository.findAll();
    }

    public List<Contact> findAll(String keyword){
        List<Contact> all = this.contactRepository.findAll();
        if(keyword == null || keyword.isEmpty()){
            return all;
        }else{
            return all.stream().filter(el-> {

                return  el.getFirstName().toLowerCase().contains(keyword.toLowerCase()) || el.getLastName().toLowerCase().contains(keyword.toLowerCase());
            }).collect(Collectors.toList());
        }

    }

    public long count(){
        return this.contactRepository.count();
    }

    public void delete(Contact contact){
        contactRepository.delete(contact);
    }

    public void save(Contact contact){
        if(contact == null){
            logger.log(Level.SEVERE, "Contact is null. Is your form connected");
            return;
        }
        contactRepository.save(contact);
    }

    @PostConstruct
    public void populateTestData() {
        if (companyRepository.count() == 0) {
            companyRepository.saveAll(
                    Stream.of("Path-Way Electronics", "E-Tech Management", "Path-E-Tech-Management")
                                    .map(Company::new)
                                    .collect(Collectors.toList()));
        }
        if (contactRepository.count() == 0) {
            Random r = new Random(0);
            List<Company> companies = companyRepository.findAll();
            contactRepository.saveAll(
                    Stream.of("Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
                                    "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
                                    "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson",
                                    "Kelly Gustavsson",
                                    "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
                                    "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
                                    "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
                                    "Jaydan Jackson", "Bernard Nilsen")
                            .map(name -> {
                                String[] split = name.split(" ");
                                Contact contact = new Contact();
                                contact.setFirstName(split[0]);
                                contact.setLastName(split[1]);
                                contact.setCompany(companies.get(r.nextInt(companies.size())));
                                contact.setStatus(Contact.Status.values()[r.nextInt(Contact
                                        .Status.values().length)]);
                                String email = (contact.getFirstName() + "." + contact
                                        .getLastName() + "@" + contact.getCompany().getName().replaceAll("[\\s-]", "") +
                                        ".com").toLowerCase();
                                contact.setEmail(email);
                                return contact;
                            }).collect(Collectors.toList()));
        }
    }

}
