package com.study.pojo;

public class Attempt {
    private User user;
    private Word word;
    private long ts;
    private boolean success;

    public Attempt() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "user=" + user +
                ", word=" + word +
                ", ts=" + ts +
                ", success=" + success +
                '}';
    }
}
