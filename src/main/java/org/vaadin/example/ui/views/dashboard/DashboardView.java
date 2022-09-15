package org.vaadin.example.ui.views.dashboard;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.service.CompanyService;
import org.vaadin.example.service.ContactService;
import org.vaadin.example.ui.MainLayout;

import java.util.Map;

@PageTitle("Dashboard | CRM")
@Route(value = "dashboard", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {
    private CompanyService companyService;
    private ContactService contactService;

    @Autowired
    public DashboardView(CompanyService companyService, ContactService contactService) {
        this.companyService = companyService;
        this.contactService = contactService;
        this.addClassName("dashboard-view");
        this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        //dashboard widgets
        add(
                getContactStats(),
                getCompaniesChart()
        );
    }

    private Component getCompaniesChart() {
       Chart chart = new Chart(ChartType.PIE);
        DataSeries dataSeries = new DataSeries();
        //to display: each company and the number of employees they have
        Map<String, Integer> stats = this.companyService.getStats();
        for(Map.Entry<String,Integer> entry: stats.entrySet()){
            dataSeries.add(new DataSeriesItem(entry.getKey(), entry.getValue()));
        }
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }

    private Span getContactStats(){
        Span stats = new Span(contactService.count() + " contacts. ");
        stats.addClassName("contact-stats");
        return stats;
    }
}
