package com.ciic.test.bean;


import com.ciic.test.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by lixuecheng on 2017/7/13.
 */
public class item {
    private String      id;
    private String      name;
    private String isused;
    private String url;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"isused\":\"")
                .append(isused).append('\"');
        sb.append(",\"url\":\"")
                .append(url).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String getIsused() {
        return isused;
    }

    public void setIsused(String isused) {
        this.isused = isused;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
