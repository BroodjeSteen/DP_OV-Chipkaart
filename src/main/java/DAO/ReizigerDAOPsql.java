package DAO;

import domein.Reiziger;

import java.lang.reflect.Array;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection conn;

    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        // Create a PreparedStatement for the INSERT statement
        String insertQuery = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);

        // Set the values for the placeholders in the SQL statement
        preparedStatement.setInt(1, reiziger.getReizigerId());
        preparedStatement.setString(2, reiziger.getVoorletters());
        preparedStatement.setString(3, reiziger.getTussenvoegsel());
        preparedStatement.setString(4, reiziger.getAchternaam());
        preparedStatement.setDate(5, new java.sql.Date(reiziger.getGeboortedatum().getTime()));

        // Execute the INSERT statement
        int rowsInserted = preparedStatement.executeUpdate();

        // Close the PreparedStatement
        preparedStatement.close();

        // Return true if at least one row was inserted
        return rowsInserted > 0;
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        String updateQuery = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);

        preparedStatement.setString(1, reiziger.getVoorletters());
        preparedStatement.setString(2, reiziger.getTussenvoegsel());
        preparedStatement.setString(3, reiziger.getAchternaam());
        preparedStatement.setDate(4, new java.sql.Date(reiziger.getGeboortedatum().getTime()));
        preparedStatement.setInt(5, reiziger.getReizigerId());

        int rowsAffected = preparedStatement.executeUpdate();

        preparedStatement.close();
        return rowsAffected > 0;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        String deleteQuery = "DELETE FROM reiziger WHERE reiziger_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);
        preparedStatement.setInt(1, reiziger.getReizigerId());

        int rowsDeleted = preparedStatement.executeUpdate();

        preparedStatement.close();
        return rowsDeleted > 0;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Reiziger reiziger = new Reiziger(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
            return reiziger;
        }
        rs.close();
        preparedStatement.close();
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException, ParseException {
        String selectQuery = "SELECT * FROM reiziger WHERE geboortedatum = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);

        preparedStatement.setDate(1, java.sql.Date.valueOf(datum));

        List<Reiziger> reizigers = new ArrayList<>();

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Reiziger reiziger = new Reiziger(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
            reizigers.add(reiziger);
        }
        rs.close();
        preparedStatement.close();
        return reizigers;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM reiziger");

        List<Reiziger> reizigers = new ArrayList<>();
        while (rs.next()) {
            Reiziger reiziger = new Reiziger(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
            reizigers.add(reiziger);
        }
        rs.close();
        st.close();
        return reizigers;
    }
}
