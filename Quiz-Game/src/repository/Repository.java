package repository;

import domain.Quiz;
import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Repository {
    protected HashMap<Integer, Quiz> Quiz = new HashMap<>();


    public void add(Integer key, Quiz recipe) {
        if(!Quiz.containsKey(key))
            Quiz.put(key,recipe);

    }
    public void modify(Integer key, Quiz recipe) {
        if(Quiz.containsKey(key))
            Quiz.put(key,recipe);}

    public Quiz findById(Integer key) {
        if (Quiz.containsKey(key))
            return Quiz.get(key);
        return null;
    }

    public String toStringRepo() {
        String repositoryStringRepresentation ="";
        for(Quiz recipe: Quiz.values())
            repositoryStringRepresentation = repositoryStringRepresentation + Quiz.toString() + "\n";
        return repositoryStringRepresentation;
    }

    public Iterable<Quiz> getAll() {
        return  Quiz.values();
    }

    public abstract void updateInDatabase(Quiz gene);
}
