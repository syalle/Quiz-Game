package service;

import domain.Quiz;
import repository.Repository;
import repository.DBRepository;

public class Service {
    private Repository repository;

    public Service(Repository repo) {
        this.repository = repo;
    }

    public void modify(int id, String text, String correct_answer, double score, String user_answer) {
        Quiz Quiz= new Quiz(id, text, correct_answer, score, user_answer);
        repository.modify(id,Quiz);
    }
    public Quiz findById(Integer key){
        return repository.findById(key);
    }

    public String toStringRepo(){
        return repository.toString();
    }
    public Iterable<Quiz> getAll(){
        return repository.getAll();
    }


    public void updateRecipe(Quiz Quiz){
        repository.modify(Quiz.getId(),Quiz);
        repository.updateInDatabase(Quiz);

    }

}
