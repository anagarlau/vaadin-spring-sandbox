package org.vaadin.example.ui.views.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.vaadin.example.backend.entity.Company;
import org.vaadin.example.backend.entity.Contact;
import org.vaadin.example.service.CompanyService;
import org.vaadin.example.service.ContactService;
import org.vaadin.example.ui.MainLayout;

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
@Route(value = "", layout = MainLayout.class)
@PageTitle("Contact | Vaadin CRM")
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)

@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class ListView extends VerticalLayout {
    private ContactService contactService;
    private CompanyService companyService;
    Grid<Contact> grid = new Grid<Contact>(Contact.class);
    TextField filterText = new TextField();
    ContactForm contactForm;


    public ListView(ContactService contactService, CompanyService companyService) {

        this.companyService = companyService;
        this.contactService=contactService;
        configureGrid();
        HorizontalLayout toolBar = getToolBar();
        addClassName("list-view");
        setSizeFull();
        contactForm = new ContactForm(companyService.findAll());
        contactForm.addListener(ContactForm.SaveEvent.class, e->this.saveForm(e));
        contactForm.addListener(ContactForm.DeleteEvent.class, e->{
            System.out.println("Delete btn " + e.getContact());
            contactService.delete(e.getContact());
            updateList();
        });
        contactForm.addListener(ContactForm.CloseEvent.class, e-> closeForm());
        //WRAP FORM IN DIV
        Div content = new Div(grid, contactForm);
        content.addClassName("content");
        content.setSizeFull();
        add(toolBar, content);
        updateList();
        closeForm();
    }

    private void saveForm(ContactForm.SaveEvent evt) {
        System.out.println(evt.getContact());
        if(contactService.save(evt.getContact()) != null){
            updateList();
            contactForm.setContact(null);
            closeForm();
        }else{
            Notification.show("Please Insert a valid contact");
        }



    }


    public void closeForm(){
        //to make it pop -> configureGrid method
        contactForm.setContact(null);
        contactForm.setVisible(false);
        removeClassName("editing");
    }


    private HorizontalLayout getToolBar() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        filterText.setPlaceholder("Filter by name");
        //the little x
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        //search field functionality
        filterText.addValueChangeListener(e->{
           updateList(filterText.getValue());
        });
        Button addContact = new Button("Add contact");
        addContact.addClickListener(click->{
           contactForm.setVisible(true);
           contactForm.setContact(new Contact());

        });
        horizontalLayout.add(filterText, addContact);
        horizontalLayout.addClassName("toolbar");
        return horizontalLayout;
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
        grid.asSingleSelect().addValueChangeListener(e->{
            editContact(e.getValue());
        });
    }

    private void editContact(Contact contact){
        if(contact == null){
            closeForm();
        }else{
            contactForm.setContact(contact);
            contactForm.setVisible(true);
//            contactForm.getStatusComboBox().setValue(contact.getStatus());
//            contactForm.getCompanyComboBox().setValue(contact.getCompany());
            addClassName("editing");

        }
    }

}
