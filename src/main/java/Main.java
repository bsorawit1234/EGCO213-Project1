import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Customer {
    String name;

    public Customer(String n) {
        name = n;
    }
}

class Booking {
    int bookingID;

    public Booking() {
        String path = "src/main/java/";
        String fileName = "bookings.txt";
        boolean openSuccess = false;

        while (!openSuccess) {
            try (Scanner scan = new Scanner(new File(path + fileName));) {
                openSuccess = true;
                ArrayList<Customer> CL = new ArrayList<Customer>();

                for(int i = 0; scan.hasNext(); i++) {
                    String line = scan.nextLine();
                    String[] col = line.split(",");

                    bookingID = Integer.parseInt(col[0].trim());

                    Customer cm = new Customer(col[1].trim());

                    boolean duplicate = false;
                    for (Customer item : CL) {
                        if (item.name.equals(cm.name)) {
                            duplicate = true;
                            // add more code
                            break;
                        }
                    }

                    if(!duplicate) CL.add(cm);

                }
                for(Customer c: CL) System.out.println(c.name);


            } catch (FileNotFoundException e) {
                System.err.println(e);
            }
        }



    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("lugo");
        Booking b1 = new Booking();
    }
}
