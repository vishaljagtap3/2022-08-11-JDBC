import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;

public class Emp {
    private int id;
    private String name;
    private Date dob;
    private String location;
    private String imagePath;

    public Emp(int id, String name, Date dob, String location, byte [] image) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.location = location;
        if(image != null) {
            try {
                imagePath = "/home/vishal/java/" + System.currentTimeMillis() + "-" + id + ".jpg";
                File userImage = new File(imagePath);
                userImage.createNewFile();

                FileOutputStream fout = new FileOutputStream(userImage);
                fout.write(image);
                fout.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            imagePath = null;
        }
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", location='" + location + '\'' +
                ", iamgepath='" + imagePath + '\'' +
                '}';
    }
}
