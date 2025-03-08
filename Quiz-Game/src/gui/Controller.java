package gui;

import domain.Quiz;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import service.Service;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class Controller {
    private Service service;
    private Quiz selectedQuestion;
    private ObservableList<Quiz> quizObservableList;
    private double totalScore;

    public Controller(Service service) {
        this.service = service;
    }
//1
    @FXML
    private ListView<Quiz> quizListView;
//2
    @FXML
    private TextField searchTextField;
//2    
    @FXML
    private TextField minScoreTextField;
//3    
    @FXML
    private TextField userAnswerUpdate;
//4
    @FXML
    private TextField totalScoreTextField;
//1
    @FXML
    private void initializeOrReset() {
        totalScore = 0;
        updateTotalScoreDisplay();
        quizObservableList = FXCollections.observableArrayList();
        List<Quiz> quizzes = new ArrayList<>();

        service.getAll().forEach(quizzes::add);

        quizzes = quizzes.stream()
                .sorted(Comparator.comparing(Quiz::getScore, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        quizObservableList.addAll(quizzes);
        quizListView.setItems(quizObservableList);

        quizListView.setCellFactory(listView -> new ListCell<Quiz>() {
            @Override
            protected void updateItem(Quiz quiz, boolean empty) {
                super.updateItem(quiz, empty);
                if (empty || quiz == null) {
                    setText(null);
                } else {
                    setText(String.format("%s (%s): %s", quiz.getId(), quiz.getText(), quiz.getUser_answer()));
                }
            }
        });
//2
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());
        minScoreTextField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());
    }
    @FXML
    private void initialize(){
        initializeOrReset();


        quizListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedQuestion = newValue;
                userAnswerUpdate.setText(selectedQuestion.getUser_answer());
            }
        });
        userAnswerUpdate.textProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedQuestion != null && !newValue.equals(selectedQuestion.getUser_answer())) {
                selectedQuestion.setUser_answer(newValue);
                service.updateRecipe(selectedQuestion);

                checkAnswer(selectedQuestion);
            }
        });

    }
//2
    private void handleSearch() {
        String searchText = searchTextField.getText().trim();
        double minScore = 0;

        try {
            minScore = Double.parseDouble(minScoreTextField.getText().trim());
        } catch (NumberFormatException e) {
            minScore = 0;
        }

        searchQuizzes(searchText, minScore);
    }
//2
    private void searchQuizzes(String searchText, double minScore) {
        if (service == null) return;

        List<Quiz> filteredQuizzes = new ArrayList<>();
        service.getAll().forEach(filteredQuizzes::add);

        filteredQuizzes = filteredQuizzes.stream()
                .filter(quiz -> quiz.getText() != null && quiz.getText().toLowerCase().contains(searchText.toLowerCase())
                        && quiz.getScore() > minScore)
                .sorted(Comparator.comparing(Quiz::getScore, Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        quizObservableList.setAll(filteredQuizzes);
    }
    //4
    private void checkAnswer(Quiz quiz) {
        if (quiz != null && quiz.getUser_answer() != null && quiz.getUser_answer().equalsIgnoreCase(quiz.getCorrect_answer())) {
            totalScore += quiz.getScore();
            updateTotalScoreDisplay();
        }
    }

    private void updateTotalScoreDisplay() {
        totalScoreTextField.setText("Total Score: " + totalScore);
    }

    @FXML
    private void handleHintButton(ActionEvent event) {
        if (selectedQuestion != null) {
            String correctAnswer = selectedQuestion.getCorrect_answer();
            if (correctAnswer.length() >= 2) {
                String hint = correctAnswer.substring(0, 2);
                userAnswerUpdate.setPromptText(hint);
                totalScore=totalScore-2;
            }
        }
    }


}
