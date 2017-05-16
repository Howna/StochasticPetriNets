package Model;


/**
 * 
 * @author Edwin
 */

public class Matrix {

    public void printMatrix(int[][] matrix) {
        if (matrix == null) {
            System.out.println("Empty matrix");
            return;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println(" ");
        }
    }    

    public double[][] extractSubmatrix(double[][] matrix, double x, int y) {
        double[][] submatrix = new double[matrix.length - 1][matrix.length - 1];
        int i, j, cur_x = 0, cur_y = 0;
        for (i = 0; i < matrix.length; i++) {
            if (i != x) {
                cur_y = 0;
                for (j = 0; j < matrix.length; j++) {
                    if (j != y) {
                        submatrix[cur_x][cur_y] = matrix[i][j];
                        cur_y++;
                    }
                }
                cur_x++;
            }
        }
        return submatrix;
    }

    public double calculateDeterminant(double[][] matrix) {
        double deter = 0;
        int i, mult = 1;
        matrix = squareUp(matrix);
        if (matrix.length > 2) {
            for (i = 0; i < matrix.length; i++) {
                deter += mult * matrix[i][0] * calculateDeterminant(extractSubmatrix(matrix, i, 0));
                mult *= -1;
            }
            return deter;
        } else {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }
    }

    public int[][] add(int[][] matrix1, int[][] matrix2) {
        int[][] result = null;
        if (matrix1.length == matrix2.length && matrix1[0].length == matrix2[0].length) {
            result = new int[matrix1.length][matrix1[0].length];
            for (int i = 0; i < matrix1.length; i++) {
                for (int j = 0; j < matrix1[i].length; j++) {
                    int sum = matrix1[i][j] + matrix2[i][j];
                    result[i][j] = sum;
                }
            }
        } else {
            System.out.println("THE DIMENSIONS OF THE MATRICES ARE INCOMPATIBLE TO ADD THEM");
        }
        return result;
    }

    public double[] add(double v1[], double v2[]) {
        double r[] = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            r[i] = v1[i] + v2[i];
        }
        return r;
    }

    public int[][] subtract(int[][] matrix1, int[][] matrix2) {
        int[][] result = null;
        if (matrix1.length == matrix2.length && matrix1[0].length == matrix2[0].length) {
            result = new int[matrix1.length][matrix1[0].length];
            for (int i = 0; i < matrix1.length; i++) {
                for (int j = 0; j < matrix1[i].length; j++) {
                    int subtraction = matrix1[i][j] - matrix2[i][j];
                    result[i][j] = subtraction;
                }
            }
        } else {
            System.out.println("DIMENSIONS OF MATRICES ARE INCOMPATIBLE TO REMOVE THEM");
        }
        return result;
    }

    public double[] subtract(double v1[], double v2[]) {
        double r[] = new double[v1.length];
        for (int i = 0; i < v1.length; i++) {
            r[i] = v1[i] - v2[i];
        }
        return r;
    }

    public double[][] multiply(double f, double[][] matrix) {
        double result[][] = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result[i][j] = matrix[i][j] * f;
            }
        }
        return result;
    }

    public double[][] multiply(float[][] matrix1, double[][] matrix2) {
        double[][] result = null;
        if (matrix1[0].length == matrix2.length) {
            result = new double[matrix1.length][matrix2[0].length];
            for (int i = 0; i < matrix1.length; i++) {
                for (int j = 0; j < matrix2[0].length; j++) {
                    double total = 0;
                    for (int k = 0; k < matrix2.length; k++) {
                        total = total + matrix1[i][k] * matrix2[k][j];
                    }
                    result[i][j] = total;
                }
            }
        } else {
            System.out.println("THE DIMENSIONS OF MATRICES ARE INCOMPATIBLE TO MULTIPLY THEM");
        }
        return result;
    }

    public double[] multiply(double vector[], double[][] matrix) {
        double[] result = new double[matrix[0].length];
        if (vector.length == matrix.length) {
            int counter = 0;
            for (int i = 0; i < matrix[0].length; i++) {
                double total = 0;
                for (int j = 0; j < vector.length; j++) {
                    total = total + vector[j] * matrix[j][i];
                }
                result[counter] = total;
                counter++;
            }
        } else {
            System.out.println("THE DIMENSIONS OF MATRICES ARE INCOMPATIBLE TO MULTIPLY THEM");
        }
        return result;
    }

    public double[][] convertToDouble(int[][] matrix) {
        double m[][] = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                m[i][j] = (double) matrix[i][j];
            }
        }
        return m;
    }

    public double[] convertToDouble(int[] matrix) {
        double m[] = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            m[i] = (double) matrix[i];
        }
        return m;
    }

    public double[][] matrixCofactors(double[][] matrix) {
        double[][] nm = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                double[][] det = new double[matrix.length - 1][matrix.length - 1];
                double detValue;
                for (int k = 0; k < matrix.length; k++) {
                    if (k != i) {
                        for (int l = 0; l < matrix.length; l++) {
                            if (l != j) {
                                int index1 = k < i ? k : k - 1;
                                int index2 = l < j ? l : l - 1;
                                det[index1][index2] = matrix[k][l];
                            }
                        }
                    }
                }
                detValue = calculateDeterminant(det);
                nm[i][j] = detValue * (double) Math.pow(-1, i + j + 2);
            }
        }
        return nm;
    }


    public double[][] squareUp(double[][] matrix) {
        if (matrix.length == matrix[0].length) {
            return matrix;
        } else {
            int rows = matrix.length;
            int cols = matrix[0].length;
            double[][] result = new double[1][1];
            if (rows > cols) {
                int c = rows - cols;
                result = new double[rows][cols + c];
            } else {
                int f = cols - rows;
                result = new double[rows + f][cols];
            }
            for (int i = 0; i < result.length; i++) {
                for (int j = 0; j < result[i].length; j++) {
                    if (i < rows && j < cols) {
                        result[i][j] = matrix[i][j];
                    } else {
                        result[i][j] = 0;
                    }
                }
            }
            return result;
        }
    }

    public double[][] attachedMatrix(double[][] matrix) {
        return transposedMatrix(matrixCofactors(matrix));
    }

    public double[][] transposedMatrix(double[][] matrix) {
        double[][] newMatrix = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                newMatrix[i][j] = matrix[j][i];
            }
        }
        return newMatrix;
    }

    public double[] Gauss_Jordan1(double m[][], double[] r) {
        for (int i = 0; i < r.length; i++) {
            double d, c = 0;
            d = m[i][i];
            if (d != 0) {
                for (int s = 0; s < r.length; s++) {
                    m[i][s] = ((m[i][s]) / d);
                }
                r[i] = (((r[i]) / d));
                for (int x = 0; x <= r.length - 1; x++) {
                    if (i != x) {
                        c = m[x][i];
                        for (int y = 0; y <= r.length - 1; y++) {
                            m[x][y] = m[x][y] - c * m[i][y];
                        }
                        r[x] = (r[x] - c * r[i]);
                    }
                }
            }
        }
        return r;
    }

    public double[][] reverseMatrix(double m[][]) {
        double r[][] = new double[1][1];
        try {
            r = new double[m[0].length][m.length];
            for (int i = 0; i < r.length; i++) {
                for (int j = 0; j < r[i].length; j++) {
                    r[i][j] = m[j][i];
                }
            }
            return r;
        } catch (Exception e) {
            System.out.println("Error");
        }
        return r;
    }
    
    public boolean compareVectors(double vector1[],double vector2[]){        
        if(vector1.length != vector2.length) return false;
        int i=0;
        for(double number : vector1){
            if(number != vector2[i])
                return false;
            i++;
        }
        return true;
    }
    
    public void printVector(double[] vector){
        System.out.print("( ");
        for(double numero : vector)
            System.out.print(numero + " ");
        System.out.println(" )");
    }
    
    public int[] convertToInteger(double[] vector){
        int vectorInt[]=new int[vector.length];
        for(int i=0;i < vector.length;i++){
            vectorInt[i]=(int) vector[i];
        }
        return vectorInt;
    }
}

