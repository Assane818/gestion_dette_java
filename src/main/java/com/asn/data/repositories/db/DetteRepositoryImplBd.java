package com.asn.data.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.asn.core.repository.impl.RepositoryBdImpl;
import com.asn.data.entities.Client;
import com.asn.data.entities.Dette;
import com.asn.data.enums.Etat;
import com.asn.data.repositories.ClientRepository;
import com.asn.data.repositories.DetteRepository;

public class DetteRepositoryImplBd extends RepositoryBdImpl<Dette> implements DetteRepository {
    public DetteRepositoryImplBd(ClientRepository clientRepository) {
        super.tableName = "dettes";
        super.className = Dette.class;
        super.clientRepository = clientRepository;

    }
    @Override
    public List<Dette> selectDettesNoSoldeClient(Client client) {
        List<Dette> dettes = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM \"%s\" WHERE \"montant\" - \"montantVerser\" > 0 AND \"client_id\" = ? AND \"etat\" = ?", tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setInt(1, client.getId());
            this.ps.setInt(2, Etat.VALIDER.ordinal());
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                dettes.add(this.convertToObject(rs, className));
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
        return dettes;
    }

    @Override
    public List<Dette> selectDettesByEtat(Etat etat) {
        List<Dette> dettes = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM \"%s\" WHERE \"etat\" = ? ", tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setInt(1, etat.ordinal());
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                dettes.add(this.convertToObject(rs, className));
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
        return dettes;
    }
    @Override
    public void updateEtatDette(Dette dette, Etat etat) {
        try {
            String sql = String.format("UPDATE \"%s\" SET \"etat\" = ? WHERE \"id\" = ?", tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setInt(1, etat.ordinal());
            this.ps.setInt(2, dette.getId());
            this.ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeConnexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void updateDette(Dette dette) {
        try {
            String sql = String.format("UPDATE \"%s\" SET \"montantVerser\" = ? WHERE \"id\" = ?", tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setDouble(1, dette.getMontantVerser());
            this.ps.setInt(2, dette.getId());
            this.ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.closeConnexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
