package org.vaadin.example.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
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
import com.vaadin.flow.data.binder.PropertyId;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import org.vaadin.example.backend.entity.Company;
import org.vaadin.example.backend.entity.Contact;

import java.util.List;

//custom component
public class ContactForm extends FormLayout {
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    @PropertyId("status")
    ComboBox<Contact.Status> statusComboBox = new ComboBox<>("Status");
    @PropertyId("company")
    ComboBox<Company> companyComboBox = new ComboBox<>("Company");
    Button save = new Button("save");
    Button delete = new Button("delete");
    Button close = new Button("close");
    Contact contact = new Contact();
    Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);

    //arg to fill the combobox for company names so as not to have extra dependencies with backend querie
    public ContactForm(List<Company> companies){
        System.out.println("Bean before save" + contact.toString());
        //add fields to custom component
        addClassName("contact-form");
        binder.bindInstanceFields(this);
        statusComboBox.setItems(Contact.Status.values());
       //fill with args
        companyComboBox.setItems(companies);
        //names as labels, not the address of the objects
        companyComboBox.setItemLabelGenerator(c->c.getName());
        add(firstName, lastName, email, statusComboBox, companyComboBox,  createButtonsLayout());


    }

    public void setContact(Contact contact){
        //when you call setBean -> fields are automatically set to these values
        this.contact=contact;
        binder.readBean(contact);
    }

    private Component createButtonsLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);
        //attaching custom event emitters
        save.addClickListener(click->{
            System.out.println("SAVE EVENT TRIGGERED");
            System.out.println("get bean " + binder.getBean());
           //validate and save
            try {
                //writes the attributes into a bean
                binder.writeBean(contact);
                //fires event caught in MainView by an inst
                fireEvent(new SaveEvent(this, contact));
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        });
        delete.addClickListener(click->{

            try {
                binder.writeBean(contact);
                fireEvent(new DeleteEvent(this, contact));
            } catch (ValidationException e) {
                e.printStackTrace();
            }


            System.out.println(binder.getBean());


        });

        close.addClickListener(e->{
            fireEvent(new CloseEvent(this));
        });

        //disabling save button when bean is not valid
        binder.addStatusChangeListener(ev->save.setEnabled(binder.isValid()));
        horizontalLayout.add(save, delete, close);
        return horizontalLayout;
    }


    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<ContactForm> {
        private Contact contact;
        protected ContactFormEvent(ContactForm source, Contact contact) {
            super(source, false);
            this.contact = contact;
        }
        public Contact getContact() {
            return contact;
        }
    }
    public static class SaveEvent extends ContactFormEvent {

        SaveEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }


    }
    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }
    }
    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ContactForm source) {
            super(source, null);
        }
    }
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public ComboBox<Contact.Status> getStatusComboBox() {
        return statusComboBox;
    }

    public ComboBox<Company> getCompanyComboBox() {
        return companyComboBox;
    }
}

