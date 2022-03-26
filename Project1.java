import java.util.Scanner;

public class Project1{

    public static void main(String[] args){

        int SIZE;
        double[][] matrix1;
        Scanner read = new Scanner(System.in);


        /* Take input & create matrix **/
        System.out.println("How many rows is your matrix? ");
        SIZE = read.nextInt();

        //create matrix
        matrix1 = new double[SIZE][SIZE];
        System.out.println("\nPlease enter your matrix (row^2 values): ");
        for(int i =0; i<SIZE; i++){
            for(int j=0; j<SIZE; j++){
                matrix1[i][j] = read.nextDouble();
            }
        }

        read.close();

        //Brute Force: Classical Matrix Multiplication
        System.out.println("~~~~Classic Matrix Multiplication ~~~~\n");
        System.out.println("Brute Force: O(^3)\n");


    }   
}