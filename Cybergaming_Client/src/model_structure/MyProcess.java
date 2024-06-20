
package model_structure;

// dùng để tạo model cho việc gửi ảnh và nhận ảnh
import java.io.Serializable;


public class MyProcess implements Serializable{
    
    private String imagename;
    private String pid;

    
    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
