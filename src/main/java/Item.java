//constructor without qty is invoked for create menu, it follows that constructor with qty is used for customer's queries

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

class Item {
    private final String type;
    protected double rate;
    private int qty;
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
    }
    public Room(String type, double rate, int qty) {super(type, rate, qty);}

    //rate with service charge & vat
    public double getRatePlus() {
        return rate + ((rate + (rate * 0.1)) * 0.07);
    }

}

class Meal extends Item {
    private static int total_meals;
    public Meal (String type, double rate) {
        super(type, rate);
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
        ArrayList<Item> hotel = new ArrayList<Item>();

        try (
                Scanner scan = new Scanner(new FileReader(path + file));
        ){
            while(scan.hasNext()) {
                String line = scan.nextLine();
                lines = line.split(",");
                character = lines[0].trim();
                type = lines[1].trim();
                rate = Double.parseDouble(lines[2].trim());

                if(character.equals("R")) {
                    Room room = new Room(type, rate);
                    hotel.add(room);
                } else if(character.equals("M")) {
                    Meal meal = new Meal(type, rate);
                    hotel.add(meal);
                } else {
                    //throw exception invalid character or something.
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return hotel;
    }
}
