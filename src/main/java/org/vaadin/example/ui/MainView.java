package org.vaadin.example.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.vaadin.example.backend.entity.Company;
import org.vaadin.example.backend.entity.Contact;
import org.vaadin.example.service.CompanyService;
import org.vaadin.example.service.ContactService;

import java.util.List;


/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route("")
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends VerticalLayout {
    private ContactService contactService;
    private CompanyService companyService;
    Grid<Contact> grid = new Grid<Contact>(Contact.class);
    TextField filterText = new TextField();
    ContactForm contactForm;


    public MainView( ContactService contactService,CompanyService companyService) {

        this.companyService = companyService;
        this.contactService=contactService;
        configureGrid();
        configureFilter();
        addClassName("list-view");
        setSizeFull();
        contactForm = new ContactForm(companyService.findAll());
        //WRAP FORM IN DIV
        Div content = new Div(grid, contactForm);
        content.addClassName("content");
        content.setSizeFull();
        add(filterText, content);
        updateList();
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by name");
        //the little x
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e->{
           updateList(filterText.getValue());
        });
    }

    private void updateList() {
        List<Contact> allContacts = this.contactService.findAll();
        //custom col
       // grid.removeColumnByKey("company");
        grid.setItems(allContacts);
        grid.addColumn(contact -> {
            Company c = contact.getCompany();
            return c == null ? "-" : c.getName();
        }).setHeader("Company");
    }

    private void updateList(String keyword) {
        List<Contact> allContacts = this.contactService.findAll(keyword);
        //custom col
        // grid.removeColumnByKey("company");
        grid.setItems(allContacts);
        grid.addColumn(contact -> {
            Company c = contact.getCompany();
            return c == null ? "-" : c.getName();
        }).setHeader("Company");
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email", "status");
        //set length of each col
        grid.getColumns().forEach(col->col.setAutoWidth(true));
    }

}
