package datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class DataSource {
    public static final String DB_NAME = "car_maintenance.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Zahari Mikov\\IdeaProjects\\Car_Maintenance_backup\\" + DB_NAME;

    public static final String TABLE_SQLITE_SEQUENCE = "sqlite_sequence";
    public static final String COLUMN_SQLITE_SEQUENCE_NAME = "name";
    public static final String COLUMN_SQLITE_SEQUENCE_SEQ = "seq";

    public static final String TABLE_CAR = "car";
    public static final String CAR_COLUMN_ID = "id";
    public static final String CAR_COLUMN_BRAND = "brand";
    public static final String CAR_COLUMN_YEAR = "year";
    public static final String CAR_COLUMN_MODEL = "model";
    public static final String CAR_COLUMN_MODIFICATION = "modification";

    public static final String TABLE_PARTS = "parts";
    public static final String PARTS_COLUMN_ID = "id";
    public static final String PARTS_COLUMN_NAME = "name";
    public static final String PARTS_COLUMN_DATE = "replace_date";
    public static final String PARTS_COLUMN_ADDITIONAL_INFO = "additional_info";
    public static final String PARTS_COLUMN_CAR_ID = "car_id";

    public static final String TABLE_DETAILS = "details";
    public static final String DETAILS_COLUMN_ID = "id";
    public static final String DETAILS_COLUMN_TYPE = "type";
    public static final String DETAILS_COLUMN_BUY_DATE = "buy_date";
    public static final String DETAILS_COLUMN_EXPIRE_DATE = "expire_date";
    public static final String DETAILS_COLUMN_CAR_ID = "car_id";

    // SELECT * FROM car;
    public static final String QUERY_CARS = "SELECT * FROM " + TABLE_CAR;

    // SELECT id FROM car
    // ORDER BY id desc
    // LIMIT 1;
    public static final String QUERY_CARS_ID = "SELECT " + CAR_COLUMN_ID + " FROM " + TABLE_CAR +
            " ORDER BY " + CAR_COLUMN_ID + " desc " +
            " LIMIT 1 ";

    // SELECT seq FROM sqlite_sequence
    // WHERE name="car";
    public static final String QUERY_SEQ_INDEX = "SELECT " + COLUMN_SQLITE_SEQUENCE_SEQ + " FROM " + TABLE_SQLITE_SEQUENCE
            + " WHERE name = ?";

    // INSERT INTO car (brand, year, model, modification) VALUES(?, ?, ?, ?)
    public static final String INSERT_INTO_CAR = "INSERT INTO " + TABLE_CAR +
            "(" + CAR_COLUMN_BRAND + ", " + CAR_COLUMN_YEAR + ", " + CAR_COLUMN_MODEL + ", " + CAR_COLUMN_MODIFICATION + ")" +
            " VALUES(?, ?, ?, ?)";

    // DELETE FROM car
    // WHERE id=?;
    public static final String DELETE_FROM_CAR = "DELETE FROM " + TABLE_CAR +
            " WHERE " + CAR_COLUMN_ID + "=?";

    // UPDATE sqlite_sequence SET seq = ? WHERE name = ?;
    public static final String UPDATE_SEQ = "UPDATE " + TABLE_SQLITE_SEQUENCE +
            " SET " + COLUMN_SQLITE_SEQUENCE_SEQ + " = ?" +
            " WHERE name = ?";

    // SELECT * FROM parts;
    public static final String QUERY_PARTS = "SELECT * FROM " + TABLE_PARTS;

    // SELECT * FROM parts WHERE car_id = ?
    public static final String QUERY_PARTS_BY_INDEX = "SELECT * FROM " + TABLE_PARTS +
            " WHERE " + PARTS_COLUMN_CAR_ID + "=?";

    // INSERT INTO parts (name, date, additionalInfo, car_id) VALUES ("Предна лява биалетка", "25/10/2020", "something", 2);
    public static final String INSERT_INTO_PARTS = "INSERT INTO " + TABLE_PARTS +
            " (" + PARTS_COLUMN_NAME + ", " + PARTS_COLUMN_DATE + ", " + PARTS_COLUMN_ADDITIONAL_INFO + ", " + PARTS_COLUMN_CAR_ID + ") " +
            " VALUES (?, ?, ?, ?) ";

    // UPDATE sqlite_sequence SET seq = ? WHERE name = 'parts';
    public static final String UPDATE_PARTS_SEQ = "UPDATE " + TABLE_SQLITE_SEQUENCE +
            " SET " + COLUMN_SQLITE_SEQUENCE_SEQ + " = ?" +
            " WHERE name = '" + TABLE_PARTS + "'";

    // DELETE FROM parts
    // WHERE id=?
    public static final String DELETE_FROM_PARTS = "DELETE FROM " + TABLE_PARTS +
            " WHERE " + PARTS_COLUMN_ID + "=?";

    // SELECT * FROM details WHERE car_id = ?
    public static final String QUERY_CAR_DETAILS_BY_ID = "SELECT * FROM " + TABLE_DETAILS +
            " WHERE " + DETAILS_COLUMN_CAR_ID + "=?";

    // INSERT INTO details (type, buy_date, expire_date, car_id)
    // VALUES(?, ?, ?, ?);
    public static final String INSERT_INTO_DETAILS = "INSERT INTO " + TABLE_DETAILS +
            "(" + DETAILS_COLUMN_TYPE + ", " + DETAILS_COLUMN_BUY_DATE + ", " +
            DETAILS_COLUMN_EXPIRE_DATE + ", " + DETAILS_COLUMN_CAR_ID + ") " +
            " VALUES(?, ?, ?, ?) ";

    // DELETE FROM details
    // WHERE id=?
    public static final String DELETE_FROM_DETAILS = "DELETE FROM " + TABLE_DETAILS +
            " WHERE " + DETAILS_COLUMN_ID + "=?";

    // DELETE FROM parts;
    public static final String DELETE_ALL_FROM_PARTS = "DELETE FROM parts";

    // DELETE FROM details;
    public static final String DELETE_ALL_FROM_DETAILS = "DELETE FROM details";

    private Connection conn;
    private PreparedStatement query_cars;
    private PreparedStatement query_cars_id;
    private PreparedStatement query_seq_index;
    private PreparedStatement query_parts;
    private PreparedStatement query_parts_by_index;
    private PreparedStatement query_car_details_by_id;
    private PreparedStatement insert_into_cars;
    private PreparedStatement insert_into_parts;
    private PreparedStatement insert_into_details;
    private PreparedStatement delete_from_cars;
    private PreparedStatement delete_from_parts;
    private PreparedStatement delete_from_details;
    private PreparedStatement delete_all_from_parts;
    private PreparedStatement delete_all_from_details;
    private PreparedStatement update_car_seq;
    private PreparedStatement update_parts_seq;

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            query_cars = conn.prepareStatement(QUERY_CARS);
            query_cars_id = conn.prepareStatement(QUERY_CARS_ID);
            query_seq_index = conn.prepareStatement(QUERY_SEQ_INDEX);
            query_parts = conn.prepareStatement(QUERY_PARTS);
            query_parts_by_index = conn.prepareStatement(QUERY_PARTS_BY_INDEX);
            query_car_details_by_id = conn.prepareStatement(QUERY_CAR_DETAILS_BY_ID);
            insert_into_cars = conn.prepareStatement(INSERT_INTO_CAR);
            insert_into_parts = conn.prepareStatement(INSERT_INTO_PARTS);
            insert_into_details = conn.prepareStatement(INSERT_INTO_DETAILS);
            delete_from_cars = conn.prepareStatement(DELETE_FROM_CAR);
            delete_from_parts = conn.prepareStatement(DELETE_FROM_PARTS);
            delete_from_details = conn.prepareStatement(DELETE_FROM_DETAILS);
            delete_all_from_parts = conn.prepareStatement(DELETE_ALL_FROM_PARTS);
            delete_all_from_details = conn.prepareStatement(DELETE_ALL_FROM_DETAILS);
            update_car_seq = conn.prepareStatement(UPDATE_SEQ);
            update_parts_seq = conn.prepareStatement(UPDATE_PARTS_SEQ);
            return true;
        } catch (SQLException e) {
            System.out.println("There is a problem: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        closeQuery(query_cars);
        closeQuery(query_cars_id);
        closeQuery(query_seq_index);
        closeQuery(query_parts);
        closeQuery(query_parts_by_index);
        closeQuery(query_car_details_by_id);
        closeQuery(insert_into_cars);
        closeQuery(insert_into_parts);
        closeQuery(insert_into_details);
        closeQuery(delete_from_cars);
        closeQuery(delete_from_parts);
        closeQuery(delete_from_details);
        closeQuery(delete_all_from_parts);
        closeQuery(delete_all_from_details);
        closeQuery(update_car_seq);
        closeQuery(update_parts_seq);
        closeConnection(conn);
    }

    private void closeQuery(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ObservableList<Car> getCars() {
        ObservableList<Car> cars = FXCollections.observableArrayList();
        try (ResultSet results = query_cars.executeQuery()) {
            while (results.next()) {
                int id = results.getInt(1);
                String brand = results.getString(2);
                String year = results.getString(3);
                String model = results.getString(4);
                String modification = results.getString(5);
                cars.add(new Car(id, brand, year, model, modification));
            }
        } catch (SQLException e) {
            System.out.println("Query 'query_cars' failed: " + e.getMessage());
            e.printStackTrace();
        }
        return cars;
    }

    public void insertIntoCar(Car car) {
        try {
            String brand = car.getBrand();
            String year = car.getYear();
            String model = car.getModel();
            String modification = car.getModification();

            insert_into_cars.setString(1, brand);
            insert_into_cars.setString(2, year);
            insert_into_cars.setString(3, model);
            insert_into_cars.setString(4, modification);

            int rowInserted = insert_into_cars.executeUpdate();
            if (rowInserted != 1) {
                System.out.println("Error with inserting to car table!!!");
            } else {
                System.out.println("Successfully inserted new car into table.");
            }

        } catch (SQLException e) {
            System.out.println("Couldn't insert to car table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean deleteFromCar(int id) {
        try {
            delete_from_cars.setInt(1, id);
            int deletedRecords = delete_from_cars.executeUpdate();
            if (deletedRecords != 1) {
                System.out.println("Deletion Unsuccessful!");
                return false;
            } else {
                System.out.println("Deletion Successful");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Couldn't delete this record: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public int getSeqIndex(String table) {
        int seqIndex = 1;

        try {
            query_seq_index.setString(1, table);
            ResultSet results = query_seq_index.executeQuery();
            if (results.next()) {
                seqIndex = results.getInt(1);
                System.out.println("SEQ INDEX: " + seqIndex);
            }
        } catch (SQLException e) {
            System.out.println("Couldn't fetch seq index: " + e.getMessage());
            e.printStackTrace();
        }

        return seqIndex;
    }

    public void updateSeq(int i, String table) {
        try {
            update_car_seq.setInt(1, i);
            update_car_seq.setString(2, table);
            int updatedRecords = update_car_seq.executeUpdate();
            if (updatedRecords != 1) {
                System.out.println("Couldn't update seq index");
            } else {
                System.out.println("Successfully updated seq index");
            }
        } catch (SQLException e) {
            System.out.println("Couldn't set new value to seq index: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ObservableList<Part> getParts(int carIndex) {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        try {
            query_parts_by_index.setInt(1, carIndex);
            ResultSet results = query_parts_by_index.executeQuery();
            while (results.next()) {
                int id = results.getInt(1);
                String name = results.getString(2);
                LocalDate date = LocalDate.parse(results.getString(3));
                String additionalInfo = results.getString(4);
                Integer carId = results.getInt(5);

                parts.add(new Part(id, name, date, additionalInfo, carId));
            }
        } catch (SQLException e) {
            System.out.println("Query 'query_cars' failed: " + e.getMessage());
            e.printStackTrace();
        }
        return parts;
    }

    public void insertIntoParts(Part part, int index) {
        try {
            String partName = part.getName();
            LocalDate date = part.getReplaceDate();
            String additionalInformation = part.getAdditionalInformation();

            insert_into_parts.setString(1, partName);
            insert_into_parts.setString(2, date.toString());

            insert_into_parts.setString(3, Objects.requireNonNullElse(additionalInformation, ""));
            insert_into_parts.setInt(4, index);

            int rowInserted = insert_into_parts.executeUpdate();
            if (rowInserted != 1) {
                System.out.println("Error with inserting to parts table!!!");
            } else {
                System.out.println("Successfully inserted new part into table.");
            }
        } catch (SQLException e) {
            System.out.println("Couldn't insert to parts table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean deleteFromParts(int id) {
        try {
            delete_from_parts.setInt(1, id);
            int deletedRecords = delete_from_parts.executeUpdate();
            if (deletedRecords != 1) {
                System.out.println("Deletion Unsuccessful!");
                return false;
            } else {
                System.out.println("Deletion Successful");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Couldn't delete this record: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList<CarDetails> getCarDetails(int carIndex) {
        ObservableList<CarDetails> details = FXCollections.observableArrayList();
        try {
            query_car_details_by_id.setInt(1, carIndex);
            ResultSet results = query_car_details_by_id.executeQuery();
            while (results.next()) {
                int id = results.getInt(1);
                String type = results.getString(2);
                String buyDate = results.getString(3);
                String expireDate = results.getString(4);
                int carId = results.getInt(5);
                details.add(new CarDetails(id, type, buyDate, expireDate, carId));
            }
        } catch (SQLException e) {
            System.out.println("Query 'query_cars' failed: " + e.getMessage());
            e.printStackTrace();
        }
        return details;
    }

    public void insertIntoDetails(CarDetails details, int index) {
        try {
            String type = details.getType();
            String buyDate = details.getBuyDate();
            String expireDate = details.getExpireDate();

            insert_into_details.setString(1, type);
            insert_into_details.setString(2, buyDate);
            insert_into_details.setString(3, expireDate);
            insert_into_details.setInt(4, index);

            int rowInserted = insert_into_details.executeUpdate();
            if (rowInserted != 1) {
                System.out.println("Error with inserting to details table!!!");
            } else {
                System.out.println("Successfully inserted new part into table.");
            }
        } catch (SQLException e) {
            System.out.println("Couldn't insert to details table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean deleteFromDetails(int id) {
        try {
            delete_from_details.setInt(1, id);
            int deletedRecords = delete_from_details.executeUpdate();
            if (deletedRecords != 1) {
                System.out.println("Deletion Unsuccessful!");
                return false;
            } else {
                System.out.println("Deletion Successful");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Couldn't delete this record: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public void deleteAllFromParts() {
        try {
            delete_all_from_parts.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Couldn't Delete Everything From Table parts: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteAllFromDetails() {
        try {
            delete_all_from_details.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Couldn't Delete Everything From Table details: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
