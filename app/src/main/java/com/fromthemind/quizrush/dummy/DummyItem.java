package com.fromthemind.quizrush.dummy;

import com.fromthemind.quizrush.RushListItem;

/**
 * Created by MEHMET on 17.05.2017.
 */

public class DummyItem implements RushListItem<DummyItem> {

    public DummyItem(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    public String getVisibleContent(){
        return content;
    }

    @Override
    public DummyItem rushItem() {
        return this;
    }
}
