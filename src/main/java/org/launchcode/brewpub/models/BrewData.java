package org.launchcode.brewpub.models;

import javax.persistence.Convert;
import java.util.ArrayList;

public class BrewData {
    /**
     * Returns the results of searching the Brews data by field and search term.
     *
     * For example, searching for brew "Shlafly" will include results
     * with "Shlafly Taproom, Inc".
     *
     * @param column Job field that should be searched.
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


//            if(aValue != null && aValue.toLowerCase().contains(value.toLowerCase()) && !column.equals("abv")) {
//                results.add(brew);
//            } else {
//                int intAbv = Integer.parseInt(aValue);
//                int intValue = Integer.parseInt(value);
//
//                if (intAbv == intValue){
//                    results.add(brew);
//                }
//            }


            if (aValue != null && column.equals("abv")) {
                double dAbv = Double.parseDouble(aValue);
                double dValue = Double.parseDouble(value);
                int intAbv = (int) dAbv;
                int intValue = (int) dValue;

                if (intAbv == intValue) {
                    results.add(brew);
                } else if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                    results.add(brew);
                }
            }
        }
        return results;

    }

    public static String getFieldValue(Brew brew, String fieldName) {
        String theValue = "";
        if (fieldName.equals("name")) {
            theValue = brew.getName();
        } else if (fieldName.equals("style")) {
            theValue = brew.getStyle().toString();
        } else if (fieldName.equals("abv")) {
            theValue = brew.getAbv().toBigInteger().toString();
        } else if (fieldName.equals("description")) {
            theValue = brew.getDescription().toString();
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
        double d = Double.parseDouble(lower_val);
        int int_val = (int) d;
        String str_int_val = Integer.toString(int_val);

        ArrayList<Brew> results = new ArrayList<>();

        for (Brew brew : allBrews) {
            if (brew.getName().toLowerCase().contains(lower_val)) {
                results.add(brew);
            } else if (brew.getStyle().toString().toLowerCase().contains(lower_val)) {
                results.add(brew);
            } else if (brew.getAbv().toString().equals(str_int_val)) {
                results.add(brew);
            } else if (brew.getDescription().toString().toLowerCase().contains(lower_val)) {
                results.add(brew);
            }

        }

        return results;
    }

}


