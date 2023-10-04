package DAO;

import domein.OVChipkaart;
import domein.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection conn;
    private ReizigerDAO rdao;

    public OVChipkaartDAOPsql(Connection conn) {
        this.conn = conn;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        String insertQuery = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);

        preparedStatement.setInt(1, ovChipkaart.getKaartNummer());
        preparedStatement.setDate(2, ovChipkaart.getGeldigTot());
        preparedStatement.setInt(3, ovChipkaart.getKlasse());
        preparedStatement.setDouble(4, ovChipkaart.getSaldo());
        preparedStatement.setInt(5, ovChipkaart.getReiziger().getReizigerId());

        int rowsInserted = preparedStatement.executeUpdate();

        preparedStatement.close();
        return rowsInserted > 0;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        String updateQuery = "UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);

        preparedStatement.setDate(1, ovChipkaart.getGeldigTot());
        preparedStatement.setInt(2, ovChipkaart.getKlasse());
        preparedStatement.setDouble(3, ovChipkaart.getSaldo());
        preparedStatement.setInt(4, ovChipkaart.getReiziger().getReizigerId());
        preparedStatement.setInt(5, ovChipkaart.getKaartNummer());

        int rowsAffected = preparedStatement.executeUpdate();

        preparedStatement.close();
        return rowsAffected > 0;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        String deleteQuery = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);

        preparedStatement.setInt(1, ovChipkaart.getKaartNummer());

        int rowsDeleted = preparedStatement.executeUpdate();

        preparedStatement.close();
        return rowsDeleted > 0;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setInt(1, reiziger.getReizigerId());

        List<OVChipkaart> ovChipkaarten = new ArrayList<>();
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) ovChipkaarten.add(new OVChipkaart(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getDouble(4), rdao.findById(rs.getInt(5))));

        rs.close();
        preparedStatement.close();
        return ovChipkaarten;
    }

    @Override
    public List<OVChipkaart> findAll() {
        return null;
    }
}
