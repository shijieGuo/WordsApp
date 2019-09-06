package com.example.guoshijie.wordsapp.Word;

public class wordrecord {
    private String word;
    private String example;
    private String exampletranslation;
    public wordrecord(){

    }
    public wordrecord(String word, String example, String exampletranslation){
        this.word=word;
        this.example=example;
        this.exampletranslation=exampletranslation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExampletranslation() {
        return exampletranslation;
    }

    public void setExampletranslation(String exampletranslation) {
        this.exampletranslation = exampletranslation;
    }
}
