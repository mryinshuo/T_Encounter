package com.shiyuji.model;

public class QuestionSettingItem {      // QuestionSettings中自定义item的实体类
    private String question;                // 问题
    private String answer;                  // 回答
    private boolean isExpanded;             // 问题是否已展开

    public QuestionSettingItem(String question, String answer, boolean isExpanded) {
        this.question = question;
        this.answer = answer;
        this.isExpanded = isExpanded;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
