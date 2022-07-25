import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] substrate;
    private final WeightedQuickUnionUF percs;
    private final int n;

    private int openSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Requries positive value.");
        }
        substrate = new boolean[(n * n) + 2];
        substrate[0] = true;
        for (int i = 1; i <= (n * n); i++) substrate[i] = false;
        substrate[(n * n) + 1] = true;
        this.percs = new WeightedQuickUnionUF((n * n) + 2);
        this.n = n;
    }

    public static void main(String[] args) {
        Percolation myTest = new Percolation(3);
        myTest.open(1, 1);
        myTest.open(2, 1);
        myTest.open(3, 1);
        if (myTest.isFull(3, 2)) System.out.println("full");
        else System.out.println("hongy");

        int first = myTest.percs.find(1);
        int second = myTest.percs.find(2);
        if (first == second) {
            System.out.println("worky worky");
        }
        else {
            System.out.println("you fucking donkey!!");
        }

        if (myTest.percolates()) System.out.println("perky!");
        else System.out.println("saggy!");
    }


    // opens the site (row, col) if it is not already open

    public void open(int row, int col) {
        isValid(row, col);
        int coor = xyTo1D(row, col);
        if (isOpen(row, col)) {
            return;
        }
        substrate[coor] = true;
        openSites++;
        // Check if the site is in the top row, if so connect to top
        if (coor <= n) {
            percs.union(coor, 0);
        }
        // Check if the site is in the bottom row, if so conenct to bot
        else if (coor > ((n * n) - n)) {
            percs.union(coor, (n * n) + 1);
        }
        // left
        if (coor - 1 != 0 && substrate[coor - 1] && ((coor - 1) % n) != 0)
            percs.union(coor, coor - 1);
        // right
        if (substrate[coor + 1] && ((coor + 1) % n) != 1) percs.union(coor, coor + 1);
        // top
        if (coor > n && substrate[coor - n]) percs.union(coor, (coor - n));
        // bottom
        if (coor <= ((n * n) - n) && substrate[coor + n]) percs.union(coor, coor + n);
    }

    // check if the site is open
    public boolean isOpen(int row, int col) {
        isValid(row, col);
        int coor = xyTo1D(row, col);
        return (substrate[coor]);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean isFull(int row, int col) {
        isValid(row, col);
        int coor = xyTo1D(row, col);
        return (percs.find(coor) == percs.find(0) && isOpen(row, col));
    }

    // convert coordinates from 2d to 1d
    private int xyTo1D(int row, int col) {
        return (row * n) - (n - col);
    }

    // check that the given coordinate is within range
    private void isValid(int row, int col) {
        if (row > n || row < 1 || col > n || col < 1) {
            throw new IllegalArgumentException("row index i out of bounds");
        }
    }

    // does the system percolate?
    public boolean percolates() {
        return (percs.find(0) == percs.find((n * n) + 1));
    }


}
