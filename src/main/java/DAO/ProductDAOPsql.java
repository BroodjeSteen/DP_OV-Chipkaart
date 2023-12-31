package DAO;

import domein.Adres;
import domein.OVChipkaart;
import domein.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
    private Connection conn;

    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        String insertQuery = "INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);

        preparedStatement.setInt(1, product.getProductNummer());
        preparedStatement.setString(2, product.getNaam());
        preparedStatement.setString(3, product.getBeschrijving());
        preparedStatement.setDouble(4, product.getPrijs());

        int rowsInserted = preparedStatement.executeUpdate();

        preparedStatement.close();

        return rowsInserted > 0;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        String updateQuery = "UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);

        preparedStatement.setString(1, product.getNaam());
        preparedStatement.setString(2, product.getBeschrijving());
        preparedStatement.setDouble(3, product.getPrijs());
        preparedStatement.setInt(4, product.getProductNummer());

        int rowsAffected = preparedStatement.executeUpdate();

        preparedStatement.close();

        return rowsAffected > 0;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        String deleteQuery = "DELETE FROM product WHERE product_nummer = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(deleteQuery);

        preparedStatement.setInt(1, product.getProductNummer());

        int rowsDeleted = preparedStatement.executeUpdate();

        preparedStatement.close();

        return rowsDeleted > 0;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        String query = "SELECT p.product_nummer, naam, beschrijving, prijs FROM ov_chipkaart_product l " +
                       "INNER JOIN product p ON p.product_nummer = l.product_nummer " +
                       "WHERE kaart_nummer = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setInt(1, ovChipkaart.getKaartNummer());

        ResultSet rs = preparedStatement.executeQuery();

        List<Product> producten = new ArrayList<>();
        while(rs.next()) {
            Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
            product.addOVChipkaartNummer(ovChipkaart.getKaartNummer());
            producten.add(product);
        }

        return producten;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        String query = "SELECT * FROM product";
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        ResultSet rs = preparedStatement.executeQuery();

        List<Product> producten = new ArrayList<>();
        while(rs.next()) {
            Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
            producten.add(product);
        }

        return producten;
    }
}
