package com.ciic.test.service;

/**
 * Created by lixuecheng on 2017/7/11.
 */
public interface Action {
    /**
     * 点击操作
     * @return
     */
    int click();
    /**
     * 清除内容操作
     * @return
     */
    int clear();
    /**
     * 元素是否存在
     * @return
     */
    boolean isExist();
    /**
     * 输入操作
     * @return
     */
    int input(String text);
    /**
     * 获取文本内容
     * @return
     */
    String getText();
    /**
     * 下拉框选择
     * @return
     */
    int select();
    /**
     * 等待时间
     * @return
     */
    int waitTime(int millisecond);
    /**
     * 上传文件
     * @return
     */
    int upload();
    /**
     * 提示框确认
     * @return
     */
    int prompt_dialog();
    /**
     * 选择框操作
     * @return
     */
    int confirm_dialog(boolean chioce);
    /**
     * 元素是否可用
     * @return
     */
    boolean isEnabled();
    /**
     * 元素是否被选择
     * @return
     */
    boolean isSelect();
    /**
     * 元素是否可见
     * @return
     */
    boolean isDisplayed();
    /**
     * 获得元素属性
     * @return
     */
    String get_attribute(String attr);
    /**
     * 获得元素html
     * @return
     */
    String getSource();



}
