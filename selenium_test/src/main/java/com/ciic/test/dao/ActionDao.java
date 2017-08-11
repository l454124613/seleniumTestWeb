package com.ciic.test.dao;

import com.ciic.test.service.Action;
import com.ciic.test.service.WebdriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lixuecheng on 2017/7/11.
 */
@Service
public class ActionDao implements Action {

    @Override
    public int click() {

        return 0;
    }

    @Override
    public int clear() {
        return 0;
    }

    @Override
    public boolean isExist() {
        return false;
    }

    @Override
    public int input(String text) {
        return 0;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public int select() {
        return 0;
    }

    @Override
    public int waitTime(int millisecond) {
        return 0;
    }

    @Override
    public int upload() {
        return 0;
    }

    @Override
    public int prompt_dialog() {
        return 0;
    }

    @Override
    public int confirm_dialog(boolean chioce) {
        return 0;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean isSelect() {
        return false;
    }

    @Override
    public boolean isDisplayed() {
        return false;
    }

    @Override
    public String get_attribute(String attr) {
        return null;
    }

    @Override
    public String getSource() {
        return null;
    }


}
