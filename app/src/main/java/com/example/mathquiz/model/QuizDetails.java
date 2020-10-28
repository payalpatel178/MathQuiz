package com.example.mathquiz.model;

import java.io.Serializable;

public class QuizDetails implements Serializable, Comparable {

    private String question;
    private double correct_answer;
    private double user_answer;

    public QuizDetails(String question, double correct_answer, double user_answer) {
        this.question = question;
        this.correct_answer = correct_answer;
        this.user_answer = user_answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public double getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(double correct_answer) {
        this.correct_answer = correct_answer;
    }

    public double getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(double user_answer) {
        this.user_answer = user_answer;
    }

    @Override
    public String toString() {
        return  "Question             : " + question + "\n"+
                "Your answer       : " + user_answer+ "\n"+
                "Correct answer  : " + correct_answer;
    }

    @Override
    public int compareTo(Object o) {
        QuizDetails otherObject=(QuizDetails) o;
        return Double.compare(user_answer,otherObject.getUser_answer());
    }
}
