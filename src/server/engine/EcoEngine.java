package server.engine;

import server.ServerValues;

import java.util.Random;

/**
 * Created 11/5/15
 * Software Development
 * TSA Conference, 2016
 * EcoEngine: Class containing code that creates and updates the Engine for the market
 */


public class EcoEngine {
    EcoEngine(int Stocks) {
        numberOfStocks = Stocks;
    }

    public static Object[][] stockInfo;
    public static int numberOfStocks;
    public static boolean positivePriceChange;
    public static double totalPriceChange;

    public static void initializeEngine(int numStocks) {
        numberOfStocks = numStocks;
        EcoEngine.createStocks();
    }

    public static Object[][] getData() {
        return stockInfo;
    }

    public static void genereateData() {
        EcoEngine.simulateStocks(stockInfo);
    }


    public static void createStocks() { //Creates a certain number of stocks, with certain information input into them
        stockInfo = new Object[numberOfStocks][7];
        String stockName;
        double stockPrice;
        int volitility, trend;
        boolean isTrending = false;
        int trendingUp = 0, trendDuration = 0;
        Random stockRandomizer = new Random();
        for (int i = 0; i < numberOfStocks; i++) {
            stockName = "";
            for (int j = 0; j <= 3; j++) {
                int number = stockRandomizer.nextInt(26 + 1); //26 letters in the alphabet, but the Random function does the number inputted minus 1.
                if (number == 1) {
                    stockName = stockName + "A";
                } else if (number == 2) {
                    stockName = stockName + "B";
                } else if (number == 3) {
                    stockName = stockName + "C";
                } else if (number == 4) {
                    stockName = stockName + "D";
                } else if (number == 5) {
                    stockName = stockName + "E";
                } else if (number == 6) {
                    stockName = stockName + "F";
                } else if (number == 7) {
                    stockName = stockName + "G";
                } else if (number == 8) {
                    stockName = stockName + "H";
                } else if (number == 9) {
                    stockName = stockName + "I";
                } else if (number == 10) {
                    stockName = stockName + "J";
                } else if (number == 11) {
                    stockName = stockName + "K";
                } else if (number == 12) {
                    stockName = stockName + "L";
                } else if (number == 13) {
                    stockName = stockName + "M";
                } else if (number == 14) {
                    stockName = stockName + "N";
                } else if (number == 15) {
                    stockName = stockName + "O";
                } else if (number == 16) {
                    stockName = stockName + "P";
                } else if (number == 17) {
                    stockName = stockName + "Q";
                } else if (number == 18) {
                    stockName = stockName + "R";
                } else if (number == 19) {
                    stockName = stockName + "S";
                } else if (number == 20) {
                    stockName = stockName + "T";
                } else if (number == 21) {
                    stockName = stockName + "U";
                } else if (number == 22) {
                    stockName = stockName + "V";
                } else if (number == 23) {
                    stockName = stockName + "W";
                } else if (number == 24) {
                    stockName = stockName + "X";
                } else if (number == 25) {
                    stockName = stockName + "Y";
                } else if (number == 26) {
                    stockName = stockName + "Z";
                }
            }
            stockPrice = stockRandomizer.nextDouble() * 50; // Generates a random number between 0 and 50.
            stockPrice = (double) Math.round(stockPrice * 100) / 100; //Rounds the random value to the hundredths place.
            volitility = stockRandomizer.nextInt(100 + 1); //Generates a random number from 1 to 100
            trend = stockRandomizer.nextInt(100 + 1);

            stockInfo[i][0] = stockName;
            stockInfo[i][1] = stockPrice;
            stockInfo[i][2] = volitility;
            stockInfo[i][3] = trend;
            stockInfo[i][4] = isTrending;
            stockInfo[i][5] = trendingUp;
            stockInfo[i][6] = trendDuration;

        }
    }

    public static void simulateStocks(Object[][] stockInput) { //Simulating the stocks by randomly changing random stock values
        int stockVolatility;
        double originalStockPrice = 0;
        double newStockPrice = 0;
        Random stockChangeParameter = new Random(); //Sets the parameter against the volatility is checked to see if there is a change in stock value
        for (int i = 0; i < numberOfStocks; i++) {
//            System.out.println("Volatility Value of Stock: " + String.valueOf(stockInput[i][2]));
            stockVolatility = Integer.parseInt(String.valueOf(stockInput[i][2]));
            if (Boolean.getBoolean(String.valueOf(stockInput[i][4]))) { //Changes the price of the stock if it is trending
                if (stockVolatility <= stockChangeParameter.nextInt(100 + 1)) {
                    originalStockPrice = Double.parseDouble(String.valueOf(stockInput[i][1]));
                    newStockPrice = changeStockPrice(originalStockPrice, Boolean.parseBoolean(String.valueOf(stockInput[i][4])), Integer.parseInt(String.valueOf(stockInput[i][5])), Integer.parseInt(String.valueOf(stockInput[i][6])));
                    stockInput[i][1] = newStockPrice;
                    stockInput[i][6] = Integer.parseInt(String.valueOf(stockInput[i][6])) + 1; //Increases the value of the trend by 1 turn, in other words, the stock is now trending
//                    System.out.println("Original Price of Stock:" + originalStockPrice);
//                    System.out.println("New Price for Stock:" + newStockPrice);
                    // For Pushing Purposes only
                }
            } else if (stockVolatility <= stockChangeParameter.nextInt(100 + 1)) {
                if (ServerValues.secCount > 0) {
                    if (originalStockPrice >= newStockPrice) { //Checks to see if the change in the stock market price was negative
                        if (positivePriceChange) { // Checks to see if the change in the stock price was positive after the initial negative
                            originalStockPrice = Double.parseDouble(String.valueOf(stockInput[i][1])) + totalPriceChange; // If it is positive, it bases the new price off of the last positive value, so as to keep the same level of possible increas in price
                            newStockPrice = changeStockPrice(originalStockPrice, Boolean.parseBoolean(String.valueOf(stockInput[i][4])), Integer.parseInt(String.valueOf(stockInput[i][5])), Integer.parseInt(String.valueOf(stockInput[i][6])));
                            stockInput[i][1] = newStockPrice;
                            if (Integer.parseInt(String.valueOf(stockInput[i][3])) <= stockChangeParameter.nextInt(100 + 1)) {
                                stockInput[i][4] = true;
                                if (originalStockPrice > newStockPrice) {
                                    stockInput[i][5] = 0;
                                }
                                stockInput[i][6] = Integer.parseInt(String.valueOf(stockInput[i][6])) + 1;
                            }
                        } else {
                            originalStockPrice = Double.parseDouble(String.valueOf(stockInput[i][1]));
                            newStockPrice = changeStockPrice(originalStockPrice, Boolean.parseBoolean(String.valueOf(stockInput[i][4])), Integer.parseInt(String.valueOf(stockInput[i][5])), Integer.parseInt(String.valueOf(stockInput[i][6])));
                            stockInput[i][1] = newStockPrice;
                            if (Integer.parseInt(String.valueOf(stockInput[i][3])) <= stockChangeParameter.nextInt(100 + 1)) {
                                stockInput[i][4] = true;
                                if (originalStockPrice > newStockPrice) {
                                    stockInput[i][5] = 0;
                                }
                                stockInput[i][6] = Integer.parseInt(String.valueOf(stockInput[i][6])) + 1;
                            } else {
                                originalStockPrice = Double.parseDouble(String.valueOf(stockInput[i][1]));
                                newStockPrice = changeStockPrice(originalStockPrice, Boolean.parseBoolean(String.valueOf(stockInput[i][4])), Integer.parseInt(String.valueOf(stockInput[i][5])), Integer.parseInt(String.valueOf(stockInput[i][6])));
                                stockInput[i][1] = newStockPrice;
                                if (Integer.parseInt(String.valueOf(stockInput[i][3])) <= stockChangeParameter.nextInt(100 + 1)) {
                                    stockInput[i][4] = true;
                                    if (originalStockPrice > newStockPrice) {
                                        stockInput[i][5] = 0;
                                    }
                                    stockInput[i][6] = Integer.parseInt(String.valueOf(stockInput[i][6])) + 1;
                                }
                            }
                        }
                    } else {
                        originalStockPrice = Double.parseDouble(String.valueOf(stockInput[i][1]));
                        newStockPrice = changeStockPrice(originalStockPrice, Boolean.parseBoolean(String.valueOf(stockInput[i][4])), Integer.parseInt(String.valueOf(stockInput[i][5])), Integer.parseInt(String.valueOf(stockInput[i][6])));
                        stockInput[i][1] = newStockPrice;
                        if (Integer.parseInt(String.valueOf(stockInput[i][3])) <= stockChangeParameter.nextInt(100 + 1)) {
                            stockInput[i][4] = true;
                            if (originalStockPrice > newStockPrice) {
                                stockInput[i][5] = 0;
                            }
                            stockInput[i][6] = Integer.parseInt(String.valueOf(stockInput[i][6])) + 1;
                        }
                    }
                } else {
//                System.out.println("No Change in Stock Price for Stock: " + stockInput[i][0]);
                }
            }
//        for (int i = 0; i < stockInput.length; i++) {
//            System.out.println(Arrays.toString(stockInput[i]));
//        }
        }
        for (int i = 0; i < numberOfStocks; i++) {
                if (Double.parseDouble(stockInput[i][1].toString()) < 0.01) {
                    stockInput[i][1] = 0.01;
                }
            }
            stockInfo = stockInput;
        }

    public static double changeStockPrice(double priceToBeChanged, boolean trending, int directionOfTrend, int currentDurationOfTrend) {
        Random percentageAdjustment = new Random();
        totalPriceChange = Math.pow(priceToBeChanged, .407); //(-.507)
        totalPriceChange = totalPriceChange / 10;
        if (trending) {
            if (directionOfTrend == 1) { //If direction of trend is 1, the stock is trending up
                if (percentageAdjustment.nextInt() <= 50 - (10 - currentDurationOfTrend)) {
                    if (percentageAdjustment.nextInt(101) <= 50) {
                        totalPriceChange = totalPriceChange + (.0001 * percentageAdjustment.nextInt(100 + 1));
                    } else {
                        totalPriceChange = totalPriceChange - (.0001 * percentageAdjustment.nextInt(100 + 1));
                    }
                    priceToBeChanged = priceToBeChanged + totalPriceChange;
                    positivePriceChange = true;
//                    System.out.println("Percent the Value Changed: " + percentageChange);
                } else {
                    if (percentageAdjustment.nextInt(101) <= 50) {
                        totalPriceChange = totalPriceChange + (.0001 * percentageAdjustment.nextInt(100 + 1));
                    } else {
                        totalPriceChange = totalPriceChange - (.0001 * percentageAdjustment.nextInt(100 + 1));
                    }
                    priceToBeChanged = priceToBeChanged - totalPriceChange;
                    positivePriceChange = false;
//                    System.out.println("Percent the Value Changed: " + percentageChange);
                }
            } else {
                if (percentageAdjustment.nextInt() <= 50 + (10 - currentDurationOfTrend)) {
                    if (percentageAdjustment.nextInt(101) <= 50) {
                        totalPriceChange = totalPriceChange + (.0001 * percentageAdjustment.nextInt(100 + 1));
                    } else {
                        totalPriceChange = totalPriceChange - (.0001 * percentageAdjustment.nextInt(100 + 1));
                    }
                    priceToBeChanged = priceToBeChanged + totalPriceChange;
                    positivePriceChange = true;
//                    System.out.println("Percent the Value Changed: " + percentageChange);
                } else {
                    if (percentageAdjustment.nextInt(101) <= 50) {
                        totalPriceChange = totalPriceChange + (.0001 * percentageAdjustment.nextInt(100 + 1));
                    } else {
                        totalPriceChange = totalPriceChange - (.0001 * percentageAdjustment.nextInt(100 + 1));
                    }
                    priceToBeChanged = priceToBeChanged - totalPriceChange;
                    positivePriceChange = false;
//                    System.out.println("Percent the Value Changed: " + percentageChange);
                }
                priceToBeChanged = (double) Math.round(priceToBeChanged * 100) / 100;
            }
        } else {
            if (percentageAdjustment.nextInt() <= 50) {
                if (percentageAdjustment.nextInt(101) <= 50) {
                    totalPriceChange = totalPriceChange + (.0001 * percentageAdjustment.nextInt(100 + 1));
                } else {
                    totalPriceChange = totalPriceChange - (.0001 * percentageAdjustment.nextInt(100 + 1));
                }
                priceToBeChanged = priceToBeChanged + totalPriceChange;
                positivePriceChange = true;
//                System.out.println("Percent the Value Changed: " + percentageChange);
            } else {
                if (percentageAdjustment.nextInt(101) <= 50) {
                    totalPriceChange = totalPriceChange + (.0001 * percentageAdjustment.nextInt(100 + 1));
                } else {
                    totalPriceChange = totalPriceChange - (.0001 * percentageAdjustment.nextInt(100 + 1));
                }
                priceToBeChanged = priceToBeChanged - totalPriceChange;
                positivePriceChange = false;
//                System.out.println("Percent the Value Changed: " + percentageChange);
            }
            priceToBeChanged = (double) Math.round(priceToBeChanged * 100) / 100;
        }
        return priceToBeChanged;
    }
}

