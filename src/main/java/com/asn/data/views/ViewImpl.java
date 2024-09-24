package com.asn.data.views;

import java.util.Scanner;
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
    
    public int saisieInt(String msg) {
        System.out.println(msg);
        return scanner.nextInt();
    }

    public Double saisieDouble(String msg) {
        System.out.println(msg);
        return scanner.nextDouble();
    }

    public String saisieString(String msg) {
        scanner.nextLine();
        System.out.println(msg);
        return scanner.nextLine();
    }
}
