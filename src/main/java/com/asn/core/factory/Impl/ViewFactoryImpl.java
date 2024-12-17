package com.asn.core.factory.Impl;

import java.util.Scanner;

import com.asn.data.services.Service;
import com.asn.data.services.UserService;
import com.asn.core.factory.ViewFactory;
import com.asn.data.entities.Article;
import com.asn.data.entities.Client;
import com.asn.data.entities.Detail;
import com.asn.data.entities.Dette;
import com.asn.data.entities.Payement;
import com.asn.data.entities.User;
import com.asn.data.services.ArticleService;
import com.asn.data.services.ClientService;
import com.asn.data.services.DetailService;
import com.asn.data.views.ArticleView;
import com.asn.data.views.ClientView;
import com.asn.data.views.DetailView;
import com.asn.data.views.DetteView;
import com.asn.data.views.PayementView;
import com.asn.data.views.UserView;
import com.asn.data.views.View;
import com.asn.data.views.impl.ArticleViewImpl;
import com.asn.data.views.impl.ClientViewImpl;
import com.asn.data.views.impl.DetailViewImpl;
import com.asn.data.views.impl.DetteViewImpl;
import com.asn.data.views.impl.PayementViewImpl;
import com.asn.data.views.impl.UserViewImpl;

public class ViewFactoryImpl<T> implements ViewFactory<T> {
    private View<T> view;
    private Scanner scanner;

    public ViewFactoryImpl(Class<T> entity ,Service<T> service) {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        if (Client.class.isAssignableFrom(entity)) {
            view = (View<T>) new ClientViewImpl(scanner, (ClientService)service);
        } else if (User.class.isAssignableFrom(entity)) {
            view = (View<T>) new UserViewImpl(scanner, (UserService)service);
        } else if (Article.class.isAssignableFrom(entity)) {
            view = (View<T>) new ArticleViewImpl(scanner, (ArticleService)service);
        } else if (Detail.class.isAssignableFrom(entity)) {
            view = (View<T>) new DetailViewImpl(scanner);
        } else if (Payement.class.isAssignableFrom(entity)) {
            view = (View<T>) new PayementViewImpl(scanner);
        }
    }
    public ViewFactoryImpl(Class<T> entity ,Service<T> service, ArticleService service1, DetailService service2) {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        } 
        if (Dette.class.isAssignableFrom(entity)) {
            view = (View<T>) new DetteViewImpl(scanner, (ClientService)service, service1, service2);
        }
    }

    @Override
    public View<T> getView() {
        return view;
    }
    
}
