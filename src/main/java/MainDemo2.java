import java.io.*;
import java.util.*;

class Item2 {
    private final String type;
    protected double rate;
    protected int qty;
    public Item2(String type, double rate) {
        this.type = type;
        this.rate = rate;
    }
    public Item2(String type, double rate, int qty) {
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

class Room2 extends Item2 {
    private static double total_rooms;
    public Room2(String type, double rate) {
        super(type, rate);
        System.out.printf("%-22srate = %,10.2f     rate++ = %,10.2f\n", this.getType(), this.getRate(), this.getRatePlus());
    }
    public Room2(String type, double rate, int qty) {super(type, rate, qty);}

    //rate with service charge & vat
    public double getRatePlus() {
        return rate + (rate * 0.1) + ((rate + (rate * 0.1)) * 0.07);
    }

    public double getBill() { return getRatePlus() * this.qty; }

}

class Meal2 extends Item2 {
    private static int total_meals;
    public Meal2(String type, double rate) {
        super(type, rate);
        System.out.printf("%-22srate = %,10.2f     rate++ = %,10.2f\n", this.getType(), this.getRate(), this.getRate());
    }
    public Meal2(String type, double rate, int qty) {
        super(type, rate, qty);
    }
}

class HotelList2 {
    public ArrayList<Item2> menu(String file) {
        double rate;
        String character, type;
        String[] lines = new String[3];
        String path = "src/main/java/";
        String filename = file;
        ArrayList<Item2> hotel = new ArrayList<Item2>();
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
                        Room2 room2 = new Room2(type, rate);
                        hotel.add(room2);
                    } else if (character.equals("M")) {
                        Meal2 meal2 = new Meal2(type, rate);
                        hotel.add(meal2);
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

        return hotel;
    }
}


class Customer2 {
    private String name;
    private int cashBack, singleRooms, twinRooms, trippleRooms, singleDormRooms, mealHeads;
    private Double singleRoomsPrice, twinRoomsPrice, trippleRoomsPrice, singleDormRoomsPrice, mealHeadsPrice;

    public Customer2(String n) {
        name = n;
        cashBack = 0; singleRooms = 0; twinRooms = 0; trippleRooms = 0; singleDormRooms = 0; mealHeads = 0;
        singleRoomsPrice = 0.00; twinRoomsPrice = 0.00; trippleRoomsPrice = 0.00; singleDormRoomsPrice = 0.00; mealHeadsPrice = 0.00;
    }

    public String getName() { return name; }
    public int getCashBack() { return cashBack; }
    public void setCashBack(int c) { cashBack = c; }
    public void setSingleRooms(int r) { singleRooms += r; }
    public int getSingleRooms() { return singleRooms; }
    public void setSingleRoomsPrice(double r) { singleRoomsPrice += r; }
    public double getSingleRoomsPrice() { return singleRoomsPrice; }
    public void setSingleDormRooms(int r) { singleDormRooms += r;}
    public int getSingleDormRooms() { return singleDormRooms; }
    public void setSingleDormRoomsPrice(double r) { singleDormRoomsPrice += r;}
    public double getSingleDormRoomsPrice() { return singleDormRoomsPrice;}
    public void setTwinRooms(int r) { twinRooms += r; }
    public void setTwinRoomsPrice(double r) { twinRoomsPrice += r; }
    public int getTwinRooms() { return twinRooms; }
    public double getTwinRoomsPrice() { return twinRoomsPrice; }
    public void setTrippleRooms(int r) { trippleRooms += r; }
    public int getTrippleRooms() { return trippleRooms; }
    public void setTrippleRoomsPrice(double r) { trippleRoomsPrice += r; }
    public double getTrippleRoomsPrice() { return trippleRoomsPrice; }
    public void setMealHeads(int h) { mealHeads += h; }
    public void setMealHeadsPrice(double h) { mealHeadsPrice += h; }
    public int getMealHeads() { return mealHeads; }
    public double getMealHeadsPrice() { return mealHeadsPrice; }
}

class Booking2 {
    int bookingID;
    public static int flag;
    ArrayList<Customer2> CL = new ArrayList<Customer2>();
    Double totsales = 0.00;
    HotelList2 h = new HotelList2();
    ArrayList<Item2> hl = h.menu("hotel.txt");
    public double getRatePlus(int i) {
        return hl.get(i).getRate() + (hl.get(i).getRate() * 0.1) + ((hl.get(i).getRate() + (hl.get(i).getRate() * 0.1)) * 0.07);
    }

        public void parseAndValidate(int value) throws InvalidInputException {
            if (value < 0) {
                throw new InvalidInputException("For input: \"" + value + "\"");
            }
    }


    public Booking2() {
        String path = "src/main/java/";
        String filename = "bookings_errors.txt";
        Scanner keyboardScan = new Scanner(System.in);
        Customer2 c1 = new Customer2("");

        boolean openSuccess = false;
        while (!openSuccess) {
            try (Scanner scan = new Scanner(new FileReader(path + filename));) {
                openSuccess = true;
                System.out.printf("\nRead booking data from file %s%s \n\n\n", path, filename);
                int iD, n, si, tw, tr, sin, me, na;
                ArrayList<Customer2> CL = new ArrayList<Customer2>();
                ArrayList<Integer> ID = new ArrayList<Integer>();
                ArrayList<Integer> night = new ArrayList<Integer>();
                ArrayList<Integer> single = new ArrayList<Integer>();
                ArrayList<Integer> twin = new ArrayList<Integer>();
                ArrayList<Integer> tripple = new ArrayList<Integer>();
                ArrayList<Integer> singleDorm = new ArrayList<Integer>();
                ArrayList<Integer> meal = new ArrayList<Integer>();
                ArrayList<String> name = new ArrayList<String>();

                int id = 1;
                int ie = 1;
                int in = 0;
                ArrayList<Integer> skip = new ArrayList<Integer>();

                while(scan.hasNext()) {
                    boolean flag = true;
                    String line = scan.nextLine();
                    String[] col = line.split(",");

                    try {
                        n = Integer.parseInt(col[2].trim());
                        parseAndValidate(n);

                        si = Integer.parseInt(col[3].trim());
                        parseAndValidate(si);

                        tw = Integer.parseInt(col[4].trim());
                        parseAndValidate(tw);

                        tr = Integer.parseInt(col[5].trim());
                        parseAndValidate(tr);

                        sin = Integer.parseInt(col[6].trim());
                        parseAndValidate(sin);

                        me = Integer.parseInt(col[7].trim());
                        parseAndValidate(me);

                        name.add(col[1].trim());
                        night.add(n);
                        single.add(si);
                        twin.add(tw);
                        tripple.add(tr);
                        singleDorm.add(sin);
                        meal.add(me);

                    } catch (InvalidInputException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
                        flag = false;
                        ie++;
                        System.out.println(e);
                        System.out.println("[" + line + "]  -->   skip this booking\n");
                    }
                    if(flag) {
                        ID.add(Integer.parseInt(col[0]));
                    }
                    id++;
                    in++;
                }
                System.out.println("===== Booking Processing =====");
                for(int i = 0; i < id - ie; i++) {

                    c1.setTwinRooms(twin.get(i)*night.get(i));
                    c1.setTwinRoomsPrice(twin.get(i)*night.get(i)*getRatePlus(1));
                    c1.setSingleDormRooms(singleDorm.get(i)*night.get(i));
                    c1.setSingleDormRoomsPrice(singleDorm.get(i)*night.get(i)*getRatePlus(3));
                    c1.setTrippleRooms(tripple.get(i)*night.get(i));
                    c1.setTrippleRoomsPrice(tripple.get(i)*night.get(i)*getRatePlus(2));
                    c1.setSingleRooms(single.get(i)*night.get(i));
                    c1.setSingleRoomsPrice(single.get(i)*night.get(i)*getRatePlus(0));
                    c1.setMealHeads(meal.get(i)*night.get(i));
                    c1.setMealHeadsPrice(meal.get(i)*night.get(i)*hl.get(4).getRate());

                    ArrayList<Item2> item2s = new ArrayList<Item2>();
                    item2s.add(new Room2("R", hl.get(0).getRate(), single.get(i)));
                    item2s.add(new Room2("R", hl.get(1).getRate(), twin.get(i)));
                    item2s.add(new Room2("R", hl.get(2).getRate(), tripple.get(i)));
                    item2s.add(new Room2("R", hl.get(3).getRate(), singleDorm.get(i)));
                    item2s.add(new Meal2("M", hl.get(4).getRate(), meal.get(i)));


                    Customer2 cm = new Customer2(name.get(i).trim());

                    boolean duplicate = false;
                    for(Customer2 c : CL) {
                        if(c.getName().equals(cm.getName())) {
                            duplicate = true;
                            c.setSingleRooms(single.get(i));
                            c.setSingleDormRooms(singleDorm.get(i));
                            c.setTwinRooms(twin.get(i)*night.get(i));
                            c.setTrippleRooms(tripple.get(i));
                            c.setMealHeads(meal.get(i));
                            cm = c;
                            break;
                        }
                    }
                    if(!duplicate) CL.add(cm);

                    bookingID = ID.get(i);
                    process(bookingID, cm, night.get(i), item2s);
                }
            } catch (FileNotFoundException e) {
                System.out.println(e);
                System.out.println("Enter file name for booking data = ");
                filename = keyboardScan.nextLine();
            }
        }

        keyboardScan.close();

        System.out.println("===== Room Summary =====");
        System.out.printf("Twin Room          total sales = %5d rooms         %,.2f  Bahts\n", c1.getTwinRooms(), c1.getTwinRoomsPrice());
        System.out.printf("Single Dorm Bed    total sales = %5d rooms         %,.2f  Bahts\n", c1.getSingleDormRooms(), c1.getSingleDormRoomsPrice());
        System.out.printf("Triple Room        total sales = %5d rooms         %,.2f  Bahts\n", c1.getTrippleRooms(), c1.getTrippleRoomsPrice());
        System.out.printf("Single Room        total sales = %5d rooms         %,.2f  Bahts\n\n", c1.getSingleRooms(), c1.getSingleRoomsPrice());
        System.out.println("===== Meal Summary =====");
        System.out.printf("Breakfast          total sales = %5d heads         %,.2f  Bahts\n", c1.getMealHeads(), c1.getMealHeadsPrice());
    }

    public void process(int id, Customer2 c, int n, ArrayList<Item2> item2s) {
        double totalRoomPrice;
        double totalMealPrice = item2s.get(4).getBill() * n;
        double totalBill = 0;
        double finalBill;
        int cashback;
        int redeem = c.getCashBack();

        for(Item2 i : item2s) {
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

        System.out.printf("Booking%3d, %s,%4d nights   >> Single Room (  %3d)    Twin Room (  %3d)    Triple Room (  %3d)    Single Dorm Bed (  %3d)    Breakfast(  %3d)\n", id, c.getName(), n, item2s.get(0).getQty(), item2s.get(1).getQty(), item2s.get(2).getQty(), item2s.get(3).getQty(), item2s.get(4).getQty());
        System.out.printf("Available cashback = %-,5d      >> Total room price++    =   %,6.2f    with service charge and VAT\n", c.getCashBack(), totalRoomPrice);
        System.out.printf("                                >> Total meal price      =   %,6.2f\n", totalMealPrice);
        System.out.printf("                                >> Total bill            =   %,6.2f    redeem = %,d\n", totalBill, redeem);
        System.out.printf("                                >> Final bill            =   %,6.2f    cashback for next booking = %,d\n\n", finalBill, cashback);

        c.setCashBack(cashback);
    }
}

public class MainDemo2 {
    public static void main(String[] args) {
        Booking2 b1 = new Booking2();
    }
}