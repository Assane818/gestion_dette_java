package com.asn.core.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.asn.core.repository.Repository;
import com.asn.data.entities.AbstractEntity;
import com.asn.data.entities.Article;
import com.asn.data.entities.Client;
import com.asn.data.entities.Detail;
import com.asn.data.entities.Dette;
import com.asn.data.entities.Payement;
import com.asn.data.entities.User;

public class RepositoryListImpl<T> implements Repository<T> {
    protected List<T> list = new ArrayList<>();
    @Override
    public int insert(T object) {
        if (object instanceof Article) {
            ((Article)object).setId(Article.getNbreArticle());
        } else if (object instanceof Client) {
            ((Client)object).setId(Client.getNbreClient());
        } else if (object instanceof Detail) {
            ((Detail)object).setId(Detail.getNbreDetail());
        } else if (object instanceof User) {
            ((User)object).setId(User.getNbreUser());
        } else if (object instanceof Dette) {
            ((Dette)object).setId(Dette.getNbreDette());
        } else if (object instanceof Payement) {
            ((Payement)object).setId(Payement.getNbrePayement());
        }
        list.add(object);
        return ((AbstractEntity) object).getId();
    }

    @Override
    public T selectById(int id) {
        for (T t : list) {
            if (((AbstractEntity)t).getId() == id) {
                return t;
            }
        }
        return null;
    }

    @Override
    public List<T> selectAll(Class<T> clazz) {
        return list;
    }

    @Override
    public T convertToObject(ResultSet rs, Class<T> clazz) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToObject'");
    }

    @Override
    public void convertToSql(PreparedStatement ps, T entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToSql'");
    }

    @Override
    public String generateRequestInsert(T object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateRequestInsert'");
    }
    
}
