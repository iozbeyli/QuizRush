package com.fromthemind.quizrush.Game;

import com.fromthemind.quizrush.Challenge;

/**
 * Created by MEHMET on 17.05.2017.
 */

public interface Challengable<T extends Challenge> {
    boolean hasChallenge();
    T getChallenge();
}
