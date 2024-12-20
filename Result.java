// no imports


// Result documentation: https://docs.google.com/document/d/1D1P6JSjmGku4GfSQsjOUKmyUJ-J4dNqXUACGQq3ezUE/edit?usp=sharing


public class Result implements Comparable<Result> {

    // fields /////

    private String name;
    private boolean isWinner;


    // constructors /////

    public Result() {
        setName("");
        setWin(false);
    }

    public Result(String candidate, boolean winner) {
        setName(candidate);
        setWin(winner);
    }


    // setters /////

    public void setName(String candidate) {
        name = candidate;
    }

    public void setWin(boolean result) {
        isWinner = result;
    }


    // getters /////

    public String getName() {
        return name;
    }

    public boolean isWinner() {
        return isWinner;
    }

    // convenience method: returns opposite of isWinner
    public boolean isLoser() {
        return !isWinner;
    }


    // other methods /////

    public boolean equals(Result r) {
        return name.equals(r.name) && isWinner == r.isWinner;
    }

    public int compareTo(Result r) {
        int comp = name.compareTo(r.name);
        if (comp == 0) {
            comp = Boolean.compare(isWinner, r.isWinner);
        }
        return comp;
    }

    public String toString() {
        return "Result[name=" + name + ",win=" + isWinner + "]";
    }

    public int hashCode() {
        return name.hashCode() + 31 * Boolean.hashCode(isWinner);
    }

    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Result) {
            return equals((Result)(obj));
        }
        else {
            return false;
        }
    }

}
