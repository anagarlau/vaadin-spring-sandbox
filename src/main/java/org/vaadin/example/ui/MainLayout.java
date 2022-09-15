package org.vaadin.example.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.vaadin.example.ui.views.dashboard.DashboardView;
import org.vaadin.example.ui.views.list.ListView;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    public MainLayout(){
        //App header
         createHeader();
        //Navigation links
         createDrawer();
    }



    private void createHeader() {
        H1 logo = new H1("Vaadin CRM");
        logo.addClassName("logo");
        //drawer toggle hides and shows navbar
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        addToNavbar(header);

    }


    private void createDrawer() {
        RouterLink listLink = new RouterLink("List", ListView.class);
        RouterLink dashboardLink = new RouterLink("Dashboard", DashboardView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(listLink, dashboardLink));
    }
}
