package org.example.inC0502;

import java.util.*;

import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.example.Common.MyUtils;
import org.example.inC0502.Exceptions.EInvalidInput;
import org.example.inC0502.Exceptions.EProductsErr;

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

// region defaultProducts
            List<Products> defaultProducts = new LinkedList<>(Arrays.asList(
                    new Products("Chips", 1261, MyUtils.randomDouble(1.5, 5.0, 2), true, MyUtils.randomInt(0, 100)),
                    new Products("Used car", 12363, MyUtils.randomInt(800, 20000), true, MyUtils.randomInt(0, 100)),
                    new Products("Tent", 1873, MyUtils.randomDouble(89, 300, 2), true, MyUtils.randomInt(0, 100)),
                    new Products("Laptop", 768324, MyUtils.randomDouble(200, 5000, 2), false, MyUtils.randomInt(0, 100)),
                    new Products("Onion", 1234, MyUtils.randomDouble(.5, 5.0, 2), true, MyUtils.randomInt(0, 100)),
                    new Products("Pasta", 1233, MyUtils.randomDouble(1.0, 4.0, 2), true, MyUtils.randomInt(0, 100)),
                    new Products("Phone", 768324, MyUtils.randomInt(200, 1500), false, MyUtils.randomInt(0, 100)),
                    new Products("Repair kit", 9832, MyUtils.randomDouble(20, 30, 2), false, MyUtils.randomInt(0, 100)),
                    new Products("New car", 73476, MyUtils.randomInt(20000, 150000), false, MyUtils.randomInt(0, 100)),
                    new Products("New car", 734761, MyUtils.randomInt(25000, 50000), true, MyUtils.randomInt(0, 100))
            ));
// endregion

//                region PRODUCT LAMBDAS
            Supplier<Stream<Products>> getAvailableProducts = () -> defaultProducts.stream()
                    .filter(products -> products.isAvailable());

            System.out.println("\nTODO: merge <getProductWithSku> and <getProductWithName> into a <BiFunction>\n");
            Function<String, Optional<Products>> getProductWithSku = sku -> getAvailableProducts.get()
                    .filter(product -> {
//                        System.out.println("comparing PROD:SKU" + product.getSku() + " WITH SKU" + sku);
                        return String.valueOf(product.getSku()).equals(sku);
                    })
                    .findFirst();

            Function<String, Optional<List<Products>>> getProductWithName = name -> {
                List<Products> matches = getAvailableProducts.get()
                        .filter(product -> product.getName().equalsIgnoreCase(name))
                        .collect(Collectors.toList());
                return matches.isEmpty() ? Optional.empty() : Optional.of(matches);
            };

            Supplier<Optional<List<Products>>> getProducts = () -> {
                List<Products> matches = defaultProducts.stream()
                        .filter(products -> products.isAvailable())
                        .collect(Collectors.toList());
                return matches.isEmpty() ? Optional.empty() : Optional.of(matches);
            };

//            MyUtils.TriFunction<Double, Boolean, Boolean, Optional<Products[]>> getProdsAsPriceLimit = (price, isUpper, isAscending) -> {
//                Products[] match = getAvailableProducts.get()
//                        .filter(product -> isUpper ? product.getPrice() >= price : product.getPrice() <= price)
////                        .sorted((a, b) -> isAscending ? a.compareTo(b) : a.reverseCompareTo(b))
//                        .sorted(isAscending ?
//                                Comparator.comparingDouble(p -> p.getPrice()) :
//                                Comparator.comparingDouble(p -> p.getPrice()).reversed()) // #0506.2349
//                        .toArray(Products[]::new);
//                if (match.length > 0) return Optional.of(match);
//                return Optional.empty();
//            };
            MyUtils.TriFunction<Double, Boolean, Boolean, Optional<List<Products>>> getProdsAsPriceLimit =
                    (price, isUpper, isAscending) -> {

                        Comparator<Products> byPrice = Comparator.comparingDouble(p -> p.getPrice());
                        Comparator<Products> cmp = isAscending ? byPrice : byPrice.reversed();

                        List<Products> match = getAvailableProducts.get()
                                .filter(p -> isUpper ? p.getPrice() >= price : p.getPrice() <= price)
                                .sorted(cmp)
                                .collect(Collectors.toList());

                        return match.isEmpty() ? Optional.empty() : Optional.of(match);
                    };

            Function<Products, String> getProductToString = p -> "SKU:" + p.getSku() + " | NAME:" + p.getName() + "(" + p.getStock() + ") | PRICE: $" + p.getPrice();
//                endregion

            do {
                System.out.println("\nWelcome to the 'We-Got-It-All' general store. Select your operation...");
                System.out.println("(1) View customer catalogue");//done
                System.out.println("(2) Find/Update product details by name and/or sku");//done enough
                System.out.println("(3) Find available product(s) above/below price");//done
                System.out.println("(4) Remove products under price");
                System.out.println("(5) View products in ascending/decending order of their price/sku/name");//done
                System.out.println("(6) Add Product");//done
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
//                            final List<Products> selectProd = Collections.emptyList();

                            if (userIn.equalsIgnoreCase("S")) {
                                do {
                                    System.out.print("Enter product SKU:");
                                    userIn = sc.nextLine();
                                    if (userIn.trim().isEmpty())
                                        System.out.println("Valid SKU is required. Please try again...");
                                } while (userIn.isEmpty());

                                Optional<Products> foundProd = getProductWithSku.apply(userIn);

                                if (foundProd.isPresent()) {
                                    System.out.println("Product found...");
                                    System.out.println(getProductToString.apply(foundProd.get()));
                                } else {
                                    System.out.println("No Product with this SKU found.");
                                    break;
                                }
//                                foundProd.ifPresent(product -> selectProd[0][0] = foundProd.get());
//                                System.out.println("Product found...");
//                                System.out.println(getProductToString.apply(selectProd[0][0]));

                            } else if (userIn.equalsIgnoreCase("N")) {
                                do {
                                    System.out.print("Enter product name(not case sensitive):");
                                    userIn = sc.nextLine();
                                    if (userIn.trim().isEmpty())
                                        System.out.println("Valid name is required. Please try again...");
                                } while (userIn.isEmpty());

//                                final String userInRef = userIn;
                                Optional<List<Products>> foundProd = getProductWithName.apply(userIn);
//                                Optional<Products> foundProd = defaultProducts.stream()
//                                        .filter(product -> product.getName().equalsIgnoreCase(userInRef))
//                                        .findFirst();

                                if (!foundProd.isPresent()) {
                                    System.out.println("No Product with this name found.");
                                    break;
                                } else {
                                    System.out.println(foundProd.get().size() + " Product(s) found...");
                                    foundProd.get().forEach(product -> {
                                        System.out.println(getProductToString.apply(product));
                                    });
                                }
//                                foundProd.ifPresent(product -> selectProd = product);
//                                System.out.println(getProductToString.apply(selectProd[0]));

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

//                    region 3.Find available Products above/below price
                    case '3':
                        double price = 0.0;
                        boolean isUpper = false;
                        boolean isAscending = false;
                        do {
                            try {
                                System.out.println("\nNOTE:: \n(eg:<23.99a OR >1.0d) where the \n first character is '<'for ascending and '>'for descending \n second is the price \n the last character is 'a' for ascending list or 'd' for descending list");
                                System.out.print("Enter '<' OR '>' for upper or lower and then the price:");
                                userIn = sc.nextLine().trim();

                                if (userIn.trim().isEmpty()) throw new NumberFormatException("Input cannot be empty");

                                if (userIn.startsWith(">")) isUpper = true;
                                else if (userIn.startsWith("<")) isUpper = false;
                                else throw new NumberFormatException("First character is invalid. Use '< or >'");

                                if (userIn.toLowerCase().endsWith("a")) isAscending = true;
                                else if (userIn.toLowerCase().endsWith("d")) isAscending = false;
                                else throw new NumberFormatException();

                                price = Double.parseDouble(userIn.substring(1, userIn.length() - 1));
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid entry. Try again");
                                userIn = "";
                            }
                        } while (userIn.isEmpty());

                        Optional<List<Products>> prods = getProdsAsPriceLimit.apply(price, isUpper, isAscending);
                        if (!prods.isPresent()) System.out.println("No results found");
                        else {
                            System.out.println(prods.get().size() + " result(s) found...");
                            for (Products p : prods.get()) System.out.println(getProductToString.apply(p));
                        }
                        break;
//                    endregion

//                        region 5.GET AVAILABLE products in asc/dec order
                    case '5':
                        List<Products> match = getAvailableProducts.get().collect(Collectors.toList());

                        if (!match.isEmpty()) {
                            match.forEach(product -> System.out.println(getProductToString.apply(product)));
                        } else System.out.println("No Products to display");
                        break;
//                        endregion

                    case'4':
                        System.out.println("--NOT IMPLEMENTED--");
                        break;

// region 6.Add Product
                    case '6':
                        System.out.println("\nAdding new product. Provide the following info");
                        int newSku = 0;
                        String newName;
                        double newPrice = 0;
                        int newStock = 0;

                        do {
                            do {
                                try {

                                    System.out.print("-SKU (max 10 numbers):");
                                    userIn = sc.nextLine();
                                    newSku = Integer.parseInt(userIn);
                                    if (getProductWithSku.apply(userIn).isPresent()) {
                                        throw new EProductsErr("The SKU exists.");
                                    }

                                } catch (NumberFormatException e) {
                                    System.out.println("Please provide a number of max 10 digits");
                                    userIn = "";
                                } catch (EProductsErr e) {
                                    System.out.println("The SKU exists. Use a different SKU.");
                                    userIn = "";
                                }
                            } while (userIn.isEmpty());

                            do {
                                System.out.print("-Name:");
                                newName = sc.nextLine().trim();
                                if (newName.isEmpty()) {
                                    newName = "";
                                    System.out.println("Name cannot be empty. Provide a valid name");
                                }
                            } while (newName.isEmpty());

                            do {
                                System.out.print("-Price:$");
                                try {
                                    userIn = sc.nextLine().trim();
                                    newPrice = Double.parseDouble(userIn);
                                } catch (NumberFormatException e) {
                                    System.out.println("Please provide a valid price.");
                                    userIn="";
                                }
                            } while (userIn.isEmpty());

                            do {
                                System.out.print("-Stock:");
                                try {
                                    userIn = sc.nextLine().trim();
                                    newStock = Integer.parseInt(userIn);
                                } catch (NumberFormatException e) {
                                    System.out.println("Please provide a valid stock amount.");
                                    userIn="";
                                }
                            } while (userIn.isEmpty());

                            System.out.println("-Abailability will be defaulted to <YES> for this product-");

                            System.out.print("\n-NAME:" + newName + "\n-PRICE:$" + newPrice + "\n-STOCK:" + newStock + "\n-Available:YES\nPLEASE CONFIRM ABOVE DETAILS... (Y)To add product | (<any other key>):start over :");

                        }while(!sc.nextLine().toLowerCase().startsWith("y"));

                        defaultProducts.add(new Products(newName, newSku, newPrice, true, newStock));

                        Optional<Products> newProd = getProductWithSku.apply(String.valueOf(newSku));
                        if(newProd.isPresent()) {
                            System.out.println("Product added | " + getProductToString.apply(newProd.get()) );
                        }else{
                            System.out.println("Something went wrong... Unable to add product");
                        }
                        break;
                    // endregion

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
