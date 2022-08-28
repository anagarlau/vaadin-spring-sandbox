package org.vaadin.example.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import org.vaadin.example.backend.entity.Company;
import org.vaadin.example.backend.entity.Contact;

import java.util.List;

//custom component
public class ContactForm extends FormLayout {
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    ComboBox<Contact.Status> statusComboBox = new ComboBox<>("Status");
    ComboBox<Company> companyComboBox = new ComboBox<>("Company");
    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");
    Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);
    public ContactForm(List<Company> companies){
        //add fields to custom component
        addClassName("contact-form");
        binder.bindInstanceFields(this);
        statusComboBox.setItems(Contact.Status.values());
        companyComboBox.setItems(companies);
        companyComboBox.setItemLabelGenerator(c->c.getName());
        add(firstName, lastName, email, statusComboBox, companyComboBox,  createButtonsLayout());


    }

    private Component createButtonsLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);
        horizontalLayout.add(save, delete, close);
        return horizontalLayout;
    }

}
