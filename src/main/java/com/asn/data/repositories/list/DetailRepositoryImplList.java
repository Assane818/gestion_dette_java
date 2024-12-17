package com.asn.data.repositories.list;

import java.util.List;

import com.asn.core.repository.impl.RepositoryListImpl;
import com.asn.data.entities.Detail;
import com.asn.data.entities.Dette;
import com.asn.data.repositories.ArticleRepository;
import com.asn.data.repositories.DetailRepository;
import com.asn.data.repositories.DetteRepository;

public class DetailRepositoryImplList extends RepositoryListImpl<Detail> implements DetailRepository {
    private ArticleRepository articleRepository;
    private DetteRepository detteRepository;
    public DetailRepositoryImplList(ArticleRepository articleRepository, DetteRepository detteRepository) {
        this.articleRepository = articleRepository;
        this.detteRepository = detteRepository;
    }

    @Override
    public void updateDetteId(Detail object, int id) {
        object.setDette(detteRepository.selectById(id));
    }

    @Override
    public List<Detail> selectDetailsInDette(Dette dette) {
        return dette.getDetails();
    }
    
}
