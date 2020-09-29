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
}
