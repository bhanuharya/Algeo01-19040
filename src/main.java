import java.util.Scanner;

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
            //fungsi utama determinan
            break;

        case 3:
            //fungsi utama matriks balikan
            break;

        case 4: 
            //fungsi utama interpolasi polinom
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
/*Determinan dengan Ekspansi Kofaktor*/
public double detCofactor(double matrix[][], int n)
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

/*Determinan dengan Reduksi Baris/Metode Gauss*/
public double detGauss(double matrix[][], int n)
{
    //Membentuk matriks segitiga atas
    for (int j=0; j<n; j++)
    {
        for (int i=j+1; i<n; i++)
        {
            for (int k=j; k<n; k++)
            {
                matrix[i][k] = matrix[i-1][k] - matrix[i][k]*matrix[i-1][j]/matrix[i][j];
            }
        }
    }

    //Mengalikan elemen diagonal utama matriks
    double determinan = 1;
    for (int k=0; k<n; k++)
    {
        determinan *= matrix[k][k];
    }
    return determinan;
}

}
