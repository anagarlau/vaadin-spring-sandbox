package org.vaadin.example;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.*;
import org.springframework.stereotype.Component;

@Component
public class ApplicationServiceInitListener implements VaadinServiceInitListener {
    @Override
    public void serviceInit(ServiceInitEvent event) {
        VaadinService source = event.getSource();
        source.addUIInitListener(uiInit -> {
           uiInit.getUI().access(new Command() {
               @Override
               public void execute() {

                   VaadinSession current = VaadinSession.getCurrent();
                   Notification.show("Current Vaadin session >>> " + current);
                   String id = current.getSession().getId();
                   Notification.show("Current HTTP session >>> " + id );
                 //  uiInit.getUI().getPage().setLocation("dashboard");
               }
           });
        });
    }
}
