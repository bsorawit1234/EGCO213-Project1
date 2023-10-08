import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

class Item {
    private final String type;
    protected double rate;
    protected int qty;
    public Item(String type, double rate) {
        this.type = type;
        this.rate = rate;
    }
    public Item(String type, double rate, int qty) {
        this.type = type;
        this.rate = rate;
        this.qty = qty;
    }
    public String getType() {
        return type;
    }
    public double getRate() {
        return rate;
    }
    public double getBill() {
        return this.rate * this.qty;
    }
}

class Room extends Item{
    private static double total_rooms;
    public Room(String type, double rate) {
        super(type, rate);
        System.out.printf("%-22srate = %,10.2f     rate++ = %,10.2f\n", this.getType(), this.getRate(), this.getRatePlus());
    }
    public Room(String type, double rate, int qty) {super(type, rate, qty);}

    //rate with service charge & vat
    public double getRatePlus() {
        return rate + (rate * 0.1) + ((rate + (rate * 0.1)) * 0.07);
    }

    public double getBill() { return getRatePlus() * this.qty; };

}

class Meal extends Item {
    private static int total_meals;
    public Meal (String type, double rate) {
        super(type, rate);
        System.out.printf("%-22srate = %,10.2f     rate++ = %,10.2f\n", this.getType(), this.getRate(), this.getRate());
    }
    public Meal (String type, double rate, int qty) {
        super(type, rate, qty);
    }
}

class HotelList {
    public ArrayList<Item> menu(String file) {
        double rate;
        String character, type;
        String[] lines = new String[3];
        String path = "src/main/java/";
        String filename = file;
        ArrayList<Item> hotel = new ArrayList<Item>();
        boolean openSuccess = false;
        Scanner keyboardScan = new Scanner(System.in);

        while (!openSuccess) {
            try (
                    Scanner scan = new Scanner(new FileReader(path + filename));
            ) {
                openSuccess = true;
                System.out.printf("Read hotel data from file %s%s \n\n", path, filename);
                while (scan.hasNext()) {
                    String line = scan.nextLine();
                    lines = line.split(",");
                    character = lines[0].trim();
                    type = lines[1].trim();
                    rate = Double.parseDouble(lines[2].trim());
                    if (character.equals("R")) {
                        Room room = new Room(type, rate);
                        hotel.add(room);
                    } else if (character.equals("M")) {
                        Meal meal = new Meal(type, rate);
                        hotel.add(meal);
                    } else {
                        //throw exception invalid character or something.
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println(e);
                System.out.println("Enter file name for hotel data = ");
                filename = keyboardScan.nextLine();
            }
        }

        keyboardScan.close();
        return hotel;
    }
}


class Customer {
    String name;
    double cashback;

    public Customer(String n) {
        name = n;
    }
}

class Booking {
    int bookingID;

    public Booking() {
        String path = "src/main/java/";
        String fileName = "bookings.txt";
        int night, singleR, twinR, tripleR, singledormB, headsBreakfast;
        boolean openSuccess = false;

        while (!openSuccess) {
            try (Scanner scan = new Scanner(new File(path + fileName));) {
                openSuccess = true;
                System.out.printf("\nRead booking data from file %s%s \n\n\n", path, fileName);
                System.out.println("===== Booking Processing =====");
                ArrayList<Customer> CL = new ArrayList<Customer>();

                for(int i = 0; scan.hasNext(); i++) {
                    String line = scan.nextLine();
                    String[] col = line.split(",");

                    bookingID = Integer.parseInt(col[0].trim());
                    Customer cm = new Customer(col[1].trim());
                    night = Integer.parseInt(col[2].trim());
                    singleR = Integer.parseInt(col[3].trim());
                    twinR = Integer.parseInt(col[4].trim());
                    tripleR = Integer.parseInt(col[5].trim());
                    singledormB = Integer.parseInt(col[6].trim());
                    headsBreakfast = Integer.parseInt(col[7].trim());

                    //Item it = new Item();

                    process(bookingID, cm.name, night, singleR, twinR, tripleR, singledormB, headsBreakfast);

                    boolean duplicate = false;
                    for (Customer item : CL) {
                        if (item.name.equals(cm.name)) {
                            duplicate = true;

                            // add more code
                            break;
                        }
                    }

                    if(!duplicate) {
                        CL.add(cm);
                    }

                }
                for(Customer c: CL) System.out.println(c.name);


            } catch (FileNotFoundException e) {
                System.err.println(e);
            }
        }
    }

    public void process(int bookingID, String name, int night, int singleR, int twinR,int tripleR, int singledormB, int headsBreakfast) {
        System.out.printf("Booking%3d, %s,%4d nights   >> Single Room (  %3d)    Twin Room (  %3d)    Triple Room (  %3d)    Single Dorm Bed (  %3d)    Breakfast(  %3d)\n", bookingID, name, night, singleR, twinR, tripleR, singledormB, headsBreakfast);

    }
}

public class Main {
    public static void main(String[] args) {

        HotelList h = new HotelList();
        h.menu("hotel.txt"); //valid name for hotel file :)
        Booking b1 = new Booking();
    }
}
