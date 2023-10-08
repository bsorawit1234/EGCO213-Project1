import java.io.*;
import java.util.*;

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
    public int getQty() { return qty; }
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

    public double getBill() { return getRatePlus() * this.qty; }

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
    private String name;
    private int cashBack;
    private int singleRooms;
    private int twinRooms;
    private int trippleRooms;
    private int singleDormRooms;
    private int mealHeads;

    public Customer(String n) {
        name = n;
        cashBack = 0;
        singleRooms = 0;
        twinRooms = 0;
        trippleRooms = 0;
        singleDormRooms = 0;
        mealHeads = 0;
    }

    public String getName() { return name; }
    public int getCashBack() { return cashBack; }
    public void setCashBack(int c) { cashBack = c; }
    public void setSingleRooms(int r) { singleRooms += r; }
    public void setSingleDormRooms(int r) { singleDormRooms += r;}
    public void setTwinRooms(int r) { twinRooms += r; }
    public void setTrippleRooms(int r) { trippleRooms += r; }
    public void setMealHeads(int h) { mealHeads += h; }
}

class Booking {
    int bookingID;
    ArrayList<Customer> CL = new ArrayList<Customer>();

    public Booking() {
        String path = "src/main/java/";
        String fileName = "bookings.txt";
        HotelList h = new HotelList();
        ArrayList<Item> hl = h.menu("hotel.txt");

        boolean openSuccess = false;
        while (!openSuccess) {
            try (Scanner scan = new Scanner(new File(path + fileName));) {
                openSuccess = true;
                System.out.printf("\nRead booking data from file %s%s \n\n\n", path, fileName);
                System.out.println("===== Booking Processing =====");
                ArrayList<Customer> CL = new ArrayList<Customer>();

                while(scan.hasNext()) {
                    String line = scan.nextLine();
                    String[] col = line.split(",");

                    bookingID = Integer.parseInt(col[0].trim());
                    int night = Integer.parseInt(col[2].trim());
                    int single = Integer.parseInt(col[3].trim());
                    int twin = Integer.parseInt(col[4].trim());
                    int tripple = Integer.parseInt(col[5].trim());
                    int singleDorm = Integer.parseInt(col[6].trim());
                    int meal = Integer.parseInt(col[7].trim());

                    ArrayList<Item> items = new ArrayList<Item>();
                    items.add(new Room("R", hl.get(0).getRate(), single));
                    items.add(new Room("R", hl.get(1).getRate(), twin));
                    items.add(new Room("R", hl.get(2).getRate(), tripple));
                    items.add(new Room("R", hl.get(3).getRate(), singleDorm));
                    items.add(new Meal("M", hl.get(4).getRate(), meal));

                    Customer cm = new Customer(col[1].trim());

                    boolean duplicate = false;
                    for(Customer c : CL) {
                        if(c.getName().equals(cm.getName())) {
                            duplicate = true;
                            c.setSingleRooms(single);
                            c.setSingleDormRooms(singleDorm);
                            c.setTwinRooms(twin);
                            c.setTrippleRooms(tripple);
                            c.setMealHeads(meal);
                            cm = c;
                            break;
                        }
                    }
                    if(!duplicate) CL.add(cm);


                    process(bookingID, cm, night, items);
                }


            } catch (FileNotFoundException e) {
                System.err.println(e);
            }
        }
    }

    public void process(int id, Customer c, int n, ArrayList<Item> items) {
        double totalRoomPrice;
        double totalMealPrice = items.get(4).getBill() * n;
        double totalBill = 0;
        double finalBill;
        int cashback;
        int redeem = c.getCashBack();

        for(Item i : items) {
            totalBill += i.getBill();
        }

        totalBill *= n;
        totalRoomPrice = totalBill - totalMealPrice;

        cashback = (int)(totalRoomPrice * 0.05);

        if(redeem > totalBill * 0.5) {
            cashback += redeem - (int)(totalBill * 0.5);
            redeem = (int)(totalBill * 0.5);
        }

        finalBill = totalBill - redeem;

        System.out.printf("Booking%3d, %s,%4d nights   >> Single Room (  %3d)    Twin Room (  %3d)    Triple Room (  %3d)    Single Dorm Bed (  %3d)    Breakfast(  %3d)\n", id, c.getName(), n, items.get(0).getQty(), items.get(1).getQty(), items.get(2).getQty(), items.get(3).getQty(), items.get(4).getQty());
        System.out.printf("Available cashback = %-,5d      >> Total room price++    =   %,6.2f    with service charge and VAT\n", c.getCashBack(), totalRoomPrice);
        System.out.printf("                                >> Total meal price      =   %,6.2f\n", totalMealPrice);
        System.out.printf("                                >> Total bill            =   %,6.2f    redeem = %,d\n", totalBill, redeem);
        System.out.printf("                                >> Final bill            =   %,6.2f    cashback for next booking = %,d\n", finalBill, cashback);

        c.setCashBack(cashback);
    }
}

public class Main {
    public static void main(String[] args) {
        Booking b1 = new Booking();
    }
}
