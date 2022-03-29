import java.util.Scanner;

public class Project1{

    public static void main(String[] args){

        int SIZE;
        int[][] matrix1,
                matrix2,
                result,
                result2,
                result3;
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
        System.out.println("\n~~~~ Classic Matrix Multiplication ~~~~\n");
        System.out.println("Brute Force: O(^3)\n");
        final long timer1Start = System.nanoTime();
        ClassicalMult(matrix1, matrix2, result, SIZE);
        final long timer1End = System.nanoTime();
        System.out.println("\nTotal time taken (ns): " + (timer1End-timer1Start));

        //Divide and Conquer
        System.out.println("\n~~~~ Naive Divide and Conquer ~~~~\n");
        System.out.println("T(n) = 8T(n/2) + c*n^2 => O(n^3)\n");
        final long timer2Start = System.nanoTime();
        result2 =DivideAndConquer(matrix1,matrix2,0,0,0,0,SIZE);
        final long timer2End = System.nanoTime();
        print(result2, SIZE);
        System.out.println("\nTotal time taken (ns): " + (timer2End-timer2Start));

        //Strassen Matrix Multiplication
        System.out.println("\n~~~~ Strassen Matrix Multiplication ~~~~\n");
        System.out.println("T(n) = 7T(n/2) + c*n^2 => O(n^(log7)))\n");
        final long timer3Start = System.nanoTime();
        result3 = Strassen(matrix1,matrix2);
        final long timer3End = System.nanoTime();
        print(result3,SIZE);
        System.out.println("\nTotal time taken (ns): " + (timer3End-timer3Start));
        

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

    //divide and conquer
    public static int[][] DivideAndConquer(int[][] matrix1, int[][] matrix2, int r1, int c1, int r2, int c2, int SIZE) {
        int[][] result = new int[SIZE][SIZE];
        if (SIZE == 1) {
            result[0][0] = matrix1[r1][c1] * matrix2[r2][c2];
        } else {
            int m = SIZE/2;

            // C11 = (A11*B11) + (A12*B21)
            int[][] A11B11 = DivideAndConquer(matrix1, matrix2, r1, c1, r2, c2, m);
            int[][] A12B21 = DivideAndConquer(matrix1, matrix2, r1, c1 + m, r2 + m, c2, m);

            // C12 = (A11*B12) + (A12*B22)
            int[][] A11B12 = DivideAndConquer(matrix1, matrix2, r1, c1, r2, c2 + m, m);
            int[][] A12B22 = DivideAndConquer(matrix1, matrix2, r1, c1 + m, r2 + m, c2 + m, m);

            // C21 = (A21*B11) + (A22*B21)
            int[][] A21B11 = DivideAndConquer(matrix1, matrix2, r1 + m, c1, r2, c2, m);
            int[][] A22B21 = DivideAndConquer(matrix1, matrix2, r1 + m, c1 + m, r2 + m, c2, m);

            // C22 = (A21*B12) + (A22*B22)
            int[][] A21B12 = DivideAndConquer(matrix1, matrix2, r1 + m, c1, r2, c2 + m, m);
            int[][] A22B22 = DivideAndConquer(matrix1, matrix2, r1 + m, c1 + m, r2 + m, c2 + m, m);

            // C11 = (A11*B11) + (A12*B21)
            sumDAC(result, A11B11, A12B21, 0, 0);

            // C12 = (A11*B12) + (A12*B22)
            sumDAC(result, A11B12, A12B22, 0, m);

            // C21 = (A21*B11) + (A22*B21)
            sumDAC(result, A21B11, A22B21, m, 0);

            // C22 = (A21*B12) + (A22*B22)
            sumDAC(result, A21B12, A22B22, m, m);
            }

        return result;
        }

    static int[][] Strassen(int[][] matrix1, int[][] matrix2) {

        int SIZE = matrix1.length;
        int[][] result = new int[SIZE][SIZE];
    
        if (SIZE == 1)
          result[0][0] = matrix1[0][0] * matrix2[0][0];
        else {
          //divde each matrix into four quadrants
          int[][] A11 = new int[SIZE/2][SIZE/2],
                  B11 = new int[SIZE/2][SIZE/2],
                  A12 = new int[SIZE/2][SIZE/2],
                  B12 = new int[SIZE/2][SIZE/2],
                  A21 = new int[SIZE/2][SIZE/2],
                  B21 = new int[SIZE/2][SIZE/2],
                  A22 = new int[SIZE/2][SIZE/2],
                  B22 = new int[SIZE/2][SIZE/2];

          partitionOrCombine(matrix1, A11, 0, 0,0);
          partitionOrCombine(matrix1, A12, 0, (SIZE/2),0);
          partitionOrCombine(matrix1, A21, (SIZE/2), 0,0);
          partitionOrCombine(matrix1, A22, (SIZE/2), (SIZE/2),0);
          partitionOrCombine(matrix2, B11, 0, 0,0);
          partitionOrCombine(matrix2, B12, 0, (SIZE/2),0);
          partitionOrCombine(matrix2, B21, (SIZE/2), 0,0);
          partitionOrCombine(matrix2, B22, (SIZE/2), (SIZE/2),0);
          
    
            // P1 = A11*(B12-B22)
            int[][] P1 = Strassen(A11, difference(B12, B22));

            // P2 = (A11+A12)*B22
            int[][] P2 = Strassen(sum(A11, A12), B22);

            // P3 = (A21 + A22)B11
            int[][] P3 = Strassen(sum(A21, A22), B11);

            // P4 = A22*(B21-B11)
            int[][] P4 = Strassen(A22, difference(B21, B11));

            // P5 = (A11+A22)*(B11+B22)
            int[][] P5 = Strassen(sum(A11, A22), sum(B11, B22));

            // P6 = (A12-A22)(B21+B22)
            int[][] P6 = Strassen(difference(A12, A22), sum(B21, B22));

            // P7 = (A11-A21)*(B11+B12)
            int[][] P7 = Strassen(difference(A21, A11), sum(B11, B12));

            // C11 = -P2 + P4 + P5 + P6
            int[][] C11 = sum(difference(sum(P4, P5), P2), P6);
            partitionOrCombine(result, C11, 0, 0, 1);
        
            // C12 = P1 + P2
            int[][] C12 = sum(P1, P2);
            partitionOrCombine(result, C12, 0, (SIZE/2), 1);
        
            // C21 = P3 + P4
            int[][] C21 = sum(P3, P4);
            partitionOrCombine(result, C21, (SIZE/2), 0, 1);
        
            // C22 = P1 - P3 + P5 - P7
            int[][] C22 = sum(difference(sum(P1, P5), P3), P7);
            partitionOrCombine(result, C22, (SIZE/2), (SIZE/2),1);
        }
        return result;
      }

      private static void sumDAC(int[][] result, int[][] matrix1, int[][] matrix2, int r1, int c1) {
        int SIZE = matrix1.length;
        for (int i = 0; i < SIZE; i++) {
          for (int j = 0; j < SIZE; j++) {
          result[i + r1][j + c1] = matrix1[i][j] + matrix2[i][j];
          }
        }
    }

      private static int[][] sum(int[][] matrix1, int[][] matrix2) {
        int SIZE = matrix1.length;
        int[][] result = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
          for (int j = 0; j < SIZE; j++)
          result[i][j] = matrix1[i][j] + matrix2[i][j];
        return result;
      }

      private static int[][] difference(int[][] matrix1, int[][] matrix2) {
        int SIZE = matrix1.length;
        int[][] result = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
          for (int j = 0; j < SIZE; j++)
          result[i][j] = matrix1[i][j] - matrix2[i][j];
        return result;
      }

      // method to split parent matrix into child matrices
      private static void partitionOrCombine(int[][] big, int[][] small, int start, int end, int option) {
        if(option==0){
            for (int i=0, a=start; i < small.length; i++, a++)
             for (int j=0, b=end; j < small.length; j++, b++)
                small[i][j] = big[a][b];
        }
        else if(option==1){
            for (int i=0, a=start; i < small.length; i++, a++)
             for (int j=0, b=end; j < small.length; j++, b++)
                big[a][b] = small[i][j];
        }
      }

}