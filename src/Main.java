import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/emp_dept", "bitcode", "Bitcode@123");
        System.out.println(con.getClass().getName());

        Statement statement = con.createStatement();

        /*ResultSet rs = statement.executeQuery("select * from emp");
        while(rs.next()) {
            System.out.println(
                    rs.getInt(1)  + " " + rs.getString(2)
                            + " " + rs.getDate(3)
            );
        }*/

        System.out.println("----------------------------");
        /*boolean isSuccessful =
                statement.execute("insert into dept values(60, 'Training', 'Pune')");
        System.out.println(isSuccessful);
*/
        System.out.println();
        System.out.println();
        //System.out.println("Stm: " + statement.getClass().getName() + " rs: " + rs.getClass().getName());

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username and password ");
        System.out.println();
        String username = scanner.nextLine();
        String password = scanner.nextLine();

        if(username.contains("'") || password.contains("'")) {
        }




        //not good
        /*String query = "select * from users where username = '" + username + "' and password = '" + password + "'";
        System.out.println(query);
        ResultSet rs = statement.executeQuery(query);
        */

        PreparedStatement pstmt =
                con.prepareStatement(
                        "select * from users where username = ? and password = ?"
                );
        pstmt.setString(1, username);
        pstmt.setString(2, password);

        ResultSet rs = pstmt.executeQuery();

        if(rs.next()) {
            System.out.println(username + " login successful...");
        }
        else {
            System.out.println(username + " login failed...");
        }

        pstmt = con.prepareStatement("update emp set name = ? where id = ?");
        pstmt.setString(1, "Virat Kohli");
        pstmt.setInt(2, 101);

        int count = pstmt.executeUpdate();
        System.out.println("count = " + count);


        con.close();
    }
}
