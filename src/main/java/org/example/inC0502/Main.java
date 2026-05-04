package org.example.inC0502;

import java.util.*;

import java.util.function.*;

import org.example.Common.MyUtils;

/*
 * <Lambdas>
 * * <AtomicReference>
 * built-in functional interface
 * <Streams>
 *   - filter, foreach
 *   - HOMEWORK: collect, sort, map, distinct, findAny vs findFirst vs anyMatch
 * <Optional>
 * */
public class Main {

    public static void main(String[] args) {

        // always return bool, expect parameters
        Predicate<Integer> isEven = i -> {
            return i % 2 == 0;
        };

        //expects return type and para type
        Function<Integer, String> isEvenText = i -> {
            return i % 2 == 0 ? "even" : "odd";
        };

        // no return, expects para type
        Consumer<Double> setDouble = i -> {
            System.out.println("idDouble: " + i);
        };

        //no para, expects return type
        Supplier<Integer> getInt = () -> 12;

        Scanner sc = new Scanner(System.in);
        boolean continueLoop = true;
        String userIn;

        while (continueLoop) {
            List<Products> defaultProducts = new LinkedList<>(Arrays.asList(
                    new Products("Chips", 1261, MyUtils.randomDouble(0.5, 5.0, 2), true, MyUtils.randomInt(0, 100)),
                    new Products("Used car", 12363, MyUtils.randomDouble(800, 20000, 2), true, MyUtils.randomInt(0, 100)),
                    new Products("Tent", 1873, MyUtils.randomDouble(89, 300, 2), true, MyUtils.randomInt(0, 100)),
                    new Products("Onion", 1234, MyUtils.randomDouble(.5, 5.0, 2), true, MyUtils.randomInt(0, 100)),
                    new Products("Pasta", 1233, MyUtils.randomDouble(1.0, 4.0, 2), true, MyUtils.randomInt(0, 100)),
                    new Products("Phone", 768324, MyUtils.randomDouble(200, 1500, 2), false, MyUtils.randomInt(0, 100)),
                    new Products("Laptop", 768324, MyUtils.randomDouble(200, 5000, 2), false, MyUtils.randomInt(0, 100)),
                    new Products("Repair kit", 9832, MyUtils.randomDouble(20, 30, 2), false, MyUtils.randomInt(0, 100)),
                    new Products("New car", 73476, MyUtils.randomDouble(20000, 150000, 2), false, MyUtils.randomInt(0, 100))
            ));

//                region PRODUCT LAMBDAS
            System.out.println("\nTODO: merge <getProductWithSku> and <getProductWithName> into a <BiFunction>\n");
            Function<String, Optional<Products>> getProductWithSku = sku -> defaultProducts.stream()
                    .filter(product -> String.valueOf(product.getSku()).equals(sku))
                    .findFirst();

            Function<String, Optional<Products>> getProductWithName = name -> defaultProducts.stream()
                    .filter(product -> product.getName().equalsIgnoreCase(name))
                    .findFirst();

            Supplier<Products[]> getAvailable = () -> defaultProducts.stream()
                    .filter(products -> products.isAvailable())
                    .toArray(Products[]::new);

            BiFunction<Double, Boolean, Optional<Products[]>> getAvailablePriceLimit = (price, isUpper) -> {
                Products[] match = defaultProducts.stream()
                        .filter(product -> isUpper ? product.getPrice() >= price : product.getPrice() <= price)
                        .toArray(Products[]::new);

                if (match.length > 0) return Optional.of(match);
                return Optional.empty();
            };

            Function<Products, String> getProductToString = p -> "NAME:" + p.getName() + "(" + p.getStock() + ") | SKU:" + p.getSku() + " | PRICE: $" + p.getPrice();
//                endregion

            do {

                System.out.println("\nWelcome to the 'We-Got-It-All' general store. Select your operation...");
                System.out.println("(1) View entire catalogue");//done
                System.out.println("(2) Find/Update product details by name and/or sku");//done enough
                System.out.println("(3) Find available product(s) above/below price");//done
                System.out.println("(4) Remove products under price");
                System.out.println("(5) View products in ascending/decending order of their price/sku/name/availability");//done
                System.out.print("(<any other key>) To Exit ::");

                switch (sc.nextLine().charAt(0)) {

//                        region 1.GET ALL PRODUCT LIST
                    case '1':
                        System.out.println();
                        System.out.println("--CATALOGUE--");

                        Function<Boolean, String> checkAvail = bool -> {
                            if (bool) return "Available";
                            return "Not Available";
                        };

                        defaultProducts.forEach(product -> System.out.println(getProductToString.apply(product)));
                        break;
//                        endregion

//                        region 2.GET PRODUCT WITH NAME/SKU FOR EDIT
                    case '2':
                        System.out.println();
                        do {
                            System.out.print("Press (S)to enter SKU OR (N)to enter name, to find the product:");
                            userIn = String.valueOf(sc.nextLine().charAt(0));
                            final Products[] selectProd = new Products[1];

                            if (userIn.equalsIgnoreCase("S")) {
                                do {
                                    System.out.print("Enter product SKU:");
                                    userIn = sc.nextLine();
                                    if (userIn.trim().isEmpty())
                                        System.out.println("Valid SKU is required. Please try again...");
                                } while (userIn.isEmpty());

                                Optional<Products> foundProd = getProductWithSku.apply(userIn);
//                                final String userInRef = userIn;
//                                Optional<Products> foundProd = defaultProducts.stream()
//                                        .filter(product -> String.valueOf(product.getSku()).equals(userInRef))
//                                        .findFirst();

                                if (!foundProd.isPresent()) {
                                    System.out.println("No Product with this SKU found.");
                                    break;
                                }
                                foundProd.ifPresent(product -> selectProd[0] = foundProd.get());
                                System.out.println("Product found...");
                                System.out.println(getProductToString.apply(selectProd[0]));

                            } else if (userIn.equalsIgnoreCase("N")) {
                                do {
                                    System.out.print("Enter product name(not case sensitive):");
                                    userIn = sc.nextLine();
                                    if (userIn.trim().isEmpty())
                                        System.out.println("Valid name is required. Please try again...");
                                } while (userIn.isEmpty());

//                                final String userInRef = userIn;
                                Optional<Products> foundProd = getProductWithName.apply(userIn);
//                                Optional<Products> foundProd = defaultProducts.stream()
//                                        .filter(product -> product.getName().equalsIgnoreCase(userInRef))
//                                        .findFirst();

                                if (!foundProd.isPresent()) {
                                    System.out.println("No Product with this name found.");
                                    break;
                                }
                                foundProd.ifPresent(product -> selectProd[0] = foundProd.get());
                                System.out.println("Product found...");
                                System.out.println(getProductToString.apply(selectProd[0]));

                            } else {
                                System.out.println("Invalid input.");
                                break;
                            }

                            System.out.print("Continue to edit? (Y)Yes | (<any other key>)No:");
                            if (String.valueOf(sc.nextLine().charAt(0)).equalsIgnoreCase("Y")) {
                                System.out.println("-EDIT PRODUCT HERE-");
                            }

                        } while (userIn.equals("1") || userIn.equals("2"));

                        break;
//                        endregion

//                    region 3.Find available Products above\below price
                    case '3':
                        double price = 0.0;
                        boolean isUpper = false;
                        do {
                            try {
                                System.out.print("\nEnter '<' OR '>' for upper or lower and then the price (eg:<23.99 OR >1.0):");
                                userIn = sc.nextLine();

                                if (userIn.trim().isEmpty()) throw new NumberFormatException();

                                if (userIn.charAt(0) == '>') isUpper = true;
                                else if (userIn.charAt(0) == '<') isUpper = false;
                                else throw new NumberFormatException();

                                price = Double.parseDouble(userIn.substring(1));
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid entry. Try again");
                                userIn = "";
                            }
                        } while (userIn.isEmpty());

                        Optional<Products[]> prods = getAvailablePriceLimit.apply(price, isUpper);
                        if (!prods.isPresent()) System.out.println("No results found");
                        else for (Products p : prods.get()) System.out.println(getProductToString.apply(p));
                        break;
//                    endregion

//                        region 5.GET AVAILABLE
                    case '6':
                        Products[] availableProducts = getAvailable.get();
                        System.out.println("\nAvailable product Catalogue(" + Arrays.stream(availableProducts).count() + ")");
                        for (Products p : availableProducts) getProductToString.apply(p);
                        break;
//                        endregion

                    default:
                        continueLoop = false;
                        break;
                }

                if (continueLoop) {
                    System.out.print("\nPRESS : (Y)To perform another operation | (<any other key>)No:");
                    if (!String.valueOf(sc.nextLine().charAt(0)).equalsIgnoreCase("Y")) continueLoop = false;
                }
            } while (continueLoop);
            System.out.println("\nClosing 'We-Got-It-All' general store. BYE!!");

            System.out.print("\nTry again? | (Y)Yes | (<any other key>)No:");
            if (String.valueOf(sc.nextLine().charAt(0)).equalsIgnoreCase("Y")) continueLoop = true;
            else continueLoop = false;
        }
    }
}
