package com.asn.data.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.asn.core.repository.impl.RepositoryBdImpl;
import com.asn.data.entities.Article;
import com.asn.data.entities.Detail;
import com.asn.data.entities.Dette;
import com.asn.data.repositories.ArticleRepository;
import com.asn.data.repositories.DetailRepository;
import com.asn.data.repositories.DetteRepository;
import com.asn.data.repositories.UserRepository;

public class DetailRepositoryImplBd extends RepositoryBdImpl<Detail> implements DetailRepository {
    
    public DetailRepositoryImplBd(ArticleRepository articleRepository, DetteRepository detteRepository) {
        super.tableName = "details";
        super.className = Detail.class;
        super.articleRepository = articleRepository;
        super.detteRepository = detteRepository;
        
    }

    @Override
    public void updateDetteId(Detail object, int id) {
        try {
            String sql = String.format("UPDATE \"%s\" SET dette_id = ? WHERE id = ?", tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setInt(1, id);
            this.ps.setInt(2, object.getId());
            this.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Detail> selectDetailsInDette(Dette dette) {
        List<Detail> details = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM \"%s\" WHERE dette_id = ?", tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setInt(1, dette.getId());
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                details.add(this.convertToObject(rs, className));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeConnexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return details;
    }

}
