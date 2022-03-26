import java.util.Scanner;

public class Project1{

    public static void main(String[] args){

        int SIZE;
        int[][] matrix1,
                   matrix2,
                    result;
        Scanner read;


        /* Take input & create matrix **/
        read = new Scanner(System.in);
        System.out.println("How many rows are your matrices? ");
        SIZE = read.nextInt();

        //create matrix1
        matrix1 = new int[SIZE][SIZE];
        System.out.println("\nPlease enter your first matrix (row^2 values): ");
        for(int i =0; i<SIZE; i++){
            for(int j=0; j<SIZE; j++){
                matrix1[i][j] = read.nextInt();
            }
        }

        //create matrix 2
        matrix2 = new int[SIZE][SIZE];
        System.out.println("\nPlease enter your second matrix (row^2 values): ");
        for(int i =0; i<SIZE; i++){
            for(int j=0; j<SIZE; j++){
                matrix2[i][j] = read.nextInt();
            }
        }
        result = new int[SIZE][SIZE];

        read.close();

        //Brute Force: Classical Matrix Multiplication
        System.out.println("\n~~~~Classic Matrix Multiplication ~~~~\n");
        System.out.println("Brute Force: O(^3)\n");
       ClassicalMult(matrix1, matrix2, result, SIZE);

    }   

    static void print(int[][] result, int size){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    static int[][] ClassicalMult(int[][] matrix1, int[][] matrix2, int[][]result, int size){
        for(int i =0; i<size; i++){
            for(int j=0; j<size; j++){
                result[i][j] = 0;
                for(int y=0; y<size; y++){
                    result[i][j] += matrix1[i][y] * matrix2[y][j];
                }
            }
        }
        print(result,size);
        return result;
    }
}