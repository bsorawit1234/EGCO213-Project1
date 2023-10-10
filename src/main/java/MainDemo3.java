import java.io.*;
import java.util.*;

class Item3 {
    private final String type;
    protected double rate;
    protected int qty;
    public Item3(String type, double rate) {
        this.type = type;
        this.rate = rate;
    }
    public Item3(String type, double rate, int qty) {
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

class Room3 extends Item3 {
    private static double total_rooms;
    public Room3(String type, double rate) {
        super(type, rate);
        System.out.printf("%-22srate = %,10.2f     rate++ = %,10.2f\n", this.getType(), this.getRate(), this.getRatePlus());
    }
    public Room3(String type, double rate, int qty) {super(type, rate, qty);}

    //rate with service charge & vat
    public double getRatePlus() {
        return rate + (rate * 0.1) + ((rate + (rate * 0.1)) * 0.07);
    }

    public double getBill() { return getRatePlus() * this.qty; }

}

class Meal3 extends Item3 {
    private static int total_meals;
    public Meal3(String type, double rate) {
        super(type, rate);
        System.out.printf("%-22srate = %,10.2f     rate++ = %,10.2f\n", this.getType(), this.getRate(), this.getRate());
    }
    public Meal3(String type, double rate, int qty) {
        super(type, rate, qty);
    }
}

class HotelList3 {
    public ArrayList<Item3> menu(String file) {
        double rate;
        String character, type;
        String[] lines = new String[3];
        String path = "src/main/java/";
        String filename = file;
        ArrayList<Item3> hotel = new ArrayList<Item3>();
        boolean openSuccess = false;
        Scanner keyboardScan = new Scanner(System.in);

        while (!openSuccess) {
            try (
                    Scanner scan = new Scanner(new FileInputStream(path + filename), "UTF-8")
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
                        Room3 room3 = new Room3(type, rate);
                        hotel.add(room3);
                    } else if (character.equals("M")) {
                        Meal3 meal3 = new Meal3(type, rate);
                        hotel.add(meal3);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println(e);
                System.out.println("Enter file name for hotel data = ");
                filename = keyboardScan.nextLine();
                keyboardScan.nextLine();
            }
        }

        return hotel;
    }
}


class Customer3 {
    private String name;
    private int cashBack, singleRooms, twinRooms, trippleRooms, singleDormRooms, mealHeads;
    private Double singleRoomsPrice, twinRoomsPrice, trippleRoomsPrice, singleDormRoomsPrice, mealHeadsPrice;

    public Customer3(String n) {
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

class Booking3 {
    int bookingID;
    public static int flag = 1;
    ArrayList<Customer3> CL = new ArrayList<Customer3>();
    Double totsales = 0.00;
    HotelList3 h = new HotelList3();
    ArrayList<Item3> hl = h.menu("hotel.txt");
    public double getRatePlus(int i) {
        return hl.get(i).getRate() + (hl.get(i).getRate() * 0.1) + ((hl.get(i).getRate() + (hl.get(i).getRate() * 0.1)) * 0.07);
    }

    public int parseAndValidate(String[] col, int index, int expectedCol) {
        int value;
        try {
            if(col.length < expectedCol) {
                throw new MissingInputException(String.valueOf(expectedCol - col.length) + " columns missing");
            }
            value = Integer.parseInt(col[index].trim());
            if (value < 0) {
                throw new InvalidInputException("For input: \"" + value + "\"");
            }
            return value;
        } catch (NumberFormatException | InvalidInputException e) {
            System.out.println(e);
        } catch (MissingInputException e) {
            System.out.println(e);
            flag = 0;
            return -1;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e);
        }
        flag = 0;
        return 0;
    }

    public Booking3() {
        String path = "src/main/java/";
        String filename = "bookings.txt";
//        String filename = "bookings_errors.txt";
        Scanner scanner = new Scanner(System.in);
        Customer3 c1 = new Customer3("");
        boolean openSuccess = false;
        int expectedCol = 8;
//        System.out.println(hl.get(0).getType());
        while(!openSuccess) {
            try (
                    Scanner keyScan = new Scanner(new FileInputStream(path + filename), "UTF-8");
                    ) {
                openSuccess = true;
                System.out.printf("\nRead booking data from file %s%s \n\n\n", path, filename);
                ArrayList<Customer3> CL = new ArrayList<Customer3>();

                ArrayList<Integer> ID = new ArrayList<Integer>();
                ArrayList<Integer> night = new ArrayList<Integer>();
                ArrayList<Integer> single = new ArrayList<Integer>();
                ArrayList<Integer> twin = new ArrayList<Integer>();
                ArrayList<Integer> tripple = new ArrayList<Integer>();
                ArrayList<Integer> singleDorm = new ArrayList<Integer>();
                ArrayList<Integer> meal = new ArrayList<Integer>();
                ArrayList<String> name = new ArrayList<String>();

                String line;
                String[] col;
                String[] ne = new String[8];
                int in = 0;

                while (keyScan.hasNext()) {
                    line = keyScan.nextLine();
                    col = line.split(",");



                    name.add(col[1].trim());
                    ID.add(parseAndValidate(col, 0, expectedCol));
                    if(ID.get(in) == -1) {
                        for(int j = 0; j < expectedCol; j++) {
                            if(j > col.length - 1) {
                                ne[j] = "0";
                            } else if(j <= col.length - 1){
                                ne[j] = col[j];
                            }
                        }
                        ID.set(in, in + 1);
                        night.add(parseAndValidate(ne, 2, expectedCol));
                        single.add(parseAndValidate(ne, 3, expectedCol));
                        twin.add(parseAndValidate(ne, 4, expectedCol));
                        tripple.add(parseAndValidate(ne, 5, expectedCol));
                        singleDorm.add(parseAndValidate(ne, 6, expectedCol));
                        meal.add(parseAndValidate(ne, 7, expectedCol));
                    } else {
                        night.add(parseAndValidate(col, 2, expectedCol));
                        single.add(parseAndValidate(col, 3, expectedCol));
                        twin.add(parseAndValidate(col, 4, expectedCol));
                        tripple.add(parseAndValidate(col, 5, expectedCol));
                        singleDorm.add(parseAndValidate(col, 6, expectedCol));
                        meal.add(parseAndValidate(col, 7, expectedCol));
                    }
                    String[] newCol = new String[8];
                    newCol[0] = String.valueOf((ID.get(in)));
                    newCol[1] = col[1].trim();
                    newCol[2] = String.valueOf((night.get(in)));
                    newCol[3] = String.valueOf((single.get(in)));
                    newCol[4] = String.valueOf((twin.get(in)));
                    newCol[5] = String.valueOf((tripple.get(in)));
                    newCol[6] = String.valueOf((singleDorm.get(in)));
                    newCol[7] = String.valueOf((meal.get(in)));


                    if(flag == 0) {
                        System.out.println("Original [" + line + "]  -->  Correction  [" + String.join(", ", newCol) + "]\n");
                        flag = 1;
                    }

                    in++;
                }

                System.out.println("===== Booking Processing =====");
                for(int i = 0; i < ID.size(); i++) {
                    c1.setTwinRooms(twin.get(i) * night.get(i));
                    c1.setTwinRoomsPrice(twin.get(i) * night.get(i) * getRatePlus(1));
                    c1.setSingleDormRooms(singleDorm.get(i) * night.get(i));
                    c1.setSingleDormRoomsPrice(singleDorm.get(i) * night.get(i) * getRatePlus(3));
                    c1.setTrippleRooms(tripple.get(i) * night.get(i));
                    c1.setTrippleRoomsPrice(tripple.get(i) * night.get(i) * getRatePlus(2));
                    c1.setSingleRooms(single.get(i) * night.get(i));
                    c1.setSingleRoomsPrice(single.get(i) * night.get(i) * getRatePlus(0));
                    c1.setMealHeads(meal.get(i) * night.get(i));
                    c1.setMealHeadsPrice(meal.get(i) * night.get(i) * hl.get(4).getRate());

                    ArrayList<Item3> item3s = new ArrayList<Item3>();
                    item3s.add(new Room3("R", hl.get(0).getRate(), single.get(i)));
                    item3s.add(new Room3("R", hl.get(1).getRate(), twin.get(i)));
                    item3s.add(new Room3("R", hl.get(2).getRate(), tripple.get(i)));
                    item3s.add(new Room3("R", hl.get(3).getRate(), singleDorm.get(i)));
                    item3s.add(new Meal3("M", hl.get(4).getRate(), meal.get(i)));


                    Customer3 cm = new Customer3(name.get(i));

                    boolean duplicate = false;
                    for (Customer3 c : CL) {
                        if (c.getName().equals(cm.getName())) {
                            duplicate = true;
                            c.setSingleRooms(single.get(i));
                            c.setSingleDormRooms(singleDorm.get(i));
                            c.setTwinRooms(twin.get(i) * night.get(i));
                            c.setTrippleRooms(tripple.get(i));
                            c.setMealHeads(meal.get(i));
                            cm = c;
                            break;
                        }
                    }
                    if (!duplicate) CL.add(cm);

                    bookingID = ID.get(i);
                    process(bookingID, cm, night.get(i), item3s);

                }

            } catch (FileNotFoundException e) {
                System.out.print(e);
                System.out.println("\nEnter file name for booking data = ");
                filename = scanner.nextLine();
            }
        }

        scanner.close();

        System.out.println("===== Room Summary =====");
        System.out.printf("Twin Room          total sales = %5d rooms         %,.2f  Bahts\n", c1.getTwinRooms(), c1.getTwinRoomsPrice());
        System.out.printf("Single Dorm Bed    total sales = %5d rooms         %,.2f  Bahts\n", c1.getSingleDormRooms(), c1.getSingleDormRoomsPrice());
        System.out.printf("Triple Room        total sales = %5d rooms         %,.2f  Bahts\n", c1.getTrippleRooms(), c1.getTrippleRoomsPrice());
        System.out.printf("Single Room        total sales = %5d rooms         %,.2f  Bahts\n\n", c1.getSingleRooms(), c1.getSingleRoomsPrice());
        System.out.println("===== Meal Summary =====");
        System.out.printf("Breakfast          total sales = %5d heads         %,.2f  Bahts\n", c1.getMealHeads(), c1.getMealHeadsPrice());
    }

    public void process(int id, Customer3 c, int n, ArrayList<Item3> item3s) {
        double totalRoomPrice;
        double totalMealPrice = item3s.get(4).getBill() * n;
        double totalBill = 0;
        double finalBill;
        int cashback;
        int redeem = c.getCashBack();

        for(Item3 i : item3s) {
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

        System.out.printf("Booking%3d, %s,%4d nights   >> Single Room (  %3d)    Twin Room (  %3d)    Triple Room (  %3d)    Single Dorm Bed (  %3d)    Breakfast(  %3d)\n", id, c.getName(), n, item3s.get(0).getQty(), item3s.get(1).getQty(), item3s.get(2).getQty(), item3s.get(3).getQty(), item3s.get(4).getQty());
        System.out.printf("Available cashback = %-,5d      >> Total room price++    =   %,10.2f    with service charge and VAT\n", c.getCashBack(), totalRoomPrice);
        System.out.printf("                                >> Total meal price      =   %,10.2f\n", totalMealPrice);
        System.out.printf("                                >> Total bill            =   %,10.2f    redeem = %,d\n", totalBill, redeem);
        System.out.printf("                                >> Final bill            =   %,10.2f    cashback for next booking = %,d\n\n", finalBill, cashback);

        c.setCashBack(cashback);
    }
}

public class MainDemo3 {
    public static void main(String[] args) {
        Booking3 b1 = new Booking3();
    }
}
