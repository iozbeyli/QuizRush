package com.fromthemind.quizrush;

/**
 * Created by MEHMET on 17.05.2017.
 */

public interface RushListItem<T> {
    String getVisibleContent();
    T rushItem();
}
