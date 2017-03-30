import java.util.Scanner;

public class Menu {
	
	private Scanner input;
	
	public Menu(){
		menuChoice();
	}

	/**
	 * call methods based on the choice selected by the user
	 */
	public void menuChoice(){
		int option = menu();
		while(option != 0){
			switch(option){
				case 1: 
					encode();
					break;
				case 2:
					decode();
					break;
				default: 
					System.out.println("Invalid option selected");
					break;
			}
			option=menu();
		}
		option = menu();
	}
	
	/**
	 * Display the options for the user 
	 * @return the number of the choice selected
	 */
	public int menu(){
		input = new Scanner(System.in);
		System.out.println("Please select an option");
		System.out.println("1. Encode a File");
		System.out.println("2. Decode a File");
		
		int option = input.nextInt();
		return option;
	}
	
	public void encode(){
		Loader loader = new Loader();
	}
	
	public void decode(){
		Decoder decoder = new Decoder();
	}
	
	public static void main(String[] args) {
		//Loader loader = new Loader();
		//Decoder decoder = new Decoder();
		Menu menu = new Menu();
	}
}
