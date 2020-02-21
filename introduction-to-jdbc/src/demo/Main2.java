package demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Main2 {
    public static void main(String[] args) throws SQLException, IOException {
//        Scanner sc = new Scanner(System.in);
//
        Properties props = new Properties();
        String appConfigPath = Main2.class.getClassLoader()
                .getResource("db.properties").getPath();
        props.load(new FileInputStream(appConfigPath));

//        props.setProperty("user", user);
//        props.setProperty("password", password);

        // 1. Load jdbc driver (optional)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Driver loaded successfully.");

        // 2. Connect to DB
        try (
                Connection connection =
                    DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/employees?useSSL=false", props);
                PreparedStatement stmt =
                    connection.prepareStatement("SELECT * FROM employees JOIN salaries ON employees.emp_no=salaries.emp_no WHERE salaries.salary > ?")) {

            System.out.println("Connected successfully.");
//            System.out.println("Enter  minimal salary (default 20000): ");
//            String salaryStr = sc.nextLine().trim();
            String salaryStr = props.getProperty("salary", "20000");
            double salary = Double.parseDouble(salaryStr);

            // 3. Execute query
            stmt.setDouble(1, salary);
            ResultSet rs = stmt.executeQuery();

            // 4. Process results
            while (rs.next()) {
                System.out.printf("| %-15.15s | %-15.15s | %10.2f |\n",
                        rs.getString(2),
                        rs.getString("last_name"),
                        rs.getDouble("salary")
                );
            }
        }
//        connection.close();
    }
}
