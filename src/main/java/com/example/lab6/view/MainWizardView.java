package com.example.lab6.view;

import com.example.lab6.pojo.Wizard;
import com.example.lab6.pojo.Wizards;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Route(value = "mainPage.it")
public class MainWizardView extends VerticalLayout {
    int index = -1;
    Wizard wizards;
    private TextField name, money;
    private RadioButtonGroup<String> sex;
    private Select<String> position, school, house;
    private Button back, create, update, del, next;

    public MainWizardView() {
        name = new TextField();
        name.setPlaceholder("Fullname");

        sex = new RadioButtonGroup<>();
        sex.setLabel("Gender : ");
        sex.setItems("Male", "Female");

        position = new Select<>();
        position.setPlaceholder("Position");
        position.setItems("Student", "Teacher");

        money = new TextField("Dollars");
        money.setPrefixComponent(new Span("$"));

        school = new Select<>();
        school.setPlaceholder("School");
        school.setItems("Hogwarts", "Beauxbatons", "Durmstrang");

        house = new Select<>();
        house.setPlaceholder("House");
        house.setItems("Gryffindor", "Ravenclaw", "Hufflepuff", "Slytherin");

        back = new Button("<<");
        create = new Button("Create");
        update = new Button("Update");
        del = new Button("Delete");
        next = new Button(">>");

        HorizontalLayout h1 = new HorizontalLayout();
        h1.add(back, create, update, del, next);
        add(name, sex, position, money, school, house, h1);

        back.addClickListener(e -> {
            Wizards listWizard = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/wizards")
                    .retrieve()
                    .bodyToMono(Wizards.class)
                    .block();
            if (index > 0) {
                index--;
            }
            Wizard select = listWizard.getWids().get(index);
            wizards = select;
            if (select.getSex().equals("m")){
                sex.setValue("Male");
            }
            else if (select.getSex().equals("f")){
                sex.setValue("Female");
            }

            if (select.getPosition().equals("student")){
                position.setValue("Student");
            }
            else if (select.getPosition().equals("teacher")){
                position.setValue("Teacher");
            }
            name.setValue(select.getName());
//            position.setValue(select.getPosition());
            money.setValue(String.valueOf(select.getMoney()));
            school.setValue(select.getSchool());
            house.setValue(select.getHouse());
        });

        next.addClickListener(e -> {
            Wizards listWizard = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/wizards")
                    .retrieve()
                    .bodyToMono(Wizards.class)
                    .block();
            if (index < listWizard.getWids().size()-1) {
                index++;
            }
            Wizard select = listWizard.getWids().get(index);
            wizards = select;
            if (select.getSex().equals("m")){
                sex.setValue("Male");
            }
            else if (select.getSex().equals("f")){
                sex.setValue("Female");
            }

            if (select.getPosition().equals("student")){
                position.setValue("Student");
            }
            else if (select.getPosition().equals("teacher")){
                position.setValue("Teacher");
            }
            name.setValue(select.getName());
//            position.setValue(select.getPosition());
            money.setValue(String.valueOf(select.getMoney()));
            school.setValue(select.getSchool());
            house.setValue(select.getHouse());
        });

        create.addClickListener(e -> {
            String s = sex.getValue().equals("Male") ? "m" : "f";

            String p = position.getValue().equals("Student") ? "student" : "teacher";
            int m = Integer.parseInt(money.getValue());

            Wizard w = new Wizard(null, s, name.getValue(), school.getValue(), house.getValue(), m, p);
            WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addWizard")
                    .body(Mono.just(w), Wizard.class)
                    .retrieve()
                    .bodyToMono(Wizard.class)
                    .block();

            Notification.show("Wizard has been Created", 5000, Notification.Position.BOTTOM_START);
        });

        update.addClickListener(e -> {
            String id = wizards.get_id();
            String s = sex.getValue().equals("Male") ? "m" : "f";

            String p = position.getValue().equals("Student") ? "student" : "teacher";
//            int m = Integer.parseInt(money.getValue());

            Wizard w = new Wizard(id, s, name.getValue(), school.getValue(), house.getValue(), Integer.parseInt(money.getValue()), p);
            WebClient.create()
                    .post()
                    .uri("http://localhost:8080/updateWizard")
                    .body(Mono.just(w), Wizard.class)
                    .retrieve()
                    .bodyToMono(Wizard.class)
                    .block();

            Notification.show("Wizard has been Updated", 5000, Notification.Position.BOTTOM_START);
        });

        del.addClickListener(e -> {
            try {
                WebClient.create()
                        .post()
                        .uri("http://localhost:8080/deleteWizard")
                        .body(Mono.just(wizards.get_id()), String.class)
                        .retrieve()
                        .bodyToMono(boolean.class)
                        .block();
            }catch (NullPointerException err){
                System.out.println("Error");
            }

            Notification.show("Wizard has been Removed", 5000, Notification.Position.BOTTOM_START);
        });

    }
}
