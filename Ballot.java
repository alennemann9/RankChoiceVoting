import java.util.ArrayList;


// Ballot documentation: https://docs.google.com/document/d/1D1P6JSjmGku4GfSQsjOUKmyUJ-J4dNqXUACGQq3ezUE/edit?usp=sharing


public class Ballot implements Comparable<Ballot> {

    // fields /////

    private ArrayList<String> choices;


    // constructor /////

    public Ballot() {
        choices = new ArrayList<String>();
    }


    // setters /////

    // the input name is added to the end of the ballot, unless:
    // - the name is already on the ballot
    // - the name is the empty String
    // returns whether the name was actually added
    public boolean addCandidate(String name) {
        if (choices.contains(name) || name.equals("")) {
            return false;
        }
        choices.add(name);
        return true;
    }


    public boolean removeCandidate(String name) {
        return choices.remove(name);
    }


    // getters /////

    // if there is a candidate on the ballot, returns the top choice
    // if there is not a candidate on the ballot, returns empty String
    public String getCurrentChoice() {
        if (choices.size() == 0) {
            return "";
        }
        return choices.get(0);
    }


    // convenience method: indicates whether ballot is empty
    // (same as getCurrentChoice() returning empty String
    public boolean isExhausted() {
        return choices.size() == 0;
    }


    // other methods /////

    public boolean equals(Ballot b) {
        return choices.equals(b.choices);
    }

    public int compareTo(Ballot b) {
        return choices.toString().compareTo(b.choices.toString());
    }

    public String toString() {
        return "Ballot[choices=" + choices + "]";
    }

    public int hashCode() {
        return choices.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Ballot) {
            return equals((Ballot)(obj));
        }
        else {
            return false;
        }
    }

}
