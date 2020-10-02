import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;

public class main {
    public static void main(String args[]) 
    {   Scanner input = new Scanner(System.in);
        int pilihan;
        do {
        System.out.println("[MENU] \n");
        
        System.out.println("1. Sistem Persamaan Linear");
        System.out.println("2. Determinan");
        System.out.println("3. Matriks Balikan");
        System.out.println("4. Interpolasi Polinom");
        System.out.println("5. Regresi linier berganda");
        System.out.println("6. Keluar");

        
        pilihan =  input.nextInt();
        
        
        switch(pilihan){

            case 1:
            //fungsi utama Sistem Persamaan Linear
            break;

        case 2: 
            System.out.println("1. Reduksi Baris");
            System.out.println("2. Ekspansi Kofaktor");
            int metodeDet = input.nextInt();
            
            System.out.println("1. Membaca dari keyboard");
            System.out.println("2. Membaca dari file");
            int bacaDet = input.nextInt();

            if(bacaDet == 1)
            {
                //Menerima input matriks
                System.out.println("Jumlah baris dan kolom:");
                int nDet = input.nextInt();
                System.out.println("Matriks:");
                double matrixDet[][] = new double [nDet][nDet];
                for(int i=0; i<nDet; i++)
                {
                    for(int j=0; j<nDet; j++)
                    {
                        matrixDet[i][j] = input.nextDouble(); 
                    }
                }

                //Menghasilkan determinan
                if(metodeDet == 1)
                {
                    double detrowreduction = detRowReduction(matrixDet,nDet);
                    System.out.println(detrowreduction);
                }
                else if(metodeDet == 2)
                {
                    double detcofactor = detCofactor(matrixDet,nDet);
                    System.out.println(detcofactor);
                }
            }
            /*else if(bacaDet == 2)
            {
                double matrixDet[][] = bacaFile(namafile);
            }*/           
            break;

        case 3:
            //fungsi utama matriks balikan
            mainInverse();
            break;

        case 4: 
            //fungsi utama interpolasi polinom
            double matrixInt[][] = new double[100][100];
            double x;
            int nInt;
            System.out.println("1. Membaca dari keyboard");
            System.out.println("2. Membaca dari file");
            int bacaInt = input.nextInt();
            if(bacaInt == 1)
            {
                System.out.println("Jumlah data:");
                nInt = input.nextInt();
                System.out.println("Matriks:");
                matrixInt = new double [nInt][2];

                for(int i=0; i<nInt; i++)
                {
                    for(int j=0; j<2; j++)
                    {
                        matrixInt[i][j] = input.nextDouble(); 
                    }
                }
                System.out.println("x:");
                x = input.nextDouble();
                intPol(matrixInt, nInt, x);
            }
            else if(bacaInt == 2)
            {
                matrixInt = bacaFile("interpolasi.txt");
                //Mendapatkan baris
                nInt = matrixInt.length;
                System.out.println("test");

                System.out.println("x:");
                x = input.nextDouble();
                intPol(matrixInt, nInt, x);
            }
            break;

        case 5:
            //funsgi utama regresi linear berganda
            break;

        case 6: 
            System.out.println("Keluar dari program...");
            System.exit(0);
             break;
        default:
        System.out.println(pilihan + " pilihan tidak valid, masukan nomor pilihan lain!");

        
        }
    }while(pilihan != 6 /* keluar loop*/);



}
    /*DETERMINAN DENGAN EKSPANSI KOFAKTOR*/
    public static double detCofactor(double matrix[][], int n)
    {
        //Basis yaitu matriks dengan 1 elemen
        if(n*n == 1)
        {
            return matrix[0][0];
        }

        //Rekurens
        else
        {
            double cofactor[][] = new double [n-1][n-1];
            double determinan = 0;
            //Ekspansi kofaktor baris pertama matriks
            for (int j=0; j<n; j++)
            {
                int iM = 1;
                int iCof = 0;
                while (iM<n)
                {
                    int jM = 0;
                    int jCof = 0;
                    while (jM<n)
                    {
                        if(jM != j)
                        {
                            cofactor[iCof][jCof] = matrix[iM][jM];
                            jCof++;
                        }
                        jM++;
                    }
                    iCof++;
                    iM++;
                }
                //Penambahan dari perkalian elemen matriks dengan kofaktornya
                determinan += Math.pow(-1, j)*matrix[0][j]*detCofactor(cofactor, n-1);
            }
            return determinan;
        }
    }


    /*DETERMINAN DENGAN REDUKSI BARIS*/
    public static double detRowReduction(double matrix[][], int n)
    {
        //Membentuk matriks segitiga atas
        int swap = 0;
        for (int j=0; j<n; j++)
        {
            //Menukar baris dengan baris terakhir jika elemen[j][j] adalah 0
            if(matrix[j][j] == 0)
            {
                double temp[] = matrix[j];
                matrix[j] = matrix[n-1];
                matrix[n-1] = temp; 
                swap = swap + 1;
            }
            //Membentuk elemen matriks di bawah leading 1 menjadi 0
            for(int i=j+1; i<n; i++)
            {
                double factor = matrix[i][j]/matrix[j][j];
                for(int k=j; k<n; k++)
                {
                    matrix[i][k] = matrix[i][k] - matrix[j][k]*factor;
                }
            }
        }

        //Mengalikan elemen diagonal utama matriks
        double determinan = 1;
        for (int k=0; k<n; k++)
        {
            determinan = determinan*matrix[k][k];
        }
        determinan = determinan*Math.pow(-1,swap);
        return determinan;
    }


    /*INTERPOLASI POLINOM*/
    public static void intPol(double matrix[][], int n, double x)
    {
        //Membentuk matriks augmented persamaan lanjar
        double intpol[][] = new double [n][n+1];
        for (int i=0; i<n; i++)
        {
            intpol[i][0] = 1;
            for (int j=1; j<n; j++)
            {
                intpol[i][j] = Math.pow(matrix[i][0], j);
            }
            intpol[i][n] = matrix[i][1];
        }

        //Mencari solusi dengan metode Gauss-Jordan
        for(int j=0; j<n; j++)
        {
            //Menukar baris dengan baris terakhir jika elemen[j][j] adalah 0
            if(intpol[j][j] == 0)
            {
                double temp[] = intpol[j];
                intpol[j] = intpol[n-1];
                intpol[n-1] = temp; 
            }

            //Membentuk leading 1
            double div = intpol[j][j];
            for(int k=0; k<n+1; k++)
            {
                intpol[j][k] = intpol[j][k]/div;
            }
            
            //Membentuk elemen matriks di atas dan di bawah leading 1 menjadi 0
            for(int i=0; i<n; i++)
            {
                double factor = intpol[i][j]/intpol[j][j];
                if(i!=j)
                {
                    for(int k=j; k<n; k++)
                    {
                        intpol[i][k] = intpol[i][k] - intpol[j][k]*factor;
                    }
                    intpol[i][n] = intpol[i][n] - intpol[j][n]*factor;
                }
            }
        }

        //Mengeluarkan persamaan polinom
        System.out.println("Persamaan polinom:");
        System.out.printf("%f", intpol[0][n]);
        for(int i=1; i<n; i++)
        {
            if(intpol[i][n] > 0)
            {
                if(i==1)
                {
                    System.out.printf("+%fx", intpol[i][n]);
                }
                else
                {
                    System.out.printf("+%fx^%d", intpol[i][n],i);
                }
            }
            else
            {
                if(i==1)
                {
                    System.out.printf("%fx", intpol[i][n]);
                }
                else
                {
                    System.out.printf("%fx^%d", intpol[i][n],i);
                }
            }
        }
        System.out.println("\n");

        //Estimasi nilai fungsi dengan masukan x
        System.out.println("Estimasi nilai fungsi: ");
        double result = 0;
        for(int i=0; i<n; i++)
        {
            result = result + intpol[i][n]*Math.pow(x, i);
        }
        System.out.println(result);
    }


    /* INPUT */
    public static void inputSPLKeyboard() {
        Scanner input = new Scanner(System.in);
        System.out.print("Jumlah persamaan = ");
        int m = input.nextInt();
        System.out.print("Jumlah peubah = ");
        int n = input.nextInt();
        double a[][] = new double[m][n];
        double b[] = new double[m];
        int i, j;
        // input matriks a
        System.out.println("Input matriks "+m+"x"+n+": ");
        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) {
                a[i][j] = input.nextDouble();
            }
        }
        // input array b
        System.out.println("Input b: ");
        for (i = 0; i < m; i++) {
            System.out.print("b[" + (i+1) + "]: ");
            b[i] = input.nextDouble();
        }
        System.out.println("Matriks augmented:");
        printMatriksAugmented(a, b);
    }
    /* OUTPUT */
    public static void printMatriks (double[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void printMatriksAugmented (double[][] a,double[] b) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.print(a[i][j]+" ");
            }
            System.out.print(b[i]);
            System.out.println();
        }
    }
    public static void printArray (double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println("x["+(i+1)+"]: "+arr[i]);
        }
    }
    
 public static void mainInverse (){
         // masukan ukuran matriks nxn
         Scanner input = new Scanner(System.in);

            
            System.out.println("1. Cari Matriks Balikan dengan Metode Ekspansi Kofaktor");
            System.out.println("2. Cari Matriks Balikan dengan Metode Gauss Jordan");
            System.out.println("Pilih Metode untuk Mencari Matriks Balikan: ");
            int pilihan = input.nextInt();
            System.out.println("Masukan dimensi dari matriks: ");
            int n = input.nextInt();
            System.out.println("\n");
           //definisikan matriks yang dibutuhkan
             double matrix[][]= new double[n][n];
            double adj[][]= new double[n][n];
            double inv[][]= new double[n][n];

             //input data matriks
            inputMatrix(input, matrix, n);
            System.out.println("\n");
 
            //Output Matriks yang diinput
             System.out.println("Matriks anda adalah: ");
             print(matrix, n);
            System.out.println("\n");
            
            System.out.println("\n");

            if (determinan(matrix, n) == 0){
                System.out.print("Matriks singular, tidak memiliki matriks balikan!");
            }else{
            
            if (pilihan == 1){
                System.out.print("Matriks balikan anda adalah: \n");
                // menentukan adjoint dari matriks
                adj = adjoint(matrix, adj, n);
 
                 // menentukan matriks balikan
                inv = matriksBalikan(matrix, n);
                print(inv, n);
            }else if (pilihan == 2){
                inv = gaussian(matrix, n);
                print(inv, n);
            }

        }
     
 
         System.out.println("\n");

         System.out.println("[Terima Kasih!]");
         System.out.println("\n");
 
    }

    public static double[][] matriksBalikan(double matrix[][], int n) { 
    

    // Mencari matriks adjoint
    double [][]adj = new double[n][n]; 
    adjoint(matrix, adj, n); 
  
    // Mencari matriks balikan dengan rumus [ (matrix)^-1 = adj(matrix) / det(matrix) ] 
    double [][]inverse = new double[n][n];
    for (int i = 0; i < n; i++) 
        for (int j = 0; j < n; j++) 
            inverse[i][j] = (adj[i][j] / determinan(matrix, n)); 
  
            return inverse; 
}  
    public static void inputMatrix(Scanner scan, double[][] matrix, int n){
        System.out.println("Masukan Data Matrix: ");
             
             for (int i = 0; i < n; i++)
             {
                 for (int j = 0; j < n; j++)
                 {
                     matrix[i][j] = scan.nextDouble();
                 }
             }
     }
     public static void print(double[][] matrix, int n){
        for (int i=0; i<n; ++i) 
        {
            for (int j=0; j<n; ++j)
            {
                System.out.print(matrix[i][j]+"  ");
            }
            System.out.println();
        }

     }

     public static double determinan(double matrix[][], int n) 
    { 
        double det = 10E-100; // supaya tidak error exception karena dibagi 0, inital det dijadikan 1 x 10^-100
      
        if (n == 1) 
            return matrix[0][0]; 
          
        // matriks temp untuk menyimpan kofaktor
        double[][] temp = new double[n][n];
          
        // untuk menyimpan pengali
        int sign = 1;  
      
        // iterasi elemen pada baris pertama
        for (int brs = 0; brs < n; brs++) 
        { 
            // mendapat kofaktor pada baris pertama, lalu mencari determinan secara rekursif
            kofaktor(matrix, temp, 0, brs, n); 
            det += sign * matrix[0][brs] * determinan(temp, n - 1); 
      
            // penukaran pengali untuk setiap pergantian
            sign = -sign; 
        } 
      
        return det ; 
    } 

    public static void kofaktor(double matrix[][], double temp[][], int p, int q, int n) { 
    int i = 0, j = 0; 

        for (int brs = 0; brs < n; brs++){ 
            for (int kol = 0; kol < n; kol++) { 
       
                // meyimpan elemen yang tidak ada di baris dan kolom tertentu pada matriks sementara
                if (brs != p && kol != q){ 
                    temp[i][j++] = matrix[brs][kol]; 

                    // jika baris terisi, menambah indeks baris dan mengulang kolom dari awal pada setip barisnya
                    if (j == n - 1){ 
                        j = 0; 
                        i++; 
         } 
     } 
 } 
} 
} 


    public static double[][] adjoint(double matrix[][],double [][]adj, int n) 
{  
    if (n == 1) { 
        adj[0][0] = 1; 
        } 
    int sign = 1; 
    double[][] temp = new double[n][n];
    
  
    for (int i = 0; i < n; i++) 
    { 
        for (int j = 0; j < n; j++) 
        { 
            // mencari bentuk matriks kofaktor
            kofaktor(matrix, temp, i, j, n); 
  
            // pengali dengan pola sesuai dengan ukuran matriks
            if ((i + j) % 2 == 0){
                sign = 1;}
            else{
                sign = -1;} 

            // mencari kofaktor entri dari matriks lalu men-transpose matriks kofaktor
            adj[j][i] = (sign)*(determinan(temp, n-1)); 
        } 
    } return adj;
} 
public static double[][] gaussian(double matrix[][], int n) {
    double[][] inverse = new double[n][n];
    
    // Membuat matriks inverse dengan elemen diagonal = 1 dan elemen lainnya = 0
    for (int i = 0; i < n; i++)
         {
             for (int j = 0; j < n; j++)
             {
                 if ( i == j){
                     inverse[i][j] = 1;
                 }else{
                     inverse[i][j] = 0;
                 }
             }           
}

//  Kemudian loop for dijalankan pada ukuran matriks input dan di dalam for loop kita telah memodifikasi matriks input 
// dan matriks invers pada setiap iterasi dengan membagi setiap elemen dengan elemen matriks tertentu.
for (int k = 0; k < n; k++)

         {  
            double temp ;
            temp = matrix[k][k];
             for (int j = 0; j < n; j++)
             {
                 matrix[k][j] /= temp;
                 inverse[k][j] /= temp;
        }
// Lalu loop dalam ini berjalan untuk ukuran matriks untuk setiap iterasi luar dan ini memodifikasi matriks input dan invers juga.
        for ( int i = 0; i < n; i++){
            temp = matrix[i][k];
            for (int j = 0; j < n; j++){
                if(i == k ) break;

                matrix[i][j] -= matrix[k][j]*temp;
                inverse[i][j] -= inverse[k][j]*temp;
            }
        }
    }
            return inverse;}
    
    /*BACA MATRIKS DARI FILE*/
    public static double[][] bacaFile(String namafile)
    {
        double result[][] = new double [100][100];
        try {
        /*Kamus*/
        String directory = "./data/" + namafile;
        File file = new File(directory);
        int row = 0;
        int column = 0;
        int i,j;

        Scanner matriks = new Scanner(file);

        /*Algoritma*/
        //Mendapatkan baris
        while (matriks.hasNextLine())
        {
            row++;
            matriks.nextLine();
        }
        matriks.close();

        matriks = new Scanner(file);
        Scanner line = new Scanner(matriks.nextLine());

        //Mendapatkan kolom
        while(line.hasNextDouble())
        {
            column++;
            line.nextDouble();
        }
        line.close();
        matriks.close();

        matriks = new Scanner(file);

        //Mengassign matriks
        result = new double [row][column];
        for(i=0; i<row; i++)
        {
            for(j=0; j<column; j++)
            {
                result[i][j] = matriks.nextDouble();
            }
        }
        matriks.close();
        } catch(FileNotFoundException e){
            System.err.printf("error");
        }
        return result;
    }
        public static void InverseSPL (double[][] A, double[] b) {
        int n=A.length;
        int i,j;
        if (detCofactor(A,n) != 0) {
            // mengubah dimensi array b menjadi matriks B, agar bisa melakukan perkalian matriks
            double[][]B = new double[n][1];
            int p=B.length;
            for (i=0;i<p;i++) {
                B[i][0]=b[i];
            }
            double[][] inverseA = matriksBalikan(A,n);
            double[][] matriksHasil = PerkalianMatriks(inverseA,B);
            System.out.println("Solusi: ");
            for (j=0;j<(matriksHasil.length);j++) {
                System.out.println("x["+(j+1)+"] = "+matriksHasil[j][0]);
            }
        } else { // jika detA nya 0, tidak bisa pakai metode invers
            System.out.println("Tidak dapat dihitung dengan metode Invers karena matriks tidak mempunyai balikan");
        }
    }

    public static double[][] PerkalianMatriks(double[][] M1, double[][] M2) {
        int barisM1 = M1.length;
        int kolomM1 = M1[0].length;
        int kolomM2 = M2[0].length;
        double[][] MHasil = new double[barisM1][kolomM2];
        int i, j, k;
        for (i = 0; i < (barisM1); i++) {
            for (j = 0; j < (kolomM2); j++) {
                for (k = 0; k < (kolomM1); k++) {
                    MHasil[i][j] += M1[i][k] * M2[k][j];
                }
            }
        }
        return (MHasil);
    }
    public static void MetodeKaidahCramer() {
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan nilai n = ");
        int n = input.nextInt();
        double a[][] = new double[n][n];
        double b[] = new double[n];
        int i, j;
        // input matriks a
        System.out.println("Input matriks "+n+"x"+n+": ");
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                a[i][j] = input.nextDouble();
            }
        }
        // input array b
        System.out.println("Input b: ");
        for (i = 0; i < n; i++) {
            System.out.print("b[" + (i+1) + "]: ");
            b[i] = input.nextDouble();
        }
        int var;
        if (detCofactor(a, n) != 0) {
            System.out.println("Solusi:");
            for (var = 0; var < n; var++) {
                System.out.print("x[" + (var + 1) + "] = ");
                Cramer(a, b, var);
            }
        } else { // jika detA nya 0
            System.out.println("Tidak dapat dihitung dengan Kaidah Cramer");
        }
        System.out.println("Keluar dari program...");
        System.exit(0);
    }
    public static void MetodeMatriksBalikan() {
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan nilai n = ");
        int n = input.nextInt();
        double A[][] = new double[n][n];
        double b[] = new double[n];
        int i, j;
        // input matriks a
        System.out.println("Input matriks "+n+"x"+n+": ");
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                A[i][j] = input.nextDouble();
            }
        }
        // input array b
        System.out.println("Input b: ");
        for (i = 0; i < n; i++) {
            System.out.print("b[" + (i+1) + "]: ");
            b[i] = input.nextDouble();
        }
        if (detCofactor(A,n) != 0) {
            // mengubah dimensi array b menjadi matriks B, agar bisa melakukan perkalian matriks
            double[][]B = new double[n][1];
            int p=B.length;
            for (i=0;i<p;i++) {
                B[i][0]=b[i];
            }
            double[][] inverseA = matriksBalikan(A,n);
            double[][] matriksHasil = PerkalianMatriks(inverseA,B);
            System.out.println("Solusi:");
            for (j=0;j<(matriksHasil.length);j++) {
                System.out.println("x["+(j+1)+"] = "+matriksHasil[j][0]);
            }
        } else { // jika detA nya 0, tidak bisa pakai metode invers
            System.out.println("Tidak dapat dihitung dengan metode Invers karena matriks tidak mempunyai balikan");
        }
        System.out.println("Keluar dari program...");
        System.exit(0);
    }
    public static void matriksHilbert() { // Digunakan untuk testing saja
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan nilai n = ");
        int n = input.nextInt();
        int i,j,k;
        double[][] H = new double[n][n];
        double[] b = new double[n];
        // Mengisi b
        b[0]=1;
        for (k=1;k<b.length;k++) {
            b[k]=0;
        }
        // Mengisi matriks Hilbert
        for (i=0;i<n;i++) {
            for (j=0;j<n;j++) {
                H[i][j]= 1.0/((i+1)+(j+1)-1.0);
            }
        }
        System.out.println("Matriks Hilbert dengan n = "+(n)+" beserta b:");
        printMatriksAugmented(H,b);
        //InverseSPL(H,b);
        /* Kaidah Cramer
        int var;
        if (detCofactor(H, n) != 0) {
            System.out.println("Solusi:");
            for (var = 0; var < n; var++) {
                System.out.print("x[" + (var + 1) + "] = ");
                Cramer(H, b, var);
            }
        } else { // jika detA nya 0
            System.out.println("Tidak dapat dihitung dengan Kaidah Cramer");
        }
        */
        /* GAUSS
        Gauss(H,b);
        printSolusiGauss(H,b);
         */
        /*
        GaussJordan(H,b);
        printSolusiGaussJordan(H,b);
         */
    }
        public static double[][] copyMatriks(double[][] Min) {
        int i, j;
        int m = Min.length;
        int n = Min[0].length;
        double[][] Mout = new double[m][n];
        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) {
                Mout[i][j] = Min[i][j];
            }
        }
        return Mout;
    }
        public static void Cramer(double[][] A, double[] b, int var) {
        double[][] temp = copyMatriks(A);
        int i;
        double detA = detCofactor(A, A.length);
        for (i = 0; i < temp.length; i++) {
            temp[i][var] = b[i];
        }
        double det_temp = detCofactor(temp, temp.length);
        System.out.println(det_temp / detA);
    }

}
