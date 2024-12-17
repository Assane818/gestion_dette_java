package com.asn.data.repositories.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.asn.core.repository.impl.RepositoryBdImpl;
import com.asn.data.entities.Dette;
import com.asn.data.entities.Payement;
import com.asn.data.repositories.DetteRepository;
import com.asn.data.repositories.PayementRepository;
import com.asn.data.repositories.UserRepository;

public class PayementRepositoryImplBd extends RepositoryBdImpl<Payement> implements PayementRepository{
    public PayementRepositoryImplBd(DetteRepository detteRepository) {
        super.tableName = "payements";
        super.className = Payement.class;
        super.detteRepository = detteRepository;
    }

    @Override
    public List<Payement> selectPayementsInDette(Dette dette) {
        List<Payement> payements = new ArrayList<>();
        try {
            String sql = String.format("SELECT * FROM \"%s\" WHERE dette_id = ?", tableName);
            this.getConnexion();
            this.iniPreparedStatement(sql);
            this.ps.setInt(1, dette.getId());
            ResultSet rs = this.ps.executeQuery();
            while (rs.next()) {
                payements.add(this.convertToObject(rs, className));
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
        return payements;
    }
}
