package repository;

import domain.Quiz;

import java.sql.*;

public class DBRepository extends Repository {
    public String databaseName;

    public DBRepository(String databaseName) {
        this.databaseName = databaseName;
        try (Connection dBConnection = DriverManager.getConnection("jdbc:sqlite:" + this.databaseName);) {
            PreparedStatement preparedStatement = dBConnection.prepareStatement("SELECT * FROM Quiz");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String text = resultSet.getString(2);
                String correct_answer = resultSet.getString(3);
                double score = resultSet.getDouble(4);
                String user_answer = resultSet.getString(5);


                Quiz recipe=new Quiz(id,text,correct_answer,score,user_answer);
                super.add(id,recipe);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateInDatabase(Quiz Quiz){
        try (Connection dbConnection = DriverManager.getConnection("jdbc:sqlite:" + this.databaseName)) {
            String sql = "UPDATE Quiz SET user_answer = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = dbConnection.prepareStatement(sql)) {
                preparedStatement.setString(1, Quiz.getUser_answer());
                preparedStatement.setInt(2, Quiz.getId());
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();


        }
    }



}