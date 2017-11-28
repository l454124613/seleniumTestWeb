package com.ciic.test.bean;

/**
 * Created by lixuecheng on 2017/7/11.
 */
public class Element {


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    private String id;
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getIsframe() {
        return isframe;
    }

    public void setIsframe(String isframe) {
        this.isframe = isframe;
    }

    private String isframe;

    private String locationMethod;
    private String value;
    private String name;
    private String topage;
    private String toframe;
    private String waitid;
    private String waitvalue;
    private String pid;
    private  String pagename;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"num\":\"")
                .append(num).append('\"');
        sb.append(",\"isframe\":\"")
                .append(isframe).append('\"');
        sb.append(",\"locationMethod\":\"")
                .append(locationMethod).append('\"');
        sb.append(",\"value\":\"")
                .append(value).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"topage\":\"")
                .append(topage).append('\"');
        sb.append(",\"toframe\":\"")
                .append(toframe).append('\"');
        sb.append(",\"waitid\":\"")
                .append(waitid).append('\"');
        sb.append(",\"waitvalue\":\"")
                .append(waitvalue).append('\"');
        sb.append(",\"pid\":\"")
                .append(pid).append('\"');
        sb.append(",\"pagename\":\"")
                .append(pagename).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getWaitid() {
        return waitid;
    }

    public void setWaitid(String waitid) {
        this.waitid = waitid;
    }

    public String getWaitvalue() {
        return waitvalue;
    }

    public void setWaitvalue(String waitvalue) {
        this.waitvalue = waitvalue;
    }

    public String getToframe() {
        return toframe;
    }

    public void setToframe(String toframe) {
        this.toframe = toframe;

    }

    public String getTopage() {
        return topage;
    }

    public void setTopage(String topage) {
        this.topage = topage;
    }

//    @Override
//    public String toString() {
//        return "{" +
//                "\"id\":\"" + id + '\"' +
//                ", \"type\":\"" + totype(type) + '\"' +
//                ", \"locationMethod\":\"" + tolo(locationMethod) + '\"' +
//                ", \"value\":\"" + value + '\"' +
//                ", \"name\":\"" + name + '\"' +
//                ", \"topage\":\"" + topage + '\"' +
//                '}';
//    }

    private  String totype(String type){
        String re="未确定";
        switch (type){
            case "1":re="按钮";break;
            case "2":re="多选";break;
            case "3":re="提示框";break;
            case "4":re="单选";break;
            case "5":re="下拉框";break;
            case "6":re="文本";break;
            case "7":re="上传";break;
        }

        return re;
    }

    private  String tolo(String locationMethod){
        String re="未确定";
        switch (locationMethod){
            case "1":re="id";break;
            case "2":re="name";break;
            case "3":re="tagname";break;
            case "4":re="linktext";break;
            case "5":re="classname";break;
            case "6":re="xpath";break;
            case "7":re="css";break;

        }

        return re;
    }



    public String getLocationMethod() {
        return locationMethod;
    }

    public void setLocationMethod(String locationMethod) {
        this.locationMethod = locationMethod;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
