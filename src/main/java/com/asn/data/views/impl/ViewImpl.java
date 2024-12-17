package com.asn.data.views.impl;

import java.util.Scanner;

import com.asn.data.views.View;

import java.util.List;

public abstract class ViewImpl<T> implements View<T> {
    protected Scanner scanner;

    protected ViewImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void affiche(List<T> datas) {
        datas.forEach(System.out::println);
    }
    @Override
    public int saisieInt(String msg) {
        System.out.println(msg);
        return scanner.nextInt();
    }
    @Override
    public Double saisieDouble(String msg) {
        System.out.println(msg);
        return scanner.nextDouble();
    }
    @Override
    public String saisieString(String msg) {
        System.out.println(msg);
        return scanner.nextLine();
    }
    @Override
    public int choiceFilter(List<String> filtre) {
        int choice;
        do {
            for (int i = 0; i < filtre.size(); i++) {
                System.out.println((i+1) + "-" + filtre.get(i));
            }
            System.out.println("Entrer votre choix:");
            choice = scanner.nextInt();
        } while (choice <= 0 || choice > filtre.size());
        return choice;
    }
}
