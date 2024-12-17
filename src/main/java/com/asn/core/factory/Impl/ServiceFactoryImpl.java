package com.asn.core.factory.Impl;

import com.asn.core.factory.ServiceFactory;
import com.asn.core.repository.Repository;
import com.asn.data.entities.Article;
import com.asn.data.entities.Client;
import com.asn.data.entities.Detail;
import com.asn.data.entities.Dette;
import com.asn.data.entities.Payement;
import com.asn.data.entities.User;
import com.asn.data.repositories.ArticleRepository;
import com.asn.data.repositories.ClientRepository;
import com.asn.data.repositories.DetailRepository;
import com.asn.data.repositories.DetteRepository;
import com.asn.data.repositories.PayementRepository;
import com.asn.data.repositories.UserRepository;
import com.asn.data.repositories.db.PayementRepositoryImplBd;
import com.asn.data.services.Service;
import com.asn.data.services.impl.ArticleServiceImpl;
import com.asn.data.services.impl.ClientServiceImpl;
import com.asn.data.services.impl.DetailServiceImpl;
import com.asn.data.services.impl.DetteServiceImpl;
import com.asn.data.services.impl.PayementServiceImpl;
import com.asn.data.services.impl.UserServiceImpl;

public class ServiceFactoryImpl<T> implements ServiceFactory<T> {
    private Service<T> service;
  
    public ServiceFactoryImpl(Class<T> entity, Repository<T> repository) {
        if (Client.class.isAssignableFrom(entity)) {
            service = (Service<T>) new ClientServiceImpl((ClientRepository)repository);
        } else if (User.class.isAssignableFrom(entity)) {
            service = (Service<T>) new UserServiceImpl((UserRepository)repository);
        } else if (Article.class.isAssignableFrom(entity)) {
            service = (Service<T>) new ArticleServiceImpl((ArticleRepository)repository);
        } else if (Dette.class.isAssignableFrom(entity)) {
            service = (Service<T>) new DetteServiceImpl((DetteRepository)repository);
        } else if (Detail.class.isAssignableFrom(entity)) {
            service = (Service<T>) new DetailServiceImpl((DetailRepository)repository);
        } else if (Payement.class.isAssignableFrom(entity)) {
            service = (Service<T>) new PayementServiceImpl((PayementRepository) repository);
        }
    }

    @Override
    public Service<T> getService() {
        return service;
    }

    
}
