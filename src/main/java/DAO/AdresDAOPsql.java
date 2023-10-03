package DAO;

import domein.Adres;
import domein.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection conn;
    private ReizigerDAO rdao;

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
        rdao = new ReizigerDAOPsql(conn);
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        rdao.save(adres.getReiziger());

        String insertQuery = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);

        preparedStatement.setInt(1, adres.getId());
        preparedStatement.setString(2, adres.getPostcode());
        preparedStatement.setString(3, adres.getHuisnummer());
        preparedStatement.setString(4, adres.getStraat());
        preparedStatement.setString(5, adres.getWoonplaats());
        preparedStatement.setInt(6, adres.getReizigerId());

        int rowsInserted = preparedStatement.executeUpdate();

        preparedStatement.close();
        return rowsInserted > 0;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        rdao.update(adres.getReiziger());

        String updateQuery = "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);

        preparedStatement.setString(1, adres.getPostcode());
        preparedStatement.setString(2, adres.getHuisnummer());
        preparedStatement.setString(3, adres.getStraat());
        preparedStatement.setString(4, adres.getWoonplaats());
        preparedStatement.setInt(5, adres.getReizigerId());
        preparedStatement.setInt(6, adres.getId());

        int rowsAffected = preparedStatement.executeUpdate();

        preparedStatement.close();
        return rowsAffected > 0;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        String deleteQuery = "DELETE FROM adres WHERE adres_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);

        preparedStatement.setInt(1, adres.getId());

        int rowsDeleted = preparedStatement.executeUpdate();

        preparedStatement.close();

        rdao.delete(adres.getReiziger());

        return rowsDeleted > 0;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        String query = "SELECT * FROM adres WHERE reiziger_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setInt(1, reiziger.getReizigerId());

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Reiziger r = rdao.findById(rs.getInt(6));
            Adres a = new Adres(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), r);
            r.setAdres(a);
            return a;
        }
        rs.close();
        preparedStatement.close();
        return null;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        String query = "SELECT * FROM adres ORDER BY adres_id ASC";
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        List<Adres> adressen = new ArrayList<>();
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Reiziger r = rdao.findById(rs.getInt(6));
            Adres a = new Adres(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), r);
            r.setAdres(a);
            adressen.add(a);
        }
        rs.close();
        preparedStatement.close();
        return adressen;
    }
}
