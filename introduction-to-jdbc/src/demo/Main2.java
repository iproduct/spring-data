package demo;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) throws SQLException, IOException {
        Scanner sc = new Scanner(System.in);
//
        Properties props = new Properties();
//        String appConfigPath = Main2.class.getClassLoader()
//                .getResource("db.properties").getPath();
//        props.load(new FileInputStream(appConfigPath));
        System.out.println("Enter  username (default root): ");
        String user = sc.nextLine().trim();
        user = user.length() > 0 ? user : "root";
        props.setProperty("user", user);

        System.out.println("Enter password (default root): ");
        String password = sc.nextLine().trim();
        password = password.length() > 0 ? password : "root";
        props.setProperty("password", password);

        // 1. Load jdbc driver (optional)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Driver loaded successfully.");

        // 2. Connect to DB
//        try (
        Connection connection =
                DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/soft_uni", props);

        System.out.println("Connected successfully.");

        // 3. Execute query
        PreparedStatement stmt =
                connection.prepareStatement("SELECT * FROM employees WHERE salary > ?");
        System.out.println("Enter  minimal salary (default 40000): ");
        String salaryStr = sc.nextLine().trim();
        salaryStr = salaryStr.length() > 0 ? salaryStr : "40000";
//            String salaryStr = props.getProperty("salary", "20000");
        double salary = Double.parseDouble(salaryStr);


        stmt.setDouble(1, salary);
        ResultSet rs = stmt.executeQuery();

        // 4. Process results
        while (rs.next()) {
            System.out.printf("| %-15.15s | %-15.15s | %10.2f |%n",
                    rs.getString(2),
                    rs.getString("last_name"),
                    rs.getDouble("salary")
            );
        }
//        }

        // 5. Close connection and statement
        connection.close();
    }
}
