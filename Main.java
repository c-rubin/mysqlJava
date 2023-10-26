import java.sql.*;
import java.util.Scanner;
// import java.sql.Driver;

public class Main {
    public static void main(String args[]){
        String url="jdbc:mysql://localhost/actionsJava";
        String user="root";
        String password ="";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url,user,password);

            doSmthWithDb(connection);

            connection.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void doSmthWithDb (Connection conn) throws Exception{
        while(true) {
            System.out.print("What do you want to do?\n\nView existing rows? (1)\nAdd another row (2)\nAnswer: ");
            Scanner scan = new Scanner(System.in);
            String answ = scan.nextLine();
            if (answ.equals("1")){
                Statement state = conn.createStatement();
                ResultSet result = state.executeQuery("Select * from users");

                System.out.println("Table users consists of the following rows:");
                while(result.next()){
                    System.out.println(result.getInt(1) + " " + result.getString(2));
                }
                break;
            }
            else if (answ.equals("2")){
//                System.out.print("Enter id (integer): ");
//                String id = scan.nextLine();
                System.out.print("Enter name (String): ");
                String name = scan.nextLine();

                PreparedStatement state = conn.prepareStatement("INSERT INTO users(name) VALUES(?);");
//                state.setString(1,id);
                state.setString(1,name);
                state.executeUpdate();
                ResultSet result = conn.createStatement().executeQuery("SELECT * FROM users");

                System.out.println("Table users now consists of the following rows:");
                while(result.next()){
                    System.out.println(result.getInt(1) + " " + result.getString(2));
                }
                break;
            }
        }
    }
}