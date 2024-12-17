package com.asn.data.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.asn.core.repository.impl.RepositoryBdImpl;
import com.asn.data.entities.Article;
import com.asn.data.entities.Detail;
import com.asn.data.repositories.ArticleRepository;
import com.asn.data.repositories.UserRepository;

public class ArticleRepositoryImplBd extends RepositoryBdImpl<Article> implements ArticleRepository {

    public ArticleRepositoryImplBd() {
        super.tableName = "articles";
        super.className = Article.class;
    }

    @Override
    public List<Article> findAllByDisponiblity() {
        List<Article> articles = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM \"%s\" WHERE \"quantite\" > 0", tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                articles.add(this.convertToObject(rs, className));
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
        return articles;
    }

    @Override
    public void update(Article article, double quantite) {
        try {
            String sql = "UPDATE articles SET quantite = ? WHERE id = ?";
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setDouble(1,  quantite);
            this.ps.setInt(2, article.getId());
            this.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Article selectByLibelle(String libelle) {
        try {
            String sql = String.format("SELECT * FROM \"%s\" WHERE \"libelle\" = ?", tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setString(1, libelle);
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                return  this.convertToObject(rs, className);
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
        return null;
    }

    @Override
    public Article selectArticleInDetail(Detail detail) {
        try {
            String sql = String.format("SELECT * FROM \"%s\" WHERE id = ?", tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setInt(1, detail.getArticle().getId());
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                return convertToObject(rs, className);
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
        return null;
    }

    // @Override
    // public List<Article> selectArticlesInDette(Dette dette) {
    //     List<Article> articles = new ArrayList<>();
    //     try {
    //         String sql = String.format("SELECT a.* FROM \"details\" d JOIN \"%s\" a ON d.article_id = a.id WHERE d.dette_id = ?", tableName);
    //         this.getConnexion();
    //         this.iniPreparedStatement(sql);
    //         this.ps.setInt(1, dette.getId());
    //         ResultSet rs = this.ps.executeQuery();
    //         while (rs.next()) {
    //             articles.add(this.convertToObject(rs, className));
    //         }
    //         rs.close();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     } finally {
    //         try {
    //             this.closeConnexion();
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //         }
    //     }
    //     return articles;
    // }
    
}
