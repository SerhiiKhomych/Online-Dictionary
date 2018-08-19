package com.study.pojo;

import javax.persistence.*;

@Entity
@Table(indexes = {
        @Index(name = "ATTEMPT_USER_WORD_INDEX", columnList = "login, wordId")
})
public class Attempt {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long attemptId;

    @Column(name = "login")
    private String login;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="wordId")
    private Word word;

    private long ts;

    private boolean success;

    public Attempt() {
    }

    public long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(long attemptId) {
        this.attemptId = attemptId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
                "attemptId=" + attemptId +
                ", ts=" + ts +
                ", success=" + success +
                '}';
    }
}
