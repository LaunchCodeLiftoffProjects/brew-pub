package org.launchcode.brewpub.models;

import java.util.ArrayList;

public class PubData {
    /**
     * Returns the results of searching the Jobs data by field and search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column Job field that should be searched.
     * @param value Value of the field to search for.
     * @param allPubs The list of jobs to search.
     * @return List of all jobs matching the criteria.
     */

    public static ArrayList<Pub> findByColumnAndValue(String column, String value, Iterable<Pub> allPubs) {

        ArrayList<Pub> results = new ArrayList<>();

        if (value.toLowerCase().equals("all")) {
            return (ArrayList<Pub>) allPubs;
        }

        if (column.equals("all")) {
            results = findByValue(value, allPubs);
            return results;
        }

        for (Pub pub : allPubs) {

            String aValue = getFieldValue(pub, column);

            if(aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {
                results.add(pub);
            }
        }
        return results;
    }

    public static String getFieldValue(Pub pub, String fieldName) {
        String theValue = "";
        if (fieldName.equals("name")) {
            theValue = pub.getName();
        } else if (fieldName.equals("address")) {
            theValue = pub.getAddress().toString();
        } else if (fieldName.equals("city")) {
            theValue = pub.getCity().toString();
        } else if (fieldName.equals("state")){
            theValue = pub.getState().toString();
        }

        return theValue;
    }

    /**
     * Search all Job fields for the given term.
     *
     * @param value The search term to look for.
     * @param allPubs The list of jobs to search.
     * @return      List of all jobs with at least one field containing the value.
     */

    public static ArrayList<Pub> findByValue(String value, Iterable<Pub> allPubs) {
        String lower_val = value.toLowerCase();

        ArrayList<Pub> results = new ArrayList<>();

        for (Pub pub : allPubs) {
            if (pub.getName().toLowerCase().contains(lower_val)) {
                results.add(pub);
            } else if (pub.getAddress().toString().toLowerCase().contains(lower_val)) {
                results.add(pub);
            } else if (pub.getCity().toString().toLowerCase().contains(lower_val)) {
                results.add(pub);
            } else if (pub.getState().toString().toLowerCase().contains(lower_val)) {
                results.add(pub);
            }

        }

        return results;
    }



}
