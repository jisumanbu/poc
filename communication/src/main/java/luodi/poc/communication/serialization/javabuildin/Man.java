package luodi.poc.communication.serialization.javabuildin;

import java.io.Serializable;

/**
 * Created by liujinjing on 2018/4/16.
 */
public class Man extends Human implements Serializable{
//    static final long serialVersionUID = 42L;

    protected String sex;
    private String name;

    @Override
    public String getSex() {
        return sex;
    }

    @Override
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
