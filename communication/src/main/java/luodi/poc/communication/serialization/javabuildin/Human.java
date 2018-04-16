package luodi.poc.communication.serialization.javabuildin;

/**
 * Created by liujinjing on 2018/4/16.
 */
public class Human {

    private boolean ifUseTool = true;
    protected String sex;

    public boolean isIfUseTool() {
        return ifUseTool;
    }

    public void setIfUseTool(boolean ifUseTool) {
        this.ifUseTool = ifUseTool;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
