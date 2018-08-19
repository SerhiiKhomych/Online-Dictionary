package com.study.pojo;

import javax.persistence.*;

@Entity
@Table(indexes = {
        @Index(name = "HISTORY_USER_WORD_INDEX", columnList = "login,wordId"),
        @Index(name = "HISTORY_USER_MODE_INDEX", columnList = "login,repetition_mode"),
        @Index(name = "HISTORY_USER_MODE_WORD_INDEX", columnList = "login,repetition_mode,wordId")
})
public class History {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long historyId;

    @Column(name = "login")
    private String login;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="wordId")
    private Word word;

    @Enumerated(EnumType.STRING)
    @Column(name = "repetition_mode")
    private RepetitionMode repetitionMode;

    private int repetitions;

    @Column(name = "success_attempts")
    private int successAttempts;

    @Column(name = "success_rate")
    private double successRate;

    @Column(name = "last_repeat_date")
    private long lastRepeatDate;

    @Column(name = "added_date")
    private long addedDate;

    public History() {
    }

    public History(String login, RepetitionMode mode, long addedDate, Word word, int repetitions, int successAttempts, double successRate, long lastRepeatDate) {
        this.login = login;
        this.repetitionMode = mode;
        this.addedDate = addedDate;
        this.word = word;
        this.repetitions = repetitions;
        this.successAttempts = successAttempts;
        this.successRate = successRate;
        this.lastRepeatDate = lastRepeatDate;
    }

    public long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(long historyId) {
        this.historyId = historyId;
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

    public RepetitionMode getRepetitionMode() {
        return repetitionMode;
    }

    public void setRepetitionMode(RepetitionMode repetitionMode) {
        this.repetitionMode = repetitionMode;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getSuccessAttempts() {
        return successAttempts;
    }

    public void setSuccessAttempts(int successAttempts) {
        this.successAttempts = successAttempts;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public long getLastRepeatDate() {
        return lastRepeatDate;
    }

    public void setLastRepeatDate(long lastRepeatDate) {
        this.lastRepeatDate = lastRepeatDate;
    }

    public long getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(long addedDate) {
        this.addedDate = addedDate;
    }

    @Override
    public String toString() {
        return "History{" +
                "word=" + word +
                ", repetitions=" + repetitions +
                ", successAttempts=" + successAttempts +
                ", successRate=" + successRate +
                ", lastRepeatDate=" + lastRepeatDate +
                ", addedDate=" + addedDate +
                ", login=" + login +
                ", repetitionMode=" + repetitionMode +
                ", historyId=" + historyId +
                '}';
    }
}
