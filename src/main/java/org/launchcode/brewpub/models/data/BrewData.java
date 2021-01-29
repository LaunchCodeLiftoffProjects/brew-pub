package org.launchcode.brewpub.models.data;

import org.launchcode.brewpub.models.Brew;

import java.util.ArrayList;

public class BrewData {
    /**
     * Returns the results of searching the Brews data by field and search term.
     *
     * For example, searching for brew "Schlafly" will include results
     * with "Schlafly Taproom, Inc".
     *
     * @param column Field that should be searched.
     * @param value Value of the field to search for.
     * @param allBrews The list of brews to search.
     * @return List of all brews matching the criteria.
     */

    public static ArrayList<Brew> findByColumnAndValue(String column, String value, Iterable<Brew> allBrews) {

        ArrayList<Brew> results = new ArrayList<>();

        if (value.toLowerCase().equals("all")) {
            return (ArrayList<Brew>) allBrews;
        }

        if (column.equals("all")) {
            results = findByValue(value, allBrews);
            return results;
        }

        for (Brew brew : allBrews) {

            String aValue = getFieldValue(brew, column);

            if (aValue != null && column.equals("abv")) {
                try {
                    double dAbv = Double.parseDouble(aValue);
                    double dValue = Double.parseDouble(value);
                    int intAbv = (int) dAbv;
                    int intValue = (int) dValue;

                    if (intAbv == intValue) {
                        results.add(brew);
                    }
                } catch (Exception e) {
                    System.out.println("Here 49");
                    e.printStackTrace();
                }
            } else if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                results.add(brew);
            }
        }

        return results;

    }

    public static String getFieldValue(Brew brew, String fieldName) {
        String theValue = "";
        try {
            if (fieldName.equals("name")) {
                theValue = brew.getName();
            } else if (fieldName.equals("style")) {
                theValue = brew.getStyle().toString();
            } else if (fieldName.equals("abv")) {
                theValue = brew.getAbv().toBigInteger().toString();
            } else if (fieldName.equals("description")) {
                theValue = brew.getDescription().toString();
            } else if (fieldName.equals("ibu")) {
                theValue = brew.getIbu().toString();
            }
        } catch (Exception e) {
            System.out.println("here 77");
            e.printStackTrace();
            theValue = null;
        }
        return theValue;
    }

    /**
     * Search all Brew fields for the given term.
     *
     * @param value The search term to look for.
     * @param allBrews The list of jobs to search.
     * @return      List of all brews with at least one field containing the value.
     */

    public static ArrayList<Brew> findByValue(String value, Iterable<Brew> allBrews) {
        String lower_val = value.toLowerCase();
        double d= 0;
        int int_val = 0;
        String str_int_val = "";
        try {
            d = Double.parseDouble(lower_val);
            int_val = (int) d;
            str_int_val = Integer.toString(int_val);
        } catch (Exception e) {
            lower_val = value.toLowerCase();
            System.out.println("findByValue");
            e.printStackTrace();
        }

        ArrayList<Brew> results = new ArrayList<>();

        for (Brew brew : allBrews) {
            try {
                if (brew.getName().toLowerCase().contains(lower_val)) {
                    results.add(brew);
                } else if (brew.getStyle().toString().toLowerCase().contains(lower_val)) {
                    results.add(brew);
                } else if (brew.getAbv().toString().equals(str_int_val)) {
                    results.add(brew);
                } else if (brew.getDescription().toString().toLowerCase().contains(lower_val)) {
                    results.add(brew);
                } else if (brew.getIbu().toString().toLowerCase().contains(lower_val)) {
                    results.add(brew);
                } else if (brew.getBrewer().toString().toLowerCase().contains(lower_val)) {
                    results.add(brew);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return results;
    }

}


