package org.vaadin.example;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

@Route("home")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class View extends VerticalLayout {

    public View(){
        IFrame frame = new IFrame();
        frame.setSizeFull();
        StreamResource resource = new StreamResource(
                "chinook.pdf", () -> getClass().getResourceAsStream("/pdf/chinook.pdf")); // file in src/main/resources/
        StreamRegistration registration = VaadinSession.getCurrent().getResourceRegistry().registerResource(resource);
        IFrame iframe = new IFrame(registration.getResourceUri().toString());
        iframe.setHeight("100%");

        add(frame);

    }
}
