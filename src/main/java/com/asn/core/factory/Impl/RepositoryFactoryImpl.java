package com.asn.core.factory.Impl;

import java.util.Map;

import com.asn.core.factory.RepositoryFactory;
import com.asn.core.repository.Repository;
import com.asn.core.services.YamlService;
import com.asn.core.services.impl.YamlServiceImpl;
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
import com.asn.data.repositories.db.ClientRepositoryImplBd;
import com.asn.data.repositories.db.UserRepositoryImplBd;

public class RepositoryFactoryImpl<T> implements RepositoryFactory<T> {
    private Repository<T> repository;

    public RepositoryFactoryImpl(Class<T> entity) {
        YamlService yamlService = new YamlServiceImpl();
        Map<String, Object> mapYaml = yamlService.loadYaml();
        if (User.class.isAssignableFrom(entity)) {
            String yh = (String)((Map<String, Object>) mapYaml.get("repository")).get("userRepository");
            this.repository = (Repository<T>) this.createRepositoryInstance(yh, null, null);
        } else if (Article.class.isAssignableFrom(entity)) {
            String yh = (String)((Map<String, Object>) mapYaml.get("repository")).get("articleRepository");
            this.repository = (Repository<T>) this.createRepositoryInstance(yh, null, null);
        } else if (Client.class.isAssignableFrom(entity)) {
            String yh = (String)((Map<String, Object>) mapYaml.get("repository")).get("clientRepository");
            String yh1 = (String)((Map<String, Object>) mapYaml.get("repository")).get("userRepository");
            this.repository = (Repository<T>) this.createRepositoryInstance(yh, yh1, null);
        } else if (Dette.class.isAssignableFrom(entity)) {
            String yh = (String)((Map<String, Object>) mapYaml.get("repository")).get("detteRepository");
            String yh1 = (String)((Map<String, Object>) mapYaml.get("repository")).get("clientRepository");
            this.repository = (Repository<T>) this.createRepositoryInstance(yh, yh1, null);    
        } else if (Detail.class.isAssignableFrom(entity)) {
            String yh = (String)((Map<String, Object>) mapYaml.get("repository")).get("detailRepository");
            String yh1 = (String)((Map<String, Object>) mapYaml.get("repository")).get("articleRepository");
            String yh2 = (String)((Map<String, Object>) mapYaml.get("repository")).get("detteRepository");
            this.repository = (Repository<T>) this.createRepositoryInstance(yh, yh1, yh2);
        } else if (Payement.class.isAssignableFrom(entity)) {
            String yh = (String)((Map<String, Object>) mapYaml.get("repository")).get("payementRepository");
            String yh1 = (String)((Map<String, Object>) mapYaml.get("repository")).get("detteRepository");
            this.repository = (Repository<T>) this.createRepositoryInstance(yh, yh1, null);
        }
    }



    public Object createRepositoryInstance(String className, String className2, String className3) {
    try {
        Class<?> clazz = Class.forName(className);
        var constructors = clazz.getDeclaredConstructors();

        // Essayer de trouver un constructeur sans paramètre
        for (var constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                return constructor.newInstance();
            }
        

        // Si className2 est fourni
        else if (constructor.getParameterCount() == 1 && className2 != null) {
            Class<?> clazz2 = Class.forName(className2);

            if (ClientRepository.class.isAssignableFrom(clazz)) {
                Object instance = clazz2.getDeclaredConstructor().newInstance();
                return clazz.getDeclaredConstructor(UserRepository.class).newInstance(instance);
            } else if (DetteRepository.class.isAssignableFrom(clazz)) {
                UserRepository userRepository = new UserRepositoryImplBd();
                Object instance = clazz2.getDeclaredConstructor(UserRepository.class).newInstance(userRepository);
                clazz.getDeclaredConstructor(ClientRepository.class).newInstance(instance);
                return constructor.newInstance(instance);
            } else if (PayementRepository.class.isAssignableFrom(clazz)) {
                UserRepository userRepository = new UserRepositoryImplBd();
                ClientRepository clientRepository = new ClientRepositoryImplBd(userRepository);
                Object instance = clazz2.getDeclaredConstructor(ClientRepository.class).newInstance(clientRepository);
                clazz.getDeclaredConstructor(DetteRepository.class).newInstance(instance);
                return constructor.newInstance(instance);
            }
        } else if (constructor.getParameterCount() == 2 && className2 != null && className3 != null) {
            Class<?> clazz2 = Class.forName(className2);
            Class<?> clazz3 = Class.forName(className3);
            if (DetailRepository.class.isAssignableFrom(clazz)) {
                Object instance2 = clazz2.getDeclaredConstructor().newInstance();
                UserRepository userRepository = new UserRepositoryImplBd();
                ClientRepository clientRepository = new ClientRepositoryImplBd(userRepository);
                Object instance3 = clazz3.getDeclaredConstructor(ClientRepository.class).newInstance(clientRepository);
                return clazz.getDeclaredConstructor(ArticleRepository.class, DetteRepository.class).newInstance(instance2, instance3);
            }
        }
    }
        // Aucune correspondance trouvée
        throw new RuntimeException("No suitable constructor found for: " + className);

    } catch (ClassNotFoundException e) {
        throw new RuntimeException("Class not found: " + e.getMessage(), e);
    } catch (NoSuchMethodException e) {
        throw new RuntimeException("No such method: " + e.getMessage(), e);
    } catch (Exception e) {
        throw new RuntimeException("Failed to create repository instance for: " + className, e);
    }
}




    @Override
    public Repository<T> getRepository() {
        return this.repository;
    }

}

