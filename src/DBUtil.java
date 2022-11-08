import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

public class DBUtil {
    private Connection con;

    private static DBUtil dbUtil = null;

    public static DBUtil getInstance() {
        if(dbUtil == null) {
            dbUtil = new DBUtil();
        }

        return dbUtil;
    }

    private DBUtil() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_dept", "bitcode", "Bitcode@123");
        }
        catch (Exception e){}
    }

    public boolean addUser(
            String username,
            String password
    ) {
        try {
            PreparedStatement preparedStatement =
                    con.prepareStatement(
                            "insert into users values(?, ?)"
                    );
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            int count = preparedStatement.executeUpdate();
            if(count == 1) {
                return true;
            }
        }
        catch (Exception e) {

        }
        return false;
    }

    ArrayList<Emp> getAllEmps() {
        try {
            Statement statement = con.createStatement();
            ResultSet rs =
                    statement.executeQuery("select id, name, dob, location, image from emp");

            ArrayList<Emp> emps = new ArrayList<>();
            while(rs.next()) {

                Blob imageBlob;

                try {
                    imageBlob = rs.getBlob(5);
                }
                catch (Exception e){
                    e.printStackTrace();
                    imageBlob = null;
                }

                emps.add(
                        new Emp(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getDate(3),
                                rs.getString(4),
                                imageBlob != null ? (imageBlob.getBytes(1, (int) imageBlob.length()) ): null
                        )
                );
            }

            return emps;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public boolean addEmp(
            int id,
            String name,
            String dob,
            int mgr,
            int deptNo,
            int sal,
            String location,
            String imagePath
    ) {

        try {
            PreparedStatement preparedStatement =
                    con.prepareStatement(
                            "insert into emp values(?,?,?,?,?,?,?,?)"
                    );

            InputStream in = new FileInputStream(imagePath);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, dob);
            preparedStatement.setInt(4, mgr);
            preparedStatement.setInt(5, deptNo);
            preparedStatement.setInt(6, sal);
            preparedStatement.setString(7, location);
            preparedStatement.setBlob(8, in);

            if(preparedStatement.executeUpdate() == 1) {
                return true;
            }

        }
        catch (Exception e) {}
        return false;


    }
}
