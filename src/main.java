import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
            System.out.println("1. Metode Eliminasi Gauss");
            System.out.println("2. Metode Eliminasi Gauss-Jordan");
            System.out.println("3. Metode Matriks Balikan");
            System.out.println("4. Metode Kaidah Cramer");
            int metodeSPL = input.nextInt();
            if (metodeSPL==1) {
                // Gauss
            }
            else if (metodeSPL==2) {
                //Gauss Jordan
            }
            else if (metodeSPL==3) {
                MetodeMatriksBalikan();
            }
            else if (metodeSPL==4) {
                MetodeKaidahCramer();
            }
            else {
                System.out.println("Keluar dari program...");
                System.exit(0);
            }
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
                bacaFile(matrixInt, "interpolasi.txt");
                //Mendapatkan baris
                nInt = matrixInt.length;

                System.out.println("x:");
                x = input.nextDouble();
                intPol(matrixInt, nInt, x);
            }
            break;

        case 5:
            regresilinear();
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
            double determinan = 10E-100;;
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
        String persamaan = "Persamaan Polinom:\n";
        System.out.printf("%f", intpol[0][n]);
        persamaan += Double.toString(intpol[0][n]);
        for(int i=1; i<n; i++)
        {
            if(intpol[i][n] > 0)
            {
                if(i==1)
                {
                    System.out.printf("+%fx", intpol[i][n]);
                    persamaan = persamaan + "+" + Double.toString(intpol[i][n]) + "x";
                }
                else
                {
                    System.out.printf("+%fx^%d", intpol[i][n],i);
                    persamaan = persamaan + "+" + Double.toString(intpol[i][n]) + "x^" + Integer.toString(i);
                }
            }
            else
            {
                if(i==1)
                {
                    System.out.printf("%fx", intpol[i][n]);
                    persamaan = persamaan + Double.toString(intpol[i][n]) + "x";
                }
                else
                {
                    System.out.printf("%fx^%d", intpol[i][n],i);
                    persamaan = persamaan + Double.toString(intpol[i][n]) + "x^" + Integer.toString(i);
                }
            }
        }
        System.out.println("\n");
        persamaan = persamaan + "\n";

        //Estimasi nilai fungsi dengan masukan x
        System.out.println("Estimasi nilai fungsi: ");
        String hasil = "Estimasi nilai fungsi:\n";
        double result = 0;
        for(int i=0; i<n; i++)
        {
            result = result + intpol[i][n]*Math.pow(x, i);
        }
        System.out.println(result);
        hasil = hasil + Double.toString(result);

        //Print to file
        TulisFileIntPol(persamaan, hasil);
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
        if (determinan(matrix, n) == 0){
           System.out.print("Matriks singular, tidak memiliki matriks balikan!");
       }else{

           
           System.out.println("1. Cari Matriks Balikan dengan Metode Ekspansi Kofaktor");
           System.out.println("2. Cari Matriks Balikan dengan Metode Gauss Jordan");
           System.out.println("Pilih Metode untuk Mencari Matriks Balikan: ");

           int pilihan = input.nextInt();
           System.out.println("\n");
           
           if (pilihan == 1){
               System.out.print("Matriks balikan anda adalah: \n");
               // menentukan adjoint dari matriks
               adj = adjoint(matrix, adj, n);

                // menentukan matriks balikan
               inv = matriksBalikan(matrix, n);

               
           System.out.println("\n");
           System.out.println("1. print ke file.txt");
           System.out.println("2. print ke console");
           int output1 = input.nextInt();

           if (output1 == 1){TulisFileMatriks(inv, n);}
               else if (output1 == 2){print(inv, n);}
               
               
           }else if (pilihan == 2){
               inv = gaussian(matrix, n);
               System.out.println("1. cetak ke file external");
               System.out.println("2. cetak di console");
               int output2 = input.nextInt();
    
               if (output2 == 1){TulisFileMatriks(inv, n);}
                   else if (output2 == 2){print(inv, n);}
                   
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
    public static void bacaFile(double matrix[][], String namafile)
    {
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
        matrix = new double [row][column];
        for(i=0; i<row; i++)
        {
            for(j=0; j<column; j++)
            {
                matrix[i][j] = matriks.nextDouble();
            }
        }
        matriks.close();
        } catch(FileNotFoundException e){
            System.err.printf("error\n");
        }
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
        System.out.println("[Metode Kaidah Cramer]");
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
            double solusiCramer[]= new double [n];
            for (var = 0; var < n; var++) {
                solusiCramer[var] = Cramer(a, b, var);
            }
            printArray(solusiCramer);
            System.out.println("Simpan hasil?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            int YN = input.nextInt();
            if (YN==1) {
                TulisFileArray(solusiCramer,n);
            }
            else if (YN==2) {
                System.out.println("Selesai");
            }
        } else { // jika detA nya 0
            System.out.println("Tidak dapat dihitung dengan Kaidah Cramer");
        }
        System.out.println("Keluar dari program...");
        System.exit(0);
    }
    public static void MetodeMatriksBalikan() { 
        System.out.println("[Metode Matriks Balikan]");
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
            System.out.println("Simpan hasil?");
            System.out.println("1. Ya");
            System.out.println("2. Tidak");
            int YN = input.nextInt();
            if (YN==1) {
                TulisFileMatriksMN(matriksHasil,matriksHasil.length,matriksHasil[0].length);
            }
            else if (YN==2) {
                System.out.println("Selesai");
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
    public static double Cramer(double[][] A, double[] b, int var) {
        double[][] temp = copyMatriks(A);
        int i;
        double detA = detCofactor(A, A.length);
        for (i = 0; i < temp.length; i++) {
            temp[i][var] = b[i];
        }
        double det_temp = detCofactor(temp, temp.length);
        return(det_temp/detA);
    }

// print to file
    public static void TulisFileMatriks(double[][] matrix, int n){
        Scanner input = new Scanner(System.in);
        System.out.print("Nama File: ");
        String namaFile = input.nextLine();
        try {
            FileWriter fileWriter = new FileWriter(namaFile);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (int i=0;i<n;i++){
                // System.out.printf("Arr[%3d]: |",i);
                for (int j=0;j<n;j++){
                    printWriter.print(matrix[i][j]+"  ");
                }
                // System.out.println("|");
                printWriter.println();
            }
            printWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /*PRINT HASIL INTERPOLASI POLINOM KE FILE*/
    public static void TulisFileIntPol(String persamaan, String hasil)
    {
        Scanner input = new Scanner(System.in);
        System.out.print("Nama File: ");
        String namaFile = input.nextLine();
        try {
            FileWriter fileWriter = new FileWriter(namaFile);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            
            printWriter.println(persamaan);
            printWriter.println(hasil);
            
            printWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
        /* Print solusi SPL ke file */
    public static void TulisFileMatriksMN(double[][] matrix, int m, int n){
        Scanner input = new Scanner(System.in);
        System.out.print("Nama File: ");
        String namaFile = input.nextLine();
        try {
            FileWriter fileWriter = new FileWriter(namaFile);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (int i=0;i<m;i++){
                for (int j=0;j<n;j++){
                    printWriter.print(matrix[i][j]+"  ");
                }
                printWriter.println();
            }
            printWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void TulisFileArray (double[] arr, int n) {
        Scanner input = new Scanner(System.in);
        System.out.print("Nama File: ");
        String namaFile = input.nextLine();
        try {
            FileWriter fileWriter = new FileWriter(namaFile);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (int i=0;i<n;i++){
                printWriter.println(arr[i]);
            }
            printWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void regresilinear() 
    {  
    Scanner input = new Scanner(System.in);
    int nb = 20;
    int nk = 4;
 
    double X[][] = new double[100][100];
    double Y[] = new double[100];
    double Aug[][] = new double[nb][nk+1];

    double M[][] = {{72.4, 76.3, 29.18, 0.9},
                    {41.6, 70.3, 29.35, 0.91},
                    {34.3, 77.1, 29.24, 0.96},
                    {35.1, 68.0, 29.27, 0.89},
                    {10.7, 79.0, 29.78, 1.00},
                    {12.9, 67.4, 29.39, 1.10},
                    {8.3, 66.8, 29.69, 1.15},
                    {20.1, 76.9, 29.48, 1.03},
                    {72.2, 77.7, 29.09, 0.77},
                    {24.0, 67.7, 29.6, 1.07},
                    {23.2, 76.8, 29.38, 1.07},
                    {47.4, 86.6, 29.35, 0.94},
                    {31.5, 76.9, 29.63, 1.10},
                    {10.6, 86.3, 29.56, 1.10},
                    {11.2, 86.0, 29.48, 1.10},
                    {73.3, 76.3, 29.40, 0.91},
                    {75.4, 77.9, 29.28, 0.87},
                    {96.6, 78.7, 29.29, 0.78},
                    {107.4, 86.8, 29.03, 0.82},
                    {54.9, 70.9, 29.37, 0.95}};

    for (int i = 0; i< nb; i++){
        for (int j = 0; j < nk; j++){
            if (j==0){ 
                X[i][j] = 1;
                
            }else if ( j== nk -1){
                Y[i] = M[i][j];
                X[i][j] = M[i][j-1];
            
            }
            else{
                X[i][j] = M[i][j-1];
            }
        }
    }
   int brs = nb;
    int kol = nk +1;

    

    for(int i =0; i<brs;i++) {
        for(int j=0;j<nk ;j++) 
             Aug[i][j] = X[i][j];    
       }

       for(int i =0; i<nb;i++) {
           Aug[i][nk] = Y[i] ;
       }
    
    
    
    System.out.println();

    normalEq(Aug, X, Y, brs, kol, nb, nk);

    }


    
    public static void printY(double[] matrix, int n){
        
             
        for (int i = 0; i < n; i++)
        {
           System.out.print(matrix[i]+"  ");
        }
}
 
    public static void inputMatrix(Scanner scan, double[][] matrix, int nb, int nk){
        System.out.println("Masukan Data Matrix: ");
             
             for (int i = 0; i < nb; i++)
             {
                 for (int j = 0; j < nk; j++)
                 {
                     matrix[i][j] = scan.nextDouble();
                 }
             }
     }

     public static void print(double[][] matrix, int nb, int nk){
        for (int i=0; i<nb; ++i) 
        {
            for (int j=0; j<nk; ++j)
            {
                System.out.print(matrix[i][j]+"  ");
            }
            System.out.println();
        }

     }
     
    public static void normalEq(double aug[][], double X[][], double Y[],  int brs, int kol, int nb, int nk){
        
        int brss = brs + 1;
        double[][] temp = new double[100][100];
        double[][] xtemp = new double[nk][nk];
        double[]   ytemp = new double[nk];
        double[][] hasil = new double[nk][nk+1];
        int i,j;
      for (i=0; i< brss; i++){
          for(j=0;j<kol;j++){
              if (i== 0 && j == 0){
                  temp[i][j] = nb ;

              }
          else if ( j == kol- 1){
              if (i == 0){
                  temp[i][j] = sumupY(Y, brs);
              }else{
                  temp[i][j] = sumupXY(X, Y, brs, kol, nb, i);
              }
          }else{
              if ( i == 0){
                  temp[i][j] = sumupX(X, brs, kol, nb, j);
              }else if (j==0){
                  temp[i][j] = sumupX(X, brs,  kol,  nb, i);
              }else{
                  temp[i][j] = sumupXX(X,  nb,  kol, i, j);
              }
          }
        }

        
    }
    
    for ( i = 0; i < nk; i++){
        for( j= 0; j < nk + 1; j ++){
            hasil[i][j] = temp[i][j];
        }

    }
    System.out.println("Bentuk normal equation for multiple linear regression :");
    System.out.println();
    print(hasil, nk, nk+1);
    System.out.println();

    int val = 0;
    val = solvespl(hasil, nk); 

    if (val == 1)      
        val = checksol(hasil, nk, val);
        cetaksolusi(hasil, nk, val); 

     
    }
   
     public static double  sumupX(double[][] X, int brs, int kol, int nb, int nk){
        double count = 0.0;
        
        for(int i = 0; i < nb; i++){
            count += X[i][nk] ;

        }
        return count;
    }

    public static double  sumupXX(double[][] X, int brs, int kol, int nb, int nk){
        double count = 0.0;
        
        for(int i = 0; i < brs ; i++){
            count += X[i][nb]*X[i][nk] ;

        } 
        return count;
    }


     public static double  sumupY(double[] y, int nb) {
         double count = 0.0;
         for (int i = 0; i < nb; i++) {
             count += y[i];
         }
         return count;
     }

     public static double  sumupXY(double[][] X, double[] y, int brs, int kol, int nb, int nk) {
        double count = 0.0;
        for (int i = 0; i < nb; i++) {
            count += (X[i][nk] * y[i]) ;
        }
        return count;
    }

    public static int solvespl(double a[][], int nk) 
{ 
    int i, j, k = 0, c, val = 0;
  
    for (i = 0; i < nk; i++) 
    { 
        if (a[i][i] == 0)  
        { 
            c = 1; 
            while ((i + c) < nk && a[i + c][i] == 0)  
                c++;          
            if ((i + c) == nk)  
            { 
                val = 1; 
                break; 
            } 
            for (j = i, k = 0; k <= nk; k++)  
            { 
                double temp =a[j][k]; 
                a[j][k] = a[j+c][k]; 
                a[j+c][k] = temp; 
            } 
        } 
  
        for (j = 0; j < nk; j++)  
        { 
              
            if (i != j)  
            { 
                  
                double p = a[j][i] / a[i][i]; 
  
                for (k = 0; k <= nk; k++)                  
                    a[j][k] = a[j][k] - (a[i][k]) * p;              
            } 
        } 
    } 
    return val; 
} 
public static int checksol(double a[][], int nK, int val) 
{ 
    int i, j; 
    double sum; 
      

    val = 3; 
    for (i = 0; i < nK; i++)  
    { 
        sum = 0; 
        for (j = 0; j < nK; j++)      
            sum = sum + a[i][j]; 
        if (sum == a[i][j])  
            val = 2;      
    } 
    return val; 
} 

public static void cetaksolusi(double a[][], int nk, int val) 
{ Scanner input = new Scanner(System.in);
    System.out.print("hasilnya adalah: "); 
    double[] hasil = new double[nk];
    if (val == 2) {   
    System.out.println("Solusi tak hingga");  }
    else if (val == 3)   {   
    System.out.println("tidak ada solusi"); }
    
    else { 
        for (int i = 0; i < nk; i++) 
                    
            hasil[i] = a[i][nk] / a[i][i] ;      
    } 

    for (int i = 0; i < nk; i++) {
                    
            System.out.print("B" + (i) + " = " +hasil[i] + " " );      
    } 
double x1, x2, x3, y, pilihan;
System.out.println("Prediksi dengan data");
System.out.println("y = B0 + B1x11 + B2x21 + B3x31");
System.out.println();

System.out.println("Masukan nilai humidity :");
x1 = input.nextDouble();
System.out.println("Masukan nilai temperature :");
x2 = input.nextDouble();
System.out.println("Masukan nilai pressure :");
x3 = input.nextDouble();
y = hasil[0] + (x1*hasil[1]) + (x2*hasil[2] + (x3*hasil[3]));
System.out.println("Nilai Prediksi adalah:");

System.out.println(y);
System.out.println();
System.out.println("Apakah anda ingin mencetak hasil prediksi ke file.txt ?");
System.out.println("1. Ya");
System.out.println("2. Tidak");
pilihan = input.nextDouble();
if ( pilihan == 1){
    TulisFileArray(hasil, nk);}
    else{System.out.println("baik");}
    
}


} 

