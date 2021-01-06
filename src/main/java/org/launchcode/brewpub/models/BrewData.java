package org.launchcode.brewpub.models;

import java.util.ArrayList;

public class BrewData {
    /**
     * Returns the results of searching the Jobs data by field and search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
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

            if(aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                results.add(brew);
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
            theValue = brew.getAbv().toString();
        } else if (fieldName.equals("description")) {
            theValue = brew.getDescription().toString();
        }

        return theValue;
    }

    /**
     * Search all Job fields for the given term.
     *
     * @param value The search term to look for.
     * @param allBrews The list of jobs to search.
     * @return      List of all brewss with at least one field containing the value.
     */

    public static ArrayList<Brew> findByValue(String value, Iterable<Brew> allBrews) {
        String lower_val = value.toLowerCase();

        ArrayList<Brew> results = new ArrayList<>();

        for (Brew brew : allBrews) {
            if (brew.getName().toLowerCase().contains(lower_val)) {
                results.add(brew);
            } else if (brew.getStyle().toString().toLowerCase().contains(lower_val)) {
                results.add(brew);
            } else if (brew.getAbv().toString().toLowerCase().contains(lower_val)) {
                results.add(brew);
            } else if (brew.getDescription().toString().toLowerCase().contains(lower_val)) {
                results.add(brew);
            }

        }

        return results;
    }

}


